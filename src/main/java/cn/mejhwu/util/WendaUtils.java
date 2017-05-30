package cn.mejhwu.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/21
 * Time:   18:38
 * Description:
 */
public class WendaUtils {

    private final static Logger logger = LoggerFactory.getLogger(WendaUtils.class);

    public final static int ANONYMITY_USER_ID = 3;
    public final static int SYSTEM_USER_ID = 4;


    public static String getJSONString(int code, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject.toString();
    }

    public static String getJSONString(int code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        return jsonObject.toString();
    }



}
