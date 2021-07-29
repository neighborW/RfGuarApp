package com.rifeng.p2p.app;

import android.app.Application;
import android.content.Context;

import com.rifeng.p2p.db.DBHelper;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.util.LogToFileUtils;
/**
 * Created by Kegh on 2018/3/19.
 */

public class BaseApp extends Application {

//    public  boolean BLUETOOTH_CONNECTED = false;
//    public  boolean DATA_REQUEST_FINISH = false;

    private static BaseApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        init();



    }

    public static BaseApp getInstance() {
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    private void init(){

        LogToFileUtils.init(this);
        DataManager.getInstance();
        DBHelper.getInstance(this);

    }




}
