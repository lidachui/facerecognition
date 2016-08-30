package com.work.leeds.facerecognition.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by leeds on 2016/8/28.
 * 图片轮播适配器
 */
public class ImageAdapter extends StaticPagerAdapter {

    private String[] urls = {
            "http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=c9f4bfb152e736d15846840cae6063f4/6c224f4a20a44623780d540f9e22720e0df3d79c.jpg",
            "http://h.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=c6153ba7d3a20cf446c5f6db43396700/10dfa9ec8a136327fd477e13928fa0ec08fac76c.jpg",
            "http://d.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=d8c645729513b07ebde8580c39e7bd15/a8014c086e061d95294ff56b7df40ad162d9ca5c.jpg"
    };

    @Override
    public View getView(ViewGroup container, int position) {
        SimpleDraweeView sdv=new SimpleDraweeView(container.getContext());
        Uri uri=Uri.parse(urls[position]);
        sdv.setImageURI(uri);
        return sdv;
    }

    @Override
    public int getCount() {
        return urls.length;
    }
}
