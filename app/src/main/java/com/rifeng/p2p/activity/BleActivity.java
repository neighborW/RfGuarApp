package com.rifeng.p2p.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;


import androidx.annotation.Nullable;

import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.manager.PressureTestManager;

import cn.fly2think.blelib.BroadcastEvent;
import cn.fly2think.blelib.TransUtils;
import de.greenrobot.event.EventBus;

/**
 * Created by Kegh on 2018/3/20.
 */

public class BleActivity extends BaseActivity {


//    protected BluetoothAdapter mBluetoothAdapter = null;
//
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected int initLayout() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        initBle();
    }

    protected void initBle() {
        if (PressureTestManager.getInstance().mBluetoothAdapter != null && !PressureTestManager.getInstance().mBluetoothAdapter.isEnabled()) {
            PressureTestManager.getInstance().mBluetoothAdapter.enable();
        }
        sendDatas(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onEventMainThread(BroadcastEvent event) {
    }

    protected boolean isConnected() {
        if (PressureTestManager.getInstance().mBluetoothAdapter == null || !PressureTestManager.getInstance().mBluetoothAdapter.isEnabled()) {
            return false;
        }
        return DataManager.getInstance().getAppManager().cubicBLEDevice != null && DataManager.getInstance().getAppManager().cubicBLEDevice.isConnected();
    }

    protected void sendDatas(byte[] datas) {
        if (isConnected()) {
            PressureTestManager.getInstance().BLUETOOTH_CONNECTED = true;
            if (datas == null) {
                return;
            }
            Log.e("tag", "data-send:" + TransUtils.bytes2hex(datas));
            DataManager.getInstance().getAppManager().cubicBLEDevice.writeValue(datas);
        }
    }


}
