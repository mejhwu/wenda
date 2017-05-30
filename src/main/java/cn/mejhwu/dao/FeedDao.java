package cn.mejhwu.dao;

import cn.mejhwu.model.FeedDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/27
 * Time:   20:21
 * Description:
 */

@Mapper
public interface FeedDao {

    String TABLE_NAME = " `feed` ";
    String INSERT_FIELDS = " `user_id`, `data`, `created_date`, `type` ";
    String SELECT_FIELDS = " `id`, " + INSERT_FIELDS;

    @Insert("INSERT INTO " + TABLE_NAME + "(" + INSERT_FIELDS
            + ") VALUES (#{userId}, #{data}, #{createdDate}, #{type})")
    int saveFeed(FeedDO feed);

    @Select({" select ", SELECT_FIELDS, " from ", TABLE_NAME, " where `id`=#{id}"})
    FeedDO getFeedById(int id);



    List<FeedDO> listUserFeed(@Param("maxId") int maxId,
                              @Param("userId") List<Integer> userIds,
                              @Param("count") int count);

}
