package cn.mejhwu.service;

import cn.mejhwu.model.LoginTicketDO;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface LoginTicketService {

    int addLoginTicket(LoginTicketDO loginTicket);

    LoginTicketDO getLoginTicketByTicket(String ticket);

    int updateLoginTicketStatus(String ticket, int status);

}
