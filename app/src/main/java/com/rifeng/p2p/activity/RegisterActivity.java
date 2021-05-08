package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pickerviewlibrary.picker.TeaPickerView;
import com.example.pickerviewlibrary.picker.entity.PickerData;
import com.example.pickerviewlibrary.picker.entity.ProvinceBean;
import com.example.pickerviewlibrary.picker.entity.SecondBean;
import com.example.pickerviewlibrary.picker.entity.ThirdBean;
import com.example.pickerviewlibrary.picker.util.JsonArrayUtil;
import com.rifeng.p2p.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends BaseActivity {

    private ImageView ivBack;
    private EditText fistName;
    private EditText lastName;
    private EditText agenName;
    private EditText postCode;
    private EditText contactName;
    private EditText area;
    private EditText address;
    private LinearLayout llAdress;

    List<String> mProvinceDatas=new ArrayList<>();
    Map<String, List<String>> mSecondDatas= new HashMap<>();
    Map<String, List<String>> mThirdDatas= new HashMap<>();

    private Button summit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        initEvent();
        intiPickerView();
    }
    @SuppressLint("WrongViewCast")
    public void initComponent(){
        ivBack = findViewById(R.id.im_back);
        summit = findViewById(R.id.sumit);
        fistName = findViewById(R.id.frstName);
        lastName = findViewById(R.id.lastName);
        agenName = findViewById(R.id.agentName);
        postCode = findViewById(R.id.et_postCode);
        contactName = findViewById(R.id.contactNumber);
        area = findViewById(R.id.et_area);
        address = findViewById(R.id.et_address);
        llAdress = findViewById(R.id.ll_address);
    }
    public void initEvent(){
        //点退出按钮回到登录界面
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = fistName.getText().toString().trim();
                String ln = lastName.getText().toString().trim();
                String an = agenName.getText().toString().trim();
                String pc = postCode.getText().toString().trim();
                String cn = contactName.getText().toString().trim();
                String ad = address.getText().toString().trim();
//                navigateTo(LoginActivity.class);
            }
        });
    }
    private  void intiPickerView(){
        //一级列表
        ProvinceBean provinceBean = new ProvinceBean();
        mProvinceDatas.addAll(provinceBean.getRepData().getProvince());
        //二级列表
        SecondBean secondBean = new SecondBean();
        mSecondDatas.putAll(secondBean.getRepData().getSecond());
        //三级列表
        ThirdBean thirdBean = new ThirdBean();
        mThirdDatas.putAll(thirdBean.getRepData().getThird());

        Log.i("json", JsonArrayUtil.toJson(mProvinceDatas));
        Log.i("json",JsonArrayUtil.toJson(mSecondDatas));
        Log.i("json", JsonArrayUtil.toJson(mThirdDatas));
        //设置数据有多少层级
        PickerData data=new PickerData();
        data.setFirstDatas(mProvinceDatas);//json: ["广东","江西"]
        data.setSecondDatas(mSecondDatas);//json: {"江西":["南昌","赣州"],"广东":["广州","深圳","佛山","东莞"]}
        data.setThirdDatas(mThirdDatas);//json: "广州":["天河区","白云区","番禹区","花都区"],"赣州":["章贡区","黄金开发区"],"东莞":["东城","南城"],"深圳":["南山区","宝安区","龙华区"],"佛山":["禅城区","顺德区"],"南昌":["东湖区","青云谱区","青山湖区"]}
        data.setInitSelectText("Selection");
        TeaPickerView teaPickerView =new TeaPickerView(this,data);
        teaPickerView.setScreenH(3)
                .setDiscolourHook(true)
                .setRadius(25)
                .setContentLine(true)
                .setRadius(25)
                .build();
        //点击输入框出现级联选择器
        llAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teaPickerView.show(llAdress);
            }
        });
        //选择器点击事件
        teaPickerView.setOnPickerClickListener(pickerData -> {
            showToast(pickerData.getFirstText()+","+pickerData.getSecondText()+","+pickerData.getThirdText());
            teaPickerView.dismiss();//关闭选择器
        });
    }
}
