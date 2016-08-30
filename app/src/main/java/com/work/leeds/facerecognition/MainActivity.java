package com.work.leeds.facerecognition;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.work.leeds.facerecognition.fragment.AddFragment;
import com.work.leeds.facerecognition.fragment.MainFragment;
import com.work.leeds.facerecognition.uimanager.ButtonManager;
import com.work.leeds.facerecognition.util.Constants;

public class MainActivity extends AppCompatActivity implements ButtonManager.onButtonClickedListener {


    FrameLayout mFrameLayout;
    TextView mTextview;
    LinearLayout mLinearLayout;
    ButtonManager mButtonManager;
    MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //打开数据库
        MyApplication.myDatabaseHelper.getWritableDatabase();//如果数据库不存在，则自动创建
        //插入部门相关信息
        insertApartmentInfo();
        //初始化相关组件
        initView();
        //设置默认的Fragment
        initFragment();
        //设置默认的text
        mTextview.setText("欢迎使用");
        //初始化点击事件
        initiEvent();

    }

    private void insertApartmentInfo() {
        //如果数据库不存在，则自动创建
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        //检查表里是否有内容
        Cursor cursor = db.rawQuery("select count(apartid) from Apartment", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count == 0) {
            //插入部门表的内容
            ContentValues values = new ContentValues();
            values.put("apartid", 001);
            values.put("apartname", "技术部");
            db.insert("Apartment", null, values);

            values.clear();
            values.put("apartid", 002);
            values.put("apartname", "人事部");
            db.insert("Apartment", null, values);

            values.clear();
            values.put("apartid", 003);
            values.put("apartname", "销售部");
            db.insert("Apartment", null, values);

            values.clear();
            values.put("apartid", 004);
            values.put("apartname", "财务部");
            db.insert("Apartment", null, values);
            Toast.makeText(
                    MainActivity.this,
                    "Table Apartment Insert Succeeded",
                    Toast.LENGTH_SHORT).show();
        }
        cursor.close();

    }

    private void initiEvent() {
        mButtonManager.setonButtonClickedListener(this);
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mMainFragment = new MainFragment();
        transaction.replace(R.id.main_content, mMainFragment, "one");
        transaction.commit();
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.main_content);
        mTextview = (TextView) findViewById(R.id.id_info_text);
        mLinearLayout = (LinearLayout) findViewById(R.id.id_button_area);
        mButtonManager = new ButtonManager(MainActivity.this, mLinearLayout);
    }

    AddFragment mAddFragment = null;

    /**
     * 相关功能按钮点击事件
     *
     * @param function
     */
    @Override
    public void onBtnClicked(int function) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (function) {
            case Constants.FUNCTION_SIGN:
                break;
            case Constants.FUNCTION_CHECK:
                break;
            case Constants.FUNCTION_ADD:
                Toast.makeText(MainActivity.this, "add function", Toast.LENGTH_SHORT).show();
                if (mAddFragment == null)
                    mAddFragment = new AddFragment();
                transaction.replace(R.id.main_content, mAddFragment, "two");
                transaction.commit();
                break;
            case Constants.FUNCTION_GUIDE:
                break;

        }
    }
}
