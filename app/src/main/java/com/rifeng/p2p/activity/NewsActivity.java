package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;
import com.google.gson.JsonObject;
import com.rifeng.p2p.HomeActivity;
import com.rifeng.p2p.R;
import com.rifeng.p2p.adapter.NewsAdapter;
import com.rifeng.p2p.entity.Notify;
import com.rifeng.p2p.entity.NotifyResponse;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.http.RFReqeustMananger;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.view.RFProgressHud;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class NewsActivity extends BaseActivity {

    @BindView(R.id.iv_base_back)
    ImageView imBack;
    @BindView(R.id.rv_recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_set_all_read)
    TextView tvSetAllRead;

    @BindView(R.id.text_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.srl_news)
    SmartRefreshLayout srl_news;

    @BindView(R.id.scrollerLayout)
    ConsecutiveScrollerLayout scrollerLayout;
    private LinearLayoutManager linearLayoutManager;
    private NewsAdapter newsAdapter;
    private List<Notify> newsData = new ArrayList<>();

    private int mIndex = 1;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    newsAdapter.setDatas(newsData);
                    newsAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_news;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {

        tvToolbarTitle.setText("Announcement");
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(this, newsData);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(newsAdapter);

    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initData() {
        refresh();
        onLoadMoreData(true);
        onClick();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void refresh() {
//        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                mIndex = 1;
//                newsData.clear();
//                onLoadMoreData(true); //刷新
//            }
//        });
//        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                mIndex++;
//                onLoadMoreData(false); //更多数据
//            }
//        });
        srl_news.setOnRefreshLoadMoreListener(new com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull com.scwang.smart.refresh.layout.api.RefreshLayout refreshLayout) {
                mIndex++;
                onLoadMoreData(false); //更多数据
            }

            @Override
            public void onRefresh(@NonNull @NotNull com.scwang.smart.refresh.layout.api.RefreshLayout refreshLayout) {
                mIndex = 1;
                newsData.clear();
                onLoadMoreData(true); //刷新
            }
        });

        srl_news.setOnMultiListener(new SimpleMultiListener(){
            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {
                super.onFooterMoving(footer, isDragging, percent, offset, footerHeight, maxDragHeight);
                scrollerLayout.setStickyOffset(offset);
            }
        });
    }

    public void onClick() {
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Notify data) {
                Bundle bundle = new Bundle();
                bundle.putString("content", data.getContent());
                bundle.putString("title", data.getTitle());
                bundle.putString("startTime", data.getStartTime());
                navigateToWithBundle(AnnouncementActivity.class, bundle);
                if ("N".equals(data.getIsRead())) {
                    setReaded(Long.parseLong(data.getId()), "N"); //设置成已读
                    onLoadMoreData(true);
                }
            }
        });
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCurrentActivity();
            }
        });
        tvSetAllRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSetAllReaded();
            }
        });
    }

    @SuppressLint("CheckResult")
    public void onLoadMoreData(final boolean isRefresh) {
        User user = DataManager.getInstance().getUser();
        if (user == null) {
            return;
        }
        RequestBody body = null;
        try {
            JSONObject obj = new JSONObject();
            obj.put("current", this.mIndex);
            obj.put("pageSize", 10);
            JSONObject paramObj = new JSONObject();
            obj.put("params", paramObj);
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRFService.getNotifies(body)
                .compose(RXHelper.RFFlowableTransformer())
                .observeOn(Schedulers.io())
                .filter(new Predicate<NotifyResponse>() {
                    @Override
                    public boolean test(NotifyResponse dataInfoRFResponse) throws Exception {
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NotifyResponse>() {
                    @Override
                    public void accept(NotifyResponse t) throws Exception {
                        RFReqeustMananger.getInstance();

                        if (isRefresh) {
                            srl_news.finishRefresh(true);
                        } else {
                            srl_news.finishLoadMore(true);
                        }

                        List<Notify> notifies = t.getRecords();
                        if (notifies != null && notifies.size() > 0) {
                            if (isRefresh) {
                                newsData = notifies;
                            } else {
                                newsData.addAll(notifies);
                            }
                            mHandler.sendEmptyMessage(0);
                        } else {

                            if (isRefresh) {
                                showToast("No data temporarily");
                            } else {

                                showToast("No more data");
                            }
                        }
                    }
                }, mThrowableConsumer, mFinishAction);
    }

    @SuppressLint("CheckResult")
    public void setReaded(Long id, String isRead) {
        mRFService.setReaded(id, isRead)
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<Notify>() {
                    @Override
                    public void accept(Notify notify) throws Exception {

                        onLoadMoreData(true);
                    }
                }, mThrowableConsumer, mFinishAction);
    }

    //执行函数
    private void doSetAllReaded() {
        Iterator<Notify> iterator = newsData.listIterator();
        boolean isNull = false;
        while (iterator.hasNext()) {
            if ("N".equals(iterator.next().getIsRead())) {
                isNull = true;
            }
        }
        if (isNull) {
            setAllReaded();
        } else {
            showToast("No unread messages");
        }
    }

    //设置全部为已读接口
    @SuppressLint("CheckResult")
    public void setAllReaded() {
        mRFService.setAllReaded()
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String notify) throws Exception {
                        onLoadMoreData(true);
                        openDialog();
                    }
                }, mThrowableConsumer, mFinishAction);
    }

    public void openDialog() {
        RFProgressHud.showSuccessMsg(this, "Successful operation");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeCurrentActivity();
        }
        return super.onKeyDown(keyCode, event);
    }
}
