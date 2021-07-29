package com.rifeng.p2p.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.UserForeManBean;

import java.util.ArrayList;
import java.util.List;

public class ForemanAdapter extends ArrayAdapter<UserForeManBean> {
    private int resourceId;

    // 适配器的构造函数，把要适配的数据传入这里
    public ForemanAdapter(Context context, int textViewResourceId, List<UserForeManBean> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View view, ViewGroup parent){
        UserForeManBean userForeManBean=getItem(position); //获取当前项的Fruit实例


        ////
        ForeManViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ForeManViewHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder.emailTextView = (TextView) view.findViewById(R.id.item_foreman_mail_textview);
            viewHolder.deleteBtn = (Button) view.findViewById(R.id.foreman_deleteBtn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ForeManViewHolder) view.getTag();
        }
        viewHolder.emailTextView.setText(userForeManBean.getEmail());
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(position);
            }
        });

        return view;
    }

    /**
     * 删除按钮的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }


    private class ForeManViewHolder {
        TextView emailTextView;

        Button deleteBtn;
    }
    public List<UserForeManBean> getData(){
        List<UserForeManBean> list = new ArrayList<>();
        if (getCount()!=0){
            for (int i=0;i<getCount();i++){
                list.add(getItem(i));
            }
        }
        return list;
    }
}
