package com.rifeng.p2p.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 主页适配器
 */
public class HomeFragmentAdapter  extends  FragmentPagerAdapter{

    private String[] mTitle;
    private ArrayList<Fragment> mFragment;

    public HomeFragmentAdapter(FragmentManager fm , String[] titles , ArrayList<Fragment> fragments) {
        super(fm);
        this.mTitle = titles;
        this.mFragment = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
