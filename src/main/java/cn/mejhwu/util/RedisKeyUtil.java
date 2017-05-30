package cn.mejhwu.util;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   19:33
 * Description:
 */
public class RedisKeyUtil {

    private final static String SPLIT = ":";
    private final static String BIZ_LIKE = "LIKE";
    private final static String BIZ_DISLIKE = "DISLIKE";
    private final static String BIZ_EVENTQUEUE = "EVENTQUEUE";
    private final static String BIZ_FOLLOWER = "FOLLOWER";
    private final static String BIZ_FOLLOWEE = "FOLLOWEE";

    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE + SPLIT + entityId + SPLIT + entityType;
    }

    public static String getDisLikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE + SPLIT + entityId + SPLIT + entityType;
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

    public static String getFollowerKey(int entityId, int entityType) {
        return BIZ_FOLLOWER + SPLIT + entityId + SPLIT + entityType;
    }

    public static String getFolloweeKey(int userId, int entityType) {
        return BIZ_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }
}
