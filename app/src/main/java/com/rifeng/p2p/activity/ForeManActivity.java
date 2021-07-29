package com.rifeng.p2p.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.adapter.AutoCompleteTextAdapter;
import com.rifeng.p2p.adapter.ForemanAdapter;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.db.DBManager;
import com.rifeng.p2p.entity.UserForeManBean;
import com.rifeng.p2p.manager.DataManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForeManActivity extends BaseActivity {


    @BindView(R.id.iv_base_back)
    ImageView backImageView;

    @BindView(R.id.text_toolbar_title)
    TextView titleTextView;

    @BindView(R.id.text_toolbar_menu)
    TextView text_toolbar_menu;

    //选中邮箱列表适配器
    private ForemanAdapter adapter;

    @BindView(R.id.foreman_btn)
    Button addBtn;
    @BindView(R.id.foreman_email_listview)
    ListView listView;

    @BindView(R.id.foreman_autoCompleteTextView)
    AutoCompleteTextView textView;

    private List<UserForeManBean> userForeManBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_fore_man);
        ButterKnife.bind(this);
        setTitle(getString(R.string.foreman_title));
        text_toolbar_menu.setVisibility(View.VISIBLE);
        text_toolbar_menu.setText("Done");


//        setRightBtn("ok", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        userForeManBeanList = DataManager.selectedForemanList;
        if (DataManager.selectedForemanList.size()!=0){
            for (int i=0;i<DataManager.selectedForemanList.size();i++){
                userForeManBeanList.add(DataManager.selectedForemanList.get(i));
            }
        }
        adapter = new ForemanAdapter(this, R.layout.item_fore_man_email, userForeManBeanList);
        listView.setAdapter(adapter);
        //ListView item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        //ListView item 中的删除按钮的点击事件
        adapter.setOnItemDeleteClickListener(new ForemanAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                DataManager.selectedForemanList.remove(i);
                adapter.notifyDataSetChanged();
            }
        });


//选中列表
        List<UserForeManBean> foreManBeanList = DBManager.getInstance(BaseApp.getInstance()).getForeManList();

        Log.i("foreManBeanList count:", "" + foreManBeanList.size());
        //搜索列表
        AutoCompleteTextAdapter autoCompleteTextAdapter = new AutoCompleteTextAdapter(foreManBeanList, this);
        textView.setAdapter(autoCompleteTextAdapter);

        //AutoCompleteTextView控件的点击事件
        textView.addTextChangedListener(new TextWatcher() {
            @Override//改变之前
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override//改变时
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //判断搜索框中是否有内容，然后再决定其下方的热门城市的是否隐藏
                //android提供了一个专门判断空字符串的方法:TextUtils.isEmpty
                if (TextUtils.isEmpty(textView.getText()) || textView.getText() != null) {

                }
            }

            @Override//改变后
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(textView.getText())) {


                }
            }
        });

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText("");
                //获取到过滤后的结果的集合
                ArrayList<UserForeManBean> results = AutoCompleteTextAdapter.newvalues;

                UserForeManBean result = results.get(position);
                String s = result.getEmail();
                textView.setText(s);
                //将光标放到文本最后
                textView.setSelection(textView.getText().length());
            }
        });


    }

    @Override
    protected int initLayout() {
        return R.layout.layout_fore_man;
    }

    @Override
    protected void initView() {

        titleTextView.setText("CC e-Mail");

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClick();
            }
        });

        text_toolbar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.selectedForemanList.clear();
                if (adapter.getData()!=null){
                    for (int i=0;i<adapter.getData().size();i++){
                        DataManager.selectedForemanList.add(adapter.getData().get(i));
                    }
                    finish();
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.foreman_btn})
    public void btnClick(View view) {


        String email = textView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            showToast(R.string.email_must_be_not_null);
            return;
        }
        if (!email.matches("^[a-zA-Z0-9_\\.-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            showToast(R.string.incorrect_e_mail_formate);
            return;
        }

        List<UserForeManBean> list = new ArrayList<UserForeManBean>();
        UserForeManBean userForeManBean = new UserForeManBean();
        //userForeManBean.setCreateDate(new Date());
        userForeManBean.setEmail(email);
        list.add(userForeManBean);
        DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(list);

        //List<UserForeManBean> foreManBeanList = DBManager.getInstance().getForeManList();

//        DataManager.selectedForemanList.add(userForeManBean);
        adapter.add(userForeManBean);
        adapter.notifyDataSetChanged();
        textView.setText("");

    }

    public String getEmailsString() {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < DataManager.selectedForemanList.size(); i++) {
            if (i == DataManager.selectedForemanList.size() - 1) {
                sb.append(DataManager.selectedForemanList.get(i).getEmail());
            } else {
                sb.append(DataManager.selectedForemanList.get(i).getEmail());
                sb.append(",");
            }
        }
        return sb.toString();

    }


    public void onBackClick() {

        finish();
    }


}