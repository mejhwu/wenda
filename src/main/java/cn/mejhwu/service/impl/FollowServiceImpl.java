package cn.mejhwu.service.impl;

import cn.mejhwu.service.FollowService;
import cn.mejhwu.util.JedisAdapter;
import cn.mejhwu.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/26
 * Time:   18:55
 * Description:
 */

@Service
public class FollowServiceImpl implements FollowService {

    private final static Logger logger = LoggerFactory.getLogger(FollowServiceImpl.class);

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public boolean follow(int userId, int entityId, int entityType) {
        try {
            String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
            String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
            Date date  = new Date();
            Jedis jedis = jedisAdapter.getJedis();
            Transaction tx = jedisAdapter.multi(jedis);
            tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
            tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
            List<Object> ret = tx.exec();
            return ret.size() == 2 && ((Long)ret.get(0)) >0 && ((Long)ret.get(1)) > 0;
        } catch (Exception e) {
            logger.error("发生异常: " + e.getMessage());
        }
        return false;
    }
    @Override
    public boolean unfollow(int userId, int entityId, int entityType) {
        try {
            String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
            String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
            Date date  = new Date();
            Jedis jedis = jedisAdapter.getJedis();
            Transaction tx = jedisAdapter.multi(jedis);
            tx.zrem(followerKey, String.valueOf(userId));
            tx.zrem(followeeKey, String.valueOf(entityId));
            List<Object> ret = tx.exec();
            return ret.size() == 2 && (Long) ret.get(0) >0 && (Long)ret.get(1) > 0;
        } catch (Exception e) {
            logger.error("发生异常: " + e.getMessage());
        }
        return false;
    }
    @Override
    public List<Integer> getFollowers(int entityId, int entityType, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, 0, count));
    }
    @Override
    public List<Integer> getFollowers(int entityId, int entityType, int offset, int limit) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, offset, limit));
    }
    @Override
    public List<Integer> getFollowees(int userId, int entityType, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }
    @Override
    public List<Integer> getFollowees(int userId, int entityType, int offset, int limit) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, limit));
    }


    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return jedisAdapter.scard(followeeKey);
    }
    @Override
    public long getFollowerCount(int entityId, int entityType) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return jedisAdapter.zcard(followerKey);
    }
    @Override
    public boolean isFollower(int userId, int entityId, int entityType) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }

    private List<Integer> getIdsFromSet(Set<String> idset) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

}
