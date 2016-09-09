package com.work.leeds.facerecognition.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leeds on 2016/9/9.
 * Json解析工具
 */
public class JsonUtil {
    /**
     * 解析比较结果
     *
     * @param result
     * @return
     */
    public static float parseCompareResult(String result) {
        float code = 0;
        try {
            JSONObject jo = new JSONObject(result);
            code = jo.getInt("similar");
            System.out.println("similar----->" + code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }
}
