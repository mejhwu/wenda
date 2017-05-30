package cn.mejhwu.service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/26
 * Time:   18:55
 * Description:
 */

public interface FollowService {
    boolean follow(int userId, int entityId, int entityType);

    boolean unfollow(int userId, int entityId, int entityType);

    List<Integer> getFollowers(int entityId, int entityType, int count);

    List<Integer> getFollowers(int entityId, int entityType, int offset, int limit);

    List<Integer> getFollowees(int userId, int entityType, int count);

    List<Integer> getFollowees(int userId, int entityType, int offset, int limit);


    long getFolloweeCount(int userId, int entityType);

    long getFollowerCount(int entityId, int entityType);

    boolean isFollower(int userId, int entityId, int entityType);
}
