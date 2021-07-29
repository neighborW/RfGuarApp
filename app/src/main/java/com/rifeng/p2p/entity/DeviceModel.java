package com.rifeng.p2p.entity;

import cn.fly2think.blelib.TransUtils;

public class DeviceModel {

    //是否获取到实时数据
    public boolean isGetRealData = false;

    public String deviceStatus = "-1";

    public String deviceBattery = "-1";

    public int round = -1;  //第几轮

    //开始压力
    public double pressureStart = 0;

    //结束压力
    public double pressureEnd = 0;

    //当前压力
    public double currentPressure = 0;

    //保压时间 分钟
    public String pressureMinStr = "00";

    //保压时间 秒
    public String pressureSecondStr = "00";

    public int totalTime = 0;

    //倒计时 分钟
    public String countdownMinStr = "00";

    //倒计时 秒
    public String countdownSecondStr = "00";

    public int realTime;


    // 多少公斤模式
    public int type;

    public String deviceUniqueCode = "";


    public void clear() {
        isGetRealData = false;

        deviceStatus = "-1";

        deviceBattery = "-1";

        round = -1;  //第几轮

        //开始压力
        pressureStart = 0;

        //结束压力
        pressureEnd = 0;

        //当前压力
        currentPressure = 0;

        //保压时间 分钟
        pressureMinStr = "00";

        //保压时间 秒
        pressureSecondStr = "00";

        totalTime = 0;

        //倒计时 分钟
        countdownMinStr = "00";

        //倒计时 秒
        countdownSecondStr = "00";

        deviceUniqueCode = "";
    }

}
