package com.rifeng.p2p.viewmodel;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.luck.picture.lib.entity.LocalMedia;
import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.LoginActivity;
import com.rifeng.p2p.activity.PressureTestActivity;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.db.DBManager;
import com.rifeng.p2p.entity.AgentInfo;
import com.rifeng.p2p.entity.Agreement;
import com.rifeng.p2p.entity.DeviceUniqueCheckResult;
import com.rifeng.p2p.entity.LookupCode;
import com.rifeng.p2p.entity.PressureResultBean;
import com.rifeng.p2p.entity.PressureTestModel;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.entity.UserAgencyPressureInfo;
import com.rifeng.p2p.http.RFException;
import com.rifeng.p2p.http.RFService;
import com.rifeng.p2p.http.RFTokenException;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.manager.PressureTestManager;
import com.rifeng.p2p.util.GetPathFromUri;
import com.rifeng.p2p.util.StringUtils;
import com.rifeng.p2p.view.RFProgressHud;
import com.rifeng.p2p.widget.RFDialog;

import org.bouncycastle.jce.provider.symmetric.ARC4;
import org.greenrobot.greendao.test.DbTest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.rifeng.p2p.viewmodel.BaseViewModel.ReturnCode.ReturnCodeNetError;

public class PressureTestViewModel extends BaseViewModel{

    public  final static String DEFAULT_TEST_MTTHOD = "Testing Method";

    ViewModelListener checkUniqueListener = null;


    public void PressureTestActivity(Activity activity){
        currentActivity = activity;
    }

