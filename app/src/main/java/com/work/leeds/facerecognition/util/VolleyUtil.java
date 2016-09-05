package com.work.leeds.facerecognition.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by leeds on 2016/9/5.
 * volley请求工具类
 */
public class VolleyUtil {
    private static RequestQueue mRequestQueue;

    /**
     * 单例创建对象
     *
     * @param context
     */
    public static void initialize(Context context) {
        if (mRequestQueue == null) {
            synchronized (VolleyUtil.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        mRequestQueue.start();
    }

    /**
     * 拿到volley请求队列
     *
     * @return
     */
    public static RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            throw new RuntimeException("先初始化");
        }
        return mRequestQueue;
    }
}
