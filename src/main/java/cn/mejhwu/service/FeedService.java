package cn.mejhwu.service;

import cn.mejhwu.model.FeedDO;
import cn.mejhwu.vo.ViewObjectVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/27
 * Time:   20:48
 * Description:
 */
public interface FeedService {
    List<FeedDO> listUserFeeds(int maxId, List<Integer> userIds, int count);

    boolean saveFeed(FeedDO feed);

    FeedDO getFeedById(int id);

    List<ViewObjectVO> timeline(int userId);
}
