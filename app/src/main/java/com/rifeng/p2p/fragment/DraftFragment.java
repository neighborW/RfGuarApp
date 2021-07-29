package com.rifeng.p2p.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.PressureTestActivity;
import com.rifeng.p2p.adapter.RecyclerDraftListAdapter;
import com.rifeng.p2p.adapter.RecyclerViewListAdapter;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.db.DBManager;
import com.rifeng.p2p.entity.PipeCodeModel;
import com.rifeng.p2p.entity.PipeDiagramModel;
import com.rifeng.p2p.entity.PressureRecord;
import com.rifeng.p2p.entity.PressureResultBean;
import com.rifeng.p2p.entity.PressureTestModel;
import com.rifeng.p2p.entity.Report;
import com.rifeng.p2p.entity.TabItem;
import com.rifeng.p2p.manager.PressureTestManager;
import com.rifeng.p2p.uphidescrollview.BaseListFragment;
import com.rifeng.p2p.util.DataUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class DraftFragment extends BaseListFragment {

    public enum CurrentSelectType{
        CurrentSelectTypeComplete,
        CurrentSelectTypeTesting,
        CurrentSelectTypeInvalid,
        CurrentSelectTypeExpiring
    }

    protected CurrentSelectType currentSelectType = CurrentSelectType.CurrentSelectTypeComplete;
    protected RecyclerView mRecyclerView;
    private TabItem mTabItem;
    private View mRootView;
    private RecyclerDraftListAdapter mListAdapter;
    private LinearLayout llDefault  , textList;
    private TextView tvCompeted, tvTesting, tvInvalid, tvExpiring;
    private int currentSelectedIndex = 0;

    ArrayList<PressureTestModel> pressureTestModelList = new ArrayList<PressureTestModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_draft, null);
        tvCompeted = mRootView.findViewById(R.id.tv_complete);
        tvTesting = mRootView.findViewById(R.id.tv_testing);
        tvInvalid = mRootView.findViewById(R.id.tv_invalid);
        tvExpiring = mRootView.findViewById(R.id.tv_expiring);
        llDefault = mRootView.findViewById(R.id.ll_draft_list);
        textList = mRootView.findViewById(R.id.text_list);
        return mRootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = view.getContext();
        searchOnClick();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_recyclerView_draft);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListAdapter = new RecyclerDraftListAdapter(getContext());
        mRecyclerView.setAdapter(mListAdapter);

       // mRecyclerView.setNestedScrollingEnabled(false);
        //设置默认选择第一个
        setSelect(0);
        getDataFromLocal();

        if (pressureTestModelList.size() == 0) {
            llDefault.setVisibility(View.VISIBLE);
            textList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else {
            llDefault.setVisibility(View.GONE);
            textList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mListAdapter.setData(pressureTestModelList);
        mListAdapter.buttonSetOnclick(new RecyclerDraftListAdapter.ButtonInterface() {
            @Override
            public void onClickLeftBtn(View view, PressureTestModel pressureRecord) { //重新测试


                PressureTestManager.getInstance().currentPressureTestModel = pressureRecord;
                PressureTestManager.getInstance().currentPressureTestModel.getPipeCodeList().clear();;
                PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList().clear();
                PressureTestManager.getInstance().currentPressureTestModel.resultList.clear();
                PressureTestManager.getInstance().currentPressureTestModel.setCurrentState("0");
                PressureTestManager.getInstance().enterType = PressureTestManager.EnterType.EnterTypeRetest;

                Intent testActivity = new Intent(mContext, PressureTestActivity.class);
                startActivity(testActivity);

            }
            @Override
            public void onClickRightBtn(View view, PressureTestModel pressureRecord) {  //多个测试结果的按钮监听事件

                PressureTestManager.getInstance().currentPressureTestModel = pressureRecord;

                PressureTestManager.getInstance().currentPressureTestModel.getPipeCodeList().clear();;
                PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList().clear();


                List<PipeCodeModel> pipeCodeList = DBManager.getInstance(BaseApp.getInstance()).getPipeCodeList(pressureRecord.getTempTestId());
                for (PipeCodeModel pipeCode : pipeCodeList){
                    PressureTestManager.getInstance().currentPressureTestModel.getPipeCodeList().add(pipeCode.getPipeCode());
                }

                List<PipeDiagramModel> pipeDiagramList = DBManager.getInstance(BaseApp.getInstance()).getPipeDiagramPath(pressureRecord.getTempTestId());
                for (PipeDiagramModel pipeDiagram : pipeDiagramList){
                    PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList().clear();
                    PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList().addAll(pipeDiagramList);

                }

                PressureTestManager.getInstance().currentPressureTestModel.resultList.clear();
                List<PressureResultBean> resultList = DBManager.getInstance(BaseApp.getInstance()).getResultListByTempTestId(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId());
                PressureTestManager.getInstance().currentPressureTestModel.resultList.addAll(resultList);

                //submit
                if ("2".equals(pressureRecord.getCurrentState()) || "3".equals(pressureRecord.getCurrentState())){
                    PressureTestManager.getInstance().enterType = PressureTestManager.EnterType.EnterTypeSubmit;


                }else{
                    PressureTestManager.getInstance().enterType = PressureTestManager.EnterType.EnterTypeContinue;
                }

                Intent testActivity = new Intent(mContext, PressureTestActivity.class);
                startActivity(testActivity);
            }
        });
    }



    private  void loadData(){


    }
    private int mLoginMode = -1;

    @Override
    public void onResume() {
        super.onResume();
        //进入页面重新获取数据
        setSelect(currentSelectedIndex);


        Log.i("=============","DraftFragment");
    }

    @Override
    protected View getSlidableView() {
        return mRecyclerView;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public void searchOnClick() {
        tvCompeted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //调用查询数据接口

                setSelect(0);
            }
        });
        tvTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelect(1);
            }
        });
        tvInvalid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelect(3);
            }
        });
        tvExpiring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelect(2);
            }
        });
    }

    public void setSelect(int view) {

        setTv();
        Log.i("view: " , "" + view);
        currentSelectedIndex = view;
        switch (view) {
            case 0:
                currentSelectType = CurrentSelectType.CurrentSelectTypeComplete;
                mListAdapter.setStatus(currentSelectType);
                tvCompeted.setBackgroundResource(R.drawable.shape_selected_background);
                tvCompeted.setTextColor(mRootView.getResources().getColor(R.color.white));
                break;
            case 1:
                currentSelectType = CurrentSelectType.CurrentSelectTypeTesting;
                mListAdapter.setStatus(currentSelectType);
                tvTesting.setBackgroundResource(R.drawable.shape_selected_background);
                tvTesting.setTextColor(mRootView.getResources().getColor(R.color.white));
                break;

            case 2:
                currentSelectType = CurrentSelectType.CurrentSelectTypeExpiring;
                mListAdapter.setStatus(currentSelectType);
                tvExpiring.setBackgroundResource(R.drawable.shape_selected_background);
                tvExpiring.setTextColor(mRootView.getResources().getColor(R.color.white));
                break;
            case 3:
                currentSelectType = CurrentSelectType.CurrentSelectTypeInvalid;
                mListAdapter.setStatus(currentSelectType);
                tvInvalid.setBackgroundResource(R.drawable.shape_selected_background);
                tvInvalid.setTextColor(mRootView.getResources().getColor(R.color.white));
                break;
        }

        //重新获取数据
        getDataFromLocal();

    }

    private void setTv() {
        tvCompeted.setTextColor(mRootView.getResources().getColor(R.color.tv_draft_type));
        tvCompeted.setBackgroundResource(R.color.tv_draft_background);
        tvTesting.setTextColor(mRootView.getResources().getColor(R.color.tv_draft_type));
        tvTesting.setBackgroundResource(R.color.tv_draft_background);
        tvInvalid.setTextColor(mRootView.getResources().getColor(R.color.tv_draft_type));
        tvInvalid.setBackgroundResource(R.color.tv_draft_background);
        tvExpiring.setTextColor(mRootView.getResources().getColor(R.color.tv_draft_type));
        tvExpiring.setBackgroundResource(R.color.tv_draft_background);
    }

    @SuppressLint("LongLogTag")
    public void getDataFromLocal(){

        pressureTestModelList.clear();

        if (currentSelectType == CurrentSelectType.CurrentSelectTypeComplete){

            pressureTestModelList.addAll( DBManager.getInstance(BaseApp.getInstance()).getCompletedPressureTestModel());

        }else if(currentSelectType == CurrentSelectType.CurrentSelectTypeTesting){
            pressureTestModelList.addAll( DBManager.getInstance(BaseApp.getInstance()).getTestingPressureTestModel());
        }else if(currentSelectType == CurrentSelectType.CurrentSelectTypeExpiring){
            pressureTestModelList.addAll( DBManager.getInstance(BaseApp.getInstance()).getExpiringPressureTestModel());
        }else if(currentSelectType == CurrentSelectType.CurrentSelectTypeInvalid){
            pressureTestModelList.addAll( DBManager.getInstance(BaseApp.getInstance()).getInvalidPressureTestModel());
        }

        System.out.println("草稿测试数据" + pressureTestModelList);
        Log.i("pressureTestModelList size: ","" + pressureTestModelList.size());
        mListAdapter.notifyDataSetChanged();

        if (pressureTestModelList.size() == 0) {
            llDefault.setVisibility(View.VISIBLE);
            textList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

        }
        else {
            llDefault.setVisibility(View.GONE);
            textList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    public void getItemClick() {



    }
    public void onLoadMore(final SmartRefreshLayout layout){
        // 模拟加载5秒钟
        layout.setEnableLoadMore(false);
        layout.finishLoadMore(1000);
    }
    public void refresh(final SmartRefreshLayout layout){
        getDataFromLocal();
        System.out.println("刷新加载数据");
//        onLoadMoreData(true ,seachnFiniIsh.getText().toString());
        layout.finishRefresh(1000);
    }
}
