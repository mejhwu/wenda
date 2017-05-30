package cn.mejhwu.dao;

import cn.mejhwu.model.MessageDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/21
 * Time:   19:07
 * Description:
 */
public interface MessageDao {

    int saveMessage(MessageDO message);

    List<MessageDO> listMessageByConversationId(String conversationId);

    List<MessageDO> listConversationByUserId(int userId);

    int countNotReadMessage(@Param("conversationId") String conversationId,
                            @Param("userId") int userId);

}
