package cn.mejhwu.dao;

import cn.mejhwu.model.UserDO;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/19
 * Time:   18:24
 * Description:
 */
public interface UserDao {


    int addUser(UserDO user);

    UserDO getUserById(int id);

    UserDO getUserByName(String name);

    int updateUserPassowrd(UserDO user);

    int removeUserById(int id);

    int updateLoginTime(int id);

}
