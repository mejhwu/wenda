package cn.mejhwu.service.impl;

import cn.mejhwu.dao.FeedDao;
import cn.mejhwu.model.FeedDO;
import cn.mejhwu.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/27
 * Time:   20:48
 * Description:
 */
@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    FeedDao feedDao;

    @Override
    public List<FeedDO> listUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDao.listUserFeed(maxId, userIds, count);
    }

    @Override
    public boolean saveFeed(FeedDO feed) {
        feedDao.saveFeed(feed);
        return feed.getId() > 0;
    }

    @Override
    public FeedDO  getFeedById(int id) {
        return feedDao.getFeedById(id);
    }

}
