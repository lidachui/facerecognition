package com.work.leeds.facerecognition.internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.work.leeds.facerecognition.util.Urls;
import com.work.leeds.facerecognition.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leeds on 2016/9/5.
 * 获取脸部特征值
 */
public class GetFaceProperty {

    private String faceCharacteristic;//面部特征值

    public String getFaceCharacteristic() {
        return faceCharacteristic;
    }

    /**
     * 获取人脸特征值
     */
    public void getFaceProperty(final String image64decode) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Urls.getFacePropertyUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //TODO 解析
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //TODO 报错
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("faceimage", image64decode);
                return params;
            }
        };

        VolleyUtil.getmRequestQueue().add(stringRequest);
    }
}
