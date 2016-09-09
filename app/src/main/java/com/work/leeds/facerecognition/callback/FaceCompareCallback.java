package com.work.leeds.facerecognition.callback;

/**
 * Created by leeds on 2016/9/7.
 * 人脸比较接口
 */
public interface FaceCompareCallback {

    void compareFace(String face1, String face2);

    void showDialog();

    void dismissDialog();

    void signSucceed();

    void signFailed();

    void signError();
}
