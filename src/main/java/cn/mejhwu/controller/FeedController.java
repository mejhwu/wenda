package cn.mejhwu.controller;

import cn.mejhwu.bo.HostHolder;
import cn.mejhwu.service.FeedService;
import cn.mejhwu.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/27
 * Time:   21:10
 * Description:
 */

@Controller
public class FeedController {

    @Autowired
    FeedService feedService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    FollowService followService;

    @RequestMapping(value = "/pullfeeds", method = RequestMethod.GET)
    public String pullFeeds(Model model) {
        int localUserId = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();
//        feedService.listUserFeeds();
        return null;
    }

}
