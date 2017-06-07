package cn.mejhwu.vo;

import cn.mejhwu.model.QuestionDO;
import cn.mejhwu.model.UserDO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/10
 * Time:   22:37
 * Description:
 */
public class UserQuestionVO {
    private QuestionDO question;
    private UserDO user;
    private int commentCount;
    private boolean followed;

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public QuestionDO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDO question) {
        this.question = question;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }
}
