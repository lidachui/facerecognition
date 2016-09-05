package com.work.leeds.facerecognition.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.bean.Record;

import java.util.List;

/**
 * Created by leeds on 2016/9/5.
 * 查询记录列表适配器
 */
public class CheckListAdapter extends CommonAdapter<Record> {


    public CheckListAdapter(Context context, List<Record> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.get(context, convertView, parent, R.layout.check_list_item, position);
        TextView tv1 = viewHolder.getView(R.id.id_check_user_name);
        TextView tv2 = viewHolder.getView(R.id.id_check_sign_time);
        //TODO 执行读取记录操作

        return viewHolder.getmConvertView();
    }

    /**
     * 更新List中的内容
     *
     * @param mlist
     */
    public void refreshList(List<Record> mlist) {
        list = mlist;
        notifyDataSetChanged();
    }

}
