package com.work.leeds.facerecognition.internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.work.leeds.facerecognition.callback.FaceCompareCallback;
import com.work.leeds.facerecognition.util.JsonUtil;
import com.work.leeds.facerecognition.util.Urls;
import com.work.leeds.facerecognition.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leeds on 2016/9/5.
 * 获取人脸相似度
 */
public class GetDegreeOfSimilarity {

    FaceCompareCallback view;

    public GetDegreeOfSimilarity(FaceCompareCallback view) {
        this.view = view;
    }

    /**
     * 获取人脸相似度
     * 第二种方式
     * 参数为两张图片的base64编码
     *
     * @param firstBaseValue
     * @param secondBaseValue
     */
    public void getDegreeOfSimilarity(final String firstBaseValue, final String secondBaseValue) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Urls.getFaceSimilarityUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        System.out.println("request succeed----->");
                        //Dialog消失
                        view.dismissDialog();
                        //解析
                        float code = JsonUtil.parseCompareResult(s);
                        if (code == 1.0 || code == -2.0 || code == -3.0 || code > 0.5){
                            view.signFailed();
                            System.out.println("sign failed----->");
                        }
                        else {
                            view.signSucceed();
                            //todo 向数据库添加打卡数据
                            System.out.println("sign succeed----->");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //回调接口报错
                        view.dismissDialog();
                        view.signError();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //TODO 因为采取第二种方式，参数是两张图片的base64编码值
                HashMap<String, String> params = new HashMap<>();
                params.put("faceimage1", firstBaseValue);
                params.put("faceimage2", secondBaseValue);
                return params;
            }
        };
        VolleyUtil.getmRequestQueue().add(stringRequest);
    }
}
