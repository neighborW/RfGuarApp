package com.rifeng.p2p.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rifeng.p2p.R;

import java.util.ArrayList;

public class PipCodeAdpater extends RecyclerView.Adapter<PipCodeAdpater.MyViewHolder> {

    public interface  PipeCodeListener{
        public abstract void clickDeleteAtPostion(int postiong);
    }

    private PipeCodeListener pipeCodeListener = null;
    private final Context context;
    private ArrayList<String> datas; //接受传入数据
    private AdapterView.OnItemClickListener mOnItemClickListener;
    public PipCodeAdpater(Context context ,ArrayList<String> datas, PipeCodeListener listener) {
        this.context = context;
        this.datas = datas;
        this.pipeCodeListener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = (View)View.inflate(context ,R.layout.item_pipecode_layout ,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.pipeCode.setText(datas.get(position));
        holder.imPipeCode.setVisibility(View.VISIBLE);
        holder.imPipeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pipeCodeListener.clickDeleteAtPostion(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
     class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pipeCode;
        ImageView imPipeCode;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pipeCode = itemView.findViewById(R.id.tv_pipecode);
            imPipeCode = itemView.findViewById(R.id.pipecode_delete_imageview);
        }
    }

    class ItemClick{


    }

}
