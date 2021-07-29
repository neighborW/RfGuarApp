package com.rifeng.p2p.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.AnnouncementActivity;
import com.rifeng.p2p.entity.NewsEntity;
import com.rifeng.p2p.entity.Notify;
import com.rifeng.p2p.entity.NotifyResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private final Context context;
    private List<Notify> datas = new ArrayList<>(); //接受传入数据
    private OnItemClickListener mOnItemClickListener;

    public NewsAdapter(Context context  , List<Notify> datas) {
        this.context = context;
        this.datas = datas;
    }

    public void setDatas(List<Notify> datas){
        this.datas = datas;
    }
    /**
     * 相当于getView方法中创建View和ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_new_layout,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return new MyViewHolder(itemView);
    }
    /**
     * 绑定数据
     * @param holder
     * @param position  当前位置
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //绑定数据 ， 根据位置得到对应的数据
        Notify data  = datas.get(position);

        if ("N".equals(data.getIsTop())){
            holder.ivTop.setVisibility(View.GONE);
        }
        if ("Y".equals(data.getIsTop())){
            holder.ivTop.setVisibility(View.VISIBLE);

        }
        if ("Y".equals(data.getIsRead())){
            System.out.println("已读");
            holder.imRead.setVisibility(View.GONE);
        }
        if ( "N".equals(data.getIsRead())){
            holder.imRead.setVisibility(View.VISIBLE);
        }
        holder.tvDate.setText(data.getUpdated());
        holder.tvTitle.setText(data.getTitle());

    }
    @Override
    public int getItemCount() {

        return datas.size();
    }
    /**
     * 初始化组件
     */
    class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ivTop;
        private final TextView tvTitle;
        private final TextView tvDate;
        private final ImageView imRead;
        private final ImageView imClick;

        private Notify notify;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTop = itemView.findViewById(R.id.iv_top);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imRead = itemView.findViewById(R.id.im_new);
            tvDate = itemView.findViewById(R.id.tv_send_time);
            imClick = itemView.findViewById(R.id.im_onclick);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(v , datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
    /**
     * 当点击RecyclerView某条的监听
     */
    public interface OnItemClickListener{
        /**
         * 当RecyclerView某个被点击的时候回调
         * @param data 点击得到的数据
         */
        void onItemClick(View view, Notify data);

    }
    private OnItemClickListener onItemClickListener;
    /**
     * 设置RecyclerView某个监听
     */
    public  void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
