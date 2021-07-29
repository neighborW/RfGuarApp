package com.rifeng.p2p;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.rifeng.p2p.activity.BaseActivity;
import com.rifeng.p2p.activity.PressureTestActivity;
import com.rifeng.p2p.entity.BleRecords;
import com.rifeng.p2p.entity.PressureTestModel;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.fragment.HomeFragment;
import com.rifeng.p2p.fragment.MineFragment;
import com.rifeng.p2p.manager.PressureTestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.iv_add)
    ImageView imageView;
    @BindView(R.id.iv_home)
    ImageView imageView_home;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_test)
    ImageView im_mine;
    @BindView(R.id.vt_test)
    TextView tvMine;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;
    @BindView(R.id.fl_main)
    FrameLayout frameLayout;

    @BindView(R.id.fl_tab)
    FrameLayout flTab;
//    @Bind(R.id.btn_cancel)
    Button btnCancel;
//    @BindView(R.id.btn_determin)
    Button btnDetermin;
    //private AlertDialog dlg;
    @OnClick({R.id.ll_mine ,R.id.ll_home , R.id.ll_add})
    public void onViewClick(View view) {
        switch (view.getId()){
            case R.id.ll_home:
                setSelect(0);
                break;
            case R.id.ll_add:
                setSelect(1);
                break;
            case R.id.ll_mine:
                setSelect(2);
                break;
        }
    }
    private HomeFragment homeFragment  ;
   // private AddFragment addFragment ;
    private MineFragment mineFragment ;
    FragmentTransaction transaction;
    //提供相应的fragment
    private void setSelect(int i) {

        if (i != 1){
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            //隐藏所有的fragment
            hideFragment();
        }

        resetTab();

        switch (i){
            case 0:
                if (homeFragment == null){
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_main,homeFragment);
                }
                transaction.show(homeFragment);
                //选中后改变颜色
                imageView_home.setImageResource(R.mipmap.home_select);
                tvHome.setTextColor(llMine.getResources().getColor(R.color.selectColor));
                break;
            case 1:
//                Intent intent = new Intent(this , AddActivity.class);
//                startActivity(intent);
                PressureTestManager.getInstance().currentPressureTestModel = new PressureTestModel();
                navigateTo(PressureTestActivity.class);
                //finish();
                break;
            case 2:
                if (mineFragment == null){
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_main,mineFragment);
                }
                transaction.show(mineFragment);
                //选中时改变颜色
                im_mine.setImageResource(R.mipmap.mine_select);
                tvMine.setTextColor( llMine.getResources().getColor(R.color.selectColor));
                break;
        }

        if (i != 1) {
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("=============","HomeActivity");
    }

    //重新设置为没有被选中之前的样子
    private void resetTab() {
        imageView_home.setImageResource(R.mipmap.home_unselect);
        im_mine.setImageResource(R.mipmap.mine_unselect);
        tvHome.setTextColor(llMine.getResources().getColor(R.color.Color_Word45));
        tvMine.setTextColor(llMine.getResources().getColor(R.color.Color_Word45));
    }
    private void hideFragment() {
        if (homeFragment != null){
            transaction.hide(homeFragment);
        }
//        if (addFragment != null){
//            transaction.hide(addFragment);
//        }
        if (mineFragment != null){
            transaction.hide(mineFragment);
        }
    }



    //物理键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //创建对话框
            System.out.println("物理键退出按钮");
            AlertDialog dlg = new AlertDialog.Builder(this).create();
            dlg.show();//显示对话框
            Window window = dlg.getWindow();//获取对话框窗口
            window.setGravity(Gravity.CENTER);//对话框居中
//            window.setWindowAnimations(R.style); //设置样式
            window.setContentView(R.layout.quit_dialog_layout);
            btnDetermin = window.findViewById(R.id.btn_determin); //获取对话框确认按钮
            btnCancel = window.findViewById(R.id.btn_cancel);//获取对话框取消按钮
            btnCancel .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dlg.dismiss();//设置对话框消失
                }
            });
            btnDetermin .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dlg.dismiss();//对话框消失
                    removeCurrentActivity();
                    finish(); //关闭当前界面，退出应用
                }
            });
        }
        return false;
    }
    //处理对话框中的按钮事件
    private void initEvent() {
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }
    @Override
    protected void initView() {

//        flTab.getBackground().setAlpha(100);
    }
    @Override
    protected void initData() {
        setSelect(0); //打开首页
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    public void onLoadMoreData(){

//        User user = CommonDataManager.getInstance().getUser();
//        if (user == null) {
//            return;
//        }
    }


}
