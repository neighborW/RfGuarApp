package com.rifeng.p2p.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.AddressItem;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class AddressSelectView extends RelativeLayout {

    private Context mC;


    private RecyclerView recyclerView;
    private TextView countryBtn;
    private TextView stateBtn;
    private TextView cityBtn;
    private TextView suburbBtn;
    private TextView currentSelectBtn;

    private Button btnSave;
    public List<AddressItem> addressList;

    private AddressItem countryModel;
    private AddressItem stateModel;
    private AddressItem cityModel;
    private AddressItem suburbModel;
    private String clickText; //回退时点击的文字

    private View countryView;
    private View stateView;
    private  View cityView;
    private  View suburbView;

    String parentId = "1";


    public AddressSelectView(Context context) {
        super(context);
        init(context);
    }

    public AddressSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddressSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private View rootView;

    private void init(Context context) {
        mC = context;
        clickText = "Select Area";
        addressList = new ArrayList<AddressItem>();
        rootView = View.inflate(context, R.layout.select_area_layout, this);
        countryBtn = rootView.findViewById(R.id.country_btn);
        stateBtn = rootView.findViewById(R.id.state_btn);
        cityBtn = rootView.findViewById(R.id.city_btn);
        suburbBtn = rootView.findViewById(R.id.suburb_btn);
        recyclerView = rootView.findViewById(R.id.address_recyleView);
        btnSave = rootView.findViewById(R.id.btn_save);

        countryView = rootView.findViewById(R.id.country_view);
        stateView = rootView.findViewById(R.id.state_view);
        cityView = rootView.findViewById(R.id.city_view);
        suburbView = rootView.findViewById(R.id.suburb_view);

       //updateCurrentSelectBtn();

       // updateButtonStatus();
        initList();
        initView();
       // loadData();
    }

    public void updateCurrentSelectBtn(){
        this.currentSelectBtn = this.countryBtn;
        if (this.countryModel != null) {
            this.currentSelectBtn = this.stateBtn;
        }

        if (this.stateModel != null) {
            this.currentSelectBtn = this.cityBtn;
        }

        if (this.cityModel != null) {
            this.currentSelectBtn = this.suburbBtn;
        }
        if (this.suburbModel != null) {
            this.currentSelectBtn = null;
        }
    }



    @SuppressLint("ResourceAsColor")
    public void updateButtonStatus(){

        if (this.currentSelectBtn == this.countryBtn) {
            this.countryBtn.setText(clickText);
            this.countryBtn.setTextColor(R.color.Color_Main);
            this.countryBtn.setVisibility(VISIBLE);

            countryView.setBackgroundResource(R.color.Color_Main);

            this.stateBtn.setVisibility(INVISIBLE);
            this.cityBtn.setVisibility(INVISIBLE);
            this.suburbBtn.setVisibility(INVISIBLE);


            stateView.setBackgroundResource(R.color.Color_Word25);
            cityView.setBackgroundResource(R.color.Color_Word25);
            suburbView.setBackgroundResource(R.color.Color_Word25);
            this.parentId = "1";
            this.countryModel = null;
            this.stateModel = null;
            this.cityModel = null;
            this.suburbModel = null;

        }else if (this.currentSelectBtn == this.stateBtn) {
            this.countryBtn.setText(this.countryModel.getLabel());
            this.countryBtn.setTextColor(Color.BLACK);

            stateView.setBackgroundResource(R.color.Color_Main);
            this.stateBtn.setText(clickText);
            this.stateBtn.setTextColor(R.color.Color_Main);


            countryView.setBackgroundResource(R.color.Color_Word25);
            cityView.setBackgroundResource(R.color.Color_Word25);
            suburbView.setBackgroundResource(R.color.Color_Word25);

            this.countryBtn.setVisibility(VISIBLE);
            this.stateBtn.setVisibility(VISIBLE);
            this.cityBtn.setVisibility(INVISIBLE);
            this.suburbBtn.setVisibility(INVISIBLE);

            this.parentId = this.countryModel.getId();
           this.stateModel = null;
            this.cityModel = null;
            this.suburbModel = null;
        }

        else if (this.currentSelectBtn == this.cityBtn) {

            this.countryBtn.setText(this.countryModel.getLabel());
            this.countryBtn.setTextColor(Color.BLACK);
            cityView.setBackgroundResource(R.color.Color_Main);

            this.stateBtn.setText(this.stateModel.getLabel());
            this.stateBtn.setTextColor(Color.BLACK);

            this.cityBtn.setText(clickText);
            this.cityBtn.setTextColor(R.color.Color_Main);

            countryView.setBackgroundResource(R.color.Color_Word25);
            stateView.setBackgroundResource(R.color.Color_Word25);
            suburbView.setBackgroundResource(R.color.Color_Word25);

            this.countryBtn.setVisibility(VISIBLE);
            this.stateBtn.setVisibility(VISIBLE);
            this.cityBtn.setVisibility(VISIBLE);
            this.suburbBtn.setVisibility(INVISIBLE);

            this.parentId = this.stateModel.getId();
            this.cityModel = null;
            this.suburbModel = null;


        }else if (this.currentSelectBtn == this.suburbBtn) {

            this.countryBtn.setText(this.countryModel.getLabel());
            this.countryBtn.setTextColor(Color.BLACK);
            suburbView.setBackgroundResource(R.color.Color_Word25);
            this.stateBtn.setText(this.stateModel.getLabel());
            this.stateBtn.setTextColor(Color.BLACK);

            this.cityBtn.setText(this.cityModel.getLabel());
            this.cityBtn.setTextColor(Color.BLACK);

            countryView.setBackgroundResource(R.color.Color_Word25);
            stateView.setBackgroundResource(R.color.Color_Word25);
            cityView.setBackgroundResource(R.color.Color_Word25);

            this.suburbBtn.setText(clickText);
            this.suburbBtn.setTextColor(Color.GRAY);

            this.countryBtn.setVisibility(VISIBLE);
            this.stateBtn.setVisibility(VISIBLE);
            this.cityBtn.setVisibility(VISIBLE);
            this.suburbBtn.setVisibility(VISIBLE);
            this.parentId = this.cityModel.getId();
            this.suburbModel = null;
        }else if (this.currentSelectBtn == null) {

            this.countryBtn.setText(this.countryModel.getLabel());
            this.countryBtn.setTextColor(Color.BLACK);

            this.stateBtn.setText(this.stateModel.getLabel());
            this.stateBtn.setTextColor(Color.BLACK);

            this.cityBtn.setText(this.cityModel.getLabel());
            this.cityBtn.setTextColor(Color.BLACK);

            this.suburbBtn.setText(this.suburbModel.getLabel());
            this.suburbBtn.setTextColor(Color.BLACK);


            this.parentId =this.suburbModel.getId();



            this.countryBtn.setVisibility(VISIBLE);
            this.stateBtn.setVisibility(VISIBLE);
            this.cityBtn.setVisibility(VISIBLE);
            this.suburbBtn.setVisibility(VISIBLE);

        }


     loadData();
    }

    private void initList() {
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectConfirmListner != null)
                    selectConfirmListner.onConfirm(countryModel,stateModel,cityModel,suburbModel);
            }
        });
        countryBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               currentSelectBtn = countryBtn;
               updateButtonStatus();
            }
        });
        stateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectBtn = stateBtn;
                updateButtonStatus();
            }
        });
        cityBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectBtn = cityBtn;
                updateButtonStatus();
            }
        });
        suburbBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectBtn = suburbBtn;
                updateButtonStatus();
            }
        });
    }
    private AddressAdapter addressAdapter;
    private void initView() {
      //  addRadioButton("选择省");
        recyclerView.setLayoutManager(new LinearLayoutManager(mC));
        addressAdapter = new AddressAdapter(mC, null);
        recyclerView.setAdapter(addressAdapter);
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


        RetrofitFactory.getInstance().getAreaList("TREE_PROVINCE",parentId)
                .compose(RXHelper.< List<AddressItem> >RFFlowableTransformer())
                .subscribe(new Consumer<List<AddressItem>>() {
                    @Override
                    public void accept(List<AddressItem> list) throws Exception {

                       addressList = list;
                        addressAdapter.setDatas(addressList);

                    }
                }, mThrowableConsumer, mFinishAction);

    }

    Consumer<Throwable> mThrowableConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable t) throws Exception {

        }
    };
    protected Action mFinishAction = new Action() {
        @Override
        public void run() throws Exception {

        }
    };
    /**
     * 选择一个位置后
     *
     * @param data
     */
    private void selectPostion(AddressItem data, boolean isAddData) {
        recyclerView.scrollToPosition(0);
        if (this.countryModel == null) {
            this.countryModel = data;
            this.currentSelectBtn = this.stateBtn;
            updateButtonStatus();
            return;
        }
        if (this.stateModel == null) {
            this.stateModel = data;
            this.currentSelectBtn = this.cityBtn;
            updateButtonStatus();
            return;
        }
        if (this.cityModel == null) {
            this.cityModel = data;
            this.currentSelectBtn = this.suburbBtn;
            updateButtonStatus();
            return;
        }
        if (this.suburbModel == null) {
            this.suburbModel = data;
            this.currentSelectBtn = null;
            updateButtonStatus();
            return;
        }
    }

    private class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Vh> {
        private Context context;
        private List<AddressItem> datas;

        public AddressAdapter(Context context, List<AddressItem> datas) {
            this.context = context;
            if (datas == null) {
                this.datas = new ArrayList<>();
            } else {
                this.datas = datas;
            }
        }
        public void setDatas(List<AddressItem> datas) {
            if (datas == null) {
                this.datas = new ArrayList<>();
            } else {
                this.datas = datas;
            }
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Vh(LayoutInflater.from(context).inflate(R.layout.item_address, parent, false));
        }
        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(@NonNull Vh holder, final int position) {
            holder.tv.setText(datas.get(position).getLabel());
            holder.tv.setOnClickListener(new OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    selectPostion(datas.get(position), true);
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
        class Vh extends RecyclerView.ViewHolder {

            public TextView tv;

            public Vh(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
            }
        }
    }
    interface OnTabClickListEner {
        void onTabClick(View view, int position);
    }
    private OnTabClickListEner onTabClickListEner;
    private void setOnTabClickListEner(OnTabClickListEner onTabClickListEner) {
        this.onTabClickListEner = onTabClickListEner;
    }

    public interface OnSelectConfirmListner {
        void onConfirm(AddressItem countryModel, AddressItem stateModel, AddressItem cityModel, AddressItem suburbModel);
    }
    public interface OnSelectCancleListner {
        void onCancle();
    }
    public OnSelectConfirmListner selectConfirmListner;
    public OnSelectCancleListner selectCancleListner;

    public void setOnSelectConfirmListEner(OnSelectConfirmListner onSelectConfirmListEner) {
        this.selectConfirmListner = onSelectConfirmListEner;
    }

    public void setOnSelectCancleListEner(OnSelectCancleListner onSelectCancleListEner) {
        this.selectCancleListner = onSelectCancleListEner;
    }

    public void setCountryModel(AddressItem countryModel) {
        this.countryModel = countryModel;
    }

    public void setStateModel(AddressItem stateModel) {
        this.stateModel = stateModel;
    }

    public void setCityModel(AddressItem cityModel) {
        this.cityModel = cityModel;
    }

    public void setSuburbModel(AddressItem suburbModel) {
        this.suburbModel = suburbModel;
    }
}
