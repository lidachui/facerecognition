package com.work.leeds.facerecognition.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by leeds on 2016/8/26.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    //员工表
    public static final String CREATE_USER = "create table User ("
            + "staffid text primary key, "
            + "staffname text, "
            + "apartid integer, "
            + "staffimage text)";
    //部门表
    public static final String CREATE_PART = "create table Apartment ("
            + "apartid integer primary key, "
            + "apartname text)";
    //记录表
    public static final String CREATE_RECORD = "create table SignRecord ("
            + "recordid  integer primary key autoincrement, "
            + "staffid text, "
            + "time text)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_PART);
        db.execSQL(CREATE_RECORD);
        Toast.makeText(mContext, "CREATE SUCCEEDED!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
