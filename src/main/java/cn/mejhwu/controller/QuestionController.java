package cn.mejhwu.controller;

import cn.mejhwu.bo.EntityType;
import cn.mejhwu.bo.HostHolder;
import cn.mejhwu.dao.UserDao;
import cn.mejhwu.model.CommentDO;
import cn.mejhwu.model.QuestionDO;
import cn.mejhwu.model.UserDO;
import cn.mejhwu.service.*;
import cn.mejhwu.util.WendaUtils;
import cn.mejhwu.vo.UserCommentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
 * Date:   2017/5/21
 * Time:   18:31
 * Description:
 */

@Controller
@RequestMapping("/question")
public class QuestionController {

    private final static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;


    @Autowired
    FollowService followService;

    @Autowired
    LikeService likeService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(String title, String content) {
        try {
            QuestionDO question = new QuestionDO();
            question.setContent(content);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if (hostHolder.getUser() == null) {
                //question.setUserId(WendaUtils.ANONYMITY_USER_ID);
                return WendaUtils.getJSONString(999);
            } else {
                question.setUserId(hostHolder.getUser().getId());
            }

            if (questionService.add(question) > 0) {
                return WendaUtils.getJSONString(0);
            }

        } catch (Exception e) {
            logger.error("增加问题失败: " + e.getMessage());
        }
        return WendaUtils.getJSONString(1, "失败");
    }

    @RequestMapping(value = "/{qid}", method = RequestMethod.GET)
    public String questionDetail(Model model, @PathVariable("qid") int qid) {

        QuestionDO question = questionService.getQuestionById(qid);
        model.addAttribute("question", question);
        UserDO questionUser = userService.getUserById(question.getUserId());
        model.addAttribute("user", questionUser);
        model.addAttribute("followerCount", followService.getFollowerCount(qid, EntityType.ENTITY_QUESTION));
        if (hostHolder.getUser() != null) {
            model.addAttribute("isFollow", followService.isFollower(
                    hostHolder.getUser().getId(), qid, EntityType.ENTITY_QUESTION));
        }

        List<CommentDO> commentList = commentService.listCommentByEntity(qid, EntityType.ENTITY_QUESTION);
        List<UserCommentVO> comments = new ArrayList<>();

        for (CommentDO comment : commentList) {
            UserDO commentUser = userService.getUserById(comment.getUserId());
            UserCommentVO vo = new UserCommentVO();
            vo.setUser(commentUser);
            vo.setComment(comment);
            vo.setLikeCount((int)likeService.countLike(comment.getId(), EntityType.ENTITY_COMMENT));
            comments.add(vo);
        }
        model.addAttribute("comments", comments);

        return "detail";
    }

}
