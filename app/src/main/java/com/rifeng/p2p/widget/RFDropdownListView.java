package com.rifeng.p2p.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.AddressItem;
import com.rifeng.p2p.entity.LookupCode;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RFDropdownListView extends RelativeLayout {

    private Context mC;

    private View rootView;
    private RecyclerView recyclerView;
//    private TextView titleView;
    RelativeLayout bgLayout;
//
//    private ImageView closeImageView;
    public List<LookupCode> lookupCodeList;

    public interface RFDropdownListViewListener {
        void onClickItem(LookupCode code);
        void onClickClose();
    }
    private RFDropdownListView.RFDropdownListViewListener dropdownListViewListener;


    public RFDropdownListView(Context context) {
        super(context);
        init(context);
    }

    public RFDropdownListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RFDropdownListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context) {
        mC = context;

        lookupCodeList = new ArrayList<LookupCode>();
        rootView = View.inflate(context, R.layout.popup_list_layout, this);

        recyclerView = rootView.findViewById(R.id.dropdown_recyleView);
//        closeImageView = rootView.findViewById(R.id.popup_close_imageview);
//        titleView = rootView.findViewById(R.id.popup_titleview);
        bgLayout = rootView.findViewById(R.id.popup_bg_click_layout);


        initList();
        initView();
        // loadData();
    }


    private void initList() {
//        closeImageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               if (dropdownListViewListener != null){
//                   dropdownListViewListener.onClickClose();
//               }
//            }
//        });
        bgLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dropdownListViewListener != null){
                   dropdownListViewListener.onClickClose();
               }
            }
        });

    }
    private DropdownListViewAdapter dropDownAdapter;
    private void initView() {
        //  addRadioButton("选择省");
        recyclerView.setLayoutManager(new LinearLayoutManager(mC));
        dropDownAdapter = new DropdownListViewAdapter(mC, lookupCodeList);
        recyclerView.setAdapter(dropDownAdapter);
//        if (addressBean != null) {
//            addressAdapter.setDatas(addressBean.getProvince());
//        }
    }

    /**
     * 初始化数据
     * 拿assets下的json文件
     */
    @SuppressLint("CheckResult")
    private void loadData() {



    }




    private class DropdownListViewAdapter extends RecyclerView.Adapter<DropdownListViewAdapter.DropdownListViewHolder> {
        private Context context;
        private List<LookupCode> datas;

        public DropdownListViewAdapter(Context context, List<LookupCode> datas) {
            this.context = context;
            if (datas == null) {
                this.datas = new ArrayList<>();
            } else {
                this.datas = datas;
            }
        }
        public void setDatas(List<LookupCode> datas) {
            if (datas == null) {
                this.datas = new ArrayList<>();
            } else {
                this.datas = datas;
            }
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public DropdownListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DropdownListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_popup_list, parent, false));
        }
        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(@NonNull DropdownListViewHolder holder, final int position) {
            holder.tv.setText(datas.get(position).getItem_name());
            holder.tv.setOnClickListener(new OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    //selectPostion(datas.get(position), true);
                    if (dropdownListViewListener!= null){
                        dropdownListViewListener.onClickItem(datas.get(position));
                    }
                    holder.tv.setTextColor(R.color.Color_Main);
                }
            });
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        @Override
        public int getItemCount() {
            return datas.size();
        }
        class DropdownListViewHolder extends RecyclerView.ViewHolder {

            public TextView tv;

            public DropdownListViewHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
            }
        }
    }

    public void setDropdownListViewListener(RFDropdownListViewListener dropdownListViewListener) {
        this.dropdownListViewListener = dropdownListViewListener;
    }

    public void setLookupCodeList(List<LookupCode> lookupCodeList) {
        this.lookupCodeList.clear();
        this.lookupCodeList.addAll(lookupCodeList);
        dropDownAdapter.notifyDataSetChanged();
    }
}
