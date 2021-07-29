package com.rifeng.p2p.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.style.PictureSelectorUIStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.R;
import com.rifeng.p2p.adapter.GridImageAdapter;
import com.rifeng.p2p.adapter.PipCodeAdpater;
import com.rifeng.p2p.adapter.PressureTestAdaper;
import com.rifeng.p2p.adapter.RoundAdapter;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.db.DBManager;
import com.rifeng.p2p.entity.AddressItem;
import com.rifeng.p2p.entity.Agreement;
import com.rifeng.p2p.entity.LastDeviceTestId;
import com.rifeng.p2p.entity.LookupCode;
import com.rifeng.p2p.entity.Option;
import com.rifeng.p2p.entity.PipeCodeModel;
import com.rifeng.p2p.entity.PipeDiagramModel;
import com.rifeng.p2p.entity.PressureResultBean;
import com.rifeng.p2p.entity.PressureTestModel;
import com.rifeng.p2p.entity.UpdataUIEven;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.entity.UserForeManBean;
import com.rifeng.p2p.http.RFException;
import com.rifeng.p2p.http.RFService;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.manager.DeviceDataManager;
import com.rifeng.p2p.manager.PressureTestManager;
import com.rifeng.p2p.util.DisplayUtil;
import com.rifeng.p2p.util.LogToFileUtils;
import com.rifeng.p2p.util.ScreenAdaptUtil;
import com.rifeng.p2p.util.StringUtils;
import com.rifeng.p2p.util.ToastUtils;
import com.rifeng.p2p.util.VoiceUtils;
import com.rifeng.p2p.view.BluetoothListDialog;
import com.rifeng.p2p.view.RFNoticeDialog;
import com.rifeng.p2p.view.RFProgressHud;
import com.rifeng.p2p.viewmodel.BaseViewModel;
import com.rifeng.p2p.viewmodel.PressureTestViewModel;
import com.rifeng.p2p.widget.AddressSelectView;
import com.rifeng.p2p.widget.FullyGridLayoutManager;
import com.rifeng.p2p.widget.GlideCacheEngine;
import com.rifeng.p2p.widget.GlideEngine;
import com.rifeng.p2p.widget.RFDropdownListView;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.fly2think.blelib.BroadcastEvent;
import cn.fly2think.blelib.RFStarBLEService;
import cn.fly2think.blelib.TransUtils;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class PressureTestActivity extends BleActivity {

    private RFNoticeDialog noticeDialog = null;

    @BindView(R.id.text_toolbar_title)
    TextView titleView;
    @BindView(R.id.text_toolbar_menu)
    TextView text_toolbar_menu;
    @BindView(R.id.img_toolbar_menu)
    ImageView img_toolbar_menu;

    @BindView(R.id.basic_data_open)
    ImageView basicOpenImageView;

    @BindView(R.id.pressure_test_open)
    ImageView pressureTestImageView;
    //basic data
    @BindView(R.id.ll_basic_data)
    LinearLayout basicDataContentLayout;

    @BindView(R.id.pressure_test_content_bg_layout)
    LinearLayout pressureTestContentBgLayout;

    private final static String TAG = "PictureSelectorTag";
    @BindView(R.id.basic_project_name)
    EditText projectNameEditText;
    @BindView(R.id.basic_address)
    EditText addressEditText;
    @BindView(R.id.basic_post_code)
    EditText postCodeEditText;
    @BindView(R.id.basic_test_date)
    EditText testTimeEditText;
    @BindView(R.id.basic_company)
    EditText companyEditText;
    @BindView(R.id.basic_pipe_brand_and_type)
    EditText pipeBrandAndTypeEditText;

    @BindView(R.id.basic_ccemail)
    TextView ccEmailEditText;

    @BindView(R.id.ccemail_bg_click_layout)
    LinearLayout ccEmailBgLayout;

//    @BindView(R.id.spinner_testmethod)
//    Spinner testMethodSpinner;
    @BindView(R.id.basic_testmethod)
    TextView testMethodTextView;

    @BindView(R.id.test_method_bg_click_layout)
    LinearLayout testMethodBgLayout;


    //蓝牙
    @BindView(R.id.blue_tooth_bg)
    LinearLayout bluetoothBgLayout;


    @BindView(R.id.bluetooth_connect_dot_view)
    View bluetoothDotView;

    @BindView(R.id.bluetooth_name_textView)
    TextView bluetoothNameView;

    @BindView(R.id.blue_tooth_connect_textView)
    TextView bluetoothConnectView;


    //电池
    @BindView(R.id.battery_status_bg)
    LinearLayout batteryStatusLayout;

    @BindView(R.id.battery_dot_view)
    View batteryDotView;

    @BindView(R.id.battery_power_textview)
    TextView batteryPowerTextview;

    @BindView(R.id.battery_info_imageview)
    ImageView batteryInfoImageView;

    @BindView(R.id.battery_power_low_tip_textview)
    TextView batteryPowerLowTipView;



    @BindView(R.id.iv_base_back)
    ImageView backImageView;

    private GridImageAdapter mAdapter;


    RFNoticeDialog agreementDialog;


    //管路图
    @BindView(R.id.pipe_diagram_bg)
    LinearLayout pipeDiagramBgLayout;

//    @BindView(R.id.pipe_diagram_gridView)
//    MyGridView pipeDiagramGridView;

    //选择测压参数
    @BindView(R.id.select_test_group_bg)
    LinearLayout selectTestGroupLayout;

    @BindView(R.id.tv_pressure_test)
    TextView selectGroupCurrentPressureTextview;

    @BindView(R.id.select_group_recyclerView)
    RecyclerView selectGroupRecycleView;

    //测压中layout
    @BindView(R.id.testing_linear_layout)
    LinearLayout testingLayout;
    @BindView(R.id.round_recyclerView)
    RecyclerView roundRecyclerView;
    @BindView(R.id.current_pressure_num)
    TextView currentPressureNumTextView;
    @BindView(R.id.start_pressure_num)
    TextView startPressureNumTextView;
    @BindView(R.id.count_down_time_textview)
    TextView countdownTextView;

    @BindView(R.id.testing_reset_btn)
    Button testingResetBtn;


    @BindView(R.id.result_reset_btn)
    Button resultResetBtn;

    @BindView(R.id.pressure_button_save)
    Button saveBtn;

    @BindView(R.id.pressure_button_submit)
    Button submitBtn;



    //测试结果layout
    @BindView(R.id.test_result_layout)
    LinearLayout testResultLayout;

    @BindView(R.id.tv_test_group)
    TextView resultTestGroupTextView;

    @BindView(R.id.tv_pre_at_start)
    TextView resultStartPressureTextView;

    @BindView(R.id.tv_pressure_at_end)
    TextView resultEndtPressureTextView;

    @BindView(R.id.tv_statu)
    TextView resultTextView;


    BluetoothListDialog bluetoothListDialog = null;

    private boolean didGetParameter = false;



    //管码
    @BindView(R.id.pipe_code_layout)
    LinearLayout pipeCodeBgLayout;
    private PipCodeAdpater pipCodeAdpater;

    //轮数adapter
    private RoundAdapter roundAdapter;

    private final int maxSelectNum = 12;



//    private ArrayList<LookupCode> testMethodList = new ArrayList<LookupCode>();

    private LookupCode currentSelectTestMethod;

    private  boolean isShowBasicData = true;

    private  boolean isShowPressureContent = true;

    private final PressureTestModel pressureTestMdoel = new PressureTestModel();


    private PressureTestAdaper selectGroupAdapter;


    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    /**
     //     * 没有权限时，点击GridView时点击的位置和路径，这2个默认值是0和
     */
    /**
     * 需要的权限
     * 本来读写权限获取其中一只另外一个就也获取了（一个权限组的），随着安卓系统更新，只获取读不行了。
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private int gridViewItemClickPosition;
    private String gridViewItemClickPath;
    /**
     * 允许上传照片最大数量
     */
    private static final int INT_MAXSIZE_IMG = 12;



    //pipe code
    @BindView(R.id.pipecode_recyclerView)
    RecyclerView pipeCodeRecyclerView;

    @BindView(R.id.add_pipecode)
    ImageView addPipeCodeImageView;

    @BindView(R.id.add_pipecode_editText)
    EditText addPipeCodeEditText;
    @BindView(R.id.pipediagram_recycler)
    RecyclerView mRecyclerView;


    private VoiceUtils voiceUtils;



    @Override
    protected int initLayout() {
        return R.layout.pressure_test_layout;
    }

    @Override
    protected void initView() {


        testTimeEditText.setEnabled(false);
        companyEditText.setEnabled(false);
        submitBtn.setEnabled(false);
        submitBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.submit_btn_disable_bg));
        basicOpenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowBasicData = !isShowBasicData;
                if (isShowBasicData){
                    basicDataContentLayout.setVisibility(View.VISIBLE);
                    basicOpenImageView.setImageResource(R.mipmap.shrink);
                }else{
                    basicDataContentLayout.setVisibility(View.GONE);
                    basicOpenImageView.setImageResource(R.mipmap.unfold);
                }
            }
        });

        pressureTestImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isShowPressureContent = !isShowPressureContent;
                if (isShowPressureContent){
                    pressureTestContentBgLayout.setVisibility(View.VISIBLE);
                    pressureTestImageView.setImageResource(R.mipmap.shrink);
                }else{
                    pressureTestContentBgLayout.setVisibility(View.GONE);
                    pressureTestImageView.setImageResource(R.mipmap.unfold);
                }

            }
        });

        testMethodTextView.setClickable(true);
        testMethodTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTextMethodPopwindow();
            }
        });

        testMethodBgLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showTextMethodPopwindow();
            }
        });

