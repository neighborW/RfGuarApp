package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.gson.Gson;
import com.rifeng.p2p.HomeActivity;
import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.AddressItem;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.fragment.MineFragment;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.util.SharedPreferencesUtils;
import com.rifeng.p2p.view.MyEditText;
import com.rifeng.p2p.widget.AddressSelectView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.rifeng.p2p.R.drawable.shape_login_form;
import static com.rifeng.p2p.R.mipmap.delete_image;

public class AboutMeActivity extends BaseActivity {

    @BindView(R.id.iv_base_back)
    ImageView imBack;

    FragmentTransaction transaction;
    private MineFragment mineFragment;

    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.first_name)
    MyEditText firstName;
    @BindView(R.id.last_name)
    MyEditText lastName;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_address)
    MyEditText address;
    @BindView(R.id.et_area)
    MyEditText area;
    @BindView(R.id.et_post_code)
    MyEditText etPostCode;
    @BindView(R.id.et_mobile_phone)
    MyEditText mobilePhone;
    @BindView(R.id.et_mail)
    MyEditText etEmail;

    @BindView(R.id.text_toolbar_title)
    TextView textToolbarTitle;

    @BindView(R.id.im_area_address)
    ImageView imArea;

    public List<AddressItem> addressList;
    private boolean isModifyAccount = false;

    private AddressItem countryModel;
    private AddressItem stateModel;
    private AddressItem cityModel;
    private AddressItem suburbModel;
    private PopupWindow popupWindow;

    private View getContentView() {
        return this.findViewById(R.id.about_layout);
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_about_me;
    }
    @Override
    protected void initView() {
        textToolbarTitle.setText("About Me");
        imBack = findViewById(R.id.iv_base_back);
    }

    @Override
    protected void initData() {

        User user  = DataManager.getInstance().getUser();
        setUserData(user);
        back();
        editClick();
        imArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow(); //打开地址选择框
            }
        });

    }
    //关闭当前Activity ，即退出这个Activity

    public void back() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCurrentActivity();
            }
        });
    }
    public void editClick() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Edit".equals(btnEdit.getText())) {
                    System.out.println("编辑");
                    setEditable();
                } else {
                    try {
                        System.out.println("保存");
                        requestSave();
                        setNotEditable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void requestSave() throws Exception {
        final User user = DataManager.getInstance().getUser();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        JSONObject object = new JSONObject(json);
        user.setFirstname(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setAddress(address.getText().toString());
        user.setPostcode(etPostCode.getText().toString());
        user.setMobile(etPostCode.getText().toString());
        user.setEmail(etEmail.getText().toString());

        object.put("id", user.getEngineerId());
        object.put("address", user.getAddress());
        object.put("firstName", user.getFirstname());
        object.put("lastName", user.getLastName());
        if (cityModel != null){
            object.put("city", cityModel.getLabel());
            object.put("cityId", cityModel.getId());
            user.setCity(cityModel.getLabel());
            user.setCityId(cityModel.getId());
        }
        else {
            object.put("city", null);
            object.put("cityId", null);
            user.setCity(null);
            user.setCityId(null);
        }
        if (countryModel != null){
            object.put("country", countryModel.getLabel());
            object.put("countryId", countryModel.getId());
            user.setCountry(countryModel.getLabel());
            user.setCountryId(countryModel.getId());
        }
        else {
            object.put("country", user.getCountry());
            object.put("countryId", user.getCountryId());
        }
        if (stateModel != null){
            object.put("state", stateModel.getLabel());
            object.put("stateId", stateModel.getId());
            user.setState(stateModel.getLabel());
            user.setStateId(stateModel.getId());
        }
        else {
            object.put("state", user.getState());
            object.put("stateId", user.getStateId());
            user.setState(null);
            user.setStateId(null);
        }
        if (suburbModel != null){
            object.put("suburb", suburbModel.getLabel());
            object.put("suburbId", suburbModel.getId());
            user.setSuburb(suburbModel.getLabel());
            user.setStateId(suburbModel.getId());
        }else {
            object.put("suburb", null);
            object.put("suburbId", null);
            user.setSuburb(null);
            user.setStateId(null);
        }
        object.put("postCode", user.getPostcode());
        object.put("mobileNumber", user.getMobile());
        object.put("email", user.getEmail());
        //判断是否修改了邮箱
        isModifyAccount = !user.getLoginAccount().equals(user.getEmail());
        object.put("loginAccount", user.getEmail());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
        mRFService.modifiedPlumberInformation(body).compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        // RFReqeustMananger.getInstance().addOperationLog(AboutMeActivity.this,"User_ModifyUserInfo","","APP","", BaseApp.getInstance().getCurrentUserId(),null);
                        showToast(R.string.modify_success);
                        user.setLoginAccount(user.getEmail());
                        DataManager.getInstance().saveUser(user);
                        setUserData(user);
                        if (isModifyAccount) {
                            SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplication());
                            sp.setParam("Account", user.getEmail());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });

                        }
                    }
                }, mThrowableConsumer, mFinishAction);
    }
    public void setUserData(User user) {
//        User user  = DataManager.getInstance().getUser();
        firstName.setText(user.getFirstname());
        lastName.setText(user.getLastName());
        etCompany.setText(user.getCompanyName());
        address.setText(user.getAddress());
        if (user.getSuburb() != null ){
            area.setText(user.getSuburb() );
        }
        if (cityModel != null && suburbModel == null){
            area.setText(user.getCity());
        }
        if ( stateModel != null  &&cityModel == null && suburbModel == null){
            area.setText( user.getState());
        }
        if ( countryModel != null && stateModel == null  &&cityModel == null && suburbModel == null){
            area.setText( user.getCountry());
        }
        etPostCode.setText(user.getPostcode());
        mobilePhone.setText(user.getMobile());
        etEmail.setText(user.getEmail());
    }
    private void setNotEditable() {

        Drawable drawable = null;
        imArea.setVisibility(View.GONE);
        btnEdit.setText("Edit");
        firstName.setEnabled(false);
        Drawable dr = null;
        firstName.setdRight(null);
        firstName.setBackground(drawable);
        lastName.setEnabled(false);
        lastName.setdRight(null);
        lastName.setBackground(drawable);
        etCompany.setEnabled(false);
        etCompany.setBackground(drawable);
        address.setEnabled(false);
        address.setdRight(null);
        address.setBackground(drawable);
        area.setEnabled(false);
        area.setBackground(drawable);
        etPostCode.setEnabled(false);
        etPostCode.setdRight(null);
        etPostCode.setBackground(drawable);
        mobilePhone.setEnabled(false);
        mobilePhone.setdRight(null);
        mobilePhone.setBackground(drawable);
        etEmail.setEnabled(false);
        etEmail.setdRight(null);
        etEmail.setBackground(drawable);
    }

