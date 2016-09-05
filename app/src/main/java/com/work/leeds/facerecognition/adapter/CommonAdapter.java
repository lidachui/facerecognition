package com.work.leeds.facerecognition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by leeds on 2016/9/5.
 * 万能适配器
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater layoutInflater;
    protected Context context;
    protected List<T> list;

    public CommonAdapter(Context context, List<T> list) {
        this.layoutInflater =LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
