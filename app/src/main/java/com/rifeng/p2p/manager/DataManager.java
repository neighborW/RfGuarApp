package com.rifeng.p2p.manager;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.provider.ContactsContract;

import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.entity.AccountInfo;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.entity.UserForeManBean;
import com.rifeng.p2p.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.fly2think.blelib.AppManager;

public class DataManager {

    public enum DeviceType{
        DeviceTypeOld, //旧设备
        DeviceTypeNew, //新设备
    }


    //是否已经获取到测压结果
    public boolean didGetResult = false;
    public static List<UserForeManBean> selectedForemanList = new ArrayList<UserForeManBean>();

    private AccountInfo accountInfoDataInfo;

    private Application appInstance;
    // 是否新设备
    // 1 为不改变用户操作习惯，当用户进入连接试压仪界面时，通过试压仪返回的识别指令来判断用户选中的蓝牙设备是新/旧设备，从而触发不同的蓝牙协议；
    // 在其他位置也需要
    public DeviceType currentDeviceType;
    public boolean IS_DEVICEID_UPDATE;
    private User curLoginUser;
    SharedPreferencesUtils sp;
    //蓝牙管理器
    private AppManager manager = null;

    public BluetoothDevice currentDevice = null;


    private static DataManager sharedInstance = null;

    public static DataManager getInstance() {
        if (sharedInstance == null){
            synchronized (DataManager.class){
                sharedInstance = new DataManager();
                sharedInstance.appInstance = BaseApp.getInstance();
                sharedInstance.sp = SharedPreferencesUtils.getInstance(BaseApp.getInstance());
            }
        }

        return sharedInstance;
    }

    private DataManager(){

    }





    public AppManager getAppManager() {
        if (manager == null) {
            initAppManager();
        }
        return manager;
    }

    private void initAppManager() {
        manager = new AppManager(appInstance);

        //蓝牙UUID初始化
        manager.setReadServiceUUID("0000fff0-0000-1000-8000-00805f9b34fb");
        manager.setWriteServiceUUID("0000fff0-0000-1000-8000-00805f9b34fb");
        manager.setNotifyUUID("0000fff3-0000-1000-8000-00805f9b34fb");
        manager.setWriteUUID("0000fff1-0000-1000-8000-00805f9b34fb");
    }


    public AccountInfo getAccountInfoDataInfo() {
        return accountInfoDataInfo;
    }

    public void setAccountInfoDataInfo(AccountInfo accountInfoDataInfo) {
        this.accountInfoDataInfo = accountInfoDataInfo;
    }

    public User getUser(){
        return sp.getObject("User", User.class);
    }

    public void saveUser(User user){

        if (getUser() != null){
            User tempUser = getUser();

        }
        //保存账号
        sp.saveObject("User", user);
    }

    public void deleteUser(){

        sp.removeParams("User");
        sp.removeParams("RF_Token");
    }

    public void saveToken(String token){

        //保存账号
        sp.saveObject("RF_Token", token);
    }

    public String getToken(){


        return sp.getObject("RF_Token", String.class);
    }


    public void saveAccount(String account){

        //保存账号
        sp.saveObject("RF_Account", account);
    }

    public String getAccount(){


        return sp.getObject("RF_Account", String.class);
    }

    public String getTempTestId(){


        return getUser().getUserId() + "_" + getTimestamp(new Date());

    }

    /**
     获取精确到毫秒的时间戳
     * @param date
     * @return
     **/
    public static Long getTimestamp(Date date){
        if (null == date) {
            return (long) 0;
        }
        String timestamp = String.valueOf(date.getTime());
        return Long.valueOf(timestamp);
    }



}
