package com.rifeng.p2p.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.Agreement;
import com.rifeng.p2p.entity.PressureParameterEntity;

import java.util.ArrayList;
import java.util.List;

public class PressureTestAdaper  extends RecyclerView.Adapter<PressureTestAdaper.MyViewHolder> {

    private   Context context;
    private List<Agreement> datas; //接受传入数据

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    private OnItemClickListener mListener = null;

    public PressureTestAdaper(Context context ,List<Agreement> datas ) {
        this.context = context;
        this.datas = datas;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = (View) View.inflate(context, R.layout.item_grounp_layout,null);
        return new PressureTestAdaper.MyViewHolder(itemView);

    }
    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Agreement agreement  = datas.get(position);
        System.out.println("测压数据"+ agreement.getGroupName());
        holder.groupNameTextView.setText(agreement.getGroupName());
        holder.remarkTextView.setText(agreement.getRemark());

        holder.bgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onItemClick(v, position, agreement);
                }
            }
        });
//        holder.itemView.setOnClickListener();
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView groupNameTextView;
        private  TextView kpaTextView;
        private TextView remarkTextView;
        private LinearLayout bgLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            groupNameTextView = itemView.findViewById(R.id.group_name_textview);
            kpaTextView = itemView.findViewById(R.id.kpa_textview);
            remarkTextView = itemView.findViewById(R.id.group_remark_textview);
            bgLayout = itemView.findViewById(R.id.item_group_bg_layout);
        }
    }


    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, int position, T item);
        boolean onItemLongClick(View itemView, int position, T item);

    }
}
