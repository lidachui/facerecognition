package com.work.leeds.facerecognition.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.work.leeds.facerecognition.MyApplication;
import com.work.leeds.facerecognition.bean.User;

/**
 * Created by leeds on 2016/8/30.
 * 数据库相关操作
 */
public class DatabaseOperation {

    /**
     * 查询部门编号
     *
     * @param name
     * @return
     */
    public static int queryApartIdByName(String name) {
        int apartNumber;
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select apartid from Apartment where apartname = ?",
                new String[]{name});
        cursor.moveToFirst();
        apartNumber = cursor.getInt(0);
        return apartNumber;
    }

    /**
     * 上传用户信息
     *
     * @param user
     */
    public static void UploadUserData(User user) {
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("staffid", user.getId());
        values.put("staffname", user.getName());
        values.put("apartid", user.getApartId());
        values.put("staffimage", user.getImageUri());
        db.insert("User", null, values);
    }

    public static boolean queryUserByName(String id) {
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from User where staffid = ?",
                new String[]{id});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
//        do{
//            String id =cursor.getString(cursor.getColumnIndex("staffid"));
//            String username =cursor.getString(cursor.getColumnIndex("staffname"));
//            int apartid=cursor.getInt(cursor.getColumnIndex("apartid"));
//            String image=cursor.getString(cursor.getColumnIndex("staffimage"));
//            Log.d("Query",id);
//            Log.d("Query",username);
//            Log.d("Query",apartid+"");
//            Log.d("Query",image);
//
//        }while(cursor.moveToNext());
    }
}
