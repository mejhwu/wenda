package cn.mejhwu.async.handler;

import cn.mejhwu.async.EventHandler;
import cn.mejhwu.async.EventModel;
import cn.mejhwu.async.EventType;
import cn.mejhwu.model.FeedDO;
import cn.mejhwu.model.MessageDO;
import cn.mejhwu.model.QuestionDO;
import cn.mejhwu.model.UserDO;
import cn.mejhwu.service.FeedService;
import cn.mejhwu.service.MessageService;
import cn.mejhwu.service.QuestionService;
import cn.mejhwu.service.UserService;
import cn.mejhwu.util.WendaUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   21:25
 * Description:
 */

@Component
public class FeedHandler implements EventHandler {

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<>();
        UserDO actor = userService.getUserById(model.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());
        if (model.getType() == EventType.COMMENT) {
            QuestionDO question = questionService.getQuestionById(model.getEntityOwnerId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandler(EventModel eventModel) {

        FeedDO feed = new FeedDO();
        feed.setCreatedDate(new Date());
        feed.setActorId(eventModel.getActorId());
        feed.setEntityType(eventModel.getEntityType());
        feed.setEntityId(eventModel.getEntityId());
        feed.setEntityOwnerId(eventModel.getEntityOwnerId());
        feed.setData(buildFeedData(eventModel));
        if (feed.getData() == null) {
            return ;
        }

        feedService.saveFeed(feed);

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.COMMENT);
    }
}
