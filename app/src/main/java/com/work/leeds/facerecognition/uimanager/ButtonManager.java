package com.work.leeds.facerecognition.uimanager;


import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.util.Constants;

/**
 * Created by leeds on 2016/8/28.
 */
public class ButtonManager implements View.OnClickListener {

    private Context mContext;
    private LinearLayout mView;
    private ImageButton mSignBtn;
    private ImageButton mCheckBtn;
    private ImageButton mAddBtn;
    private ImageButton mGuideBtn;

    public ButtonManager(Context mContext, LinearLayout mView) {
        this.mContext = mContext;
        this.mView = mView;
        initView();
        initEvent();
    }

    private void initEvent() {
        mSignBtn.setOnClickListener(this);
        mCheckBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);
        mGuideBtn.setOnClickListener(this);
    }

    private void initView() {
        mSignBtn=(ImageButton)mView.findViewById(R.id.id_sign_btn);
        mCheckBtn=(ImageButton)mView.findViewById(R.id.id_check_btn);
        mAddBtn=(ImageButton)mView.findViewById(R.id.id_add_btn);
        mGuideBtn=(ImageButton)mView.findViewById(R.id.id_guide_btn);
    }

    @Override
    public void onClick(View v) {
     switch(v.getId()){

         case R.id.id_sign_btn:
             if(monButtonClickedListener!=null)
                 monButtonClickedListener.onBtnClicked(Constants.FUNCTION_SIGN);
             break;
         case R.id.id_check_btn:
             if(monButtonClickedListener!=null)
                 monButtonClickedListener.onBtnClicked(Constants.FUNCTION_CHECK);
             break;
         case R.id.id_add_btn:
             if(monButtonClickedListener!=null)
                 monButtonClickedListener.onBtnClicked(Constants.FUNCTION_ADD);
             break;
         case R.id.id_guide_btn:
             if(monButtonClickedListener!=null)
                 monButtonClickedListener.onBtnClicked(Constants.FUNCTION_GUIDE);
             break;
     }
    }

    /**
     * 点击接口调用
     */
    public interface onButtonClickedListener{
         void onBtnClicked(int function);
    }

    private onButtonClickedListener monButtonClickedListener;

    public void setonButtonClickedListener(onButtonClickedListener listener){
        this.monButtonClickedListener=listener;
    }
}
