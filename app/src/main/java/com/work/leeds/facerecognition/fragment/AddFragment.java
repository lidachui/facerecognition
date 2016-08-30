package com.work.leeds.facerecognition.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.bean.Apartment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeds on 2016/8/29.
 * 添加fragment
 */
public class AddFragment extends Fragment implements View.OnClickListener {

    LinearLayout mLinearLayout;
    Context mContext;

    private EditText mNameText;
    private EditText mNumberText;
    private TextView mAprtmentText;
    private TextView mPictureText;
    private ImageView mChooseApartBtn;
    private ImageView mChoosePictureBtn;
    private Button mAddBtn;
    private ImageView mBackBtn;


    private List<Apartment> ApartList =new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        //TODO 查询部门信息 并保存
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.add_fragment_layout, container, false);
        initView();
        initEvent();
        return mLinearLayout;
    }

    private void initEvent() {
        mChooseApartBtn.setOnClickListener(this);
        mChoosePictureBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }


    private void initView() {
        mBackBtn = (ImageView) findViewById(R.id.id_add_back_btn);
        mNameText = (EditText) findViewById(R.id.id_name_text);
        mNameText = (EditText) findViewById(R.id.id_job_number_text);
        mAprtmentText = (TextView) findViewById(R.id.id_apartment_text);
        mChooseApartBtn = (ImageView) findViewById(R.id.id_apartment_btn);
        mPictureText = (TextView) findViewById(R.id.id_picture_text);
        mChoosePictureBtn = (ImageView) findViewById(R.id.id_picture_btn);
        mAddBtn = (Button) findViewById(R.id.id_add_user_btn);
    }

    private View findViewById(int viewId) {
        return mLinearLayout.findViewById(viewId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_add_user_btn:
                //TODO 添加用户信息
                Toast.makeText(mContext,"add user succeed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_apartment_btn:
                //TODO 选取部门
                break;
            case R.id.id_add_back_btn:
                //TODO 返回主界面
                break;
            case R.id.id_picture_btn:
                //TODO 选取图片

                break;
        }
    }
}
