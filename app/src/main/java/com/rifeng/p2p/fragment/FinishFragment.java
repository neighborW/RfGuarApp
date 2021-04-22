package com.rifeng.p2p.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.adapter.ReportListAdapter;
import com.rifeng.p2p.entity.Report;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishFragment extends Fragment {


    private String title;

    public FinishFragment() {
        // Required empty public constructor
    }

    /**
     * 返回一个FinishFragment实例对象
     * @param title  标题
     * @return
     */
    public static  FinishFragment newInstance(String title){

        FinishFragment finishFragment = new FinishFragment();
        finishFragment.title = title;
        return finishFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_finish , container , false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Report> datas = new LinkedList<>();

        for (int i = 0 ; i < 8 ;i++){
            Report report = new Report();

            report.setAuthor("王昌侣");
            report.setVtitle("无");
            report.setCategoryName("动作片");
            datas.add(report);

        }
        ReportListAdapter listAdapter = new ReportListAdapter(getActivity() , datas);

        recyclerView.setAdapter(listAdapter);
       return view;
    }

}