//        testMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                String methodCode = PressureTestManager.getInstance().testMethodList.get(position).getItem_code();
//                String testMethod = PressureTestManager.getInstance().testMethodList.get(position).getItem_name();
//                currentSelectTestMethod = PressureTestManager.getInstance().testMethodList.get(position);
//
//                TextView view = (TextView) selectedItemView;
//                if (testMethod.equals(PressureTestViewModel.DEFAULT_TEST_MTTHOD)){
//                    view.setTextColor(ContextCompat.getColor(PressureTestActivity.this, R.color.Color_Word25));
//                }else{
//                    view.setTextColor(ContextCompat.getColor(PressureTestActivity.this, R.color.Color_Word85));
//                }
//
//                PressureTestManager.getInstance().currentPressureTestModel.setTestMethod(testMethod);
//                PressureTestManager.getInstance().currentPressureTestModel.setTestMethodCode(methodCode);
//                // mAgent.setTestMethod(methodCode);
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//
//            }
//
//        });

        bluetoothBgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivityForResult(new Intent(PressureTestActivity.this, BluetoothActivity.class), 111);

                if (PressureTestManager.getInstance().isBluetoothConnected()){
                    //已经连接就断开连接
                    PressureTestManager.getInstance().disconnectBlueTooth();
                    updateCurrentState();
                }else{

                    //如果没有连接就弹出连接蓝牙框
                    DisplayMetrics dm = new DisplayMetrics();
                    WindowManager m = getWindowManager();
                    m.getDefaultDisplay().getMetrics(dm);


                    bluetoothListDialog = new BluetoothListDialog(PressureTestActivity.this, R.style.Dialog, new BluetoothListDialog.BluetoothDialogListener() {
                        @Override
                        public void didSelectBluetooth() {
                            RFProgressHud.showLoading(PressureTestActivity.this, "loading");
                            //刷新当前UI
                            updateCurrentState();
                        }
                    });
                    //dayMeetingListView.setMeetingList(meetingList);
                    final WindowManager.LayoutParams params = bluetoothListDialog.getWindow().getAttributes();
                    bluetoothListDialog.show();
                    params.width = dm.widthPixels - DisplayUtil.dp2px(PressureTestActivity.this, 12);
                    params.height = DisplayUtil.dp2px(PressureTestActivity.this, 391);
                    bluetoothListDialog.getWindow().setAttributes(params);
                }

            }
        });


        //添加管码
        addPipeCodeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = addPipeCodeEditText.getText().toString();

                addPipeCodeEditText.setText("");

                if ("".equals(code) || null == code){
                    RFProgressHud.showErrorMsg(PressureTestActivity.this, "Code can not be null!");
                    return;
                }

               ArrayList<String> pipeCodeList =  PressureTestManager.getInstance().currentPressureTestModel.getPipeCodeList();

                if (pipeCodeList.size() >= 10){
                    RFProgressHud.showErrorMsg(PressureTestActivity.this, "Enter up to 10 pipecodes!");
                    return;
                }


               for (String str : pipeCodeList){
                   if (str.equals(code)){
                       RFProgressHud.showErrorMsg(PressureTestActivity.this, "Code Exist!");

                       return;
                   }
               }


                pipeCodeList.add(code);
                pipCodeAdpater.notifyDataSetChanged();
            }
        });

        testingResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               resetDevice(true);
               resetTestModel();

                RFProgressHud.showSuccessMsg(PressureTestActivity.this, getResources().getString(R.string.Reset_Successfully));
            }
        });

        resultResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDevice(true);
                resetTestModel();

                RFProgressHud.showSuccessMsg(PressureTestActivity.this,  getResources().getString(R.string.Reset_Successfully));
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePressureTestModel();
                RFProgressHud.showSuccessMsg(PressureTestActivity.this, getString(R.string.pressuretest_save_success));
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //提交前先保存
                savePressureTestModel();

                //提交数据
                submitPressureTestData();


                   // submitImageData();
            }
        });


        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //退出关闭蓝牙
                PressureTestManager.getInstance().disconnectBlueTooth();
                PressureTestActivity.this.finish();
            }
        });

        ccEmailBgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.selectedForemanList.clear();
                DataManager.selectedForemanList.addAll(getForemanBeanList());
                //传值
                Intent foremanIntent = new Intent(PressureTestActivity.this, ForeManActivity.class);
                startActivityForResult(foremanIntent, 1);
            }
        });

        ccEmailEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.selectedForemanList.clear();
                DataManager.selectedForemanList.addAll(getForemanBeanList());
                //传值
                Intent foremanIntent = new Intent(PressureTestActivity.this, ForeManActivity.class);
                startActivityForResult(foremanIntent, 1);
            }
        });


        //设置选择组别adapter
        setupSelectGroupRecyclerView();

        //设置管码adapter
        setupPipecodeRecyclerView();

        //设置轮数adapter
        setupRoundRecyclerView();

//        //设置pipediagram
//        setImageSelectEvent();
        setPipeDiagramView();

        voiceUtils = new VoiceUtils(this);


    }

    //获取整个layout的上下文，即layout的根组件
    private View getContentView() {
        return this.findViewById(R.id.pressure_test_all_bg_layout);
    }


    PopupWindow popupWindow;
    /**
     * 显示popupWindow
     */
    private void showTextMethodPopwindow() {

        Log.i("showTextMethodPopwindow","call =====");

        if (PressureTestManager.getInstance().testMethodList.size() == 0){

            return;
        }
        View contentView = LayoutInflater.from(this).inflate(R.layout.test_method_popup_layout, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.style.anim_testmethod_popup);
//        popupWindow.setTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setFocusable(true);//需要设置为ture，表示可以聚焦

        //需要设置背景，用物理键返回的时候
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources()));
        popupWindow.setOutsideTouchable(true);

        popupWindow.showAtLocation(PressureTestActivity.this.getContentView(), Gravity.RIGHT | Gravity.BOTTOM, 0, 0);
        RFDropdownListView selectview = contentView.findViewById(R.id.select_testmethod);
        selectview.setLookupCodeList(PressureTestManager.getInstance().testMethodList);

        selectview.setDropdownListViewListener(new RFDropdownListView.RFDropdownListViewListener() {
            @Override
            public void onClickItem(LookupCode code) {
                testMethodTextView.setText(code.getItem_name());
                PressureTestManager.getInstance().currentPressureTestModel.setTestMethod(code.getItem_name());
                PressureTestManager.getInstance().currentPressureTestModel.setTestMethodCode(code.getItem_code());
                popupWindow.dismiss();
            }

            @Override
            public void onClickClose() {
                popupWindow.dismiss();
            }
        });


    }

    private void resetDevice(boolean isShowLoading) {

        if (isShowLoading){
            RFProgressHud.showLoading(PressureTestActivity.this, "reseting");
        }

        //重置试压仪
        DeviceDataManager.getInstance().resetDevice(new DeviceDataManager.DeviceDataManagerListener() {
            @Override
            public void success() {
                if (isShowLoading) {
                    RFProgressHud.hideDialog();
                    Log.i("===========hideDialog", "resetDevice");
                }
            }

            @Override
            public void fail() {
            }
        });
    }

    //提交数据
    public void submitPressureTestData(){

        if (!checkPressureTestData()){
            return;
        }


        RFProgressHud.showLoading(PressureTestActivity.this, "submiting...");
        PressureTestViewModel vm = new PressureTestViewModel();
        vm.setCurrentActivity(this);
        vm.submitPressureTestData(new BaseViewModel.ViewModelListener() {
            @Override
            public void success(String msg) {
                RFProgressHud.hideDialog();
                Log.i("===========hideDialog", "submitPressureTestData success");
                //保存testId
                PressureTestManager.getInstance().currentPressureTestModel.setTestId(msg);
                savePressureTestModel();
                submitImageData();

            }

            @Override
            public void fail(String msg, BaseViewModel.ReturnCode code) {
                RFProgressHud.hideDialog();
                Log.i("===========hideDialog", "submitPressureTestData fail");
                RFProgressHud.showErrorMsg(PressureTestActivity.this, msg);
            }

            @Override
            public void finish() {

            }
        });

    }

    private Boolean checkPressureTestData() {

        if(PressureTestManager.getInstance().currentPressureTestModel.getTestMethod().equals(getResources().getString(R.string.test_method))){
            showToast(getString(R.string.test_method_cant_empty));
            return false;
        }
        return true;
    }

    private void submitImageData()  {


        RFProgressHud.showLoading(PressureTestActivity.this, "upload image...");
        PressureTestViewModel vm = new PressureTestViewModel();
        vm.setCurrentActivity(this);
        vm.uploadDiagrams(new BaseViewModel.ViewModelListener() {
            @Override
            public void success(String msg) {

                //成功后删除本地数据
                deleteCurrentPressureTest();
                RFProgressHud.hideDialog();
                Log.i("===========hideDialog", "submitImage success");
                RFProgressHud.showSuccessMsg(PressureTestActivity.this, "submit success", new RFProgressHud.RFProgressHudListener() {
                    @Override
                    public void didCloseHub() {
                        PressureTestActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PressureTestManager.getInstance().disconnectBlueTooth();
                                PressureTestActivity.this.finish();
                            }
                        });

                    }
                });
            }

            @Override
            public void fail(String msg, BaseViewModel.ReturnCode code) {
                RFProgressHud.hideDialog();

                Log.i("===========hideDialog", "submitImage fail");
                RFProgressHud.showErrorMsg(PressureTestActivity.this, msg);
            }

            @Override
            public void finish() {

            }
        });

    }

    private void deleteCurrentPressureTest() {

        PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;
        DBManager.getInstance(BaseApp.getInstance()).clearPipeDiagramByTempTestId(currentModel.getTempTestId());
        DBManager.getInstance(BaseApp.getInstance()).clearPipeCodeByTempTestId(currentModel.getTempTestId());
        DBManager.getInstance(BaseApp.getInstance()).clearPressureResultByTempTestId(currentModel.getTempTestId());
        DBManager.getInstance(BaseApp.getInstance()).delete(currentModel);
    }

    @Override
    protected void initData() {

        DataManager.getInstance().didGetResult = false;
        DataManager.getInstance().IS_DEVICEID_UPDATE  = false;

        titleView.setText(getString(R.string.pressuretest_title));
        text_toolbar_menu.setVisibility(View.VISIBLE);
        text_toolbar_menu.setText("Reset");
        text_toolbar_menu.setTextColor(getResources().getColor(R.color.Color_Main));
        img_toolbar_menu.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.icon_reset).centerInside().into(img_toolbar_menu);

        text_toolbar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticeDialog();
            }
        });

        retestEnter();

        //获取testMethod
        requestTestMethod();

        //获取试压参数
        getUserAgencyPressureInfo();
        //填充数据
        initBasicData();

        updateTestDate();

        //刷新当前UI
        updateCurrentState();
    }

    @Override
    public void onStart() {
        super.onStart();

        //设置监听蓝牙发送的数据
        DeviceDataManager.getInstance().setUpdateDataListener(new DeviceDataManager.OnUpdateDataListener() {
            @Override
            public void updateDataListener(byte[] datas) {
                forwardData(datas);
            }

            @Override
            public void goResult(String result) {

                updateCurrentState();

            }

            @Override
            public void getResule4Wating() {

            }

            @Override
            public void testPass() {
                Log.i("======", "testPass call");
                PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;

                DeviceDataManager.getInstance().isReadDeviceStatus = false;
                currentModel.resultList.clear();
                currentModel.resultList.addAll(DBManager.getInstance(BaseApp.getInstance()).getResultListByTempTestId(currentModel.getTempTestId()));
                if (currentModel.resultList.size() == currentModel.optionList.size()){
                    PressureTestManager.getInstance().currentPressureTestModel.setCurrentState("3");
                    //updateCurrentPressureModelStatus("3");
                }

                Log.i("======", "testPass call");
                Log.i("======", "resultList:" + currentModel.resultList.size());
                Log.i("======", "optionList:" + currentModel.optionList.size());
                resetDevice(false);

                updateCurrentState();

            }

            @Override
            public void testFail() {

                DeviceDataManager.getInstance().isReadDeviceStatus = false;
                //updateCurrentPressureModelStatus("2");
                PressureTestManager.getInstance().currentPressureTestModel.setCurrentState("2");
                updateCurrentState();

                resetDevice(false);
            }

            @Override
            public void didConnectBlueTooth() {

                RFProgressHud.showLoading(PressureTestActivity.this, "waiting device init...");

            }

            @Override
            public void didGetDeviceUniqueCode() {

                //
                savePressureTestModel();
                Log.i("=====单据状态：", PressureTestManager.getInstance().currentPressureTestModel.getCurrentState());
                Log.i("=====设备状态：", DeviceDataManager.getInstance().currentDeviceModel.deviceStatus);

                //连接到设备，校验完设备唯一码， 判断是否当前单据
                //设备不是空闲状态
                if ( !"0".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus)) {

                    //当前单据如果不是最后的测试单据， 弹框重置
                    if (!PressureTestManager.getInstance().isLastTest()){
                        RFProgressHud.hideDialog();
                        Log.i("===========hideDialog", "didGetDeviceUniqueCode");
                        DeviceDataManager.getInstance().isReadDeviceStatus = false;
                        Log.i("======","" + DeviceDataManager.getInstance().currentDeviceModel.deviceStatus);
                        showNoticeDialog();



                    }else{
                        //如果是当前单据，但是是点retest进来
                        if (PressureTestManager.getInstance().enterType == PressureTestManager.EnterType.EnterTypeRetest){

                            resetDevice(true);
                            DeviceDataManager.getInstance().isReadDeviceStatus = true;
                        }else{
                            DeviceDataManager.getInstance().isReadDeviceStatus = true;
                        }

                    }

                }

                final String tmp = DeviceDataManager.getInstance().currentDeviceModel.deviceUniqueCode;


                PressureTestViewModel vm = new PressureTestViewModel();
                vm.checkDeviceUniqueCode(tmp, new BaseViewModel.ViewModelListener() {
                    @Override
                    public void success(String msg) {
                        DataManager.getInstance().IS_DEVICEID_UPDATE = true;
                        PressureTestManager.getInstance().currentPressureTestModel.setDeviceUniqueId(tmp);
                        savePressureTestModel();
                        didCheckUniqueCode();
                        Log.e("===getDeviceCheck", "success");
                    }

                    @Override
                    public void fail(String msg, BaseViewModel.ReturnCode code) {

                       RFProgressHud.showErrorMsg(PressureTestActivity.this, msg);
                    }

                    @Override
                    public void finish() {

                    }
                });
                updateCurrentState();
            }
            public void didCheckUniqueCode(){


            }
        });

    }


    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
