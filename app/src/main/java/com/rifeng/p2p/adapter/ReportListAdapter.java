package com.rifeng.p2p.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.Report;

import java.util.ArrayList;

public  class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.MyViewHolder> {

    private Context mComtext;
    private ArrayList<Report> list;

    public ReportListAdapter(Context context, ArrayList<Report> list) {
        this.mComtext = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = View.inflate(mComtext , R.layout.item_report_layout , null);
        return new MyViewHolder(viewItem);
    }
    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Report report = list.get(position);
        if ( "pass".equals(report.getStatus())){ //当状态等于啥时，设置相应的颜色
            System.out.println("通过状态");
            holder.llTestStatus.setBackgroundResource(R.color.Color_Pass);
        }
        if ("Fail".equalsIgnoreCase(report.getStatus())){
            holder.llTestStatus.setBackgroundResource(R.color.Color_Fail);
        }

        holder.dateDay.setText(report.getCurrentDay());

        holder.status.setText(report.getStatus());

        holder.company.setText(report.getCompany());

        holder.email.setText(report.getEmail());
        holder.mobilePhone.setText(report.getMobilePhone());

        holder.testingMethod.setText(report.getTestingMethod());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView dateDay;
        private TextView company;
        private TextView status;
        private TextView email;
        private TextView mobilePhone;
        private TextView testingMethod;
        private LinearLayout llTestStatus;
        private Button btnEmail;
        private Button btnReport;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateDay = itemView.findViewById(R.id.tv_test_time);
            status = itemView.findViewById(R.id.tv_test_status);
            company = itemView.findViewById(R.id.tv_company);
            email = itemView.findViewById(R.id.tv_email);
            mobilePhone = itemView.findViewById(R.id.tv_mobile_phone);
            testingMethod = itemView.findViewById(R.id.tv_testing_method);
            btnEmail = itemView.findViewById(R.id.btn_email);
            btnReport = itemView.findViewById(R.id.btn_report);
            llTestStatus = itemView.findViewById(R.id.ll_test_status);

            btnReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(v , list.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view , Report data);
    }
    private OnItemClickListener onItemClickListener;
    /**
     * 设置RecyclerView某个监听
     * @param onItemClickListener
     */
    public  void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
