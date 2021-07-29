package com.rifeng.p2p.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rifeng.p2p.uphidescrollview.BaseListFragment;

import java.util.List;

/**
 * Depiction: TabLayout 和 Fragment，viewpager结合使用的viewpager adapter。
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;
    private List<? extends BaseListFragment> mFragments;

    public TabPagerAdapter(FragmentManager fm, List<String> titleList, List<? extends BaseListFragment> fragments) {
        super(fm);
        this.mTitles = titleList;
        this.mFragments = fragments;
    }

    @Override
    public BaseListFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? "" : mTitles.get(position);
    }
}
