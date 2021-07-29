package com.rifeng.p2p.manager;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.R;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.db.DBManager;
import com.rifeng.p2p.entity.DeviceModel;
import com.rifeng.p2p.entity.Option;
import com.rifeng.p2p.entity.Point;
import com.rifeng.p2p.entity.PressureResultBean;
import com.rifeng.p2p.entity.UpdataUIEven;
import com.rifeng.p2p.http.RFException;
import com.rifeng.p2p.http.RFService;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.util.LogToFileUtils;
import com.rifeng.p2p.util.StringUtils;
import com.rifeng.p2p.view.RFProgressHud;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.fly2think.blelib.BroadcastEvent;
import cn.fly2think.blelib.RFStarBLEService;
import cn.fly2think.blelib.TransUtils;
import de.greenrobot.event.EventBus;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by keguihong on 2017/9/15.
 */

public class DeviceDataManager {
    //是否成功发送测压参数
    public static boolean didSuccessSendSelectParam = false;

    public interface DeviceDataManagerListener{

        public void success();
        public void fail();
    }

    public DeviceDataManagerListener mListener = null;

    public DeviceModel currentDeviceModel = new DeviceModel();

    public final static int maxTime = 600;

    private static DeviceDataManager ourInstance;

    public List<Byte> recvs = new ArrayList<>();
    public int len = 0;

    private int bleDeviceRound;

    //是否要使用设备状态
    public boolean isReadDeviceStatus = false;
    public String result = "";

    OnUpdateDataListener updateDataListener;
    OnAddEngResultListener onAddEnqListener;

    public boolean didGetDeviceUniqueCode= false;


    File bleDataFile;
    //private BleRecords records = new BleRecords();
    List<String> testRoundTime = new ArrayList<>();
    List<Long> optionList = new ArrayList<>();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();


