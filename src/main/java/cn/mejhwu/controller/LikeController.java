package cn.mejhwu.controller;

import cn.mejhwu.async.EventModel;
import cn.mejhwu.async.EventProducer;
import cn.mejhwu.async.EventType;
import cn.mejhwu.bo.EntityType;
import cn.mejhwu.bo.HostHolder;
import cn.mejhwu.model.CommentDO;
import cn.mejhwu.service.CommentService;
import cn.mejhwu.service.LikeService;
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
 * Date:   2017/5/25
 * Time:   19:26
 * Description:
 */

@Controller
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtils.getJSONString(999, "用户未登陆");
        }

        CommentDO comment = commentService.getCommentById(commentId);

        EventModel eventModel = new EventModel();
        eventModel.setType(EventType.LIKE).setActorId(hostHolder.getUser().getId());
        eventModel.setEntityId(commentId).setEntityType(EntityType.ENTITY_COMMENT);
        eventModel.setEntityOwnerId(comment.getUserId());
        eventProducer.fireEvent(eventModel);

        long likeCount = likeService.like(hostHolder.getUser().getId(),
                                commentId, EntityType.ENTITY_COMMENT);
        return WendaUtils.getJSONString(0, String.valueOf(likeCount));
    }


    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public String disLike(int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtils.getJSONString(999, "用户未登陆");
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(),
                commentId, EntityType.ENTITY_COMMENT);
        return WendaUtils.getJSONString(0, String.valueOf(likeCount));
    }
}
