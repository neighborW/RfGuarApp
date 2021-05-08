package com.rifeng.p2p.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.rifeng.p2p.R;
import com.rifeng.p2p.adapter.HomeFragmentAdapter;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.Bind;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout ;

    private ArrayList<Fragment> mFragment = new ArrayList<>();

    private final String[] mTitle = {"Finish","Draft"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity() , R.layout.fragment_home , null);
        viewPager = view.findViewById(R.id.fixedViewPager);
        slidingTabLayout = view.findViewById(R.id.commonTabLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (String title : mTitle){
            mFragment.add(FinishFragment.newInstance(title));
        }
         viewPager.setAdapter(new HomeFragmentAdapter(getFragmentManager() , mTitle,mFragment));
        slidingTabLayout.setViewPager(viewPager);
    }
}
