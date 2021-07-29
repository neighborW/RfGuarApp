package com.rifeng.p2p.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.PressureRecord;
import com.rifeng.p2p.entity.Report;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caizhiming on 2016/8/13.
 */

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ItemViewHolder>{

    private final Context mContext;
    private final ArrayList<PressureRecord> mData = new ArrayList<>();
    private RefreshLayout mRefreshLayout;

    public RecyclerViewListAdapter(Context context){
        mContext =context;
    }
    public void setData(List<PressureRecord> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_report_layout,parent,false);
        return new ItemViewHolder(view);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_report_layout,null,false);
        System.out.println("数据长度1" + mData.size());
        if(holder instanceof ItemViewHolder){
            holder.bindData(mData.get(position),position);
        }
        PressureRecord report = mData.get(position);

        if ( "fail".equalsIgnoreCase(report.getTestResult())){ //当状态等于啥时，设置相应的颜色
            holder.llTestStatus.setBackgroundResource(R.drawable.item_fail);
            holder.Imfinish.setImageResource(R.mipmap.not_pass);
            holder.tvTestStatus.setTextColor(view.getResources().getColor(R.color.Color_Fail_Text));
        }
        if ("pass".equalsIgnoreCase(report.getTestResult())){
            holder.llTestStatus.setBackgroundResource(R.drawable.item_pass);
            holder.Imfinish.setImageResource(R.mipmap.pass_2x);
            holder.tvTestStatus.setTextColor(view.getResources().getColor(R.color.Color_Pass_Text));
        }
    }
    protected void closeLoadMore() {
        mRefreshLayout.finishLoadMore(false);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private final TextView testData;
        private final TextView companyName;
        private final TextView testResult;
        private final TextView email;
        private final TextView mobilePhone;
        private final TextView testingMethod;
        private final TextView projectName;
        private final LinearLayout llTestStatus;
        private final TextView tvTestStatus;
        private Button btnEmail;
        private final Button btnReport;
        private final ImageView Imfinish;

        public ItemViewHolder(View root){
            super(root);
            testData = itemView.findViewById(R.id.tv_test_time);
            testResult = itemView.findViewById(R.id.tv_test_status);
            companyName = itemView.findViewById(R.id.tv_company);
            email = itemView.findViewById(R.id.tv_email);
            mobilePhone = itemView.findViewById(R.id.tv_mobile_phone);
            testingMethod = itemView.findViewById(R.id.tv_testing_method);
            btnEmail = itemView.findViewById(R.id.btn_email);
            btnReport = itemView.findViewById(R.id.btn_report);
            llTestStatus = itemView.findViewById(R.id.ll_test_status);
            Imfinish = itemView.findViewById(R.id.im_finish);
            tvTestStatus = itemView.findViewById(R.id.tv_test_status);
            projectName = itemView.findViewById(R.id.tv_project_name);

            btnEmail = itemView.findViewById(R.id.btn_email);
        }
        public void bindData(final PressureRecord data, final int pos) {
            testData.setText(data.getTestDate());
            companyName.setText(data.getCompanyName());
            testResult.setText(data.getTestResult());
            email.setText(data.getEmail());
            mobilePhone.setText(data.getMobileNumber());
            testingMethod.setText(data.getTestMethod());

            projectName.setText(data.getProjectName());


            btnEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonInterface != null){
                        buttonInterface.onClickEmail(v , mData.get(getLayoutPosition()));
                    }
                }
            });
            btnReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonInterface != null){
                        buttonInterface.onClickReport(v , mData.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
    public void clear() {
        this.mData.clear();
    }

    /**
     * 当点击RecyclerView某条的监听
     */
    public interface OnItemClickListener{
        /**
         * 当RecyclerView某个被点击的时候回调
         * @param view 点击item的视图
         * @param data 点击得到的数据
         */
        void onItemClick(View view, PressureRecord data);
    }
    private OnItemClickListener onItemClickListener;
    /**
     * 设置RecyclerView某个监听
     */
    public  void setOnItemClickListener(RecyclerViewListAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private ButtonInterface buttonInterface;

    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        void onClickEmail(View view, PressureRecord pressureRecord);
        void onClickReport(View view, PressureRecord pressureRecord);

    }
}
