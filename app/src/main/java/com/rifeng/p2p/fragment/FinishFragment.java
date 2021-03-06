package com.rifeng.p2p.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.NewReportActivity;
import com.rifeng.p2p.adapter.RecyclerViewListAdapter;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.entity.PressureRecord;
import com.rifeng.p2p.entity.PressureRecordResponse;
import com.rifeng.p2p.entity.TabItem;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.http.RFReqeustMananger;
import com.rifeng.p2p.http.RFService;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.uphidescrollview.BaseListFragment;
import com.rifeng.p2p.util.StringUtil;
import com.rifeng.p2p.util.UIUtils;
import com.rifeng.p2p.view.RFProgressHud;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishFragment extends BaseListFragment {

    protected RFService mRFService  ;
    private RecyclerView rvRecyclerViewFinish;
    protected RecyclerView mRecyclerView;

    private List<PressureRecord> datas = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    private TabItem mTabItem;
    private View mRootView;
    private RecyclerViewListAdapter mListAdapter;
    private LinearLayout llDataList, llParant;
    private LinearLayout llSearch;
    private View llDefault;
    private HomeFragment homeFragment;

    private EditText seachnFiniIsh;

    private TextView tvNullResult;

    private ImageView imSearch;

    private RefreshLayout refreshLayout ;

    private final ArrayList<PressureRecord> pressureRecords = new ArrayList<>();

    private int pageNum = 1;

    //    @Bind(R.id.btn_cancel)
    Button btnCancel;
    //    @BindView(R.id.btn_determin)
    Button btnDetermin;

    TextView tvWarning;

    private AlertDialog dlg;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    System.out.println("?????????????????????" + datas);
                    mListAdapter.setData(datas);
                    mListAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    mListAdapter.setData(datas);
                    mListAdapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_finish, null);
        homeFragment = new HomeFragment();
        homeFragment.getContext();
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_recyclerView_finish);
        llDefault = mRootView.findViewById(R.id.ll_default);
