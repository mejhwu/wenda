package cn.mejhwu.service;

import cn.mejhwu.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   19:29
 * Description:
 */
public interface LikeService {

    long like(int userId, int entityId, int entityType);

    long disLike(int userId, int entityId, int entityType);
    long countLike(int entityId, int entityType);
    int getLikeStatus(int userId, int entityId, int entityType);
}
