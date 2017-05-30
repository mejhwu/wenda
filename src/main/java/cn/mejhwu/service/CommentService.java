package cn.mejhwu.service;

import cn.mejhwu.model.CommentDO;

import javax.xml.stream.events.Comment;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   11:15
 * Description:
 */
public interface CommentService {

    List<CommentDO> listCommentByEntity(int entityId, int entityType);

    int saveComment(CommentDO comment);

    int countCommentByEntity(int entityId, int entityType);

    int removeCommentById(int id);
    CommentDO getCommentById(int id);
}
