package cn.mejhwu.dao;

import cn.mejhwu.model.CommentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/21
 * Time:   19:07
 * Description:
 */
public interface CommentDao {

    int saveComment(CommentDO comment);

    List<CommentDO> listCommentByEntity(@Param("entityId") int entityId,
                                        @Param("entityType") int entityType);

    int countCommentByEntity(@Param("entityId") int entityId,
                             @Param("entityType") int entityType);

    int updateCommentStatus(@Param("id") int id, @Param("status") int status);

    CommentDO getCommentById(int id);

    CommentDO getComment(@Param("userId") int userId,
                         @Param("entityId") int entityId,
                         @Param("entityType") int entityType);

}
