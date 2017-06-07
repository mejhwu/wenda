package cn.mejhwu.service.impl;

import cn.mejhwu.dao.LoginTicketDao;
import cn.mejhwu.model.LoginTicketDO;
import cn.mejhwu.model.UserDO;
import cn.mejhwu.dao.UserDao;
import cn.mejhwu.service.UserService;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/19
 * Time:   19:00
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    @Override
    public Map<String, String> register(String userName, String password) {
        Map<String, String> map = new HashMap<>();

        if (StringUtils.isEmpty(userName)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        UserDO user = userDao.getUserByName(userName);
        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }

        user = new UserDO();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl("http://images.nowcoder.com/head/" + new Random().nextInt(1000)  + "t.png");
        try {
            password += user.getSalt();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(password.getBytes());
            user.setPassword(Hex.encodeHexString(md5Bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        logger.info(user.toString());
        userDao.addUser(user);
        user = userDao.getUserByName(userName);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    @Override
    public Map<String, String> login(String userName, String password) {
        Map<String, String> map = new HashMap<>();

        if (StringUtils.isEmpty(userName)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        UserDO user = userDao.getUserByName(userName);
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        password += user.getSalt();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(password.getBytes());
            password = Hex.encodeHexString(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(!password.equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        userDao.updateLoginTime(user.getId());
        return map;
    }

    public String addLoginTicket(int userId) {
        LoginTicketDO loginTicket = new LoginTicketDO();
        loginTicket.setUserId(userId);
        Date date = new Date(new Date().getTime() + 3600*24*100);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDao.addLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }

    @Override
    public void logout(String ticket) {
        loginTicketDao.updateLoginTicketStatus(ticket, 1);
    }

    @Override
    public UserDO getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public UserDO getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public int updateLoginTime(int id) {
        return userDao.updateLoginTime(id);
    }
}
