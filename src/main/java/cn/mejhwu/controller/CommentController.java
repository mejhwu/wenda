package cn.mejhwu.controller;

import cn.mejhwu.async.EventModel;
import cn.mejhwu.async.EventProducer;
import cn.mejhwu.async.EventType;
import cn.mejhwu.bo.EntityType;
import cn.mejhwu.bo.HostHolder;
import cn.mejhwu.model.CommentDO;
import cn.mejhwu.service.CommentService;
import cn.mejhwu.util.WendaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   12:07
 * Description:
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addComment(int questionId, String content) {
        try {
            CommentDO comment = new CommentDO();
            comment.setContent(content);
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtils.ANONYMITY_USER_ID);
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            comment.setStatus(0);
            commentService.saveComment(comment);

            //发出评论事件
            EventModel model = new EventModel();
            model.setType(EventType.COMMENT).setActorId(hostHolder.getUser().getId());
            model.setEntityId(questionId).setEntityType(EntityType.ENTITY_QUESTION);
            eventProducer.fireEvent(model);

        } catch (Exception e) {
            logger.error("增加评论失败: " + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }

}
