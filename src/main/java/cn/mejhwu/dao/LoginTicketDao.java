package cn.mejhwu.dao;

import cn.mejhwu.model.LoginTicketDO;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/19
 * Time:   22:02
 * Description:
 */
public interface LoginTicketDao {

    int addLoginTicket(LoginTicketDO loginTicket);

    LoginTicketDO getLoginTicketByTicket(String ticket);

    int updateLoginTicketStatus(@Param("ticket") String ticket,
                                @Param("status") int status);

}
