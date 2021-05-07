package com.rifeng.p2p;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rifeng.p2p.fragment.AddFragment;
import com.rifeng.p2p.fragment.HomeFragment;
import com.rifeng.p2p.fragment.MineFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class HomeActivity extends FragmentActivity{
    @Bind(R.id.iv_add)
    ImageView imageView;
    @Bind(R.id.iv_home)
    ImageView imageView_home;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.iv_test)
    ImageView im_mine;
    @Bind(R.id.vt_test)
    TextView tvMine;
    @Bind(R.id.ll_home)
    LinearLayout llHome;
    @Bind(R.id.ll_add)
    LinearLayout llAdd;
    @Bind(R.id.ll_mine)
    LinearLayout llMine;
    @Bind(R.id.fl_main)
    FrameLayout frameLayout;
    Button btnCancel;
    Button btnDetermin;
    private AlertDialog dlg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initEvent();
    }
    @OnClick({R.id.ll_mine ,R.id.ll_home , R.id.ll_add})
    public void onViewClick(View view) {
        switch (view.getId()){
            case R.id.ll_home:
                System.out.println("1111111");
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
    private AddFragment addFragment ;
    private MineFragment mineFragment ;
    FragmentTransaction transaction;
    //提供相应的fragment
    @SuppressLint("ResourceAsColor")
    private void setSelect(int i) {

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        //隐藏所有的fragment
        hideFragment();
        //重新设置选中颜色及图片
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
                tvHome.setTextColor(R.color.selectColor);
                break;
            case 1:
                if (addFragment == null){
                    addFragment = new AddFragment();
                    transaction.add(R.id.fl_main , addFragment);
                }
                transaction.show(addFragment);
                break;
            case 2:
                if (mineFragment == null){
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_main,mineFragment);
                }
                transaction.show(mineFragment);

                //选中时改变颜色
                im_mine.setImageResource(R.mipmap.mine_select);
                tvMine.setTextColor(R.color.selectColor);
                break;
        }
        transaction.commit();
    }
    //重新设置为没有被选中之前的样子
    private void resetTab() {
        imageView_home.setImageResource(R.mipmap.home_unselect);
        im_mine.setImageResource(R.mipmap.mine_unselect);
//        tvHome.setTextColor();
//        tvMine.setTextColor();
    }
    private void hideFragment() {
        if (homeFragment != null){
            transaction.hide(homeFragment);
        }
        if (addFragment != null){
            transaction.hide(addFragment);
        }
        if (mineFragment != null){
            transaction.hide(mineFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //创建对话框
            dlg = new AlertDialog.Builder(this).create();
            dlg.show();//显示对话框
            Window window = dlg.getWindow();//获取对话框窗口
            window.setGravity(Gravity.CENTER);//对话框居中
//            window.setWindowAnimations(R.style); //设置样式
            window.setContentView(R.layout.quit_dialog_layout);
            btnDetermin = window.findViewById(R.id.btn_determin); //获取对话框确认按钮
            btnCancel = window.findViewById(R.id.btn_cancel);//获取对话框取消按钮

            initEvent();
//            btnCancel = window
        }
        return super.onKeyDown(keyCode, event);
    }
    //处理对话框中的按钮事件
    private void initEvent() {
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
                finish(); //关闭当前界面，退出应用
            }
        });
    }
}