//        llDataList = mRootView.findViewById(R.id.ll_data_list);
//        llParant = mRootView.findViewById(R.id.ll_parant);
        llSearch = mRootView.findViewById(R.id.ll_search);
        tvNullResult = mRootView.findViewById(R.id.tv_result);
        return mRootView;
    }

    public View getContent(){
        return mRootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = view.getContext();
        seachnFiniIsh = view.findViewById(R.id.seach_in_finish);
        imSearch = view.findViewById(R.id.im_search);
        initData();
        onClick();
    }
    private final int mLoginMode = -1;
    @Override
    public void onResume() {
        super.onResume();

        Log.i("===========", "FinishFragment");
        //?????????????????????????????????
        pageNum = 1 ;
        onLoadMoreData(true ,seachnFiniIsh.getText().toString());
    }
    @Override
    protected View getSlidableView() {
        return mRecyclerView;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    private void initData() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListAdapter = new RecyclerViewListAdapter(getContext());
        mRecyclerView.setAdapter(mListAdapter);
        onLoadMoreData(true ,seachnFiniIsh.getText().toString());
    }

    public void onClick(){

        mListAdapter.buttonSetOnclick(new RecyclerViewListAdapter.ButtonInterface() {
            @Override
            public void onClickEmail(View view, PressureRecord pressureRecord) {
                openDialog(pressureRecord.getTestId());
            }
            @Override
            public void onClickReport(View view, PressureRecord pressureRecord) { //????????????
                Bundle bundle   = new Bundle();
                System.out.println("??????testId:" + pressureRecord.getTestId());
                bundle.putString("testId", pressureRecord.getTestId());
                navigateToWithBundle(NewReportActivity.class , bundle);
            }
        });
        //??????????????????
        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadMoreData(true ,seachnFiniIsh.getText().toString());
            }
        });
        //????????????
        seachnFiniIsh.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    System.out.println("??????11111");
                    onLoadMoreData(true ,seachnFiniIsh.getText().toString());
                }
                return false;
            }
        });
    }
    /**
     *
     */
    @SuppressLint("CheckResult")
    public  void onLoadMoreData(final boolean isRefresh  , String projectName) {

        User user = DataManager.getInstance().getUser();
        mRFService = RetrofitFactory.getInstance();
        if (user == null) {
            return;
        }
        RequestBody body = null;
        try {
            JSONObject obj = new JSONObject();
            obj.put("agencyId", user.getAgentId());
            obj.put("current", this.pageNum);
            obj.put("pageSize",10);
            JSONObject paramObj = new JSONObject();
            paramObj.put("agencyId",user.getAgentId());
            paramObj.put("userId",user.getUserId());

            System.out.println("????????????" + seachnFiniIsh.getText().toString());
            paramObj.put("projectName",projectName);

            obj.put("params",paramObj);
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRFService.getPressureRecord(body)

                .compose(RXHelper.RFFlowableTransformer())
                .observeOn(Schedulers.io())
                .filter(new Predicate<PressureRecordResponse>() {
            @Override
            public boolean test(PressureRecordResponse dataInfoRFResponse) throws Exception {
                return true;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PressureRecordResponse>() {
                    @Override
                    public void accept(PressureRecordResponse t) throws Exception {
                        RFReqeustMananger.getInstance();
                        if (t.getCode() == 0){
                            List<PressureRecord> list = t.getRecords();
                            if (list != null && list.size() > 0){
                                if (isRefresh){
                                    llDefault.setVisibility(View.GONE);
                                    datas = list;
                                }
                                else {
                                    llDefault.setVisibility(View.GONE);
                                    datas.addAll(list);
                                }
                                mHandler.sendEmptyMessage(0);
                            }else {
                                if (isRefresh){
                                    datas = t.getRecords();
                                    if (!StringUtil.isEmpty(seachnFiniIsh.getText().toString())){
                                        llDefault.setVisibility(View.VISIBLE);
                                        datas = list;
                                    }
                                    llDefault.setVisibility(View.VISIBLE);
                                    tvNullResult.setText("No such record");
                                    mHandler.sendEmptyMessage(0);
                                }
                                else {
//
                                    llDefault.setVisibility(View.GONE);
//                                    System.out.println("??????????????????");
                                    showToast("No more Data");
//                                    tvNullResult.setText("No more record");
                                    mHandler.sendEmptyMessage(0);
                                }

                            }
                        }
                    }
                } , mThrowableConsumer, mFinishAction);
    }
    //????????????????????????
    public void sendEmail(String testId){
        mRFService.getRecordChartForPDFAndSendMail(testId)
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
//
                        RFProgressHud.showSuccessMsg(getActivity() , "E_Mail Send Success!" );
                    }
                }, mThrowableConsumer, mFinishAction);
    }
    public void openDialog( String testId){
        //???????????????
        dlg = new AlertDialog.Builder(getActivity()).create();
        dlg.show();//???????????????
        Window window = dlg.getWindow();//?????????????????????
        window.setGravity(Gravity.CENTER);//???????????????
//            window.setWindowAnimations(R.style); //????????????
        window.setContentView(R.layout.quit_dialog_layout);
        btnDetermin = window.findViewById(R.id.btn_determin); //???????????????????????????
        btnCancel = window.findViewById(R.id.btn_cancel);//???????????????????????????
        tvWarning = window.findViewById(R.id.tv_warning);
        tvWarning.setText("Are you sure to send the email?");
        btnCancel .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();//?????????????????????
            }
        });
        btnDetermin .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(testId);
                dlg.dismiss();//?????????????????????
            }
        });
    }
    public void onLoadMore(final SmartRefreshLayout layout){ //??????????????????
        // ????????????3??????
        if (StringUtil.isEmpty(seachnFiniIsh.getText().toString())){
            pageNum = pageNum + 1;
            onLoadMoreData(false ,seachnFiniIsh.getText().toString());
        }
        else { //???????????????????????????????????????????????????
            onLoadMoreData(true , seachnFiniIsh.getText().toString());
        }
        System.out.println("???????????????finish");
        layout.finishLoadMore(1000);
    }
    public void refresh(final SmartRefreshLayout layout){
        System.out.println("????????????");
        pageNum = 1 ;
        onLoadMoreData(true ,seachnFiniIsh.getText().toString());
        layout.finishRefresh(1000);
    }
}
