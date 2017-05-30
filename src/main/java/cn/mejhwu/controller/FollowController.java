package cn.mejhwu.controller;

import cn.mejhwu.async.EventModel;
import cn.mejhwu.async.EventProducer;
import cn.mejhwu.async.EventType;
import cn.mejhwu.bo.EntityType;
import cn.mejhwu.bo.HostHolder;
import cn.mejhwu.service.FollowService;
import cn.mejhwu.service.UserService;
import cn.mejhwu.util.WendaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/26
 * Time:   19:36
 * Description:
 */

@Controller
public class FollowController {

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = "/followUser", method = RequestMethod.POST)
    @ResponseBody
    public String followUser(int userId) {
        if (hostHolder.getUser() == null) {
            return WendaUtils.getJSONString(999);
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(),
                                            userId, EntityType.ENTITY_USER);

        if (ret) {

            EventModel eventModel = new EventModel();
            eventModel.setType(EventType.FOLLOW).setActorId(hostHolder.getUser().getId());
            eventModel.setEntityId(userId).setEntityType(EntityType.ENTITY_USER);
            eventModel.setEntityOwnerId(userId);
            eventProducer.fireEvent(eventModel);


            return WendaUtils.getJSONString(0, String.valueOf(
                    followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
        }

        return WendaUtils.getJSONString(1);
    }

    @RequestMapping(value = "/unfollowUser", method = RequestMethod.POST)
    @ResponseBody
    public String unfollowUser(int userId) {
        if (hostHolder.getUser() == null) {
            return WendaUtils.getJSONString(999);
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(),
                userId, EntityType.ENTITY_USER);

        if (ret) {

            EventModel eventModel = new EventModel();
            eventModel.setType(EventType.UNFOLLOW).setActorId(hostHolder.getUser().getId());
            eventModel.setEntityId(userId).setEntityType(EntityType.ENTITY_USER);
            eventModel.setEntityOwnerId(userId);
            eventProducer.fireEvent(eventModel);


            return WendaUtils.getJSONString(0, String.valueOf(
                    followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
        }

        return WendaUtils.getJSONString(1);
    }

    @RequestMapping(value = "/followQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String followQuestion(int questionId) {
        if (hostHolder.getUser() == null) {
            return WendaUtils.getJSONString(999);
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(),
                questionId, EntityType.ENTITY_QUESTION);
        String msg = String.valueOf(followService.getFollowerCount(questionId,  EntityType.ENTITY_QUESTION));
        return WendaUtils.getJSONString(ret ? 0 : 1, msg);
    }

    @RequestMapping(value = "/unfollowQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String unfollowQuestion(int questionId) {
        if (hostHolder.getUser() == null) {
            return WendaUtils.getJSONString(999);
        }

        boolean ret = followService.unfollow(hostHolder.getUser().getId(),
                questionId, EntityType.ENTITY_QUESTION);

        return WendaUtils.getJSONString(ret ? 0 : 1,
                String.valueOf(followService.getFollowerCount(questionId,  EntityType.ENTITY_USER)));
    }

}
