package cn.mejhwu.service;

import cn.mejhwu.model.QuestionDO;
import cn.mejhwu.vo.UserQuestionVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/21
 * Time:   18:41
 * Description:
 */
public interface QuestionService {

    int add(QuestionDO question);


    List<UserQuestionVO> listQuestionOrderByDate(int offset, int limit);

    QuestionDO getQuestionById(int id);


    List<QuestionDO> listQuestionByUserId(int userId, int offset, int limit);
    List<UserQuestionVO> listQuestionAndUserByUserId(int userId, int offset, int limit);

    int updateCommentCount(int questionId, int commentCount);

}
