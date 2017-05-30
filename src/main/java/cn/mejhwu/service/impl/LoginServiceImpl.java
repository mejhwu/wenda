package cn.mejhwu.service.impl;

import cn.mejhwu.dao.LoginTicketDao;
import cn.mejhwu.model.LoginTicketDO;
import cn.mejhwu.service.LoginTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/19
 * Time:   22:52
 * Description:
 */

@Service
public class LoginServiceImpl implements LoginTicketService {

    @Autowired
    LoginTicketDao loginTicketDao;


    @Override
    public int addLoginTicket(LoginTicketDO loginTicket) {
        return loginTicketDao.addLoginTicket(loginTicket);
    }

    @Override
    public LoginTicketDO getLoginTicketByTicket(String ticket) {
        return loginTicketDao.getLoginTicketByTicket(ticket);
    }

    @Override
    public int updateLoginTicketStatus(String ticket, int status) {
        return loginTicketDao.updateLoginTicketStatus(ticket, status);
    }
}
