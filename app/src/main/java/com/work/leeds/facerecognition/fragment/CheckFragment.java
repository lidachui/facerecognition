package com.work.leeds.facerecognition.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.adapter.CheckListAdapter;
import com.work.leeds.facerecognition.bean.Apartment;
import com.work.leeds.facerecognition.bean.Record;
import com.work.leeds.facerecognition.callback.onBackClickedListener;
import com.work.leeds.facerecognition.sqlite.DatabaseOperation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by leeds on 2016/9/2.
 * 查询fragment
 */
public class CheckFragment extends Fragment implements View.OnClickListener {

    private TextView mApartTextView;
    private TextView mTimeTextView;
    private Button mCheckButton;
    private ImageView mBackButton;
    private List<Apartment> ApartList;
    private String[] aparts;
    private Context mContext;
    private Calendar c;

    private ListView mListView;
    private List<Record> mList;
    private CheckListAdapter mCheckListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        //获取到部门信息
        ApartList = DatabaseOperation.getApartInfo();
        aparts = new String[ApartList.size()];
        for (int i = 0; i < ApartList.size(); i++) {
            aparts[i] = ApartList.get(i).getApartmentName();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.check_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initEvent();

    }

    private void initEvent() {
        mApartTextView.setOnClickListener(this);
        mTimeTextView.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mCheckButton.setOnClickListener(this);
    }

    private void initView(View view) {
        mApartTextView = (TextView) view.findViewById(R.id.id_choose_apart);
        mTimeTextView = (TextView) view.findViewById(R.id.id_choose_time);
        mBackButton = (ImageView) view.findViewById(R.id.id_add_back_btn);
        mCheckButton = (Button) view.findViewById(R.id.id_check_record_btn);
        mListView = (ListView) view.findViewById(R.id.id_msg_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_choose_apart:
                //选择部门
                new AlertDialog.Builder(mContext)
                        .setTitle("请选择部门")
                        .setItems(aparts, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String select_item = aparts[which].toString();
                                mApartTextView.setText(select_item);
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.id_choose_time:
                //选择时间
                c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTimeTextView.setText(year + "/" + monthOfYear+1 + "/" + dayOfMonth);
                    }
                }, year, month, day).show();

                break;
            case R.id.id_add_back_btn:
                //点击返回广告页
                if (getActivity() instanceof onBackClickedListener) {
                    ((onBackClickedListener) getActivity()).onBackClicked();
                }
                break;
            case R.id.id_check_record_btn:
                Toast.makeText(mContext, "hahaha", Toast.LENGTH_SHORT).show();
                //TODO 点击查询相关记录
                if (hasLimitation()) {
                    //TODO 执行查询操作,目前是虚拟数据
                    mList = new ArrayList<>();
                    mList.add(new Record("陈依凡", "7:40"));
                    mList.add(new Record("王博", "8:00"));
                    mList.add(new Record("高朴", "7:50"));
                    if (mCheckListAdapter == null) {
                        mCheckListAdapter = new CheckListAdapter(mContext, mList);
                        mListView.setAdapter(mCheckListAdapter);
                    } else {
                        mCheckListAdapter.refreshList(mList);
                    }
                }
                break;
        }
    }

    /**
     * 判断是否输入了限制条件
     *
     * @return
     */
    private boolean hasLimitation() {
        if (mApartTextView.getText().toString().trim().equals("选择部门")
                ||
                mTimeTextView.getText().toString().trim().equals("选择时间")) {
            return false;
        }else{
            return true;
        }
    }
}
