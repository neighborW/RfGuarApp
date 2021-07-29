package com.rifeng.p2p.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.adapter.BluetoothListAdapter;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.manager.PressureTestManager;
import com.rifeng.p2p.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.fly2think.blelib.AppManager;
import cn.fly2think.blelib.BroadcastEvent;
import cn.fly2think.blelib.CubicBLEDevice;
import cn.fly2think.blelib.RFStarBLEService;
import cn.fly2think.blelib.TransUtils;
import de.greenrobot.event.EventBus;

public class BluetoothListDialog extends Dialog implements AppManager.RFStarManageListener{

    @BindView(R.id.bluetooth_search_btn)
    Button searchBtn;

    @BindView(R.id.blue_tooth_recycle_view)
    RecyclerView bluetoothRecycleView;


    private BluetoothListAdapter mAdapter = null;
    private String connectDeviceName;

    private List<BluetoothDevice> devices;
    private BluetoothDevice currentDevice;
    private boolean isSearching = true;

    private Activity mContext;

    private LinearLayoutManager linearLayoutManager;
    //protected BluetoothAdapter mBluetoothAdapter = null;
    private  BluetoothDialogListener mListener = null;


    public interface BluetoothDialogListener{
        public void didSelectBluetooth();
    }

    public BluetoothListDialog(@NonNull Activity context) {
        super(context);
        mContext = context;
    }

    public BluetoothListDialog(@NonNull Activity context, int themeResId, BluetoothDialogListener listener) {
        super(context, themeResId);
        mContext = context;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("","======onCreate call");
        // 指定布局
        this.setContentView(R.layout.bluetooth_list_layout);
        ButterKnife.bind(this);
        this.setCancelable(true);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initView();
        initBLE();
        IntentFilter statusFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        mContext.registerReceiver(bleReceiver, statusFilter);

        if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {
            DataManager.getInstance().getAppManager().cubicBLEDevice.disconnectedDevice();
            DataManager.getInstance().getAppManager().cubicBLEDevice = null;
        }



    }

    private BroadcastReceiver bleReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getAction()){
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch(blueState){
                        case BluetoothAdapter.STATE_TURNING_ON:
                            break;
                        case BluetoothAdapter.STATE_ON:
                            doDiscovery();
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            break;
                    }
                    break;
            }
        }
    };

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        cancelDiscovery();
        DataManager.getInstance().getAppManager().startScanBluetoothDevice();
        DataManager.getInstance().getAppManager().isEdnabled(mContext);
    }

    private void cancelDiscovery() {

        DataManager.getInstance().getAppManager().stopScanBluetoothDevice();
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        bluetoothRecycleView.setLayoutManager(linearLayoutManager);
        devices = new ArrayList<BluetoothDevice>();
        mAdapter = new BluetoothListAdapter(mContext, devices, new BluetoothListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object item) {
                mAdapter.setSelect(position);
                currentDevice = (BluetoothDevice) item;
                connectDevice();

            }

            @Override
            public boolean onItemLongClick(View itemView, int position, Object item) {
                return false;
            }
        });

        bluetoothRecycleView.setAdapter(mAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDiscovery();
            }
        });
    }
    private void connectDevice(){
        if (currentDevice != null) {
           // RFProgressHud.showLoading(getContext(), "connecting...");

            cancelDiscovery();
            // 连接设备前先断开之前的连接
            if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {
                DataManager.getInstance().getAppManager().cubicBLEDevice.disconnectedDevice();
                DataManager.getInstance().getAppManager().cubicBLEDevice = null;
            }
            // 连接设备
            DataManager.getInstance().getAppManager().bluetoothDevice = currentDevice;
            connectDeviceName = currentDevice.getName();
            DataManager.getInstance().getAppManager().cubicBLEDevice = new CubicBLEDevice(getContext().getApplicationContext(),
                    DataManager.getInstance().getAppManager().bluetoothDevice);
        }
    }

    private void initBLE() {
        // Get local Bluetooth adapter

        PressureTestManager.getInstance().mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();;

        // If the adapter is null, then Bluetooth is not supported
        if (PressureTestManager.getInstance().mBluetoothAdapter == null) {
            ToastUtils.showToast(mContext, mContext.getString(R.string.ble_no_support_ble));
            dismiss();
            return;
        }
        if (!mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.showToast(mContext, mContext.getString(R.string.ble_no_support_ble_4));
            dismiss();
            return;
        }

        DataManager.getInstance().getAppManager().setRFstarBLEManagerListener(this);

        if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {
            DataManager.getInstance().getAppManager().cubicBLEDevice.disconnectedDevice();
            DataManager.getInstance().getAppManager().cubicBLEDevice = null;
        }
        doDiscovery();
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
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean isConnected() {
        if (PressureTestManager.getInstance().mBluetoothAdapter == null || !PressureTestManager.getInstance().mBluetoothAdapter.isEnabled()) {
            return false;
        }
        Log.i("=====","cubicBLEDevice != null: " + (DataManager.getInstance().getAppManager().cubicBLEDevice != null) );
        return DataManager.getInstance().getAppManager().cubicBLEDevice != null && DataManager.getInstance().getAppManager().cubicBLEDevice.isConnected();
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

        }
    }


    @Override
    public void RFstarBLEManageListener(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {

        if (bluetoothDevice.getName()!= null && bluetoothDevice.getName().startsWith("RF")) {
            devices.add(bluetoothDevice);

            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void RFstarBLEManageStartScan() {
        devices.clear();
    }

    @Override
    public void RFstarBLEManageStopScan() {

    }

    public void onEventMainThread(BroadcastEvent event) {
        Intent intent = event.getIntent();
        String action = intent.getAction();
        if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {
        } else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
//            ToastUtils.showToast(this, getString(R.string.ble_connection_lost));
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
            if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {
                DataManager.getInstance().getAppManager().cubicBLEDevice.setNotification(true);
            }


            if (mListener != null){
                mListener.didSelectBluetooth();
            }
            Log.i("=====","did connect");
            //RFProgressHud.hideDialog();
            dismiss();
        } else if (RFStarBLEService.ACTION_GATT_CONNECTING.equals(action)) {

        } else if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {
            byte[] datas = intent.getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
        }
    }
}
