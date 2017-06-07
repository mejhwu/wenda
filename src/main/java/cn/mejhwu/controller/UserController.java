package cn.mejhwu.controller;

import cn.mejhwu.bo.EntityType;
import cn.mejhwu.bo.HostHolder;
import cn.mejhwu.model.QuestionDO;
import cn.mejhwu.model.UserDO;
import cn.mejhwu.service.*;
import cn.mejhwu.vo.UserQuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/30
 * Time:   21:49
 * Description:
 */

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    FeedService feedService;

    @RequestMapping("/user/{id}")
    public String userHome(Model model, @PathVariable("id") int id) {
        UserDO user = userService.getUserById(id);
        model.addAttribute("profileUser", user);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followed", followService.isFollower(
                    hostHolder.getUser().getId(), id, EntityType.ENTITY_USER));
        }

        model.addAttribute("followerCount", followService.getFollowerCount(id, EntityType.ENTITY_USER));
        model.addAttribute("followeeCount", followService.getFolloweeCount(id, EntityType.ENTITY_USER));
        model.addAttribute("vos", feedService.timeline(id));

        return "profile";
    }

}
