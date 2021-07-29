package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.AddressItem;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.view.RFProgressHud;
import com.rifeng.p2p.widget.AddressSelectView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class RegisterActivity extends BaseActivity {

    private ImageView ivBack;
    private EditText fistName;
    private EditText lastName;
    private TextView companyName;
    private EditText postCode;
    private EditText contactName;
    private EditText area;
    private EditText address;
    private LinearLayout llAdress;
    private ImageView imAreaAddress;


    List<String> mProvinceDatas = new ArrayList<>();
    Map<String, List<String>> mSecondDatas = new HashMap<>();
    Map<String, List<String>> mThirdDatas = new HashMap<>();
    private Button summit;

    private boolean isModifyAccount = false;

    private AddressItem countryModel;
    private AddressItem stateModel;
    private AddressItem cityModel;
    private AddressItem suburbModel;
    private PopupWindow popupWindow;

    private boolean isEdit;

    private Bundle bundle;

    private AlertDialog dlg;
    private TextView tvPrompt;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ivBack = findViewById(R.id.im_back);
        summit = findViewById(R.id.sumit);
        fistName = findViewById(R.id.frstName);
        lastName = findViewById(R.id.lastName);
        companyName = findViewById(R.id.et_company);
        postCode = findViewById(R.id.et_postCode);
        contactName = findViewById(R.id.contactNumber);
        area = findViewById(R.id.et_area);
        address = findViewById(R.id.et_address);
        llAdress = findViewById(R.id.ll_address);
        imAreaAddress = findViewById(R.id.im_area_address);

        companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCompanyName(bundle.get("invitationCode").toString());
            }
        });
    }

    @Override
    protected void initData() {

        Intent i = getIntent();
        bundle = i.getExtras();
        getCompanyName(bundle.get("invitationCode").toString());
        //点退出按钮回到登录界面
        back();
        onClick();
    }

    public void onClick() {
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegister();
            }
        });
        imAreaAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
            }
        });
    }

    private void back() {
        //退出回到原界面
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(SignupActivity.class);
                finish();
            }
        });
    }

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
                if (suburbModel != null) {
                    area.setText(suburbModel.getLabel() + " " + cityModel.getLabel() + " " + stateModel.getLabel() + " " + countryModel.getLabel());
                }
                if (cityModel != null && suburbModel == null) {
                    area.setText(cityModel.getLabel() + " " + stateModel.getLabel() + " " + countryModel.getLabel());
                }
                if (stateModel != null && cityModel == null && suburbModel == null) {
                    area.setText(stateModel.getLabel() + " " + countryModel.getLabel());
                }
                if (countryModel != null && stateModel == null && cityModel == null && suburbModel == null) {
                    area.setText(countryModel.getLabel());
                }
                popupWindow.dismiss();
                isEdit = true;
            }
        });
    }

    //注册接口的调用
    @SuppressLint("CheckResult")
    private void doRegister() {
        if (TextUtils.isEmpty(fistName.getText().toString())) {
            showToast(R.string.first_name_must_be_not_null);
            return;
        }
        if (TextUtils.isEmpty(lastName.getText().toString())) {
            showToast(R.string.surname_must_be_not_null);
            return;
        }
        JSONObject params = new JSONObject();

        try {
            params.put("firstName", fistName.getText().toString());
            params.put("lastName", lastName.getText().toString());
            params.put("companyName", bundle.get("companyName"));
            params.put("address", address.getText().toString());

            params.put("postcode", postCode.getText().toString());
            params.put("confirmPassword", bundle.getString("confirmPassword"));
            params.put("email", bundle.getString("email"));
            params.put("password", bundle.getString("password"));
            params.put("invitationCode", bundle.getString("invitationCode"));
            if (countryModel != null) {
                params.put("country", countryModel.getId());

            } else {
                showToast(R.string.Regother_Area_required);
                return;
            }
            if (stateModel != null) {
                params.put("state", stateModel.getId());
            } else {
                params.put("state", "");
            }
            if (cityModel != null) {
                params.put("city", cityModel.getId());
            } else {
                params.put("city", "");
            }

            if (suburbModel != null) {
                params.put("suburb", suburbModel.getId());
            } else {
                params.put("suburb", "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showLoadingDialog();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), params.toString());
        mRFService.addPlumber(body).compose(RXHelper.<String>RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        openDialog();
                    }
                }, mThrowableConsumer, mFinishAction);
    }

    //获取整个layout的上下文，即layout的根组件
    private View getContentView() {
        return this.findViewById(R.id.ll_register);
    }

    //点击物理键退出到登录界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("退出");
            navigateTo(LoginActivity.class);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openDialog() {
        RFProgressHud.showSuccessMsg(this, "Register Successful", new RFProgressHud.RFProgressHudListener() {
            @Override
            public void didCloseHub() {
                navigateTo(LoginActivity.class);
                finish();
            }
        });
    }
    public void getCompanyName(String invitationCode) {
        mRFService.getCompanyName(invitationCode)
                .compose(RXHelper.<JsonObject>RFFlowableTransformer())
                .subscribe(new Consumer<JsonObject>() {
                    @Override
                    public void accept(JsonObject jsonObject) throws Exception {
                        companyName.setText(jsonObject.get("companyName").getAsString());
//                        companyName.setEnabled(false);
                    }
                }, mThrowableConsumer, mFinishAction);
    }
}