    private DeviceDataManager() {
        bleDataFile = getBleDataFile(BaseApp.getInstance().getApplicationContext().getString(R.string.app_name));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public static DeviceDataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DeviceDataManager();
        }
        return ourInstance;
    }

    public void clear() {
        recvs = new ArrayList<>();
        len = 0;

        currentDeviceModel.pressureStart = 0;
        currentDeviceModel.pressureEnd = 0;
        currentDeviceModel.currentPressure = 0;
        currentDeviceModel.totalTime = 0;
        currentDeviceModel.realTime = 0;

        result = "";
        LogToFileUtils.write("清空缓存数据");
    }

    public void exit() {
//        clear();
        if (EventBus.getDefault().isRegistered(ourInstance)) {
            EventBus.getDefault().unregister(ourInstance);
        }
        ourInstance = null;
    }

    // 蓝牙 状态以及数据回调接口
    public void onEventMainThread(BroadcastEvent event) {


        Intent intent = event.getIntent();
        String action = intent.getAction();
        if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {
            LogToFileUtils.write("蓝牙状态：ACTION_GATT_CONNECTED");

        } else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
            currentDeviceModel.totalTime = 0;
            currentDeviceModel.realTime = 0;
            LogToFileUtils.write("蓝牙断开链接，清空总时及实时的时间");
            didGetDeviceUniqueCode = false;
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
        } else if (RFStarBLEService.ACTION_GATT_CONNECTING.equals(action)) {
        } else if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {
            byte[] datas = intent.getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
            Log.e("tag", "data:" + TransUtils.bytes2hex(datas));
            LogToFileUtils.write("data-get:" + TransUtils.bytes2hex(datas));

            // 新设备 发送 fff3 到手机
            // 收到 fff3 表示新设备
            if ((datas[0] & 0xFF) == 0xFF && (datas[1] & 0xFF) == 0xF3) {
                LogToFileUtils.write("新设备发送fff3到手机 手机回复fff1");
                // 需要回复 fff1 给设备
                DataManager.getInstance().currentDeviceType = DataManager.DeviceType.DeviceTypeNew;
                // 数据溢出 -1 表示 FF, -2 表示 FE
                byte[] settings = {-1, -15};

                sendDatas(settings);
                return;
            }

            //收到测压结果
            if (datas[0] == 0x57 && datas[1] == 0x50 && datas[2] == 0x47 && datas[3] == 0x05) {
                if (DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew
                ) {
                    getNewPressurePreAll(datas);
                } else {
                    getPressurePerAll(datas);
                }
                recvs.clear();
            } else if (datas[0] == 0x57 && datas[1] == 0x50 && datas[2] == 0x47 && datas[3] == 0x03 && datas[4] == 0x00) {
                forwardOldAppId();
                recvs.clear();
            } else if (datas[0] == 0x57 && datas[1] == 0x50 && datas[2] == 0x47 && datas[3] == 0x10 && datas[4] == 0x02 && datas[5] == 0x4f && datas[6] == 0x4b) {
                //接收到APP发的数据，测压仪应返回一个指令，表示数据已接收。
                //0x57  0x50 0x47  0x10   0x02  0x4f  0x4b
                if (mListener != null){
                    //发送完试压参数，设备收到指令返回
                    Log.i("====", "发送完试压参数，设备收到指令返回");
                    didSuccessSendSelectParam = true;
                    mListener.success();
                }
                recvs.clear();
            } else if (datas[0] == 0x57 && datas[1] == 0x50 && datas[2] == 0x47 && datas[3] == 0x0A && datas[4] == 0x0B) {

                //获取到设备唯一码
//                if(!(DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew)){
                didGetDeviceUniqueCode = true;
                // 获取设备id
                Log.e("tag", "获取新设备 试压仪编码");
                String responseID = TransUtils.bytes2hex(datas);
                int length = responseID.length();
                String tmp = responseID.substring(10, length);
                Log.e("tag", "试压仪编码 " + tmp);
                currentDeviceModel.deviceUniqueCode = tmp;
                updateDataListener.didGetDeviceUniqueCode();


            } else {

                for (byte b : datas) {
                    recvs.add(b);
                    if (recvs.size() == 1) {
                        if (recvs.get(0) != 0x57) {
                            recvs.clear();
                        }
                    } else if (recvs.size() == 2) {
                        if (recvs.get(1) != 0x50) {
                            recvs.clear();
                        }
                    } else if (recvs.size() == 3) {
                        if (recvs.get(2) != 0x47) {
                            recvs.clear();
                        }
                    } else if (recvs.size() == 5) {
                        len = recvs.get(4) & 0xff;
                    } else if (recvs.size() == len + 5) {




                        if (updateDataListener != null) {
                            forwardDatas(TransUtils.toPrimitive(recvs));
                            updateDataListener.updateDataListener(TransUtils.toPrimitive(recvs));
                        }

                        UpdataUIEven even = new UpdataUIEven();
                        even.setRecvs(TransUtils.toPrimitive(recvs));
                        EventBus.getDefault().post(even);
                        recvs.clear();
                    }
                }


            }



        }

    }

    public void fail(String msg) {
        DataManager.getInstance().IS_DEVICEID_UPDATE = true;
        if (DeviceDataManager.this.onAddEnqListener != null) {
            DeviceDataManager.this.onAddEnqListener.onFaile(msg);
        }
    }

    private void forwardDatas(byte[] datas) {
        //语音播报
        if (datas[3] == 0x02) {
            switch (datas[5]) {
                case 0x30:
                    Log.e("tag", "请加压");
                    return;
                case 0x31:
                    Log.e("tag", "超压");
                    return;
                case 0x32:
                    Log.e("tag", "测试异常");
                    return;
                case 0x33:
                    Log.e("tag", "继续测试");
                    return;
                case 0x34:
                    Log.e("tag", "低电提示");
                    return;
                case 0x35:
                    Log.e("tag", "已超压");
                    return;
            }

            int value = datas[5] & 0xff;
            if (value % 2 == 0) {
                int end = value / 2;
                Log.e("tag", "第" + end + "次保压结束");
            } else {
                int start = value / 2 + 1;
                Log.e("tag", "第" + start + "次保压开始");
            }
            //实时数据模式
        } else if (datas[3] == 0x01) {


            currentDeviceModel.isGetRealData = true;

            // strSeveralTests
            currentDeviceModel.round = datas[7] & 0xff;

            byte[] pressure_starts = {0x00, 0x00, datas[8], datas[9]};
            //初始压力值 测试初始值 strTestInitialValue
            currentDeviceModel.pressureStart = Integer.parseInt(TransUtils.bytes2hex(pressure_starts), 16);

            byte[] pressure_ends = {0x00, 0x00, datas[10], datas[11]};
            //最终压力值 测试结束值 strTestEndValue
            currentDeviceModel.pressureEnd = Integer.parseInt(TransUtils.bytes2hex(pressure_ends), 16);

            byte[] pressure_reals = {0x00, 0x00, datas[12], datas[13]};
            //实时压力值 当前压力值 strCurrentPressureValue
            currentDeviceModel.currentPressure = Integer.parseInt(TransUtils.bytes2hex(pressure_reals), 16);

            //倒计时总时间
            // strTheHoldingTimeMinutes
            int minT = datas[14] & 0xff;
            // strTheHoldingTimeSeconds
            int secT = datas[15] & 0xff;
            // 一个周期总共时间
            currentDeviceModel.totalTime = minT * 60 + secT;

            //实时倒计时
            // strCountdownTimeMinutes
            int min = datas[16] & 0xff;
            // strCountdownTimeSeconds
            int sec = datas[17] & 0xff;
            // 实际倒计时时间
            currentDeviceModel.realTime = min * 60 + sec;

            // strKilogramsModel
            int type = datas[18] & 0xff;//测试模式（多少公斤）


            String deviceType = "";
            if (DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew) {
                deviceType = "新设备(数据最后读取保存)";
            } else {
                deviceType = "旧设备(数据实时保存)";
            }

            if(PressureTestManager.getInstance().currentPressureTestModel.getDeviceUniqueId() == null || "".equals(PressureTestManager.getInstance().currentPressureTestModel.getDeviceUniqueId())){
                getNewAppKey();
            }
            //如果新连接上没获取到key
//            if (!didGetDeviceUniqueCode) {
//                getNewAppKey();
//            }
            //电池
            switch (datas[6]){
                case 0x00:
                    currentDeviceModel.deviceBattery = "0";
                    break;
                case 0x01:
                    currentDeviceModel.deviceBattery = "1";
                    break;
                case 0x02:
                    currentDeviceModel.deviceBattery = "2";
                    break;
                case 0x03:
                    currentDeviceModel.deviceBattery = "3";
                    break;
                case 0x04:
                    currentDeviceModel.deviceBattery = "4";
                    break;
                default:
                    currentDeviceModel.deviceBattery = "0";
                    break;
            }
            switch (datas[5]) {


                case 0x00:
                    // result strEquipmentState 就是设备状态
                    LogToFileUtils.write("蓝牙等待状态");
                    result = "等待";
                    // 等待中，压力开始值设置为0
                    currentDeviceModel.pressureStart = 0;
                    currentDeviceModel.deviceStatus = "0";

                    break;
                case 0x01:
                    result = "测试中";
                    currentDeviceModel.deviceStatus = "1";
                    if (!(DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew)) {

                        //旧设备暂不处理

                    }
                    LogToFileUtils.write("设备类型:" + deviceType + "测试中：第" + currentDeviceModel.round + "次测试 " + "状态结果：" + result + " 初始压力值：" + currentDeviceModel.pressureStart + " 实时压力值：" + currentDeviceModel.currentPressure + " pressure_end：" + currentDeviceModel.pressureEnd + " 倒计时总时间：" + currentDeviceModel.totalTime + " 实时倒计时:" + currentDeviceModel.realTime);

                    break;
                case 0x02:
                    currentDeviceModel.deviceStatus = "2";
                    LogToFileUtils.write("异常");
                    result = "FAIL";
                    if (DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew) {
                        getResultData("FAIL");
                    } else {
                        getResultData("FAIL");
                    }
                    break;
                case 0x03:
                    if ( currentDeviceModel.round == PressureTestManager.getInstance().currentPressureTestModel.optionList.size()){
                        currentDeviceModel.deviceStatus = "3";
                        result = "PASS";
                        getResultData("PASS");
                        break;
                    }

            }




        } else if (datas[3] == 0x03) {
            //连接上默认发送 5750470300

        } else if (datas[3] == 0x08) {
            //新设备，回复415418010A
            Log.e("TAG", "新设备反馈 57504708010A");
            DataManager.getInstance().currentDeviceType = DataManager.DeviceType.DeviceTypeNew;
            forwardNewAppId();
        }
    }


    /**
     * APP识别码
     * 收到0x57  0x50  0x47  0x03   0x00指令时需回应
     */
    private void forwardOldAppId() {
        byte[] settings = {0x41, 0x54, 0x12, 0x03, 0x01, 0x02, 0x03};
        sendDatas(settings);
    }

    // 新设备，发送 415418010A
    private void forwardNewAppId() {
//        41 54 18 01 0A
        byte[] settings = {0x41, 0x54, 0x18, 0x01, 0x0A};
        sendDatas(settings);
    }

    public void getNewAppKey() {
        byte[] settings = {0x41, 0x54, 0x0A, 0x00, 0x0A};
        sendDatas(settings);
    }


    protected boolean isConnected() {
        return DataManager.getInstance().getAppManager().cubicBLEDevice != null && DataManager.getInstance().getAppManager().cubicBLEDevice.isConnected();
    }

    protected void sendDatas(byte[] datas) {
        if (isConnected()) {
            if (datas == null) {
                return;
            }
            Log.e("tag", "data-send:" + TransUtils.bytes2hex(datas));
            LogToFileUtils.write("data-send:" + TransUtils.bytes2hex(datas));
            LogToFileUtils.write("data-send:" + TransUtils.bytes2hex(datas));
            DataManager.getInstance().getAppManager().cubicBLEDevice.writeValue(datas);
        }
    }

    public void getResultData(String result) {


        //如果当前单据是测试中
        if (updateDataListener != null) {
            updateDataListener.getResule4Wating();
        }
//            //试压仪最后round是加了1的
        getBleDeviceData();

    }


    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            if (result.equalsIgnoreCase("FAIL")) {
                if (bleDeviceRound <= currentDeviceModel.round) {
                    forwardDataPreAll(bleDeviceRound);
                    mHandler.postDelayed(this, 1000);
                    bleDeviceRound++;
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //这里为<round是因为最后的结果中，试压仪返回的round是+1了的
                            //弹窗显示失败
                            if (updateDataListener != null) {
                                updateDataListener.testFail();

                            }
                            //forwordFinishAllTest();
                        }
                    }, 1000);
                }
            } else {


                if (bleDeviceRound <= PressureTestManager.getInstance().currentPressureTestModel.getOptionList().size()) {
                    forwardDataPreAll(bleDeviceRound);
                    mHandler.postDelayed(this, 1000);
                    bleDeviceRound++;
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //这里为<round是因为最后的结果中，试压仪返回的round是+1了的
                            if (updateDataListener != null) {
                                //弹窗显示成功
                                //updateDataListener.testPass();


                            }

                            //forwordFinishAllTest();
                        }
                    }, 2000);
                }
            }

        }
    };


    /**
     * 获取试压仪保存的数据
     */
    private void getBleDeviceData() {
        bleDeviceRound = 1;
        mHandler.post(r);
    }


    public void setUpdateDataListener(OnUpdateDataListener updateDataListener) {
        this.updateDataListener = updateDataListener;
    }

    public void setOnAddEnqListener(OnAddEngResultListener onAddEnqListener) {
        this.onAddEnqListener = onAddEnqListener;
    }

    private File getBleDataFile(String id) {
        File file;
        // 判断是否有SD卡或者外部存储器
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 有SD卡则使用SD - PS:没SD卡但是有外部存储器，会使用外部存储器
            // SD\Android\data\包名\files\Log\logs.txt
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BleData/");
        } else {
            // 没有SD卡或者外部存储器，使用内部存储器
            // \data\data\包名\files\Log\logs.txt
            file = new File(BaseApp.getInstance().getFilesDir().getPath() + "/BleData/");
        }
        // 若目录不存在则创建目录
        if (!file.exists()) {
            file.mkdir();
        }
        File bleDataFile = new File(file.getPath(), id + ".txt");
        if (!bleDataFile.exists()) {
            try {
                bleDataFile.createNewFile();
            } catch (Exception e) {
                Log.e("tag", "Create file failure !!! " + e.toString());
            }
        }
        return bleDataFile;
    }

    public void writeBleData(String str) {
//        // 判断是否初始化或者初始化是否成功
//        bleDataFile = getBleDataFile(BaseApp.getInstance().getApplicationContext().getString(R.string.app_name));
//        if (null == bleDataFile || !bleDataFile.exists()) {
//            return;
//        }
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(bleDataFile, true));
//            bw.write(str);
//            bw.write("\r\n");
//            bw.flush();
//        } catch (Exception e) {
//            Log.e("tag", "Write failure !!! " + e.toString());
//        }
        LogToFileUtils.write(str);
    }

    public interface OnUpdateDataListener {

        void didConnectBlueTooth();


        void didGetDeviceUniqueCode();

        void didCheckUniqueCode();

        void updateDataListener(byte[] datas);

        void goResult(String result);

        void testPass();

        void testFail();

        void getResule4Wating();
    }


    public interface OnAddEngResultListener {
        void onFaile(String faileMsg);
    }

    //重置试压仪
    public void resetDevice(DeviceDataManagerListener listener){
        forwordFinishAllTest();
        //Thread.sleep(1000);
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {

                if (listener != null){


                    listener.success();
                }
            }
        }, 2000);

    }

    public void forwordFinishAllTest() {
        LogToFileUtils.write("发送重置设备指令");
        if (DataManager.getInstance().currentDeviceType == DataManager.DeviceType.DeviceTypeNew) {
            //新设备重置
            byte[] replys = {0x41, 0x54, 0x31, 0x00};
            sendDatas(replys);
        } else {
            byte[] replys = {0x41, 0x54, 0x13, 0x00};
            sendDatas(replys);
        }

        currentDeviceModel.clear();

    }

    public void forwardDataPreAll(int round) {
        byte[] bytes = {0x41, 0x54, 0x14, 0x01, TransUtils.int2bytes(round)[3]};
        sendDatas(bytes);
    }


    public void getNewPressurePreAll(byte[] datas) {
        LogToFileUtils.write("新设备 DataManager: 蓝牙返回575047");
        // 需求： 将目前实时获取试压数据的机制改为可延迟获取试压数据 未完成
        // 只有 20个字节
        // 数据个数
        long num = datas[4] & 0xff;
        // 数据下标(顺序)
        long squence = datas[5] & 0xff;
        // 测试轮数 ()
        int rounds = datas[7] & 0xff;
        // 分钟
        int starMin = datas[8] & 0xff;

        String resultStr = "";
        try {
            resultStr = new String(datas, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        float startPressure = 0;
        float endPressure = 0;

        List<String> pointArray = new ArrayList<String>();
        // press 值, 需要拿剩下的所有位数
        // 每2个字节代表一个压力数据
        for (int i = 9; i < datas.length - 1; i = i + 2) {
            byte[] press_value = {0x00, 0x00, datas[i], datas[i + 1]};
            // 压力数据
            Integer cur_press_value = Integer.parseInt(TransUtils.bytes2hex(press_value), 16);
            if (i == 9) {
                //第一个作为结束压力
                endPressure = cur_press_value;
            }
            //最后一个为开始压力
            startPressure = cur_press_value;
            // 当前第几轮
            int curRound = rounds;
//            saveNewValue(curRound + "", starMin + "", cur_press_value + "");
            pointArray.add("{\"point\"" +  ":\""+ starMin+"min\",\"pressure\":" + cur_press_value + "}");
            // 对应的分钟数 从起始分钟数开始递减
            starMin--;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("[");

        if (pointArray.size() > 0) {
            for (int i = pointArray.size() - 1; i >= 0; i--) {
                String point = pointArray.get(i);
                if (i == 0) {
                    sb.append(point);
                } else {
                    sb.append(point);
                    sb.append(",");
                }
            }
        }

        sb.append("]");


        Log.i("======didGetResult", "" + sb.toString() + "   rounds:" + rounds + " optinSize:" + PressureTestManager.getInstance().currentPressureTestModel.getOptionList().size());

        if (rounds <= PressureTestManager.getInstance().currentPressureTestModel.getOptionList().size()) {

            Option option = PressureTestManager.getInstance().currentPressureTestModel.getOptionList().get(rounds-1);
            PressureResultBean resultBean = new PressureResultBean();
            resultBean.setTempTestId(PressureTestManager.getInstance().currentPressureTestModel.getTempTestId());
            resultBean.setRound("" + rounds);
            resultBean.setResult(sb.toString());
            resultBean.setGroupPressure(option.getStartPressure() + "");
            resultBean.setStartPressure(startPressure + ""); //
            resultBean.setEndPressure(endPressure + "");
            resultBean.setDecisionStandard(option.getDecisionStandard());

            //如果当前单据是设备最后的测试单据
            if (PressureTestManager.getInstance().isLastTest()){

                //保存到数据库
                DBManager.getInstance(BaseApp.getInstance()).insertOrReplace(resultBean);


                if ("3".equals(currentDeviceModel.deviceStatus)){
                    //如果是获取到最后一轮数据
                    if (rounds == PressureTestManager.getInstance().currentPressureTestModel.getOptionList().size()) {
                        updateDataListener.testPass();
                        // 处理完所有数据
                        DataManager.getInstance().didGetResult = true;
                    }
                }else if("2".equals(currentDeviceModel.deviceStatus)){
                    updateDataListener.testFail();

                }


            }

        }







    }

    public void getPressurePerAll(byte[] bytes) {
        LogToFileUtils.write("蓝牙返回57504705XXXXX, 读取获取所有试压数据");
        int length = bytes[4] & 0xff;
        int round = bytes[5] & 0xff;
        int min = bytes[6] & 0xff;
        if (round > 0 && round - 1 < testRoundTime.size()) {
            int testTime = Integer.valueOf(testRoundTime.get(round - 1));


            for (int i = min, j = 0, k = length - 2; k > 0; k -= 2, j++, i--) {
                String p = "";
                p = j + "min";
//                Point point = DBManager.getInstance().getPoint(records.getTestId(), records.getGroupId(), round, p);
//                if (point != null) {
//                    int pressure = getPressureValue(bytes[7 + j * 2], bytes[7 + j * 2 + 1]);
//                    point.setPressure(pressure + "");
//                    point.setPoint(p);
//                    DBManager.getInstance().insertOrReplace(point);
//                    LogToFileUtils.write("getPressurePerAll:" + " testId:" + records.getTestId() + " groudId" + records.getGroupId() + " round:" + round + " point:" + p + " pressure:" + pressure);
//                }
            }

        }

    }

    public int getPressureValue(byte h, byte l) {
        byte[] bytes = {0x00, 0x00, h, l};
        //最终压力值
        int pressure = Integer.parseInt(TransUtils.bytes2hex(bytes), 16);
        return pressure;
    }

    public void selectTestGroup( List<Option> options, DeviceDataManagerListener listener){
        this.mListener = listener;
        didSuccessSendSelectParam = false;
        try {
            for (Option o : options) {
                //休眠100ms
                // 2 因新蓝牙芯片的接收字节数受限，APP将发送试压仪字节数调整为20字节/次，超过20个字节分多次发送，间隔20ms (后面改成100ms)
                Thread.sleep(100);
                String group = o.getGroup();
                sendDatas(getNewOptionData(group, o));
            }
            //发送完设置参数,发送这个指令，试压仪开始试压工作
            Thread.sleep(1000);
            ;//间隔500ms，确保参数的指令发送完
            //41541300
            byte[] confirms = {0x41, 0x54, 0x13, 0x00};
            sendDatas(confirms);

            //三秒后如果不成功则返回失败
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (didSuccessSendSelectParam == false){
                    if (DeviceDataManager.this.mListener != null){
                        DeviceDataManager.this.mListener.fail();
                    }
                }


            }, 3000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    // 新协议
    // 3 APP将后台已维护对应代理的超压压力上限值参数（即起始压力-组别压力>?kPa不允许进入测压阶段）以指令的形式发给试压仪，用于试压仪自动判断是否超压
    private byte[] getNewOptionData(String groupId, Option option) {
        byte[] datas = new byte[14];
        datas[0] = 0x41;
        datas[1] = 0x54;
        datas[2] = 0x11;
        datas[3] = 0x0A;
        // 添加groupId
        datas[4] = getIntByte(Integer.parseInt(groupId), 3);
        // 加入 roundId
        datas[5] = getIntByte(Integer.parseInt(option.getRoundId()), 3);
        //测试压力值
        long startPressure = (int)(Double.parseDouble(option.getStartPressure())) / 10;
        datas[6] = getIntByte((int) startPressure, 3);
        //分
        int min = (int)(Double.parseDouble(option.getTestTime())) / 60;
        datas[7] = getIntByte(min, 3);
        //秒
        int sec =  (int)(Double.parseDouble(option.getTestTime())) % 60;
        datas[8] = getIntByte(sec, 3);
        //降压
        int decRange =  (int)(Double.parseDouble(option.getPressureDecRange())) / 10;
        datas[9] = getIntByte(decRange, 3);
        //升压
        int riseRange =  (int)(Double.parseDouble(option.getPressureRiseRange()));
        datas[10] = getIntByte(riseRange, 3);
        String decisionStandard = option.getDecisionStandard();
        //自动判断
        if (decisionStandard != null && decisionStandard.equalsIgnoreCase("Y")) {
            datas[11] = 0x01;
        } else {
            datas[11] = 0x00;
        }
        //提示超压
        int overpressure =  (int)(Double.parseDouble(option.getOverpressure()));
        datas[12] = getIntByte(overpressure, 3);

        // 超压上线
        datas[13] = getIntByte( (int)(Double.parseDouble(option.getCannotpressureNum())), 3);

        return datas;
    }



    //获取试压指令(旧协议)
    private byte[] getOptionData(String groupId, List<Option> list) {
        byte[] datas = new byte[5 + list.size() * 7];
        datas[0] = 0x41;
        datas[1] = 0x54;
        datas[2] = 0x11;
        datas[3] = getIntByte((list.size() * 7 + 1), 3);
        datas[4] = getIntByte(Integer.parseInt(groupId), 3);
        for (int i = 0; i < list.size(); i++) {
            // 测试压力值
            long startPressure = Long.parseLong(list.get(i).getStartPressure()) / 10;
            datas[4 + 1 + 7 * i] = getIntByte((int) startPressure, 3);
            //分
            int min =  (int)(Double.parseDouble(list.get(i).getTestTime())) / 60;
            datas[4 + 2 + 7 * i] = getIntByte(min, 3);
            //秒
            int sec = (int)(Double.parseDouble(list.get(i).getTestTime())) % 60;
            datas[4 + 3 + 7 * i] = getIntByte(sec, 3);
            // 降压
            int decRange =  (int)(Double.parseDouble(list.get(i).getPressureDecRange())) / 10;
            datas[4 + 4 + 7 * i] = getIntByte(decRange, 3);
            // 升压
            int riseRange =  (int)(Double.parseDouble(list.get(i).getPressureRiseRange()));
            datas[4 + 5 + 7 * i] = getIntByte(riseRange, 3);
            // 自动判断
            String decisionStandard = list.get(i).getDecisionStandard();
            if (decisionStandard != null && decisionStandard.equalsIgnoreCase("Y")) {
                datas[4 + 6 + 7 * i] = 0x01;
            } else {
                datas[4 + 6 + 7 * i] = 0x00;
            }
            //提示超压
            int overpressure =  (int)(Double.parseDouble(list.get(i).getOverpressure()));
            datas[4 + 7 + 7 * i] = getIntByte(overpressure, 3);
        }
        return datas;
    }

    public byte getIntByte(int number, int num) {
        byte[] b = TransUtils.int2bytes(number);
        return b[num];
    }

}