//    Drawable drawable = getResources().getDrawable(R.mipmap.delete_image);
//    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    private void setEditable() {
        btnEdit.setText("Save");
        imArea.setVisibility(View.VISIBLE);
        firstName.setEnabled(true);
        Drawable drawable1 = getResources().getDrawable(delete_image);
        firstName.setCompoundDrawables(null , null, drawable1,null );
        firstName.setBackground(null);
        lastName.setEnabled(true);
        lastName.setCompoundDrawables(null , null, drawable1,null );
        lastName.setBackground(null);
        address.setEnabled(true);
        address.setCompoundDrawables(null , null, drawable1,null );
        address.setBackground(null);
        area.setEnabled(true);
        area.setBackground(null);
        etPostCode.setEnabled(true);
        etPostCode.setCompoundDrawables(null , null, drawable1,null );
        etPostCode.setBackground(null);
        mobilePhone.setEnabled(true);
        mobilePhone.setCompoundDrawables(null , null, drawable1,null );
        mobilePhone.setBackground(null);
        etEmail.setEnabled(true);
        etEmail.setCompoundDrawables(null , null, drawable1,null );
        etEmail.setBackground(null);
    }

    private boolean isEdit;

    private final TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void afterTextChanged(Editable editable) {
            isEdit = true;
        }
    };
    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.address_popup_layout, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(getContentView(), Gravity.RIGHT | Gravity.BOTTOM, 0, 0);
        AddressSelectView selectview = contentView.findViewById(R.id.addressSelect);
        selectview.setCountryModel(countryModel);
        selectview.setCityModel(cityModel);
        selectview.setStateModel(stateModel);
        selectview.setSuburbModel(suburbModel);
        selectview.updateCurrentSelectBtn();
        selectview.updateButtonStatus();
        selectview.setOnSelectCancleListEner(new AddressSelectView.OnSelectCancleListner() {
            @Override
            public void onCancle() {
                popupWindow.dismiss();
            }
        });
        selectview.setOnSelectConfirmListEner(new AddressSelectView.OnSelectConfirmListner() {
            @Override
            public void onConfirm(AddressItem country, AddressItem state, AddressItem city, AddressItem suburb) {
                countryModel = country;
                stateModel = state;
                cityModel = city;
                suburbModel = suburb;
//                System.out.println("国家：" + countryModel + " 州" + stateModel + " 市" + cityModel);
                if (suburbModel != null ){
                    area.setText(suburbModel.getLabel()  + " " + cityModel.getLabel() + " " + stateModel.getLabel() + " " + countryModel.getLabel() );
                }
                if (cityModel != null && suburbModel == null){
                    area.setText(cityModel.getLabel() + " " + stateModel.getLabel() + " "  +countryModel.getLabel() );
                }
                if (stateModel != null && cityModel == null && suburbModel == null){
                    area.setText(stateModel.getLabel() + " "  +countryModel.getLabel());
                }
                if (countryModel != null && stateModel == null && cityModel == null && suburbModel == null){
                    area.setText(countryModel.getLabel());
                }
               // area.setText(stateModel.getLabel() + " " + countryModel.getLabel());
                popupWindow.dismiss();
                isEdit = true;
            }
        });
    }
}