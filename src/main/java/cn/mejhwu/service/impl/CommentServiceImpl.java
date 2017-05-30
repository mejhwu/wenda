package cn.mejhwu.service.impl;

import cn.mejhwu.dao.CommentDao;
import cn.mejhwu.model.CommentDO;
import cn.mejhwu.service.CommentService;
import cn.mejhwu.service.SensitivieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    @Override
    public List<CommentDO> listCommentByEntity(int entityId, int entityType) {
        return commentDao.listCommentByEntity(entityId, entityType);
    }

    @Override
    public int saveComment(CommentDO comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitivieService.filter(comment.getContent()));
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

    public CommentDO getCommentById(int id) {
        return commentDao.getCommentById(id);
    }
}
