package com.rifeng.p2p.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;

import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;
import com.donkingliang.consecutivescroller.ConsecutiveViewPager;
import com.google.android.material.tabs.TabLayout;
import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.NewsActivity;
import com.rifeng.p2p.activity.PressureTestActivity;
import com.rifeng.p2p.adapter.TabPagerAdapter;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.db.DBManager;
import com.rifeng.p2p.entity.ReportData;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.http.RFService;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.uphidescrollview.BaseListFragment;
import com.rifeng.p2p.uphidescrollview.ScrollViewPagerAdapter;
import com.rifeng.p2p.view.RFNoticeDialog;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.rifeng.p2p.R.id;


public class HomeFragment extends BaseFragment {

    private TextView tvTotalData, tvPassData, tvPassingRateData;
    private ImageView imNew;

    private TextView tvNotRead;
    private BaseListFragment fragment;
    private ConsecutiveScrollerLayout scrollerLayout;
    private ConsecutiveViewPager viewPager;
    private TabLayout tabLayout;
    private SmartRefreshLayout refreshLayout;

    private TabPagerAdapter mAdapter;
    private ScrollViewPagerAdapter mScrollViewPagerAdapter;
    protected RFService mRFService;

    private TextView tvUserName;

    private FinishFragment finishFragment;
    private DraftFragment draftFragment;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        onLoadNotRead();
        tabLayout = mRootView.findViewById(R.id.tab_layout_vp);
        viewPager = mRootView.findViewById(id.vp_viewPager);
        refreshLayout = mRootView.findViewById(id.refreshLayout);
        scrollerLayout = mRootView.findViewById(id.scrollerLayout);

        imNew = mRootView.findViewById(id.im_new);
        tvTotalData = mRootView.findViewById(R.id.tv_total_data);
        tvPassData = mRootView.findViewById(id.tv_pass_data);
        tvPassingRateData = mRootView.findViewById(id.tv_passing_rate);
        tvNotRead = mRootView.findViewById(id.tv_not_read);
        tvUserName = mRootView.findViewById(id.tv_user_name);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabTextView(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabTextView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        onLoadNotRead();
        onLoadData();

        Log.i("=============", "HomeFragment");
    }

    /**
     * 字体加粗变颜色
     *
     * @param tab
     * @param isBold
     */
    public void changeTabTextView(TabLayout.Tab tab, boolean isBold) {
        View view = tab.getCustomView();
        if (null == view) {
            tab.setCustomView(R.layout.tab_layout_text);
        }
        TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
        if (isBold) {
            textView.setTextAppearance(getActivity(), R.style.TabLayoutTextStyleSelect);
        } else {
            textView.setTextAppearance(getActivity(), R.style.TabLayoutTextStyle);
        }


    }


    @Override
    protected void initData() {
        setName();//设置名字
        onClick();
        setListener();
        onLoadData();
        onLoadNotRead();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadExpiringData();
            }
        }, 1000);//3秒后执行Runnable中的run方法

    }

    RFNoticeDialog noticeDialog;

    public void loadExpiringData() {


        //如果存在快过期的单据弹出提示
        List expiringList = DBManager.getInstance(BaseApp.getInstance()).getExpiringPressureTestModel();
        if (expiringList != null && expiringList.size() > 0) {

            String msg = String.format("There're %d pressure testing records which will be invalidated after %d day.Do you want to submit it?", expiringList.size(), 7);

            noticeDialog = new RFNoticeDialog(getActivity(), msg, "NO", "YES", new RFNoticeDialog.RFNoticeDialogListener() {

                @Override
                public void leftBtnClick() {


                }

                @Override
                public void rightBtnClick() {
                    tabLayout.getTabAt(1).select();
                    if (draftFragment != null) {
                        draftFragment.setSelect(2);
                    }


                }

                @Override
                public void centerBtnClick() {

                }
            });

            noticeDialog.show();

        }
    }

    //
//    public void setSelectBold(){
//
//        tabLayout.setOnTabSelectedListener(new Layout.);
//    }
    public void onClick() {
        imNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
    }


    public void setName() {
        User user = DataManager.getInstance().getUser();
        tvUserName.setText("Hi," + user.getUserName());
    }

    private void setListener() {
        mAdapter = new TabPagerAdapter(getFragmentManager(), getTabs(), (List<? extends BaseListFragment>) getFragments());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);


        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout r) {
                // 把加载的动作传给当初的fragment

                System.out.println("加载更多数据");
                fragment = mAdapter.getItem(viewPager.getCurrentItem());
                fragment.onLoadMore(refreshLayout);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout r) {
                //刷新更新数据
                onLoadNotRead();
                onLoadData();
                fragment = mAdapter.getItem(viewPager.getCurrentItem());
                fragment.refresh(refreshLayout);
            }
        });

        refreshLayout.setOnMultiListener(new SimpleMultiListener() {
            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {
                // 上拉加载时，保证吸顶头部不被推出屏幕。
//                System.out.println("上拉家加载" + offset);
                Log.e("eee", "****" + offset);
                scrollerLayout.setStickyOffset(offset);
            }
        });
    }

    //获取历史数据的统计
    public void onLoadData() {
        RetrofitFactory.getInstance().getReportData()
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<ReportData>() {
                    @Override
                    public void accept(ReportData list) throws Exception {
                        tvTotalData.setText(list.getReportNum().toString());
                        tvPassData.setText(list.getPassNum().toString());
                        tvPassingRateData.setText(String.format("%.1f", list.getRate()));
                    }
                }, mThrowableConsumer, mFinishAction);
    }

    @SuppressLint("CheckResult")
    public void onLoadNotRead() {
        RetrofitFactory.getInstance().getNotRead()
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<HashMap<String, Integer>>() {
                    @Override
                    public void accept(HashMap<String, Integer> list) throws Exception {

                        System.out.println("list" + list);

                        if (list.get("isNotReadNum") < 99 && list.get("isNotReadNum") > 0) {
                            if (tvNotRead.getVisibility() == View.GONE) {
                                tvNotRead.setVisibility(View.VISIBLE);
                            }
                            tvNotRead.setText(list.get("isNotReadNum").toString());
                        } else if (list.get("isNotReadNum") > 99) {
                            if (tvNotRead.getVisibility() == View.GONE) {
                                tvNotRead.setVisibility(View.VISIBLE);
                            }
                            tvNotRead.setText(99 + "+");
                        } else {
                            tvNotRead.setVisibility(View.GONE);
                        }
                    }
                }, mThrowableConsumer, mFinishAction);
    }

    private List<String> getTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add("Finished");
        tabs.add("Draft");
        return tabs;
    }

    // 提供给Fragment获取使用。
    public SmartRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    private List<BaseListFragment> getFragments() {
        List<BaseListFragment> fragmentList = new ArrayList<>();
        finishFragment = new FinishFragment();
        draftFragment = new DraftFragment();
        fragmentList.add(finishFragment);
        fragmentList.add(draftFragment);
        return fragmentList;
    }
}