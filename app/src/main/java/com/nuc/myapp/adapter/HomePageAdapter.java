package com.nuc.myapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class HomePageAdapter extends FragmentPagerAdapter {

    private String[] mTtiles = {"Pass","Draft"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    public HomePageAdapter(FragmentManager fm , String[] mTtiles , ArrayList<Fragment> mFragments ) {
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
