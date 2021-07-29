package com.rifeng.p2p.db;



import android.app.Application;
import android.util.Log;

import com.rifeng.p2p.db.greendao.DeviceUniqueCheckResultDao;
import com.rifeng.p2p.db.greendao.LastDeviceTestIdDao;
import com.rifeng.p2p.db.greendao.PipeCodeModelDao;
import com.rifeng.p2p.db.greendao.PipeDiagramModelDao;
import com.rifeng.p2p.db.greendao.PressureResultBeanDao;
import com.rifeng.p2p.db.greendao.PressureTestModelDao;
import com.rifeng.p2p.db.greendao.UserAgencyPressureInfoDao;
import com.rifeng.p2p.entity.DeviceUniqueCheckResult;
import com.rifeng.p2p.entity.LastDeviceTestId;
import com.rifeng.p2p.entity.LookupCode;
import com.rifeng.p2p.entity.PipeCodeModel;
import com.rifeng.p2p.entity.PipeDiagramModel;
import com.rifeng.p2p.entity.PressureResultBean;
import com.rifeng.p2p.entity.PressureTestModel;
import com.rifeng.p2p.entity.UserAgencyPressureInfo;
import com.rifeng.p2p.entity.UserForeManBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

/**
 * Created by Kegh on 2018/3/28.
 */

public class DBManager<T> {

    private Application appInstance;

    private static volatile DBManager instance = null;

