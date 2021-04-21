package com.nuc.myapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyPageAdapter extends FragmentPagerAdapter {

    private String[] mTtiles = {"首页","收藏","我的"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public MyPageAdapter(FragmentManager fm ,String[] mTtiles ,ArrayList<Fragment> mFragments ) {
        super(fm);
        this.mTtiles = mTtiles;
        this.mFragments = mFragments;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
}
