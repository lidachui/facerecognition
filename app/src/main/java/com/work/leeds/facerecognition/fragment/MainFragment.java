package com.work.leeds.facerecognition.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.adapter.ImageAdapter;

/**
 * Created by leeds on 2016/8/28.
 * 广告fragment
 */
public class MainFragment extends Fragment {

    LinearLayout mLinearLayout;
    Context mContext;
    private RollPagerView mRollViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.main_fragment_layout, container, false);
        return mLinearLayout;
    }

    @Override
    public void onStart() {

        super.onStart();
        Toast.makeText(mContext, "start main frgment", Toast.LENGTH_SHORT).show();
        mRollViewPager = (RollPagerView) mLinearLayout.findViewById(R.id.id_roll_view_pager);
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(4000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new ImageAdapter());
        //设置指示器
        mRollViewPager.setHintView(new ColorPointHintView(mContext, Color.YELLOW, Color.WHITE));
    }
}
