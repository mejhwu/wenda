package cn.mejhwu.vo;

import cn.mejhwu.model.CommentDO;
import cn.mejhwu.model.UserDO;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   12:41
 * Description:
 */
public class UserCommentVO {
    private UserDO user;
    private CommentDO comment;
    private int commentCount;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

    public CommentDO getComment() {
        return comment;
    }

    public void setComment(CommentDO comment) {
        this.comment = comment;
    }
}
