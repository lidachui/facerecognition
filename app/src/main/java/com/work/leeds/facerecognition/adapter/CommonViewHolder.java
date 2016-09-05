package com.work.leeds.facerecognition.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by leeds on 2016/9/5.
 * 通用V
 */
public class CommonViewHolder {

    private final SparseArray<View> sparseArray;
    private View mConvertView;

    /**
     * 构造函数
     *
     * @param context
     * @param viewGroup
     * @param layoutId
     * @param position
     */
    public CommonViewHolder(Context context, ViewGroup viewGroup, int layoutId, int position) {
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        this.sparseArray = new SparseArray<>();
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个Viewholder对象 避免多次调用构造函数创建
     *
     * @param ConvertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static CommonViewHolder get(Context context, View ConvertView, ViewGroup parent, int layoutId, int position) {
        if (ConvertView == null) {
            return new CommonViewHolder(context,parent,layoutId,position);
        }
        return (CommonViewHolder)ConvertView.getTag();
    }

    /**
     * 通过控件的id获取对应的控件，如果没有则加入views
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view =sparseArray.get(viewId);
        if(view==null){
            view=mConvertView.findViewById(viewId);
            sparseArray.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 获得convertview
     * @return
     */
    public View getmConvertView() {
        return mConvertView;
    }
}
