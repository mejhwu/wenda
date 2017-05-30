package cn.mejhwu.dao;

import cn.mejhwu.model.QuestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/21
 * Time:   18:49
 * Description:
 */
public interface QuestionDao {

    int saveQuestionDao(QuestionDO question);

    List<QuestionDO> listQuestionOrderByDate(@Param("offset") int offset, @Param("limit") int limit);

    QuestionDO getQuestionById(int id);

}
