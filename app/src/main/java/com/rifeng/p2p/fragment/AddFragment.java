package com.rifeng.p2p.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rifeng.p2p.R;
import com.rifeng.p2p.util.ExpandView;

public class AddFragment extends BaseFragment {

    private ExpandView expandView;

    private LinearLayout linearLayout  , linearLayout2;

    private LinearLayout LlBasicData , LlPreTest;
    private ImageView imgShrink , testIm;
    private static EditText[] editTexts = new EditText[7];
    private TextView textViewTitle;
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }
    @Override
    protected int initLayout() {
        return R.layout.fragment_add;
    }
    @Override
    protected void initView() {

        linearLayout = mRootView.findViewById(R.id.lin_tv);
        linearLayout2 = mRootView.findViewById(R.id.lin_test);

        imgShrink = mRootView.findViewById(R.id.iv_basic);
        testIm = mRootView.findViewById(R.id.iv_test);

        textViewTitle  = mRootView.findViewById(R.id.et_basic);


        LlBasicData = mRootView.findViewById(R.id.ll_basic_data);
        LlPreTest = mRootView.findViewById(R.id.ll_pre_test);


        editTexts[0] = mRootView.findViewById(R.id.et_project_name);
        editTexts[1] = mRootView.findViewById(R.id.et_site_address);
        editTexts[2] = mRootView.findViewById(R.id.et_post_code);
        editTexts[3] = mRootView.findViewById(R.id.et_date);
        editTexts[4] = mRootView.findViewById(R.id.et_company);
        editTexts[5] = mRootView.findViewById(R.id.et_brandandtype);
        editTexts[6] = mRootView.findViewById(R.id.et_email);
    }
    @Override
    protected void initData() {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LlBasicData.getVisibility() == View.VISIBLE){
                    LlBasicData.setVisibility(View.GONE);
                    imgShrink.setImageResource(R.mipmap.shrink);
                }
                else {
                    LlBasicData.setVisibility(View.VISIBLE);
                    imgShrink.setImageResource(R.mipmap.unfold);
                }
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LlPreTest.getVisibility() == View.VISIBLE){
                    LlPreTest.setVisibility(View.GONE);
                    testIm.setImageResource(R.mipmap.shrink);
                }
                else {
                    LlPreTest.setVisibility(View.VISIBLE);
                    testIm.setImageResource(R.mipmap.unfold);
                }
            }
        });
    }
}
