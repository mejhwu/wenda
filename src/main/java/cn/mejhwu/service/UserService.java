package cn.mejhwu.service;

import cn.mejhwu.model.UserDO;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/19
 * Time:   18:57
 * Description:
 */
public interface UserService {

    Map<String, String> register(String userName, String password);

    Map<String, String> login(String userName, String password);

    void logout(String ticket);

    UserDO getUserById(int id);

    UserDO getUserByName(String name);

    int updateLoginTime(int id);
}
