package cn.mejhwu.controller;

import cn.mejhwu.bo.HostHolder;
import cn.mejhwu.model.MessageDO;
import cn.mejhwu.model.UserDO;
import cn.mejhwu.service.MessageService;
import cn.mejhwu.service.UserService;
import cn.mejhwu.util.WendaUtils;
import cn.mejhwu.vo.UserMessage;
import cn.mejhwu.vo.ViewObjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   14:06
 * Description:
 */

@Controller
@RequestMapping("/msg")
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(String toName, String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtils.getJSONString(999, "未登陆");
            }

            UserDO toUser = userService.getUserByName(toName);
            if (toUser == null) {
                return WendaUtils.getJSONString(1, "用户不存在");
            }

            MessageDO message = new MessageDO();
            message.setContent(content);
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(toUser.getId());
            message.setHasRead(1);
            message.setCreatedDate(new Date());
            message.setConversationId();
            messageService.saveMessage(message);
            return WendaUtils.getJSONString(0);
        } catch (Exception e) {
            logger.error("发送消息失败: " + e.getMessage());
            return WendaUtils.getJSONString(1);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String conversation(Model model) {
        if (hostHolder.getUser() == null) {
            return "redirect:/loginPage";
        }

        int userId = hostHolder.getUser().getId();
        List<MessageDO> messageList = messageService.listConversationByUserId(userId);
        List<ViewObjectVO> conversations = new ArrayList<>();
        for (MessageDO message : messageList) {
            ViewObjectVO vo = new ViewObjectVO();
            vo.put("message", message);
            vo.put("user", userService.getUserById(message.getFromId()));
            vo.put("notReadCount", messageService.countNotReadMessage(
                    message.getConversationId(), hostHolder.getUser().getId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String conversationDetail(Model model, String conversationId) {
        try {
            List<MessageDO>  messageList = messageService.listMessageByConversationId(conversationId);
            List<UserMessage> messages = new ArrayList<>();
            for (MessageDO message : messageList) {
                UserMessage vo = new UserMessage();
                UserDO user = userService.getUserById(message.getFromId());
                vo.setMessage(message);
                vo.setUser(user);
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取详情失败: " + e.getMessage());
        }
        return "letterDetail";
    }
}
