package com.rifeng.p2p.uphidescrollview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rifeng.p2p.entity.TabItem;
import com.rifeng.p2p.fragment.DraftFragment;
import com.rifeng.p2p.fragment.FinishFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caizhiming on 2016/11/14.
 */
public class ScrollViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseListFragment> mFragmentList = new ArrayList<>();
    private List<TabItem> mTitleList = new ArrayList<>();
    public BaseListFragment getFragment(int pos){
        return mFragmentList.get(pos);
    }
    public void setTabLayoutData(List<TabItem> list){
        if(list != null && list.size() > 0){
            mTitleList.clear();
            mTitleList.addAll(list);
            mFragmentList.clear();

            for (int i = 0; i < mTitleList.size(); i++) {
                if ("Finish".equals(mTitleList.get(i).getItemName())){
                    BaseListFragment item = new FinishFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(mTitleList.get(i).getItemName(),mTitleList.get(i));
                    item.setArguments(bundle);
                    mFragmentList.add(item);
                }
                else {
                    BaseListFragment item = new DraftFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(mTitleList.get(i).getItemName(),mTitleList.get(i));
                    item.setArguments(bundle);
                    mFragmentList.add(item);
                }
            }
        }
    }
    public ScrollViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public View getSlidableView (int index) {
        return mFragmentList.get(index).getSlidableView();
    }
    @Override
    public Fragment getItem(int position) {
        if(mFragmentList!= null && mFragmentList.size() > 0){
            return mFragmentList.get(position);
        }else{
            return null;
        }
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
    @Override
    public int getCount() {
        return mTitleList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position).getItemName();
    }
}
