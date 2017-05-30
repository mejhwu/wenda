package cn.mejhwu.service.impl;

import cn.mejhwu.bo.EntityType;
import cn.mejhwu.dao.QuestionDao;
import cn.mejhwu.dao.UserDao;
import cn.mejhwu.model.QuestionDO;
import cn.mejhwu.service.CommentService;
import cn.mejhwu.service.QuestionService;
import cn.mejhwu.service.SensitivieService;
import cn.mejhwu.vo.UserQuestionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/21
 * Time:   19:21
 * Description:
 */

@Service
public class QuestionServiceImpl implements QuestionService {

    private final static Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    QuestionDao questionDao;

    @Autowired
    UserDao userDao;

    @Autowired
    SensitivieService sensitivieService;

    @Autowired
    CommentService commentService;

    @Override
    public int add(QuestionDO question) {
        //敏感词过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));

        question.setTitle(sensitivieService.filter(question.getTitle()));
        question.setContent(sensitivieService.filter(question.getContent()));
        return questionDao.saveQuestionDao(question) > 0 ? question.getId() : 0;
    }

    public List<UserQuestionVO> listQuestionOrderByDate(int offset, int limit) {
        List<UserQuestionVO> vos = new ArrayList<>();

        List<QuestionDO> questionList = questionDao.listQuestionOrderByDate(offset, limit);
        for (QuestionDO question : questionList) {
            UserQuestionVO vo = new UserQuestionVO();
            vo.setQuestion(question);
            vo.setUser(userDao.getUserById(question.getUserId()));
            vo.setCommentCount(commentService.countCommentByEntity(
                    question.getId(), EntityType.ENTITY_QUESTION));
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public QuestionDO getQuestionById(int id) {
        return questionDao.getQuestionById(id);
    }
}
