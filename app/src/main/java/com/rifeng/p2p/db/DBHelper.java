package com.rifeng.p2p.db;

import android.app.Application;

import com.rifeng.p2p.db.greendao.DaoMaster;
import com.rifeng.p2p.db.greendao.DaoSession;

/**
 * Created by Kegh on 2018/3/28.
 */

public class DBHelper {

    private Application appInstance;

    private static volatile DBHelper ourInstance = null;

    public static DBHelper getInstance(Application currentApp) {
        if (ourInstance == null) {
            synchronized (DBHelper.class) {
                if (ourInstance == null) {
                    ourInstance = new DBHelper( currentApp);

                }
            }
        }
        return ourInstance;
    }



    private DBHelper(Application currentApp) {
        appInstance = currentApp;
        initDaoSession();
    }

    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "players.db";
    private DaoSession mDaoSession;
    private DaoMaster.OpenHelper openHelper;

    private void initDaoSession() {
        openHelper = new MyOpenHelper(
                appInstance, DATABASE_NAME, null);
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initDaoSession();
        }
        return mDaoSession;
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public void closeHelper() {
        if (openHelper != null) {
            openHelper.close();
            openHelper = null;
        }
    }
}
