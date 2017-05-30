package cn.mejhwu.vo;

import cn.mejhwu.model.MessageDO;
import cn.mejhwu.model.UserDO;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   14:44
 * Description:
 */
public class UserMessage {

    private MessageDO message;
    private UserDO user;
    private int notReadCount;

    public int getNotReadCount() {
        return notReadCount;
    }

    public void setNotReadCount(int notReadCount) {
        this.notReadCount = notReadCount;
    }

    public MessageDO getMessage() {
        return message;
    }

    public void setMessage(MessageDO message) {
        this.message = message;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }
}