    //通过网络接口获取TestMethod
    public void getTestMethod(ViewModelListener listener){

        currentListener = listener;

        mRFService.getTestMethod()
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<List<LookupCode>>() {
                    @Override
                    public void accept(List<LookupCode> list) throws Exception {

                        PressureTestManager.getInstance().testMethodList.clear();


                        PressureTestManager.getInstance().testMethodList.addAll(list);

//                        LookupCode defaultCode = new LookupCode();
//                        defaultCode.setItem_name(DEFAULT_TEST_MTTHOD);
//                        PressureTestManager.getInstance().testMethodList.add(defaultCode);



                        if (currentListener != null){

                            //保存到本地
                            DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(list);
                            currentListener.success("");
                        }
                    }
                }, mThrowableConsumer, mFinishAction);

    }

    //从本地获取testmethod
    public void getTestMethodFromLocal(ViewModelListener listener){

        currentListener = listener;
        List<LookupCode> testMethodList = DBManager.getInstance(BaseApp.getInstance()).getLookUpCodeList();
        if (testMethodList != null && testMethodList.size() > 0){
            currentListener.success("");
        }else{
            currentListener.fail("no data", ReturnCode.ReturnCodeOther);
        }


    }



    //获取试压参数
    public void getAgencyPressureInfo(ViewModelListener listener){

        currentListener = listener;

        User user = DataManager.getInstance().getUser();
        String agencyId = user.getAgentId();


        mRFService.getAgentInfo(agencyId, user.getCompanyId())
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<AgentInfo>() {
                    @Override
                    public void accept(AgentInfo agentInfo) {


                        //保存试压参数到本地
                        if (agentInfo.getAgreements() != null && agentInfo.getAgreements().size() > 0 && agentInfo.getOptions() != null && agentInfo.getOptions().size() > 0){

                            Gson gson = new Gson();
                            String jsonStr = gson.toJson(agentInfo);
                            UserAgencyPressureInfo info = new UserAgencyPressureInfo();
                            info.setUserId(user.getUserId());
                            info.setPressureInfoJsonStr(jsonStr);
                            DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(info);

                            //倒序
                            //Collections.reverse(agentInfo.getOptions());
                            PressureTestManager.getInstance().currentPressureTestModel.getAgreementList().clear();
                            PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().clear();
                            PressureTestManager.getInstance().currentPressureTestModel.getAgreementList().addAll(agentInfo.getAgreements());
                            PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().addAll(agentInfo.getOptions());

                        }else{

                        }
                        if (currentListener != null){
                            currentListener.success("");
                        }
                    }
                }, mThrowableConsumer, mFinishAction);



    }


    public void getAgencyPressureInfoFromLocal(ViewModelListener listener){

        currentListener = listener;

        User user = DataManager.getInstance().getUser();
        UserAgencyPressureInfo userAgencyPressureInfo = DBManager.getInstance(BaseApp.getInstance()).getAgencyPressureInfo(user.getUserId());
        if (userAgencyPressureInfo != null){
            Gson gson = new Gson();
            AgentInfo agentInfo = gson.fromJson(userAgencyPressureInfo.getPressureInfoJsonStr(),AgentInfo.class);

            PressureTestManager.getInstance().currentPressureTestModel.getAgreementList().clear();
            PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().clear();
            PressureTestManager.getInstance().currentPressureTestModel.getAgreementList().addAll(agentInfo.getAgreements());
            PressureTestManager.getInstance().currentPressureTestModel.getAllOptionList().addAll(agentInfo.getOptions());


            if(currentListener != null){
                currentListener.success("");
            }else{
               // currentListener.fail("", ReturnCode.ReturnCodeOther);
            }
        }else{
            if(currentListener != null){
                currentListener.fail("", ReturnCode.ReturnCodeOther);
            }
        }


    }
    //获取试压参数
    public void getServerTime(ViewModelListener listener) {
        currentListener = listener;
        mRFService.getServerTime()
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<Map<String, String>>() {
                    @Override
                    public void accept(Map<String, String> map) {

                        String timeStr = getTimeStr(Long.parseLong( map.get("currentDay")));
                        PressureTestManager.getInstance().currentPressureTestModel.setTestTimeStr(timeStr);
                        PressureTestManager.getInstance().currentPressureTestModel.setTestTime(Long.parseLong( map.get("currentDay")));


                        Log.i("==============", "" + timeStr);

                        if (currentListener != null){
                            currentListener.success("");
                        }
                    }
                }, mThrowableConsumer, mFinishAction);
    }

    public void getLocalTime(ViewModelListener listener){

        currentListener = listener;

        SimpleDateFormat sdf=new SimpleDateFormat("dd MMM yyyy hh:mm:ss aa", Locale.US);
        Date date = new Date();
        String sd = sdf.format(date);      // 时间戳转换成时间
        System.out.println("格式化结果：" + sd);

        PressureTestManager.getInstance().currentPressureTestModel.setTestTimeStr(sd);
        PressureTestManager.getInstance().currentPressureTestModel.setTestTime(date.getTime());


        if (currentListener != null){
            currentListener.success("");
        }

    }

    public String getTimeStr(long interval){

        SimpleDateFormat sdf=new SimpleDateFormat("dd MMM yyyy hh:mm:ss aa", Locale.US);
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(interval))));      // 时间戳转换成时间
        System.out.println("格式化结果：" + sd);

        return sd;
    }



    void showNoticeDialog(Context context, String title, String message, String btnPositiveStr, int backgroundRes, String textColor, boolean cancelable, View.OnClickListener btnPositivelistener) {
        try {
            RFDialog.Builder builder = new RFDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(btnPositiveStr, btnPositivelistener);
            builder.setCancelable(cancelable);
            Dialog dialog = builder.create();

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) builder.getIDialog().getTitleView().getLayoutParams();
            params.gravity = Gravity.LEFT;
            builder.getIDialog().getTitleView().setGravity(Gravity.LEFT);
            if (backgroundRes != 0) {
                builder.getIDialog().getView().setBackgroundResource(backgroundRes);
            }
            if (!StringUtils.isNullOrEmpty(textColor)) {
                builder.getIDialog().getTitleView().setTextColor(Color.parseColor(textColor));
                builder.getIDialog().getMessageView().setTextColor(Color.parseColor(textColor));
            }
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void checkDeviceUniqueCode(String uniqueCode, ViewModelListener listener){

        checkUniqueListener = listener;

        RFService mRFService = RetrofitFactory.getInstance();
        mRFService.getDeviceCheck(uniqueCode, DataManager.getInstance().getUser().getUserId())
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {


                        //保存到数据库

                        DeviceUniqueCheckResult result = new DeviceUniqueCheckResult();
                        result.setDeviceCode(uniqueCode);
                        result.setResult("Y");
                        result.setUserId(DataManager.getInstance().getUser().getUserId());
                        DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(result);


                        if (checkUniqueListener != null){
                            checkUniqueListener.success("Y");
                        }


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable t) throws Exception {
                        String msg = "";
                        ReturnCode errorCode = ReturnCode.ReturnCodeOther;
                        if (t instanceof IOException) {
                            if (currentActivity != null){
                                msg = currentActivity.getString(R.string.net_error);
                            }
                            errorCode = ReturnCodeNetError;
                            checkDeviceUniqueCodeFromLocal(uniqueCode, listener);
                            return;

                        } else if (t instanceof RFException) {
                            msg = t.getMessage();
                        }else if(t instanceof RFTokenException){
                            if (currentActivity != null){
                                msg = t.getMessage();
                                currentActivity.startActivity(new Intent(currentActivity, LoginActivity.class));
                                currentActivity.finish();
                            }

                            errorCode = ReturnCode.ReturnCodeTokenExpire;
                            //token过期

                        }
                        else {
                            if (BuildConfig.DEBUG) {
                                msg = t.getStackTrace().toString();
                            } else{
                                if (currentActivity != null){
                                    msg = currentActivity.getString(R.string.unkown_error);
                                }
                            }

                        }
                        if (BuildConfig.DEBUG) {
                            t.printStackTrace();
                        }

                        if (checkUniqueListener != null){
                            checkUniqueListener.fail(msg, errorCode);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

    }

    //本地缓存
    public void checkDeviceUniqueCodeFromLocal(String uniqueCode, ViewModelListener listener){
        checkUniqueListener = listener;

        DeviceUniqueCheckResult result =  DBManager.getInstance(BaseApp.getInstance()).getUniqueCodeCheckResult(uniqueCode, DataManager.getInstance().getUser().getUserId());
        if (result != null){
            checkUniqueListener.success("Y");
        }else{
            checkUniqueListener.fail("check device error", ReturnCodeNetError);
        }
    }

    public void submitPressureTestData(ViewModelListener listener) {
        currentListener = listener;

        PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;


        User user = DataManager.getInstance().getUser();
        JSONObject submitObj = new JSONObject();
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("engineerId", user.getEngineerId());
            obj.put("agencyId", user.getAgentId());

            obj.put("city", currentModel.getAddress());
            obj.put("projectName", currentModel.getProjectName());
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            obj.put("testDate", currentModel.getTestTimeStr());
            obj.put("postCode", currentModel.getPostCode());
            obj.put("memo", currentModel.getPipeBrandAndType());
            obj.put("plumbingCompany", currentModel.getCompany());
            obj.put("groupId",currentModel.getGroupId());
            obj.put("testIdFromApp",currentModel.getTempTestId());
            obj.put("deviceUniqueCode",currentModel.getDeviceUniqueId());
            obj.put("testType",currentModel.getTestType());

//            obj.put("testBy", mEditTestedBy.getText().toString());
            if (currentModel.getTestMethodCode() != null){
                obj.put("testMethod", currentModel.getTestMethodCode());
            }

            //TODO:
            obj.put("ccemail", currentModel.getCcEmail());

            obj.put("testResult", currentModel.getCurrentState().equals("3") ? "PASS" : "FAIL");

            JSONArray pipeCodeArray = new JSONArray();

            List<String> codeList = currentModel.getPipeCodeList();
            for (int i = 0; i < codeList.size(); i++) {

                pipeCodeArray.put(codeList.get(i));
            }

            submitObj.put("basicData", obj);
            submitObj.put("pipecode",pipeCodeArray);
            submitObj.put("history",getHistoryString());




        } catch (JSONException e) {
            e.printStackTrace();
            RFProgressHud.showErrorMsg( currentActivity,"");
            return;
        }


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), submitObj.toString());


        mRFService.submitPressureResult(body)
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String testId) {


                        if (currentListener != null){
                            currentListener.success(testId);
                        }
                    }
                }, mThrowableConsumer, mFinishAction);

    }

    public void uploadDiagrams(ViewModelListener listener) {
        currentListener = listener;


        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, RequestBody> map = new HashMap<>();
                List<LocalMedia> localPaths = PressureTestManager.getInstance().getMediaList();
                MultipartBody.Part[] photo = new MultipartBody.Part[localPaths.size()];
                localPaths.remove("plus");
                File file = null;
                int i = 0;
                for (LocalMedia imgUrl : localPaths) {
                    try {
                        file = new File(imgUrl.getRealPath());
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                        //注意：file就是与服务器对应的key,后面filename是服务器得到的文件名
                        photo[i] = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
                        i++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Map<String , Long> testId = new HashMap<>();
                testId.put("testId", Long.valueOf(PressureTestManager.getInstance().currentPressureTestModel.getTestId()));
                Log.i("==========testIdis:", PressureTestManager.getInstance().currentPressureTestModel.getTestId());
                mRFService.uploadPipeImage(photo , testId).compose(RXHelper.RFFlowableTransformer())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {

                                if (currentListener != null){
                                    currentListener.success(s);
                                }
                            }
                        }, mThrowableConsumer, mFinishAction);
            }
        }).start();
    }

    public String getHistoryString(){
        PressureTestModel currentModel = PressureTestManager.getInstance().currentPressureTestModel;

        List<PressureResultBean> resultArray = DBManager.getInstance(BaseApp.getInstance()).getResultListByTempTestId(currentModel.getTempTestId());

        if (resultArray == null || resultArray.size() == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{\"record\":[");
        for (int i = 0; i < resultArray.size(); i++){
            PressureResultBean resultBean = resultArray.get(i);
            if (i != resultArray.size() - 1){
                sb.append(String.format("{\"round\":\"%s\",\"decisionStandard\":\"%s\",\"startPressure\":\"%s\",\"history\":%s},", resultBean.getRound(), resultBean.getDecisionStandard(), resultBean.getGroupPressure(), resultBean.getResult()));
            }else{
                sb.append(String.format("{\"round\":\"%s\",\"decisionStandard\":\"%s\",\"startPressure\":\"%s\",\"history\":%s}", resultBean.getRound(), resultBean.getDecisionStandard(), resultBean.getGroupPressure(), resultBean.getResult()));

            }
        }

        sb.append("]}");

        return sb.toString();
    }


}
