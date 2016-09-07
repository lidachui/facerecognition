package com.work.leeds.facerecognition.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.work.leeds.facerecognition.MyApplication;
import com.work.leeds.facerecognition.bean.Apartment;
import com.work.leeds.facerecognition.bean.User;

import java.util.ArrayList;
import java.util.List;

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
     * 通过id查询员工信息
     *
     * @param id
     * @return
     */
    public static User queryUserInfoById(String id) {
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from User where staffid = ?",
                new String[]{id});
        if (cursor.moveToFirst()) {
            User user;
            do {
                String userid = cursor.getString(cursor.getColumnIndex("staffid"));
                String username = cursor.getString(cursor.getColumnIndex("staffname"));
                int apartid = cursor.getInt(cursor.getColumnIndex("apartid"));
                String image = cursor.getString(cursor.getColumnIndex("staffimage"));
                user = new User(userid, username, apartid, image);
            } while (cursor.moveToNext());
            return user;
        } else {
            return null;
        }
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


    /**
     * 判断用户是否已存在
     *
     * @param id
     * @return
     */
    public static boolean IsUserExist(String id) {
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from User where staffid = ?",
                new String[]{id});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取全部部门信息
     * @return
     */
    public static List<Apartment> getApartInfo() {
        List<Apartment> mList = new ArrayList<>();
        //查询Apartment表中的所有数据
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("Apartment", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //遍历cursor对象
                int apartnumber = cursor.getInt(cursor.getColumnIndex("apartid"));
                String apartname = cursor.getString(cursor.getColumnIndex("apartname"));
                Apartment apart = new Apartment(apartnumber, apartname);
                mList.add(apart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    /**
     * 根据部门id获得部门名字
     * @param apartId
     * @return
     */
    public static String queryApartNameById(int apartId){
        String apartName;
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select apartname from Apartment where apartid = ?",
                new String[]{""+apartId});
        cursor.moveToFirst();
        apartName = cursor.getString(0);
        return apartName;
    }
}