    public static DBManager getInstance(Application currentApp) {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                    instance.appInstance = currentApp;
                }
            }
        }
        return instance;
    }

    /**
     * 插入一条数据
     *
     * @param data
     */
    public boolean insert(T data) {
        return DBHelper.getInstance(appInstance).getDaoSession().insert(data) != -1;//不等于-1是true 否则是false
    }

    /**
     * 插入一条数据
     */
    public void inser(final List<T> datas) {
        DBHelper.getInstance(appInstance).getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (T object : datas) {
                    DBHelper.getInstance(appInstance).getDaoSession().insert(object);
                }
            }
        });
    }

    /**
     * 插入一条数据
     *
     * @param data
     */
    public boolean insertOrReplace(T data) {
        return DBHelper.getInstance(appInstance).getDaoSession().insertOrReplace(data) != -1;//不等于-1是true 否则是false
    }


    /**
     * 插入一条数据
     */
    public void insertOrReplace(final List<T> datas) {
        DBHelper.getInstance(appInstance).getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (T object : datas) {
                    long result = DBHelper.getInstance(appInstance).getDaoSession().insertOrReplace(object);
                }
            }
        });
    }


    public void update(T data) {
        DBHelper.getInstance(appInstance).getDaoSession().update(data);
    }


    public void update(final List<T> datas) {
        DBHelper.getInstance(appInstance).getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (T object : datas) {
                    DBHelper.getInstance(appInstance).getDaoSession().update(object);
                }
            }
        });
    }



    public void delete(T data) {
        DBHelper.getInstance(appInstance).getDaoSession().delete(data);
    }


    public void delete(final List<T> datas) {
        DBHelper.getInstance(appInstance).getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (T object : datas) {
                    DBHelper.getInstance(appInstance).getDaoSession().delete(object);
                }
            }
        });
    }


    public void cleanTable(final T data) {
        DBHelper.getInstance(appInstance).getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                DBHelper.getInstance(appInstance).getDaoSession().deleteAll(data.getClass());

            }
        });
    }

    //获取包工头列表
    public List<UserForeManBean> getForeManList() {
        return DBHelper.getInstance(appInstance).getDaoSession().getUserForeManBeanDao().loadAll();
    }


    //获取testmethod列表
    public List<LookupCode> getLookUpCodeList(){
        return DBHelper.getInstance(appInstance).getDaoSession().getLookupCodeDao().loadAll();
    }

    //获取试压参数
    public UserAgencyPressureInfo getAgencyPressureInfo(String userId){

        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(UserAgencyPressureInfo.class);
        List<UserAgencyPressureInfo> list = builder.where(UserAgencyPressureInfoDao.Properties.UserId.eq(userId)).list();
        UserAgencyPressureInfo info = (list != null && list.size() > 0) ? list.get(0) : null;
        return info;
    }


    public DeviceUniqueCheckResult getUniqueCodeCheckResult(String uniqueCode, String userId){
        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(DeviceUniqueCheckResult.class);
        List<DeviceUniqueCheckResult> list = builder.where(builder.and(DeviceUniqueCheckResultDao.Properties.DeviceCode.eq(uniqueCode),DeviceUniqueCheckResultDao.Properties.UserId.eq(userId))).list();
        DeviceUniqueCheckResult result = (list != null && list.size() > 0) ? list.get(0) : null;
        return result;
    }

    //获取complete
    public List<PressureTestModel> getCompletedPressureTestModel(){
        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(PressureTestModel.class);
        Date currentDate = new Date();
        long secondsOf30Day = 2592000000L;
        long beforeSeconds = currentDate.getTime() - secondsOf30Day ; //减去30天的毫秒数

        Log.i("====beforeSeconds", "" + beforeSeconds + "   current:" + currentDate.getTime());
        List pressureList = builder.where(
                builder.and(PressureTestModelDao.Properties.TestTime.gt(beforeSeconds),builder.or(PressureTestModelDao.Properties.CurrentState.eq("2"),
                        PressureTestModelDao.Properties.CurrentState.eq("3")))

        ).orderDesc(PressureTestModelDao.Properties.TestTime).list();

        return pressureList;
    }

    //获取testing
    public List<PressureTestModel> getTestingPressureTestModel(){
        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(PressureTestModel.class);
        Date currentDate = new Date();
        long beforeSeconds = currentDate.getTime() - 2592000000L ; //减去30天的毫秒数
        Log.i("====beforeSeconds", "" + beforeSeconds);
        List pressureList = builder.where(
                builder.and(PressureTestModelDao.Properties.TestTime.gt(beforeSeconds),builder.or(PressureTestModelDao.Properties.CurrentState.eq("0"),
                        PressureTestModelDao.Properties.CurrentState.eq("1")))

        ).orderDesc(PressureTestModelDao.Properties.TestTime).list();

        Log.i("====pressureList", "" + pressureList.size());

        return pressureList;
    }

    //获取expiring
    public List<PressureTestModel> getExpiringPressureTestModel(){
        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(PressureTestModel.class);
        Date currentDate = new Date();
        long beforeSeconds = (currentDate.getTime() - 1987200000L) ; //减去23天的毫秒数
        long afterSeconds = currentDate.getTime() - 2592000000L ; //减去30天的毫秒数
        List pressureList = builder.where(
                builder.and(PressureTestModelDao.Properties.TestTime.gt(afterSeconds), PressureTestModelDao.Properties.TestTime.lt(beforeSeconds),builder.or(PressureTestModelDao.Properties.CurrentState.eq("3"),
                        PressureTestModelDao.Properties.CurrentState.eq("2")))

        ).orderDesc(PressureTestModelDao.Properties.TestTime).list();
        Log.i("====pressureList", "" + pressureList.size());
        return pressureList;
    }


    //获取Invalid
    public List<PressureTestModel> getInvalidPressureTestModel(){
        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(PressureTestModel.class);
        Date currentDate = new Date();
        long afterSeconds = currentDate.getTime() - 2592000000L ; //减去30天的毫秒数
        List pressureList = builder.where(PressureTestModelDao.Properties.TestTime.lt(afterSeconds)).orderDesc(PressureTestModelDao.Properties.TestTime).list();
        Log.i("====pressureList", "" + pressureList.size());
        return pressureList;
    }



    //获取pipecode
    public List<PipeCodeModel> getPipeCodeList(String tempTestid){

        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(PipeCodeModel.class);
        List<PipeCodeModel> list = builder.where(PipeCodeModelDao.Properties.TempTestId.eq(tempTestid)).list();
        return list;
    }

    //删除旧数据管码
    public void clearPipeCodeByTempTestId(String tempTestId){
        List<PipeCodeModel> list = getPipeCodeList(tempTestId);
        if (list != null && list.size() > 0){
           delete((List<T>) list);
        }
    }

    //获取pipeDiagram
    public List<PipeDiagramModel> getPipeDiagramPath(String tempTestid){

        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(PipeDiagramModel.class);
        List<PipeDiagramModel> list = builder.where(PipeDiagramModelDao.Properties.TempTestId.eq(tempTestid)).list();
        return list;
    }

    //删除旧数据管路图
    public void clearPipeDiagramByTempTestId(String tempTestId){
        List<PipeDiagramModel> list = getPipeDiagramPath(tempTestId);
        if (list != null && list.size() > 0){
            delete((List<T>) list);
        }
    }

    // 获取测压结果集
    public List<PressureResultBean> getResultListByTempTestId(String tempTestId){
        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(PressureResultBean.class);
        List<PressureResultBean> list = builder.where(PressureResultBeanDao.Properties.TempTestId.eq(tempTestId)).orderAsc(PressureResultBeanDao.Properties.Round).list();
        return list;
    }

    public void clearPressureResultByTempTestId(String tempTestId){
        List<PressureResultBean> list = getResultListByTempTestId(tempTestId);
        if (list != null && list.size() > 0){
            delete((List<T>) list);
        }
    }


    public List<LastDeviceTestId> getLastDeviceTestId(String deviceId){
        QueryBuilder builder = DBHelper.getInstance(appInstance).getDaoSession().queryBuilder(LastDeviceTestId.class);
        List<LastDeviceTestId> list = builder.where(LastDeviceTestIdDao.Properties.DeviceUniqueId.eq(deviceId)).list();
        return list;
    }

}

