package cn.mejhwu.service;

import cn.mejhwu.dao.MessageDao;
import cn.mejhwu.model.MessageDO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   14:00
 * Description:
 */
public interface MessageService {

    int saveMessage(MessageDO message);

    List<MessageDO> listMessageByConversationId(String conversationId);

    List<MessageDO> listConversationByUserId(int userId);

    int countNotReadMessage(String conversationId, int userId);
}
