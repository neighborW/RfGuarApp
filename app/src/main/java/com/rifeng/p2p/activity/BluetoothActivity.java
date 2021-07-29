package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.adapter.BluetoothListAdapter;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.manager.PressureTestManager;
import com.rifeng.p2p.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.fly2think.blelib.AppManager;
import cn.fly2think.blelib.BroadcastEvent;
import cn.fly2think.blelib.CubicBLEDevice;
import cn.fly2think.blelib.RFStarBLEService;

public class BluetoothActivity extends BleActivity implements AppManager.RFStarManageListener {

    @BindView(R.id.bluetooth_search_btn)
    Button searchBtn;

    @BindView(R.id.blue_tooth_recycle_view)
    RecyclerView bluetoothRecycleView;

    private BluetoothListAdapter mAdapter = null;

    private LinearLayoutManager linearLayoutManager;
    private String connectDeviceName;

    private List<BluetoothDevice> devices;
    private BluetoothDevice currentDevice;
    private boolean isSearching = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.bluetooth_list_layout);
        ButterKnife.bind(this);

        if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {
            DataManager.getInstance().getAppManager().cubicBLEDevice.disconnectedDevice();
            DataManager.getInstance().getAppManager().cubicBLEDevice = null;
        }

        initView();
        initBLE();
        IntentFilter statusFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bleReceiver, statusFilter);



    }

    @Override
    protected int initLayout() {
        return R.layout.bluetooth_list_layout;
    }


    @Override
    protected void initData() {

    }


    @SuppressLint("WrongConstant")
    protected void initView() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        bluetoothRecycleView.setLayoutManager(linearLayoutManager);
        devices = new ArrayList<BluetoothDevice>();
        mAdapter = new BluetoothListAdapter(this, devices, new BluetoothListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object item) {
                mAdapter.setSelect(position);
                currentDevice = (BluetoothDevice) item;
                setResult(RESULT_OK);
                finish();
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

    private void initBLE() {
        // Get local Bluetooth adapter
        PressureTestManager.getInstance().mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (PressureTestManager.getInstance().mBluetoothAdapter == null) {
            ToastUtils.showToast(this, getString(R.string.ble_no_support_ble));
            finish();
            return;
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.showToast(this, getString(R.string.ble_no_support_ble_4));
            finish();
            return;
        }

        DataManager.getInstance().getAppManager().setRFstarBLEManagerListener(this);

        if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {
            DataManager.getInstance().getAppManager().cubicBLEDevice.disconnectedDevice();
            DataManager.getInstance().getAppManager().cubicBLEDevice = null;
        }
        doDiscovery();
    }



    private void connectDevice(){
        if (currentDevice != null) {
            showLoading();
            cancelDiscovery();
            // 连接设备前先断开之前的连接
            if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {
                DataManager.getInstance().getAppManager().cubicBLEDevice.disconnectedDevice();
                DataManager.getInstance().getAppManager().cubicBLEDevice = null;
            }
            // 连接设备
            DataManager.getInstance().getAppManager().bluetoothDevice = currentDevice;
            connectDeviceName = currentDevice.getName();
            DataManager.getInstance().getAppManager().cubicBLEDevice = new CubicBLEDevice(getApplicationContext(),
                    DataManager.getInstance().getAppManager().bluetoothDevice);
        } else {
            ToastUtils.showToast(this, getString(R.string.ble_select_device));
        }
    }
    @Override
    public void RFstarBLEManageListener(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
        if (bluetoothDevice.getName().startsWith("RF")){
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
            hideDialog();
            if (DataManager.getInstance().getAppManager().cubicBLEDevice != null) {
                DataManager.getInstance().getAppManager().cubicBLEDevice.setNotification(true);
            }
            ToastUtils.showToast(this, getString(R.string.ble_connected_to) + connectDeviceName);
            setResult(RESULT_OK);
            finish();
        } else if (RFStarBLEService.ACTION_GATT_CONNECTING.equals(action)) {

        } else if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {
            byte[] datas = intent.getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
        }
    }


    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        cancelDiscovery();

        DataManager.getInstance().getAppManager().startScanBluetoothDevice();
        DataManager.getInstance().getAppManager().isEdnabled(this);
    }

    private void cancelDiscovery() {
        DataManager.getInstance().getAppManager().stopScanBluetoothDevice();
    }

    @Override
    protected void initBle() {

    }

    @Override
    protected void onDestroy() {
        cancelDiscovery();
        if(bleReceiver != null){
            unregisterReceiver(bleReceiver);
        }
        super.onDestroy();
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
}
