package com.rifeng.p2p.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.Report;

import java.util.List;

public class ReportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mComtext;
    private List<Report> list;

    public ReportListAdapter(Context context, List<Report> list) {
        this.mComtext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mComtext).inflate(R.layout.report_item_layout,parent,false);
         MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Report report = list.get(position);
        myViewHolder.tvTitle.setText(report.getVtitle());
        myViewHolder.tvAuthor.setText(report.getCategoryName());
//        myViewHolder.tvDz.setText(report.);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvDz;
        private TextView tvComment;
        private TextView tvCollect;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            tvTitle = itemView.findViewById(R.id.title);
            tvAuthor = itemView.findViewById(R.id.author);
            tvDz = itemView.findViewById(R.id.dz);
            tvComment = itemView.findViewById(R.id.comment);
            tvTitle = itemView.findViewById(R.id.collect);

        }
    }
}
