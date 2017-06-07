package cn.mejhwu.async.handler;

import cn.mejhwu.async.EventHandler;
import cn.mejhwu.async.EventModel;
import cn.mejhwu.async.EventType;
import cn.mejhwu.model.MessageDO;
import cn.mejhwu.model.QuestionDO;
import cn.mejhwu.model.UserDO;
import cn.mejhwu.service.MessageService;
import cn.mejhwu.service.QuestionService;
import cn.mejhwu.service.UserService;
import cn.mejhwu.util.WendaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   21:25
 * Description:
 */

@Component
public class FollowHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Override
    public void doHandler(EventModel eventModel) {
        MessageDO message = new MessageDO();
        message.setFromId(WendaUtils.SYSTEM_USER_ID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setCreatedDate(new Date());

        UserDO user = userService.getUserById(eventModel.getActorId());
        message.setContent("用户<a href=\"/user/" + user.getId() + "\">" +
                            user.getName() + "</a>关注了你");
        message.setConversationId();
        message.setHasRead(1);
        messageService.saveMessage(message);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
