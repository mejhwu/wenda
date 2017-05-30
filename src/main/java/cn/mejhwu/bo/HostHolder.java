package cn.mejhwu.bo;

import cn.mejhwu.model.UserDO;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/20
 * Time:   13:35
 * Description:
 */

@Component
public class HostHolder {

    private static ThreadLocal<UserDO> users = new ThreadLocal<>();

    public UserDO getUser() {
        return users.get();
    }

    public void setUser(UserDO user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }



}
