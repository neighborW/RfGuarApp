package com.rifeng.p2p.manager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

import com.luck.picture.lib.entity.LocalMedia;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.db.DBManager;
import com.rifeng.p2p.entity.LastDeviceTestId;
import com.rifeng.p2p.entity.LookupCode;
import com.rifeng.p2p.entity.PressureTestModel;

import java.util.ArrayList;
import java.util.List;

import cn.fly2think.blelib.AppManager;


public class PressureTestManager {

    public BluetoothAdapter mBluetoothAdapter = null;


    //蓝牙是否已经连接上
    public  boolean BLUETOOTH_CONNECTED = false;

    //是否结束测试，获取完测试结果
    public boolean isFinishTest = false;

    //设备上个状态
    public int lastState = -1;


    //当前设备状态
    public  CurrentDeviceState currentDeviceState = CurrentDeviceState.CurrentDeviceStateFree;
    //当前界面状态
    public CurrentUIState currentUIState = CurrentUIState.CurrentUIStateNoConnect;
    //默认为新增
    public EnterType enterType = EnterType.EnterTypeNew;
    //当前测压相关数据
    public  PressureTestModel currentPressureTestModel = new PressureTestModel();

    public  List<LookupCode> testMethodList = new ArrayList<LookupCode>();



//    private AppManager manager = null;

  public  enum CurrentDeviceState{
        CurrentDeviceStateFree, //空闲
        CurrentDeviceStateTesting,
        CurrentDeviceStateFail,
        CurrentDeviceStatePass
    }


    public  enum CurrentUIState{
        CurrentUIStateNoConnect, //未连接蓝牙
        CurrentUIStateDidConnect, //已连接蓝牙
        CurrentUIStateSelectGroup, //选择组别
        CurrentUIStateTesting,  //测试中
        CurrentUIStateTestEnd   //测试结束
    }

    public  enum EnterType{
        EnterTypeNew, //新增
        EnterTypeRetest, //重测
        EnterTypeContinue, //继续
        EnterTypeSubmit //提交
    }


    private static PressureTestManager sharedInstance = null;

    public static PressureTestManager getInstance() {
        if (sharedInstance == null){
            synchronized (PressureTestManager.class){
                sharedInstance = new PressureTestManager();

            }
        }

        return sharedInstance;
    }

    private PressureTestManager(){

    }





    //判断是否连接蓝牙
    public boolean isBluetoothConnected() {
        if (mBluetoothAdapter == null ||  (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled())) {
            return false;
        }

        AppManager manager = DataManager.getInstance().getAppManager();
        BluetoothDevice currentDevice = manager.bluetoothDevice;
        if (manager.cubicBLEDevice == null){
            return false;
        }
        if (manager.cubicBLEDevice.isConnected()){
            return true;
        }else{
            return false;
        }
    }
    ;
    public List<LocalMedia> mediaList = new ArrayList<LocalMedia>();
    //for test
    public List<LocalMedia> getMediaList(){
        return mediaList;
    }

    //获取当前连接的蓝牙名称
    public String getBluetoothName(){
        String devName = "";
        AppManager manager = DataManager.getInstance().getAppManager();
        if (DataManager.getInstance().getAppManager().cubicBLEDevice != null && DataManager.getInstance().getAppManager().cubicBLEDevice.isConnected()){
            devName = manager.bluetoothDevice.getName();

        }

        if (devName == null || devName.length() == 0) {
            devName = "unknow-device";
        }


        return devName;

    }

    public void disconnectBlueTooth() {

        BluetoothDevice currentDevice = DataManager.getInstance().getAppManager().bluetoothDevice;
        if (DataManager.getInstance().getAppManager().cubicBLEDevice != null){
            DataManager.getInstance().getAppManager().cubicBLEDevice.disconnectedDevice();
            DataManager.getInstance().getAppManager().cubicBLEDevice = null;
        }
        if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {

        }
    }

    //判断是否是当前设备的测试,连接上设备后才能调用
    public boolean isLastTest(){
        if (currentPressureTestModel.getTempTestId() == null || "".equals(currentPressureTestModel.getTempTestId())){
            return false;
        }

        List<LastDeviceTestId> list =  DBManager.getInstance(BaseApp.getInstance()).getLastDeviceTestId(DeviceDataManager.getInstance().currentDeviceModel.deviceUniqueCode);

        if (list.size() > 0 && currentPressureTestModel.getTempTestId().equals(list.get(0).getTempTestId())){
            return true;
        }

        return false;
    }

    public String getLastTestId(){
        List<LastDeviceTestId> list =  DBManager.getInstance(BaseApp.getInstance()).getLastDeviceTestId(DeviceDataManager.getInstance().currentDeviceModel.deviceUniqueCode);

        if (list.size() > 0 ){
            return list.get(0).getTempTestId();
        }

        return "";
    }

}
