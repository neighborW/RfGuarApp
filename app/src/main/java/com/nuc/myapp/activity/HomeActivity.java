package com.nuc.myapp.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.nuc.myapp.R;
import com.nuc.myapp.adapter.MyPageAdapter;
import com.nuc.myapp.entity.TabEntity;
import com.nuc.myapp.fragment.CollectFragment;
import com.nuc.myapp.fragment.HomeFragment;
import com.nuc.myapp.fragment.MyFragment;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private String[] mTtiles = {"首页","收藏","我的"};

    private int[] mIconUnselectIds = {
            R.mipmap.home_unselect, R.mipmap.collect_unselect,
            R.mipmap.my_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.home_selected, R.mipmap.collect_selected,
            R.mipmap.my_selected};

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ViewPager viewPager;

    private CommonTabLayout commonTabLayout;


    @Override
    protected int initLayout() {
        return  R.layout.activity_home;
    }

    /**
     * 视图初始化
     */
    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }
    @Override
    protected void initData() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        mFragments.add(CollectFragment.newInstance());
        for (int i = 0 ; i < mTtiles.length ; i++){
            mTabEntities.add(new TabEntity(mTtiles[i] , mIconSelectIds[i] , mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        //tab点击事件
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
        viewPager.setOffscreenPageLimit(mFragments.size());
        //界面切换是触发事件，界面切换时执行onPageSelected
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager() , mTtiles, mFragments));
    }
}
