package cn.mejhwu.service.impl;

import cn.mejhwu.dao.MessageDao;
import cn.mejhwu.model.MessageDO;
import cn.mejhwu.service.MessageService;
import cn.mejhwu.service.SensitivieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   14:01
 * Description:
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    SensitivieService sensitivieService;

    @Override
    public int saveMessage(MessageDO message) {
        message.setContent(sensitivieService.filter(message.getContent()));
        return messageDao.saveMessage(message);
    }

    @Override
    public List<MessageDO> listMessageByConversationId(String conversationId) {
        return messageDao.listMessageByConversationId(conversationId);
    }

    @Override
    public List<MessageDO> listConversationByUserId(int userId) {
        return messageDao.listConversationByUserId(userId);
    }

    @Override
    public int countNotReadMessage(String conversationId, int userId) {
        return messageDao.countNotReadMessage(conversationId, userId);
    }
}