//            boolean mode = cb_mode.isChecked();
            if (true) {  //相册or单独拍照
                System.out.println("点击添加");
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(PressureTestActivity.this)
                        .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                        //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                        .setPictureUIStyle(PictureSelectorUIStyle.ofDefaultStyle())
                        //.setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                        //.setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
                        .setPictureWindowAnimationStyle(PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle())// 自定义相册启动退出动画
                        .isWeChatStyle(true)// 是否开启微信图片选择风格
                        .isUseCustomCamera(true)// 是否使用自定义相机
                        .setLanguage(LanguageConfig.ENGLISH)// 设置语言，默认中文
                        .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
                        .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
                        .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                        //.isSyncCover(true)// 是否强制从MediaStore里同步相册封面，如果相册封面没显示异常则没必要设置
                        //.isCameraAroundState(false) // 是否开启前置摄像头，默认false，如果使用系统拍照 可能部分机型会有兼容性问题
                        //.isCameraRotateImage(false) // 拍照图片旋转是否自动纠正
                        //.isAutoRotating(false)// 压缩时自动纠正有旋转的图片
                        .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                        //.isAutomaticTitleRecyclerTop(false)// 连续点击标题栏RecyclerView是否自动回到顶部,默认true
                        //.loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                        //.setOutputCameraPath(createCustomCameraOutPath())// 自定义相机输出目录
                        //.setButtonFeatures(CustomCameraView.BUTTON_STATE_BOTH)// 设置自定义相机按钮状态
                        .setCaptureLoadingColor(ContextCompat.getColor(PressureTestActivity.this, R.color.Color_Main))
                        .maxSelectNum(12)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .maxVideoSelectNum(0) // 视频最大选择数量
                        //.minVideoSelectNum(1)// 视频最小选择数量
                        //.closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 关闭在AndroidQ下获取图片或视频宽高相反自动转换
                        .imageSpanCount(3)// 每行显示个数
                        //.queryFileSize() // 过滤最大资源,已废弃
                        //.filterMinFileSize(5)// 过滤最小资源，单位kb
                        //.filterMaxFileSize()// 过滤最大资源，单位kb
                        .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                        .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                        .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                        .isAndroidQTransform(true)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                        .isOriginalImageControl(true)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                        .isAutoScalePreviewImage(false)// 如果图片宽度不能充满屏幕则自动处理成充满模式
                        //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义视频播放回调控制，用户可以使用自己的视频播放界面
                        //.bindCustomPreviewCallback(new MyCustomPreviewInterfaceListener())// 自定义图片预览回调接口
                        //.bindCustomCameraInterfaceListener(new MyCustomCameraInterfaceListener())// 提供给用户的一些额外的自定义操作回调
                        //.bindCustomPermissionsObtainListener(new MyPermissionsObtainCallback())// 自定义权限拦截
                        //.bindCustomChooseLimitListener(new MyChooseLimitCallback()) // 自定义选择限制条件Dialog
                        //.cameraFileName(System.currentTimeMillis() +".jpg")    // 重命名拍照文件名、如果是相册拍照则内部会自动拼上当前时间戳防止重复，注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
                        //.renameCompressFile(System.currentTimeMillis() +".jpg")// 重命名压缩文件名、 如果是多张压缩则内部会自动拼上当前时间戳防止重复
                        //.renameCropFileName(System.currentTimeMillis() + ".jpg")// 重命名裁剪文件名、 如果是多张裁剪则内部会自动拼上当前时间戳防止重复
                        .selectionMode(PictureConfig.MULTIPLE) // 多选 or 单选
                        .isPreviewImage(true)// 是否可预览图片
                        .isPreviewVideo(true)// 是否可预览视频
                        //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
                        //.queryMimeTypeConditions(PictureMimeType.ofWEBP())
                        .isEnablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
                        //.isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
                        .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg,Android Q使用PictureMimeType.PNG_Q
                        .isEnableCrop(false)// 是否裁剪
                        //.basicUCropConfig()//对外提供所有UCropOptions参数配制，但如果PictureSelector原本支持设置的还是会使用原有的设置
                        .isCompress(true)// 是否压缩
                        //.compressFocusAlpha(true)// 压缩时是否开启透明通道
                        //.compressEngine(ImageCompressEngine.createCompressEngine()) // 自定义压缩引擎
                        //.compressQuality(80)// 图片压缩后输出质量 0~ 100
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
                        //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
//
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(false)// 是否显示gif图片
                        //.isWebp(false)// 是否显示webp图片,默认显示
                        //.isBmp(false)//是否显示bmp图片,默认显示
                        .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        //.setCropDimmedColor(ContextCompat.getColor(getContext(), R.color.app_color_white))// 设置裁剪背景色值
                        //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
                        //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .isOpenClickSound(false)// 是否开启点击声音
                        .selectionData(mAdapter.getData())// 是否传入已选图片
                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                        //.videoMinSecond(10)// 查询多少秒以内的视频
                        //.videoMaxSecond(15)// 查询多少秒以内的视频
                        //.recordVideoSecond(10)//录制视频秒数 默认60s
                        //.isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
                        .cutOutQuality(90)// 裁剪输出质量 默认100
                        //.cutCompressFormat(Bitmap.CompressFormat.PNG.name())//裁剪图片输出Format格式，默认JPEG
                        .minimumCompressSize(100)// 小于多少kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled(false) // 裁剪是否可旋转图片
                        .scaleEnabled(false)// 裁剪是否可放大缩小图片
                        .scaleEnabled(false)// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        .forResult(new MyResultCallback(mAdapter));
            } else {
                // 单独拍照
                PictureSelector.create(PressureTestActivity.this)
                        .openCamera(PictureMimeType.ofAll())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
//                        .theme(themeId)// 主题样式设置 具体参考 values/styles
                        .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
//                        .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
//                        .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
                        .setPictureWindowAnimationStyle(PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle())// 自定义相册启动退出动画
//                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
//                        .isUseCustomCamera(cb_custom_camera.isChecked())// 是否使用自定义相机
                        //.setOutputCameraPath()// 自定义相机输出目录
                        .minSelectNum(1)// 最小选择数量
                        //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                        .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高，默认为true
                        .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高，默认false
                        .selectionMode(PictureConfig.MULTIPLE )// 多选 or 单选
                        //.cameraFileName(System.currentTimeMillis() + ".jpg")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用
                        //.renameCompressFile(System.currentTimeMillis() + ".jpg")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
                        //.renameCropFileName(System.currentTimeMillis() + ".jpg")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
                        .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                        .isPreviewImage(true)// 是否可预览图片
                        .isPreviewVideo(true)// 是否可预览视频
                        .isEnablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .isAndroidQTransform(true)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                        .isEnableCrop(false)// 是否裁剪
                        //.basicUCropConfig()//对外提供所有UCropOptions参数配制，但如果PictureSelector原本支持设置的还是会使用原有的设置
                        .isCompress(true)// 是否压缩
                        .compressQuality(60)// 图片压缩后输出质量
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
                        .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                        //.setCircleDimmedColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪背景色值
                        //.setCircleDimmedBorderColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪边框色值
                        //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .isOpenClickSound(true)// 是否开启点击声音
                        .selectionData(mAdapter.getData())// 是否传入已选图片
                        .isAutoScalePreviewImage(false)// 如果图片宽度不能充满屏幕则自动处理成充满模式
                        //.isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 废弃 改用cutOutQuality()
                        .cutOutQuality(90)// 裁剪输出质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        .forResult(new MyResultCallback(mAdapter));
            }
        }
    };

    /**
     * 返回结果回调
     */
    private static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {

        private final WeakReference<GridImageAdapter> mAdapterWeakReference;
//
        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {


            PressureTestModel currentModel =  PressureTestManager.getInstance().currentPressureTestModel;
            PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList().clear();

            for (LocalMedia media : result) {
                if (media.getWidth() == 0 || media.getHeight() == 0) {
                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                        media.setWidth(imageExtraInfo.getWidth());
                        media.setHeight(imageExtraInfo.getHeight());
                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(PictureAppMaster.getInstance().getAppContext(), media.getPath());
                        media.setWidth(videoExtraInfo.getWidth());
                        media.setHeight(videoExtraInfo.getHeight());
                    }
                }

                //加入到model
                currentModel.getPipeDiagramList().add(new PipeDiagramModel(media, currentModel.getTempTestId()));




                Log.i(TAG, "文件名: " + media.getFileName());
                Log.i(TAG, "是否压缩:" + media.isCompressed());
                Log.i(TAG, "压缩:" + media.getCompressPath());
                Log.i(TAG, "原图:" + media.getPath());
                Log.i(TAG, "绝对路径:" + media.getRealPath());
                Log.i(TAG, "是否裁剪:" + media.isCut());
                Log.i(TAG, "裁剪:" + media.getCutPath());
                Log.i(TAG, "是否开启原图:" + media.isOriginal());
                Log.i(TAG, "原图路径:" + media.getOriginalPath());
                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                Log.i(TAG, "宽高: " + media.getWidth() + "x" + media.getHeight());
                Log.i(TAG, "Size: " + media.getSize());

            }


            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get().setList(result);
                PressureTestManager.getInstance().mediaList.clear();
                PressureTestManager.getInstance().mediaList.addAll(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "PictureSelector Cancel");
        }
    }



    private PictureParameterStyle getDefaultStyle() {
        // 相册主题
        PictureParameterStyle mPictureParameterStyle = new PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        // 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle.isOpenCompletedNumStyle = false;
        // 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle.isOpenCheckNumStyle = false;
        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e");
        // 相册父容器背景色
        mPictureParameterStyle.pictureContainerBackgroundColor = ContextCompat.getColor(this, R.color.app_color_black);
        // 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_arrow_up;
        // 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_arrow_down;
        // 相册文件夹列表选中圆点
        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval;
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back;
        // 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(this, R.color.picture_color_white);
        // 相册右侧取消按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
        mPictureParameterStyle.pictureCancelTextColor = ContextCompat.getColor(this, R.color.picture_color_white);
        // 选择相册目录背景样式
        mPictureParameterStyle.pictureAlbumStyle = R.drawable.picture_new_item_select_bg;
        // 相册列表勾选图片样式
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_checkbox_selector;
        // 相册列表底部背景色
        mPictureParameterStyle.pictureBottomBgColor = ContextCompat.getColor(this, R.color.picture_color_grey);
        // 已选数量圆点背景样式
        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval;
        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
        mPictureParameterStyle.picturePreviewTextColor = ContextCompat.getColor(this, R.color.picture_color_fa632d);
        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
        mPictureParameterStyle.pictureUnPreviewTextColor = ContextCompat.getColor(this, R.color.picture_color_white);
        // 相册列表已完成色值(已完成 可点击色值)
        mPictureParameterStyle.pictureCompleteTextColor = ContextCompat.getColor(this, R.color.picture_color_fa632d);
        // 相册列表未完成色值(请选择 不可点击色值)
        mPictureParameterStyle.pictureUnCompleteTextColor = ContextCompat.getColor(this, R.color.picture_color_white);
        // 预览界面底部背景色
        mPictureParameterStyle.picturePreviewBottomBgColor = ContextCompat.getColor(this, R.color.picture_color_grey);
        // 外部预览界面删除按钮样式
        mPictureParameterStyle.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete;
        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalControlStyle = R.drawable.picture_original_wechat_checkbox;
        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalFontColor = ContextCompat.getColor(this, R.color.white);
        // 外部预览界面是否显示删除按钮
        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true;
        // 设置NavBar Color SDK Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP有效
        mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#393a3e");
        // 文件夹字体颜色
        mPictureParameterStyle.folderTextColor = Color.parseColor("#4d4d4d");
        // 文件夹字体大小
        mPictureParameterStyle.folderTextSize = 16;

        return mPictureParameterStyle;
    }

    public void setPipeDiagramView(){

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenAdaptUtil.dip2px(this, 8), false));
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);



        mRecyclerView.setAdapter(mAdapter);

        if (PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList().size() > 0){
            PressureTestManager.getInstance().mediaList.clear();
            for (PipeDiagramModel model : PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList()){

                PressureTestManager.getInstance().mediaList.add(model.getLocalMedia());
            }
            mAdapter.setList( PressureTestManager.getInstance().mediaList);
        }

        mAdapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = mAdapter.getData();

            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(PressureTestActivity.this)
                                .themeStyle(R.style.picture_default_style)
                                .setPictureStyle(getDefaultStyle())// 动态自定义相册主题
                                .externalPictureVideo(TextUtils.isEmpty(media.getAndroidQToPath()) ? media.getPath() : media.getAndroidQToPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(PressureTestActivity.this)
                                .externalPictureAudio(PictureMimeType.isContent(media.getPath()) ? media.getAndroidQToPath() : media.getPath());
                        break;
                    default:
                        // 预览图片 可自定长按保存路径
//                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
//                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
//                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                        PictureSelector.create(PressureTestActivity.this)
                                .themeStyle(R.style.picture_default_style) // xml设置主题
                                .setPictureStyle(getDefaultStyle())// 动态自定义相册主题
                                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义播放回调控制，用户可以使用自己的视频播放界面
                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                .openExternalPreview(position, selectList);
                        break;
                }
            }
        });

    }

    //更新测试中数据显示
    public void updateTestingData(String countdown){

        currentPressureNumTextView.setText("" + DeviceDataManager.getInstance().currentDeviceModel.currentPressure);
        startPressureNumTextView.setText("" +  DeviceDataManager.getInstance().currentDeviceModel.pressureStart);
        countdownTextView.setText(countdown);


    }

    // ble 蓝牙状态以及数据回调接口
    // setBlockOnBeatsBreak
    public void onEventMainThread(UpdataUIEven event) {
        forwardData(event.getRecvs());
    }



    public void forwardData(byte[] datas) {
        if (datas[3] == 0x02) {
            switch (datas[5]) {
                case 0x30:
                    Log.e("tag", "请加压");
                    voiceUtils.playVoice("pleasekeeppressurising.mp3");
                    return;
                case 0x31:
                    voiceUtils.playVoice("overpressured.mp3");
                    Log.e("tag", "超压");
                    return;
                case 0x32:
                    voiceUtils.playVoice("testfailed.mp3");
                    Log.e("tag", "测试异常");
                    return;
                case 0x33:
                    voiceUtils.playVoice("testcontinue.mp3");
                    Log.e("tag", "继续测试");
                    return;
                case 0x34:
//                    voiceUtils.playVoice("pleasekeeppressurising.mp3");
                    Log.e("tag", "低电提示");
                    return;
                case 0x35:
                    voiceUtils.playVoice("pressureexceedsthesettingpleasedropthepressure.mp3");
                    Log.e("tag", "已超压，请降压到设定范围内");
                    return;
            }

            int value = datas[5] & 0xff;
            if (value % 2 == 0) {
                int end = value / 2;
                List<String> list = new ArrayList<>();
                list.add("round.mp3");
                list.add(end + ".mp3");
                list.add("pressureholdfinished.mp3");
                voiceUtils.playVoice(list);
//                Log.e("tag", "第" + end + "次保压结束");
            } else {
                int start = value / 2 + 1;
                List<String> list = new ArrayList<>();
                list.add("pressureholdround.mp3");
                list.add(start + ".mp3");
                voiceUtils.playVoice(list);
//                Log.e("tag", "第" + start + "次保压开始");
            }
        }
        // 实时模式
        else if (datas[3] == 0x01) {

            Log.e("实时数据", "" + datas);
            Log.e("实时数据", "" + TransUtils.bytes2hex(datas));




            if (PressureTestManager.getInstance().currentPressureTestModel.getCurrentState().equals("0") || PressureTestManager.getInstance().currentPressureTestModel.equals( "1")){
//                    PressureTestManager.getInstance().currentPressureTestModel.setCurrentState();
                if (DeviceDataManager.getInstance().isReadDeviceStatus && !"3".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus)){
                    updateCurrentPressureModelStatus(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus);
                }

            }


            int power = datas[6] & 0xff;

            int times = datas[7] & 0xff;
            if (datas[5] == 0x03) {
                times--;
            }
//            textPressuretestTestNo.setText(String.format(getString(R.string.pressuretest_test_no), times));

            //实时数据

            // strSeveralTests
            int round = datas[7] & 0xff;

            //



//            //实时倒计时
            int min = datas[16] & 0xff;
            int sec = datas[17] & 0xff;
            String countDownTime = formatNum(min) + ":" + formatNum(sec);
            updateTestingData(countDownTime);



            int type = datas[18] & 0xff;
            switch (type) {
                case 0x00:
                    break;
                case 0x03:
                    break;
            }

            // iOS BLDATA.strEquipmentState = [BLFunction numberHexString:[dataStar substringWithRange:NSMakeRange(10,2)]];
            switch (datas[5]) {
                case 0x00:
                    PressureTestManager.getInstance().lastState = 0;

                    break;
                case 0x01:
                    //0转到1
                    if (PressureTestManager.getInstance().lastState == 0){
                    }
                    PressureTestManager.getInstance().lastState = 1;

                    //设置测试中当前轮
                    if (round > 0){
                        roundAdapter.setCurrentPositon(round - 1);
                    }

                    break;
                case 0x02:
                    if (PressureTestManager.getInstance().lastState != 1) {
                        return;
                    }
                    if (DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew) {
                        //请求到当前轮数的数据
                        requestTestData(round);
                        // 已经接受到数据而且那边已经处理完，进行重置
//                        if (DataManager.getInstance().didGetResult) {
//                            //重置
//                           resetDevice();
//                            DataManager.getInstance().didGetResult = false;
//                        }
                    }
                    PressureTestManager.getInstance().lastState = 2;
//                    PressureTestManager.getInstance().currentPressureTestModel.setCurrentState("2");

//                    showResult(false);
//                    LogToFileUtils.write("测试状态：" + "异常");
                    break;
                case 0x03:

                    if (DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew) {
                        LogToFileUtils.write("Press Test Act 测试状态：" + "合格");
//                        // 如果 当前的轮数是最后一轮
                        if (round == PressureTestManager.getInstance().currentPressureTestModel.optionList.size()) {

                            // 已经接受到数据而且那边已经处理完，进行重置
                            requestTestData(round);


//                            if (DataManager.getInstance().didGetResult) {
//                                //重置
//
//                                DeviceDataManager.getInstance().getResultData("PASS");
//                                LogToFileUtils.write("测试状态：" + "合格");
////                                showResult(true);
//                                PressureTestManager.getInstance().lastState = 3;
////                                PressureTestManager.getInstance().currentPressureTestModel.setCurrentState("3");
//                            } else {
//                                // 已经接受到数据而且那边已经处理完，进行重置
//                                requestTestData(round);
//                            }
                        }
                    } else {
                        LogToFileUtils.write("测试状态：" + "合格");

                    }
                    break;
            }
            updateRound(times);

            updateCurrentState();
        }
    }

    public void updateRound(int round) {

        if (PressureTestManager.getInstance().lastState == 1){
            roundAdapter.setCurrentPositon(round - 1);
            roundAdapter.notifyDataSetChanged();
        }


    }

    private void requestTestData(int round) {
        for (int i = 0; i < round; i++) {
            try {
                //休眠200ms
                Thread.sleep(200);
                //获取数据 round 从 1 开始算
                int tmp = i + 1;
                LogToFileUtils.write("测试状态：" + "合格" + "从试压仪中获取" + tmp + "轮数据");
                DeviceDataManager.getInstance().forwardDataPreAll(tmp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateTestDate(){

        PressureTestViewModel vm = new PressureTestViewModel();
        vm.getServerTime(new BaseViewModel.ViewModelListener() {
            @Override
            public void success(String msg) {

                testTimeEditText.setText(PressureTestManager.getInstance().currentPressureTestModel.getTestTimeStr());

            }

            @Override
            public void fail(String msg, BaseViewModel.ReturnCode code) {
                vm.getLocalTime(new BaseViewModel.ViewModelListener() {
                    @Override
                    public void success(String msg) {
                        testTimeEditText.setText(PressureTestManager.getInstance().currentPressureTestModel.getTestTimeStr());
                    }

                    @Override
                    public void fail(String msg, BaseViewModel.ReturnCode code) {

                    }

                    @Override
                    public void finish() {

                    }
                });

            }

            @Override
            public void finish() {

            }
        });

    }

    public void initBasicData(){

        PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;
        projectNameEditText.setText(currentModel.getProjectName());
        addressEditText.setText(currentModel.getAddress());
        companyEditText.setText(DataManager.getInstance().getUser().getCompanyName());
        postCodeEditText.setText(currentModel.getPostCode());
        pipeBrandAndTypeEditText.setText(currentModel.getPipeBrandAndType());
        ccEmailEditText.setText(currentModel.getCcEmail());

    }


    public void getUserAgencyPressureInfo(){
        PressureTestViewModel vm = new PressureTestViewModel();
        vm.getAgencyPressureInfo(new BaseViewModel.ViewModelListener() {
            @Override
            public void success(String msg) {

                didGetParameter = true;
                didGetPressureInfo();
            }

            @Override
            public void fail(String msg, BaseViewModel.ReturnCode code) {

                //如果失败，从本地再读取一次
                PressureTestViewModel vm2 = new PressureTestViewModel();
                vm2.getAgencyPressureInfoFromLocal(new BaseViewModel.ViewModelListener() {
                    @Override
                    public void success(String msg) {
                        didGetPressureInfo();
                        didGetParameter = true;
                    }

                    @Override
                    public void fail(String msg, BaseViewModel.ReturnCode code) {
                        didGetParameter = true;
                        showToast(PressureTestActivity.this.getString(R.string.no_pressure_test_parameters));
                    }

                    @Override
                    public void finish() {

                    }
                });

            }

            @Override
            public void finish() {

            }
        });

    }

    public void didGetPressureInfo(){

        if (PressureTestManager.getInstance().currentPressureTestModel.getAgreementList().size() == 0){
            RFProgressHud.showErrorMsg(this, getString(R.string.no_pressure_test_parameters));
        }

        selectGroupAdapter.notifyDataSetChanged();


//        String groupId = PressureTestManager.getInstance().currentPressureTestModel.getGroupId();
//        //如果当前模型有选择过测压参数
//        if (groupId != null && !"".equals(groupId)){
//            selectPressureByGroupId(groupId);
//        }


    }

    public void selectPressureByGroupId(String groupId){

    }


    private void requestTestMethod() {

        PressureTestViewModel vm = new PressureTestViewModel();
        vm.getTestMethod(new BaseViewModel.ViewModelListener(){

            @Override
            public void success(String msg) {
                didGetTestMethod();
            }

            @Override
            public void fail(String msg, BaseViewModel.ReturnCode code) {

                //网络获取失败，从本地获取
                PressureTestViewModel vm2 = new PressureTestViewModel();
                vm2.getTestMethodFromLocal(new BaseViewModel.ViewModelListener() {
                    @Override
                    public void success(String msg) {
                        didGetTestMethod();
                    }

                    @Override
                    public void fail(String msg, BaseViewModel.ReturnCode code) {

                    }

                    @Override
                    public void finish() {

                    }
                });
            }


            @Override
            public void finish() {

            }
        });
    }

    public void didGetTestMethod(){


        PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;
        int index = -1;
        for (int i = 0; i <  PressureTestManager.getInstance().testMethodList.size(); i++){
            LookupCode code = PressureTestManager.getInstance().testMethodList.get(i);
            if (code.getItem_name().equals(currentModel.getTestMethod())){
                index = i;
                testMethodTextView.setText(code.getItem_name());
                break;
            }
        }



//        int finalIndex = index;
//        PressureTestActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                TestMethodAdapter<LookupCode> adapter = new TestMethodAdapter<>(PressureTestActivity.this, R.layout.simple_spinner_item, PressureTestManager.getInstance().testMethodList);
//                adapter.setDropDownViewResource(R.layout.test_method_drop_down);
//                testMethodSpinner.setAdapter(adapter);
//                if (finalIndex != -1){
//                    testMethodSpinner.setSelection(finalIndex,  true);
//                }else{
//                    if (PressureTestManager.getInstance().testMethodList.size() > 0) {
//                        testMethodSpinner.setSelection(PressureTestManager.getInstance().testMethodList.size() - 1, true);
//                    }
//                }
//
//            }
//        });
    }

    class TestMethodAdapter<T> extends ArrayAdapter {
        public TestMethodAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            //返回数据的统计数量，大于0项则减去1项，从而不显示最后一项
            int i = super.getCount();
            return i > 0 ? i - 1 : i;
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            LookupCode code = (LookupCode) super.getItem(position);
            return code.getItem_name();
        }
    }


    @SuppressLint("WrongConstant")
    public void setupSelectGroupRecyclerView(){

        selectGroupAdapter = new PressureTestAdaper(this , PressureTestManager.getInstance().currentPressureTestModel.getAgreementList());
        selectGroupAdapter.setmListener(new PressureTestAdaper.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object item) {

                //点击选择组别
                showAgreementDialog(position, (Agreement) item);
            }

            @Override
            public boolean onItemLongClick(View itemView, int position, Object item) {
                return false;
            }
        });
        selectGroupRecycleView.setAdapter(selectGroupAdapter);
        selectGroupRecycleView.setLayoutManager(
                new GridLayoutManager(
                        PressureTestActivity.this ,2,
                        GridLayoutManager.VERTICAL ,
                        false));


    }


    private void showAgreementDialog(int position, Agreement item){
        // 如果还没有更新设备
        if (!isConnected() && !DataManager.getInstance().IS_DEVICEID_UPDATE) {
            sendDatas(null);
            ToastUtils.showToast(PressureTestActivity.this,"Not Init Yet");
            return;
        }

        Agreement agreement = item;

        String msg = item.getAgreement().replace("\\n", "\n");
        agreementDialog = new RFNoticeDialog(this, msg, "I Agree", new RFNoticeDialog.RFNoticeDialogListener(){

            @Override
            public void leftBtnClick() {


            }

            @Override
            public void rightBtnClick() {



            }

            @Override
            public void centerBtnClick() {
                selectGroup(position, item);
            }
        });

        agreementDialog.show();

    }

    private void selectGroup(int position, Agreement item) {


        Agreement agreement = item;

         PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;

        if ("".equals(currentModel.getDeviceUniqueId())){
            currentModel.setDeviceUniqueId(DeviceDataManager.getInstance().currentDeviceModel.deviceUniqueCode);

        }





        int optionPostion = -1;
        for (int i = 0; i < PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().size(); i++){

            Log.i("========agreementId", agreement.getId());


            if (PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().get(i).size() > 0){
                String group_id = PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().get(i).get(0).getGroupId();

                Log.i("=======option groupId", group_id);
                if (group_id.equals(agreement.getId())){
                    // 当前选中的
                    optionPostion = i;
                    break;
                }
            }

        }
        Log.i("=======tttt",  getString(R.string.no_pressure_test_parameters));
        if (optionPostion == -1){

            sendDatas(null);
            Log.i("=======tttt",  getString(R.string.no_pressure_test_parameters));
            ToastUtils.showToast(PressureTestActivity.this,getString(R.string.no_pressure_test_parameters));
            return;
        }
        /**
         * 试压前判断
         * <p>
         * 如果current pressure【试压仪获取的当前压力】-start_pressure【起始压力】>0并且<=overpressure【起始压力-组别压力>?KPa提示超压】 进入试压；
         * 如果current pressure【试压仪获取的当前压力】-start_pressure【起始压力】 >overpressure【起始压力-组别压力>?KPa提示超压】 并且<= cannotpressure_num【起始压力-组别压力>?KPa不允许进入测压阶段】 提示“已超压”，但是还是能进入试压
         * 如果current pressure【试压仪获取的当前压力】-start_pressure【起始压力】 > cannotpressure_num【起始压力-组别压力>?KPa不允许进入测压阶段】 提示“已超压，不允许进入测压！”，不能进入试压阶段
         */
        double currentPressure = DeviceDataManager.getInstance().currentDeviceModel.currentPressure;
        double startPressure = DeviceDataManager.getInstance().currentDeviceModel.pressureStart;
        double overPressure =  (int)(Double.parseDouble(PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().get(optionPostion).get(0).getOverpressure()));
        double cannotpressureNum = (int)(Double.parseDouble( PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().get(optionPostion).get(0).getCannotpressureNum()));


        if (currentPressure - startPressure > overPressure && currentPressure - startPressure <= cannotpressureNum) {
            voiceUtils.playVoice("overpressured.mp3");
        }
        //没有协议内容则给出提示
        if (StringUtils.isNullOrEmpty(agreement.getAgreement())) {
            startPressureTest(agreement,optionPostion);
        } else {
            int finalPosition = optionPostion;
            startPressureTest(agreement,finalPosition);

            PressureTestManager.getInstance().currentPressureTestModel.setTestType(agreement.getTestType());
            PressureTestManager.getInstance().currentPressureTestModel.setGroupId(agreement.getId());
            savePressureTestModel();


            LastDeviceTestId lastDevice = new LastDeviceTestId();
            lastDevice.setDeviceUniqueId(DeviceDataManager.getInstance().currentDeviceModel.deviceUniqueCode);
            lastDevice.setTempTestId(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId());
            DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(lastDevice);


//            RFProgressHud.showLoading(this, "loading");
////                int finalPosition = optionPostion;
//            int finalPosition = optionPostion;
//            showNoticeDialog(PressureTestActivity.this, getString(R.string.dilog_title_pt_notice),
//                    agreement.getAgreement().replace("\\n", "\n"),
//                    getString(R.string.dilog_btn_pt_agree), null, R.drawable.shape_dialog_orange,
//                    null, true,
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startPressureTest(agreement,finalPosition);
//                        }
//                    }, new View.OnClickListener(
//                    ) {
//                        @Override
//                        public void onClick(View v) {
//                            hideDialog();
//                        }
//                    });
        }
    }

    private void setupPipecodeRecyclerView(){

        ArrayList<String> pipeCodeList = PressureTestManager.getInstance().currentPressureTestModel.getPipeCodeList();
        pipCodeAdpater = new PipCodeAdpater(this, pipeCodeList, new PipCodeAdpater.PipeCodeListener() {
            @Override
            public void clickDeleteAtPostion(int postion) {

                if (postion < pipeCodeList.size()){
                    pipeCodeList.remove(postion);
                    pipCodeAdpater.notifyDataSetChanged();
                }
            }
        });
        pipeCodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pipeCodeRecyclerView.setAdapter(pipCodeAdpater);
        pipeCodeRecyclerView.setNestedScrollingEnabled(false);
        //设置分割线
       // pipeCodeRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this , 2));
    }

    private void setupRoundRecyclerView(){

        roundAdapter = new RoundAdapter(this, PressureTestManager.getInstance().currentPressureTestModel.getOptionList());

        roundRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        roundRecyclerView.setAdapter(roundAdapter);
        roundRecyclerView.setNestedScrollingEnabled(false);

    }

    public String getEmailsString(){

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < DataManager.selectedForemanList.size(); i++){
            if (i == DataManager.selectedForemanList.size() - 1){
                sb.append(DataManager.selectedForemanList.get(i).getEmail());
            }else{
                sb.append(DataManager.selectedForemanList.get(i).getEmail());
                sb.append(",");
            }
        }
        return sb.toString();

    }

    public ArrayList<UserForeManBean> getForemanBeanList(){
        ArrayList<UserForeManBean> list = new ArrayList<>();

        String ccEmailStr = ccEmailEditText.getText().toString();
        if (ccEmailStr.length() > 0){
            String[] emailList =  ccEmailStr.split(",");
            for(String email : emailList){
                UserForeManBean bean = new UserForeManBean();
                bean.setEmail(email);
                list.add(bean);
            }
        }
        return list;


    }
    /**
     * 遍历判断权限是否都有请求
     * @param neededPermissions
     * @return
     */
    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    public void changeUIState(PressureTestManager.CurrentUIState newState){

            //连接上等待试压仪发送数据
        if (PressureTestManager.getInstance().currentUIState != PressureTestManager.CurrentUIState.CurrentUIStateDidConnect &&
                newState == PressureTestManager.CurrentUIState.CurrentUIStateDidConnect){

        }

        //状态转变，关掉loading
        if (PressureTestManager.getInstance().currentUIState .equals(PressureTestManager.CurrentUIState.CurrentUIStateDidConnect) &&!
                newState.equals(PressureTestManager.CurrentUIState.CurrentUIStateDidConnect)){
            RFProgressHud.hideDialog();
            Log.i("===========hideDialog", "changeUIState " + PressureTestManager.getInstance().currentUIState  + " to " + newState);
        }

        if (!(PressureTestManager.getInstance().currentUIState.equals(PressureTestManager.CurrentUIState.CurrentUIStateTesting) )&&
                newState.equals(PressureTestManager.CurrentUIState.CurrentUIStateTesting)){
            RFProgressHud.hideDialog();
            Log.i("===========hideDialog", "changeUIState " + PressureTestManager.getInstance().currentUIState  + " to " + newState);
        }

        if (!(PressureTestManager.getInstance().currentUIState.equals(PressureTestManager.CurrentUIState.CurrentUIStateSelectGroup) )&&
                newState.equals(PressureTestManager.CurrentUIState.CurrentUIStateSelectGroup)){
            RFProgressHud.hideDialog();
            Log.i("===========hideDialog", "changeUIState " + PressureTestManager.getInstance().currentUIState  + " to " + newState);
        }
        Log.i("===========hideDialog", "before " + PressureTestManager.getInstance().currentUIState );

        PressureTestManager.getInstance().currentUIState = newState;
        Log.i("===========hideDialog", "to " + PressureTestManager.getInstance().currentUIState );




    }

    public void updateCurrentState(){



        //For test
       // PressureTestManager.getInstance().currentPressureTestModel.setCurrentState("3");
        Log.i("=====单据状态：", PressureTestManager.getInstance().currentPressureTestModel.getCurrentState());
        Log.i("=====设备状态：", DeviceDataManager.getInstance().currentDeviceModel.deviceStatus + " size:" + PressureTestManager.getInstance().mediaList.size());



        if("2".equalsIgnoreCase(PressureTestManager.getInstance().currentPressureTestModel.getCurrentState()) || "3".equalsIgnoreCase(PressureTestManager.getInstance().currentPressureTestModel.getCurrentState())){
            changeUIState(PressureTestManager.CurrentUIState.CurrentUIStateTestEnd);
           // resetDevice();
        }


        if (PressureTestManager.getInstance().isBluetoothConnected() == true ){

            if("1".equalsIgnoreCase(PressureTestManager.getInstance().currentPressureTestModel.getCurrentState())){

                if ("0".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus) ){
                   changeUIState(PressureTestManager.CurrentUIState.CurrentUIStateSelectGroup);
                }
                if ("1".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus) ){
                    changeUIState(PressureTestManager.CurrentUIState.CurrentUIStateTesting);
                }

                if ("2".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus) ||  "3".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus)){
                    //获取结果

                    DeviceDataManager.getInstance().getResultData("PASS");


                }

            }

            if("0".equalsIgnoreCase(PressureTestManager.getInstance().currentPressureTestModel.getCurrentState())){


                if(PressureTestManager.getInstance().currentUIState == PressureTestManager.CurrentUIState.CurrentUIStateNoConnect){
                    changeUIState(PressureTestManager.CurrentUIState.CurrentUIStateDidConnect);
                }


                if ("0".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus) ){
                    changeUIState(PressureTestManager.CurrentUIState.CurrentUIStateDidConnect);

                }

                if ("0".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus) && PressureTestManager.getInstance().currentUIState == PressureTestManager.CurrentUIState.CurrentUIStateDidConnect ){
                    changeUIState( PressureTestManager.CurrentUIState.CurrentUIStateSelectGroup);

                }

                //如果当前单据状态是未开始测试，设备状态是测试中
                if ("1".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus) ){

                    //从数据库获取当前设备的最后测试id， 如果跟当前单据相符，或者没有保存过单据，当做是新增，改变状态，否则弹框提示重置设备
                    changeUIState(PressureTestManager.CurrentUIState.CurrentUIStateTesting);

                }

                if ("2".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus) ||  "3".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus)){
                    //获取结果
                    //PressureTestManager.getInstance().currentUIState = PressureTestManager.CurrentUIState.CurrentUIStateTestEnd;
                }

            }
        }else{
            if("2".equalsIgnoreCase(PressureTestManager.getInstance().currentPressureTestModel.getCurrentState()) || "3".equalsIgnoreCase(PressureTestManager.getInstance().currentPressureTestModel.getCurrentState())){
                changeUIState(PressureTestManager.CurrentUIState.CurrentUIStateTestEnd);


            }else{
               changeUIState(PressureTestManager.CurrentUIState.CurrentUIStateNoConnect);
            }

        }

        updateUIState();
    }

    public void updateUIState(){

        if (PressureTestManager.getInstance().currentUIState == PressureTestManager.CurrentUIState.CurrentUIStateNoConnect){

            //未连接蓝牙
            bluetoothDotView.setBackgroundResource(R.drawable.dot_disconnnect);
            bluetoothNameView.setText("Disconnected");
            bluetoothConnectView.setText("Connect");

            batteryDotView.setBackgroundResource(R.drawable.dot_disconnnect);
            batteryStatusLayout.setVisibility(View.GONE);
            batteryPowerLowTipView.setVisibility(View.GONE);

            selectTestGroupLayout.setVisibility(View.GONE);
            testingLayout.setVisibility(View.GONE);
            testResultLayout.setVisibility(View.GONE);
            pipeDiagramBgLayout.setVisibility(View.GONE);
            pipeCodeBgLayout.setVisibility(View.GONE);

        }

        if (PressureTestManager.getInstance().currentUIState == PressureTestManager.CurrentUIState.CurrentUIStateDidConnect){

            bluetoothDotView.setBackgroundResource(R.drawable.dot_connnect);
            bluetoothNameView.setText(DataManager.getInstance().getAppManager().bluetoothDevice .getName());


            batteryDotView.setBackgroundResource(R.drawable.dot_connnect);
            batteryStatusLayout.setVisibility(View.VISIBLE);


            selectTestGroupLayout.setVisibility(View.GONE);
            testingLayout.setVisibility(View.GONE);
            testResultLayout.setVisibility(View.GONE);
            pipeDiagramBgLayout.setVisibility(View.GONE);
            pipeCodeBgLayout.setVisibility(View.GONE);

        }
        if (PressureTestManager.getInstance().currentUIState == PressureTestManager.CurrentUIState.CurrentUIStateSelectGroup){


            //选择参数
            batteryDotView.setBackgroundResource(R.drawable.dot_connnect);
            batteryStatusLayout.setVisibility(View.VISIBLE);
            batteryPowerLowTipView.setVisibility(View.VISIBLE);

            selectTestGroupLayout.setVisibility(View.VISIBLE);
            selectGroupCurrentPressureTextview.setText(DeviceDataManager.getInstance().currentDeviceModel.currentPressure + "");
            testingLayout.setVisibility(View.GONE);
            testResultLayout.setVisibility(View.GONE);
            pipeDiagramBgLayout.setVisibility(View.GONE);
            pipeCodeBgLayout.setVisibility(View.GONE);

            //如果还没测压参数
            if (PressureTestManager.getInstance().currentPressureTestModel.getAgreementList().size() == 0 && !didGetParameter){
               getUserAgencyPressureInfo();
            }else{
                selectGroupAdapter.notifyDataSetChanged();
            }
        }
        if (PressureTestManager.getInstance().currentUIState == PressureTestManager.CurrentUIState.CurrentUIStateTesting){

            //测试中

            batteryDotView.setBackgroundResource(R.drawable.dot_connnect);
            batteryStatusLayout.setVisibility(View.VISIBLE);
            batteryPowerLowTipView.setVisibility(View.VISIBLE);

            selectTestGroupLayout.setVisibility(View.GONE);
            testingLayout.setVisibility(View.VISIBLE);
            testResultLayout.setVisibility(View.GONE);
            pipeDiagramBgLayout.setVisibility(View.GONE);
            pipeCodeBgLayout.setVisibility(View.GONE);

        }
        if (PressureTestManager.getInstance().currentUIState == PressureTestManager.CurrentUIState.CurrentUIStateTestEnd){

            //测试结束
            batteryDotView.setBackgroundResource(R.drawable.dot_connnect);
            batteryStatusLayout.setVisibility(View.VISIBLE);
            batteryPowerLowTipView.setVisibility(View.VISIBLE);

            selectTestGroupLayout.setVisibility(View.GONE);
            testingLayout.setVisibility(View.GONE);
            testResultLayout.setVisibility(View.VISIBLE);
            pipeDiagramBgLayout.setVisibility(View.VISIBLE);

            pipeCodeBgLayout.setVisibility(View.VISIBLE);
            submitBtn.setEnabled(true);
            submitBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.submit_btn_corner_shape));
            setResult();
        }

        //设置蓝牙模块UI
        if (PressureTestManager.getInstance().isBluetoothConnected() == true ){
            bluetoothDotView.setBackgroundResource(R.drawable.dot_connnect);
            bluetoothNameView.setText(DataManager.getInstance().getAppManager().bluetoothDevice .getName());


            batteryDotView.setBackgroundResource(R.drawable.dot_connnect);
            batteryStatusLayout.setVisibility(View.VISIBLE);
            batteryPowerLowTipView.setVisibility(View.GONE);

            bluetoothConnectView.setText("Disconnect");
            //更新电池
            if (!"-1".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceStatus)){

                batteryStatusLayout.setVisibility(View.VISIBLE);
                batteryPowerLowTipView.setVisibility(View.VISIBLE);

                if ("0".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceBattery)){
                    batteryInfoImageView.setImageResource(R.mipmap.power_0);
                    batteryPowerLowTipView.setVisibility(View.VISIBLE);
                }else if ("1".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceBattery)){
                    batteryInfoImageView.setImageResource(R.mipmap.power_1);
                    batteryPowerLowTipView.setVisibility(View.VISIBLE);
                }else if ("2".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceBattery)){
                    batteryInfoImageView.setImageResource(R.mipmap.power_2);
                    batteryPowerLowTipView.setVisibility(View.GONE);
                }else if ("3".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceBattery)){
                    batteryInfoImageView.setImageResource(R.mipmap.power_3);
                    batteryPowerLowTipView.setVisibility(View.GONE);
                }else if ("4".equals(DeviceDataManager.getInstance().currentDeviceModel.deviceBattery)){
                    batteryInfoImageView.setImageResource(R.mipmap.power_4);
                    batteryPowerLowTipView.setVisibility(View.GONE);
                }



            }else{

                batteryStatusLayout.setVisibility(View.GONE);
                batteryPowerLowTipView.setVisibility(View.GONE);

            }

        }else{
            //未连接蓝牙
            bluetoothDotView.setBackgroundResource(R.drawable.dot_disconnnect);
            bluetoothNameView.setText("Disconnected");
            bluetoothConnectView.setText("Connect");

            batteryDotView.setBackgroundResource(R.drawable.dot_disconnnect);
            batteryStatusLayout.setVisibility(View.GONE);
            batteryPowerLowTipView.setVisibility(View.GONE);

        }

    }


    public void setResult(){

        PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;

        for (Agreement  agreement : currentModel.getAgreementList()) {
            if (agreement.getId().equals(currentModel.getGroupId()) ) {
                resultTestGroupTextView.setText(agreement.getGroupName() + "kPa");
            }
        }

        List<PressureResultBean> list = DBManager.getInstance(BaseApp.getInstance()).getResultListByTempTestId(currentModel.getTempTestId());
        currentModel.resultList.addAll(list);

        List<PressureResultBean> resultList = currentModel.resultList;
        for (int i = resultList.size() - 1; i >= 0; i--){
            PressureResultBean resultBean = resultList.get(i);
            if (resultBean.getDecisionStandard().equals("Y")){
                resultStartPressureTextView.setText(resultBean.getStartPressure() + "kPa");
                resultEndtPressureTextView.setText(resultBean.getEndPressure() + "kPa");

            }
        }

        if("2".equals(currentModel.getCurrentState())){
            resultTextView.setText("FAIL");
            resultTextView.setTextColor(ContextCompat.getColor(PressureTestActivity.this, R.color.Color_Fail_Text));
        }else if("3".equals(currentModel.getCurrentState())){
            resultTextView.setText("PASS");
            resultTextView.setTextColor(ContextCompat.getColor(PressureTestActivity.this, R.color.Color_Pass_Text));
        }else{
            resultTextView.setText("");
        }

    }

    public void updateSelectGroupLayout(){

    }

    //保存试压数据
    private void savePressureTestModel(){

        User currentUser = DataManager.getInstance().getUser();

        PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;

        //如果没有testId 生成一个
        if (PressureTestManager.getInstance().currentPressureTestModel.getTempTestId() == null || "".equalsIgnoreCase(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId())){
            PressureTestManager.getInstance().currentPressureTestModel.setTempTestId(DataManager.getInstance().getTempTestId());
        }
        PressureTestManager.getInstance().currentPressureTestModel.setProjectName(getNotNullString(projectNameEditText.getText().toString()));
        PressureTestManager.getInstance().currentPressureTestModel.setAddress(getNotNullString(addressEditText.getText().toString()));
        PressureTestManager.getInstance().currentPressureTestModel.setPostCode(getNotNullString(postCodeEditText.getText().toString()));
        PressureTestManager.getInstance().currentPressureTestModel.setCompany(getNotNullString(companyEditText.getText().toString()));
        PressureTestManager.getInstance().currentPressureTestModel.setCcEmail(getNotNullString(ccEmailEditText.getText().toString()));
        PressureTestManager.getInstance().currentPressureTestModel.setPipeBrandAndType(getNotNullString(pipeBrandAndTypeEditText.getText().toString()));
        PressureTestManager.getInstance().currentPressureTestModel.setEmail(currentUser.getEmail());
        PressureTestManager.getInstance().currentPressureTestModel.setMobile(currentUser.getMobile());
        if (currentModel.getDeviceUniqueId() == null || "".equals(currentModel.getDeviceUniqueId())){
            PressureTestManager.getInstance().currentPressureTestModel.setDeviceUniqueId(DeviceDataManager.getInstance().currentDeviceModel.deviceUniqueCode);
        }

        //保存basic
        DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(PressureTestManager.getInstance().currentPressureTestModel);


        DBManager.getInstance(BaseApp.getInstance()).clearPipeCodeByTempTestId(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId());
        //保存管码
        for (int i = 0; i < PressureTestManager.getInstance().currentPressureTestModel.getPipeCodeList().size(); i++){
            String pipeCode = PressureTestManager.getInstance().currentPressureTestModel.getPipeCodeList().get(i);
            PipeCodeModel model = new PipeCodeModel();
            model.setTempTestId(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId());
            model.setPipeCode(pipeCode);
            DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(model);
        }


        DBManager.getInstance(BaseApp.getInstance()).clearPipeDiagramByTempTestId(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId());
        //保存管路图
        if (PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList().size() > 0){
            DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList());
        }


        DBManager.getInstance(BaseApp.getInstance()).clearPressureResultByTempTestId(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId());
        DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(PressureTestManager.getInstance().currentPressureTestModel.resultList);



    }


    public String getNotNullString(String str){
        if (str == null){
            return "";
        }

        return str;
    }


    // ble 蓝牙状态以及数据回调接口
    // setBlockOnBeatsBreak
    public void onEventMainThread(BroadcastEvent event) {

        Intent intent = event.getIntent();
        String action = intent.getAction();
        if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {

            DeviceDataManager.getInstance().recvs.clear();
            LogToFileUtils.write("已连接上蓝牙服务");
            // bug 82 让指令先充分走完
            showLoadingDuragion(4000);

        } else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
//            ToastUtils.showToast(this, "连接断开");
            DeviceDataManager.getInstance().currentDeviceModel.totalTime = 0;
            DeviceDataManager.getInstance().currentDeviceModel.realTime = 0;
            LogToFileUtils.write("蓝牙连接断开");
            updateCurrentState();
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
            voiceUtils.playVoice("bluetoothconnected.mp3");
            LogToFileUtils.write("蓝牙连接成功");
        } else if (RFStarBLEService.ACTION_GATT_CONNECTING.equals(action)) {

        } else if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {

        }

        updateCurrentState();
    }

    private void showLoadingDuragion(int duration) {
        // 新设备需要时间获取设备表示
        //showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //hideDialog();
                //PressureTestActivity.this.updateCurrentState();
                DeviceDataManager.getInstance().getNewAppKey();
            }


        }, duration);

    }

    private void startPressureTest(Agreement agreement, final int optionsPosition) {
       // DeviceDataManager.getInstance().selectPosition = optionsPosition;
//        ToastUtils.showToast(this, "Button Position:"+ agreementPosition+" OptionPosition:"+optionsPosition);
        //RFProgressHud.showLoading(this, "loading...");

        if ( !DataManager.getInstance().IS_DEVICEID_UPDATE){
            RFProgressHud.showErrorMsg(PressureTestActivity.this, "Device init fail");
            //重新校验一次
            DeviceDataManager.getInstance().getNewAppKey();
            return;
        }

        RFProgressHud.showLoading(PressureTestActivity.this, "waiting device data");



        new Handler().post(new Runnable() {
            @Override
            public void run() {
                LogToFileUtils.write("start test");
                DeviceDataManager.getInstance().recvs.clear();
                if (!isConnected()) {
                    RFProgressHud.hideDialog();
                    Log.i("===========hideDialog", "startPressureTest ");
                    return;
                }

                List<Option> selectPotions = PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().get(optionsPosition);
                PressureTestManager.getInstance().currentPressureTestModel.getOptionList().addAll(selectPotions);

                //Agreement selectAgreement = PressureTestManager.getInstance().currentPressureTestModel.getAgreementList().get(agreementPosition);
                //saveBleRecord(selectAgreement.getId(), selectAgreement.getGroupName(), selectPotions);



                if (DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew) {
                    LogToFileUtils.write("新设备测试组点击，执行新设备测试");
                    List<Option> options = selectPotions;
                    DeviceDataManager.getInstance().selectTestGroup(options, new DeviceDataManager.DeviceDataManagerListener() {
                        @Override
                        public void success() {
                            updateCurrentPressureModelStatus("1");
                            //RFProgressHud.hideDialog();
                        }

                        @Override
                        public void fail() {

                            RFProgressHud.hideDialog();
                            RFProgressHud.showErrorMsg(PressureTestActivity.this, "Send data to device fail");
                        }
                    });

                } else {
                    //RFProgressHud.hideDialog();
                    //旧协议
//                    LogToFileUtils.write("旧设备测试组点击，执行旧设备测试");
//                    sendDatas(getOptionData(selectPotions.get(0).getGroupId(), selectPotions));
                }
                //间隔500ms，确保参数的指令发送完
//                ((ValueAdapter) mAdapter).setItemClickListener(null);
//                optionList.clear();
//                optionList.addAll(selectPotions);
//                mTestProgressAdapter.setData(optionList);

//                Thread.sleep(500);

                PressureTestManager.getInstance().currentPressureTestModel.getOptionList().clear();
                PressureTestManager.getInstance().currentPressureTestModel.getOptionList().addAll(selectPotions);

                //发送完参数到测试中


                roundAdapter.notifyDataSetChanged();
                //生成tempId
                savePressureTestModel();
                updateCurrentState();


            }
        });


    }


    protected void sendDatas(byte[] datas) {
        if (isConnected()) {
           // BaseApp.getInstance().BLUETOOTH_CONNECTED = true;
            if (datas == null) {
                return;
            }
            Log.e("tag", "data-send:" + TransUtils.bytes2hex(datas));
            DataManager.getInstance().getAppManager().cubicBLEDevice.writeValue(datas);

        } else {
           updateCurrentState();
        }
    }


    public byte getIntByte(int number, int num) {
        byte[] b = TransUtils.int2bytes(number);
        return b[num];
    }


    private String formatNum(int num) {
        if (num < 0) {
            num = 0;
        }
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }

    }



    public void resetTestModel(){
        PressureTestManager.getInstance().currentPressureTestModel.setCurrentState("0");
        PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;
       PressureTestManager.getInstance().currentPressureTestModel.resultList.clear();
        PressureTestManager.getInstance().currentPressureTestModel.getPipeCodeList().clear();
        PressureTestManager.getInstance().currentPressureTestModel.getPipeDiagramList().clear();
        DeviceDataManager.getInstance().currentDeviceModel.deviceStatus = "0";
        PressureTestManager.getInstance().currentPressureTestModel.setDeviceUniqueId("");
        //清掉测压结果,管路图管码
        DBManager.getInstance(BaseApp.getInstance()).clearPressureResultByTempTestId(currentModel.getTempTestId());
        DBManager.getInstance(BaseApp.getInstance()).clearPipeCodeByTempTestId(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId());
        DBManager.getInstance(BaseApp.getInstance()).clearPipeDiagramByTempTestId(currentModel.getTempTestId());


        updateCurrentState();

    }

    public void updateCurrentPressureModelStatus(String status){

        //如果状态是测试完成，不再赋值
        if ("2".equals(PressureTestManager.getInstance().currentPressureTestModel.getCurrentState()) || "3".equals(PressureTestManager.getInstance().currentPressureTestModel.getCurrentState())) {

            return;
        }

        Log.i("pressureStatus: ", status);
        PressureTestManager.getInstance().currentPressureTestModel.setCurrentState(status);
        updateCurrentState();
    }

    public void showNoticeDialog(){
        //forTest

        if (noticeDialog != null){
            return;
        }
        PressureTestActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                noticeDialog = new RFNoticeDialog(PressureTestActivity.this, "The device is in pressure, reset the device?", "exit", "reset", new RFNoticeDialog.RFNoticeDialogListener(){

                    @Override
                    public void leftBtnClick() {
                        PressureTestActivity.this.finish();
                    }

                    @Override
                    public void rightBtnClick() {



                        resetDevice(true);

                        resetTestModel();

                    }

                    @Override
                    public void centerBtnClick() {

                    }
                });

                noticeDialog.show();
            }
        });
    }


    public void retestEnter(){

        //当前单据如果不是最后的测试单据， 弹框重置
//        if (!PressureTestManager.getInstance().isLastTest()){
//            DeviceDataManager.getInstance().isReadDeviceStatus = false;
//            Log.i("======","" + DeviceDataManager.getInstance().currentDeviceModel.deviceStatus);
//            showNoticeDialog();
//        }
        if(PressureTestManager.getInstance().enterType == PressureTestManager.EnterType.EnterTypeRetest){
            resetDevice(false);
            resetTestModel();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ccEmailEditText.setText(getEmailsString());
        savePressureTestModel();
    }

}
