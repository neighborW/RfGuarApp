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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.PressureRecord;
import com.rifeng.p2p.entity.PressureTestModel;
import com.rifeng.p2p.fragment.DraftFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDraftListAdapter extends RecyclerView.Adapter<RecyclerDraftListAdapter.ItemViewHolder> {


    private final Context mContext;
    private List<PressureTestModel> mData = new ArrayList<>();
    private RefreshLayout mRefreshLayout;

    private DraftFragment.CurrentSelectType status;

    public RecyclerDraftListAdapter(Context context){
        mContext =context;
    }

    public void setData(List<PressureTestModel> data){
//        mData.clear();
//        mData.addAll(data);
        mData = data;

        notifyDataSetChanged();
    }

    public void setStatus(DraftFragment.CurrentSelectType status){

        this.status = status;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_report_layout,parent,false);
        return new ItemViewHolder(view);
    }

    public String getResultByState(String state){
        if ("0".equals(state)){
            return "";
        }
        if ("1".equals(state)){
            return "";
        }
        if ("2".equals(state)){
            return "Fail";
        }
        if ("3".equals(state)){
            return "Success";
        }

        return "";
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_report_layout,null,false);
        PressureTestModel model = mData.get(position);
        holder.buttonBgLayout.setVisibility(View.VISIBLE);
        if (status == DraftFragment.CurrentSelectType.CurrentSelectTypeComplete){

            if ("2".equals(model.getCurrentState() )){
                  holder.testResult.setText(R.string.test_fail);
                holder.testResult.setTextColor(view.getResources().getColor(R.color.Color_Fail_Text));
                holder.llTestStatus.setBackgroundResource(R.drawable.item_fail);
            }
            else{
                holder.testResult.setText(R.string.test_pass);
                holder.testResult.setTextColor(view.getResources().getColor(R.color.Color_Pass_Text));
                holder.llTestStatus.setBackgroundResource(R.drawable.item_pass);
            }

            System.out.println("测试状态" + holder.testResult.getText());


        }
        if (status == DraftFragment.CurrentSelectType.CurrentSelectTypeTesting){
            holder.testResult.setText(R.string.test_testing);
            holder.testResult.setTextColor(view.getResources().getColor(R.color.textd_testing));
            holder.llTestStatus.setBackgroundResource(R.drawable.item_testing);
        }
        if (status  == DraftFragment.CurrentSelectType.CurrentSelectTypeInvalid){
            holder.testResult.setText(R.string.test_invalid);
            holder.testResult.setTextColor(view.getResources().getColor(R.color.textd_invalid));
            holder.llTestStatus.setBackgroundResource(R.drawable.item_invalid);
            holder.buttonBgLayout.setVisibility(View.GONE);
        }
        if (status == DraftFragment.CurrentSelectType.CurrentSelectTypeExpiring){
            holder.testResult.setText(R.string.test_expiring);
            holder.testResult.setTextColor(view.getResources().getColor(R.color.textd_expiring));
            holder.llTestStatus.setBackgroundResource(R.drawable.item_expiring);
        }


        holder.Imfinish.setVisibility(View.GONE);
        holder.btnRetest.setText("Retest");

        holder.testTime.setText(model.getTestTimeStr());
        holder.companyName.setText(model.getCompany());
//        holder.testResult.setText(getResultByState(model.getCurrentState()));
        holder.email.setText(model.getEmail());
        holder.mobilePhone.setText(model.getMobile());
        holder.testingMethod.setText(model.getTestMethod());
        holder.projectNameTextView.setText(model.getProjectName());


        if (model.getCurrentState().equals("2") || model.getCurrentState().equals("3")){
            holder.btnCommon.setText("Submit");
        }
        if (model.getCurrentState().equals("0") ||  model.getCurrentState().equals("1")){
            holder.btnCommon.setText("Continue");
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private final TextView testTime;
        private final TextView companyName;
        private final TextView testResult;
        private final TextView email;
        private final TextView mobilePhone;
        private final TextView testingMethod;
        private final LinearLayout llTestStatus;
        private final TextView tvTestStatus;
        private final TextView projectNameTextView;
        private final Button btnRetest;
        private final Button btnCommon;
        private final ImageView Imfinish;
        private final LinearLayout buttonBgLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            testTime = itemView.findViewById(R.id.tv_test_time);
            testResult = itemView.findViewById(R.id.tv_test_status);
            companyName = itemView.findViewById(R.id.tv_company);
            email = itemView.findViewById(R.id.tv_email);
            mobilePhone = itemView.findViewById(R.id.tv_mobile_phone);
            testingMethod = itemView.findViewById(R.id.tv_testing_method);
//            btnEmail = itemView.findViewById(R.id.btn_email);
            llTestStatus = itemView.findViewById(R.id.ll_test_status);
            Imfinish = itemView.findViewById(R.id.im_finish);
            tvTestStatus = itemView.findViewById(R.id.tv_test_status);
            projectNameTextView = itemView.findViewById(R.id.tv_project_name);
            btnCommon = itemView.findViewById(R.id.btn_report); //由于该适配器与原来的finish公用一个item，所以这里
            btnRetest = itemView.findViewById(R.id.btn_email);
            buttonBgLayout =  itemView.findViewById(R.id.draft_button_bg_layout);;
            btnRetest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonInterface != null){
                        buttonInterface.onClickLeftBtn(v , mData.get(getLayoutPosition()));
                    }
                }
            });
            btnCommon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonInterface.onClickRightBtn(v , mData.get(getLayoutPosition()));
                }
            });

        }

    }

    private RecyclerDraftListAdapter.ButtonInterface buttonInterface;
    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(RecyclerDraftListAdapter.ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }
    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        void onClickLeftBtn(View view, PressureTestModel model);
        void onClickRightBtn(View view, PressureTestModel model);
    }
}
