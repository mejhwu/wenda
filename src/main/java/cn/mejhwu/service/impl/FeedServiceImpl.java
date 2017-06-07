package cn.mejhwu.service.impl;

import cn.mejhwu.bo.EntityType;
import cn.mejhwu.dao.CommentDao;
import cn.mejhwu.dao.FeedDao;
import cn.mejhwu.dao.QuestionDao;
import cn.mejhwu.dao.UserDao;
import cn.mejhwu.model.CommentDO;
import cn.mejhwu.model.FeedDO;
import cn.mejhwu.model.QuestionDO;
import cn.mejhwu.model.UserDO;
import cn.mejhwu.service.FeedService;
import cn.mejhwu.util.JedisAdapter;
import cn.mejhwu.util.RedisKeyUtil;
import cn.mejhwu.vo.ViewObjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    UserDao userDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    CommentDao commentDao;

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

    @Override
    public List<ViewObjectVO> timeline(int userId) {

        String key = RedisKeyUtil.getFolloweeKey(userId, EntityType.ENTITY_QUESTION);

        Set<String> questionIdSet = jedisAdapter.zrevrange(key, 0, (int)jedisAdapter.zcard(key) + 1);

        List<Integer> questionIdList = new ArrayList<>();

        for (String str : questionIdSet) {
            questionIdList.add(Integer.parseInt(str));
        }

        List<FeedDO> feeds = feedDao.listFeed(questionIdList, EntityType.ENTITY_COMMENT, 0, 20);

        List<ViewObjectVO> vos = new ArrayList<>();

        UserDO user = null;
        QuestionDO question = null;
        CommentDO comment = null;

        for (FeedDO feed : feeds) {
            ViewObjectVO vo = new ViewObjectVO();
            user = userDao.getUserById(feed.getActorId());
            question = questionDao.getQuestionById(feed.getEntityOwnerId());
            comment = commentDao.getCommentById(feed.getEntityId());
            vo.put("user", user);
            vo.put("question", question);
            vo.put("comment", comment);
            vos.add(vo);
        }

        return vos;
    }

}
