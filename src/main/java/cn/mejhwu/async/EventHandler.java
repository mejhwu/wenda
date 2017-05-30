package cn.mejhwu.async;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   20:58
 * Description:
 */
public interface EventHandler {

    void doHandler(EventModel eventModel);

    List<EventType> getSupportEventType();

}
