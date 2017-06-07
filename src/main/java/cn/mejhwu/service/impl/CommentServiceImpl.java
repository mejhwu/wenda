package cn.mejhwu.service.impl;

import cn.mejhwu.dao.CommentDao;
import cn.mejhwu.dao.QuestionDao;
import cn.mejhwu.model.CommentDO;
import cn.mejhwu.service.CommentService;
import cn.mejhwu.service.QuestionService;
import cn.mejhwu.service.SensitivieService;
import cn.mejhwu.vo.ViewObjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   11:54
 * Description:
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    SensitivieService sensitivieService;

    @Autowired
    QuestionService questionService;

    @Override
    public List<CommentDO> listCommentByEntity(int entityId, int entityType) {
        return commentDao.listCommentByEntity(entityId, entityType);
    }

    @Override
    @Transactional
    public int saveComment(int questionID, CommentDO comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitivieService.filter(comment.getContent()));
        questionService.updateCommentCount(questionID, questionService.getQuestionById(questionID).getCommentCount()+1);
        return commentDao.saveComment(comment);
    }

    @Override
    public int countCommentByEntity(int entityId, int entityType) {
        return commentDao.countCommentByEntity(entityId, entityType);
    }

    @Override
    public int removeCommentById(int id) {
        return commentDao.updateCommentStatus(id, 1);
    }

    @Override
    public CommentDO getCommentById(int id) {
        return commentDao.getCommentById(id);
    }

    public CommentDO getComment(int userId, int entityId, int entityType) {
        return commentDao.getComment(userId, entityId, entityType);
    }

}
