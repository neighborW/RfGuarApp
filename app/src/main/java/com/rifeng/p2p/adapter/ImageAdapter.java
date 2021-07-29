package com.rifeng.p2p.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.rifeng.p2p.R;
import com.rifeng.p2p.config.Constract;

import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private LayoutInflater inflater;
    private ArrayList<String> datas; //接受传入数据

    public ImageAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_grid_image, viewGroup, false);
            holder.iv = view.findViewById(R.id.add_image);
            holder.deleteImage = view.findViewById(R.id.delete_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final String path = datas.get(i);

        if (path.equals(Constract.PLUS)) {
            holder.iv.setImageResource(R.mipmap.add_image);
        } else {

            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.image_thub)
                    .error(R.mipmap.image_thub)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.iv);
        }
        return view;
    }
    class ViewHolder {
        ImageView iv;
        ImageView deleteImage;
        LinearLayout llDelete;
    }
}
