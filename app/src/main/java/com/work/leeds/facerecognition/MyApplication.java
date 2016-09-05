package com.work.leeds.facerecognition;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.work.leeds.facerecognition.sqlite.MyDatabaseHelper;
import com.work.leeds.facerecognition.util.VolleyUtil;

/**
 * Created by leeds on 2016/8/26.
 *
 */
public class MyApplication extends Application {

     public static MyDatabaseHelper myDatabaseHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        myDatabaseHelper =new MyDatabaseHelper(getApplicationContext(),"SignStore.db",null,1);

        //初始化Fresco类
        Fresco.initialize(this);

        //初始化Volley队列
        VolleyUtil.initialize(getApplicationContext());
    }
}
