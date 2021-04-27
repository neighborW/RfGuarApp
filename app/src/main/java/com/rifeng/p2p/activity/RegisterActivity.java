package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.rifeng.p2p.R;

public class RegisterActivity extends BaseActivity {

    private ImageView ivBack;
    private EditText fistName;
    private EditText lastName;
    private EditText agenName;
    private EditText postCode;
    private EditText contactName;
    private EditText area;
    private EditText address;

    private Button summit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        initEvent();
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
    }
    public void initEvent(){
        //点退出按钮回到登录界面
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(LoginActivity.class);
            }
        });
    }
}
