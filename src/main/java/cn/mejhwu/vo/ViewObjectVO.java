package cn.mejhwu.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/25
 * Time:   15:41
 * Description:
 */
public class ViewObjectVO {

    private Map<String, Object> map = new HashMap<String, Object>();

    public void put(String key, Object obj) {
        map.put(key, obj);
    }

    public Object get(String key) {
        return map.get(key);
    }

}
