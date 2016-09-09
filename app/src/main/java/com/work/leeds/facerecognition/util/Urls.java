package com.work.leeds.facerecognition.util;

/**
 * Created by leeds on 2016/9/5.
 * 相关请求接口
 */
public class Urls {

    public static final String mykey = "e0df182191b5f5bf9f0bf5a1283c2620";

    /**
     * 获取人脸特征值接口
     *
     * @return
     */
    public static String getFacePropertyUrl() {
        return  "http://api.facecore.cn/api/facedetect?appkey=c34e7a2059d771d00b9300de725029f7";
    }

    /**
     * 获取人脸相似度接口
     * 采用第二种方式
     *
     * @return
     */
    public static String getFaceSimilarityUrl() {
        return "http://api.facecore.cn/api/facedetectandcompare?appkey=c34e7a2059d771d00b9300de725029f7";
    }
}
