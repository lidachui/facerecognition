package com.work.leeds.facerecognition.uimanager;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.bean.User;
import com.work.leeds.facerecognition.callback.TakePicCallback;
import com.work.leeds.facerecognition.sqlite.DatabaseOperation;

/**
 * Created by leeds on 2016/9/6.
 * 信息显示UI管理类
 */
public class InfoViewManager implements View.OnClickListener {

    private Context mContext;
    private View mView;
    private EditText mNumberText;
    private TextView mNameText;
    private TextView mApartText;
    private Button mCommitBtn;
    private Button mTakePicBtn;
    private Button mResetBtn;
    private Button mSignBtn;
    private User mUser=null;


    private TakePicCallback mTakePicCalllback;

    public void setmTakePicCalllback(TakePicCallback mTakePicCalllback) {
        this.mTakePicCalllback = mTakePicCalllback;
    }

    public InfoViewManager(Context mContext, View mView) {
        this.mContext = mContext;
        this.mView = mView;
        initView();
        initEvent();
    }

    private void initEvent() {
        mCommitBtn.setOnClickListener(this);
        mTakePicBtn.setOnClickListener(this);
        mResetBtn.setOnClickListener(this);
        mSignBtn.setOnClickListener(this);
    }

    private void initView() {
        mNumberText = (EditText) findViewById(R.id.id_staff_number_text);
        mNameText = (TextView) findViewById(R.id.id_staff_name);
        mApartText = (TextView) findViewById(R.id.id_staff_apart);
        mCommitBtn = (Button) findViewById(R.id.id_commit_btn);
        mTakePicBtn = (Button) findViewById(R.id.id_take_photo);
        mResetBtn = (Button) findViewById(R.id.id_reset_camera);
        mSignBtn = (Button) findViewById(R.id.id_staff_sign_btn);
    }

    private View findViewById(int id) {
        return mView.findViewById(id);
    }

    public EditText getmNumberText() {
        return mNumberText;
    }

    public TextView getmNameText() {
        return mNameText;
    }

    public TextView getmApartText() {
        return mApartText;
    }

    public User getmUser() {
        return mUser;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_commit_btn:
                //TODO 查询员工信息
                if (mNumberText.getText().toString().equals("")) {

                } else {
                     mUser = DatabaseOperation.queryUserInfoById(mNumberText.getText().toString().trim());
                    if (mUser != null) {
                        mNameText.setText(mUser.getName());
                        mApartText.setText(DatabaseOperation.queryApartNameById(mUser.getApartId()));
                    }else{
                        mNameText.setText("null");
                        mApartText.setText("null");
                    }
                }
                break;
            case R.id.id_take_photo:
                //TODO 回调拍照方法
                mTakePicCalllback.takePhoto();
                break;
            case R.id.id_reset_camera:
                //TODO 回调重置方式
                mTakePicCalllback.resetCamera();
                break;
            case R.id.id_staff_sign_btn:
                //TODO 回调对比方式
                mTakePicCalllback.comparePic();
                break;
        }
    }

}
