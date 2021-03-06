package com.rifeng.p2p.db.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.rifeng.p2p.entity.AutoSubmitResult;
import com.rifeng.p2p.entity.BleDataBean;
import com.rifeng.p2p.entity.BleRecords;
import com.rifeng.p2p.entity.DeviceUniqueCheckResult;
import com.rifeng.p2p.entity.HistoryRecordBean;
import com.rifeng.p2p.entity.LastDeviceTestId;
import com.rifeng.p2p.entity.LookupCode;
import com.rifeng.p2p.entity.PipeCodeModel;
import com.rifeng.p2p.entity.PipeDiagramModel;
import com.rifeng.p2p.entity.Point;
import com.rifeng.p2p.entity.PressureResultBean;
import com.rifeng.p2p.entity.PressureTestModel;
import com.rifeng.p2p.entity.UserAgencyPressureInfo;
import com.rifeng.p2p.entity.UserForeManBean;

import com.rifeng.p2p.db.greendao.AutoSubmitResultDao;
import com.rifeng.p2p.db.greendao.BleDataBeanDao;
import com.rifeng.p2p.db.greendao.BleRecordsDao;
import com.rifeng.p2p.db.greendao.DeviceUniqueCheckResultDao;
import com.rifeng.p2p.db.greendao.HistoryRecordBeanDao;
import com.rifeng.p2p.db.greendao.LastDeviceTestIdDao;
import com.rifeng.p2p.db.greendao.LookupCodeDao;
import com.rifeng.p2p.db.greendao.PipeCodeModelDao;
import com.rifeng.p2p.db.greendao.PipeDiagramModelDao;
import com.rifeng.p2p.db.greendao.PointDao;
import com.rifeng.p2p.db.greendao.PressureResultBeanDao;
import com.rifeng.p2p.db.greendao.PressureTestModelDao;
import com.rifeng.p2p.db.greendao.UserAgencyPressureInfoDao;
import com.rifeng.p2p.db.greendao.UserForeManBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig autoSubmitResultDaoConfig;
    private final DaoConfig bleDataBeanDaoConfig;
    private final DaoConfig bleRecordsDaoConfig;
    private final DaoConfig deviceUniqueCheckResultDaoConfig;
    private final DaoConfig historyRecordBeanDaoConfig;
    private final DaoConfig lastDeviceTestIdDaoConfig;
    private final DaoConfig lookupCodeDaoConfig;
    private final DaoConfig pipeCodeModelDaoConfig;
    private final DaoConfig pipeDiagramModelDaoConfig;
    private final DaoConfig pointDaoConfig;
    private final DaoConfig pressureResultBeanDaoConfig;
    private final DaoConfig pressureTestModelDaoConfig;
    private final DaoConfig userAgencyPressureInfoDaoConfig;
    private final DaoConfig userForeManBeanDaoConfig;

    private final AutoSubmitResultDao autoSubmitResultDao;
    private final BleDataBeanDao bleDataBeanDao;
    private final BleRecordsDao bleRecordsDao;
    private final DeviceUniqueCheckResultDao deviceUniqueCheckResultDao;
    private final HistoryRecordBeanDao historyRecordBeanDao;
    private final LastDeviceTestIdDao lastDeviceTestIdDao;
    private final LookupCodeDao lookupCodeDao;
    private final PipeCodeModelDao pipeCodeModelDao;
    private final PipeDiagramModelDao pipeDiagramModelDao;
    private final PointDao pointDao;
    private final PressureResultBeanDao pressureResultBeanDao;
    private final PressureTestModelDao pressureTestModelDao;
    private final UserAgencyPressureInfoDao userAgencyPressureInfoDao;
    private final UserForeManBeanDao userForeManBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        autoSubmitResultDaoConfig = daoConfigMap.get(AutoSubmitResultDao.class).clone();
        autoSubmitResultDaoConfig.initIdentityScope(type);

        bleDataBeanDaoConfig = daoConfigMap.get(BleDataBeanDao.class).clone();
        bleDataBeanDaoConfig.initIdentityScope(type);

        bleRecordsDaoConfig = daoConfigMap.get(BleRecordsDao.class).clone();
        bleRecordsDaoConfig.initIdentityScope(type);

        deviceUniqueCheckResultDaoConfig = daoConfigMap.get(DeviceUniqueCheckResultDao.class).clone();
        deviceUniqueCheckResultDaoConfig.initIdentityScope(type);

        historyRecordBeanDaoConfig = daoConfigMap.get(HistoryRecordBeanDao.class).clone();
        historyRecordBeanDaoConfig.initIdentityScope(type);

        lastDeviceTestIdDaoConfig = daoConfigMap.get(LastDeviceTestIdDao.class).clone();
        lastDeviceTestIdDaoConfig.initIdentityScope(type);

        lookupCodeDaoConfig = daoConfigMap.get(LookupCodeDao.class).clone();
        lookupCodeDaoConfig.initIdentityScope(type);

        pipeCodeModelDaoConfig = daoConfigMap.get(PipeCodeModelDao.class).clone();
        pipeCodeModelDaoConfig.initIdentityScope(type);

        pipeDiagramModelDaoConfig = daoConfigMap.get(PipeDiagramModelDao.class).clone();
        pipeDiagramModelDaoConfig.initIdentityScope(type);

        pointDaoConfig = daoConfigMap.get(PointDao.class).clone();
        pointDaoConfig.initIdentityScope(type);

        pressureResultBeanDaoConfig = daoConfigMap.get(PressureResultBeanDao.class).clone();
        pressureResultBeanDaoConfig.initIdentityScope(type);

        pressureTestModelDaoConfig = daoConfigMap.get(PressureTestModelDao.class).clone();
        pressureTestModelDaoConfig.initIdentityScope(type);

        userAgencyPressureInfoDaoConfig = daoConfigMap.get(UserAgencyPressureInfoDao.class).clone();
        userAgencyPressureInfoDaoConfig.initIdentityScope(type);

        userForeManBeanDaoConfig = daoConfigMap.get(UserForeManBeanDao.class).clone();
        userForeManBeanDaoConfig.initIdentityScope(type);

        autoSubmitResultDao = new AutoSubmitResultDao(autoSubmitResultDaoConfig, this);
        bleDataBeanDao = new BleDataBeanDao(bleDataBeanDaoConfig, this);
        bleRecordsDao = new BleRecordsDao(bleRecordsDaoConfig, this);
        deviceUniqueCheckResultDao = new DeviceUniqueCheckResultDao(deviceUniqueCheckResultDaoConfig, this);
        historyRecordBeanDao = new HistoryRecordBeanDao(historyRecordBeanDaoConfig, this);
        lastDeviceTestIdDao = new LastDeviceTestIdDao(lastDeviceTestIdDaoConfig, this);
        lookupCodeDao = new LookupCodeDao(lookupCodeDaoConfig, this);
        pipeCodeModelDao = new PipeCodeModelDao(pipeCodeModelDaoConfig, this);
        pipeDiagramModelDao = new PipeDiagramModelDao(pipeDiagramModelDaoConfig, this);
        pointDao = new PointDao(pointDaoConfig, this);
        pressureResultBeanDao = new PressureResultBeanDao(pressureResultBeanDaoConfig, this);
        pressureTestModelDao = new PressureTestModelDao(pressureTestModelDaoConfig, this);
        userAgencyPressureInfoDao = new UserAgencyPressureInfoDao(userAgencyPressureInfoDaoConfig, this);
        userForeManBeanDao = new UserForeManBeanDao(userForeManBeanDaoConfig, this);

        registerDao(AutoSubmitResult.class, autoSubmitResultDao);
        registerDao(BleDataBean.class, bleDataBeanDao);
        registerDao(BleRecords.class, bleRecordsDao);
        registerDao(DeviceUniqueCheckResult.class, deviceUniqueCheckResultDao);
        registerDao(HistoryRecordBean.class, historyRecordBeanDao);
        registerDao(LastDeviceTestId.class, lastDeviceTestIdDao);
        registerDao(LookupCode.class, lookupCodeDao);
        registerDao(PipeCodeModel.class, pipeCodeModelDao);
        registerDao(PipeDiagramModel.class, pipeDiagramModelDao);
        registerDao(Point.class, pointDao);
        registerDao(PressureResultBean.class, pressureResultBeanDao);
        registerDao(PressureTestModel.class, pressureTestModelDao);
        registerDao(UserAgencyPressureInfo.class, userAgencyPressureInfoDao);
        registerDao(UserForeManBean.class, userForeManBeanDao);
    }
    
    public void clear() {
        autoSubmitResultDaoConfig.clearIdentityScope();
        bleDataBeanDaoConfig.clearIdentityScope();
        bleRecordsDaoConfig.clearIdentityScope();
        deviceUniqueCheckResultDaoConfig.clearIdentityScope();
        historyRecordBeanDaoConfig.clearIdentityScope();
        lastDeviceTestIdDaoConfig.clearIdentityScope();
        lookupCodeDaoConfig.clearIdentityScope();
        pipeCodeModelDaoConfig.clearIdentityScope();
        pipeDiagramModelDaoConfig.clearIdentityScope();
        pointDaoConfig.clearIdentityScope();
        pressureResultBeanDaoConfig.clearIdentityScope();
        pressureTestModelDaoConfig.clearIdentityScope();
        userAgencyPressureInfoDaoConfig.clearIdentityScope();
        userForeManBeanDaoConfig.clearIdentityScope();
    }

    public AutoSubmitResultDao getAutoSubmitResultDao() {
        return autoSubmitResultDao;
    }

    public BleDataBeanDao getBleDataBeanDao() {
        return bleDataBeanDao;
    }

    public BleRecordsDao getBleRecordsDao() {
        return bleRecordsDao;
    }

    public DeviceUniqueCheckResultDao getDeviceUniqueCheckResultDao() {
        return deviceUniqueCheckResultDao;
    }

    public HistoryRecordBeanDao getHistoryRecordBeanDao() {
        return historyRecordBeanDao;
    }

    public LastDeviceTestIdDao getLastDeviceTestIdDao() {
        return lastDeviceTestIdDao;
    }

    public LookupCodeDao getLookupCodeDao() {
        return lookupCodeDao;
    }

    public PipeCodeModelDao getPipeCodeModelDao() {
        return pipeCodeModelDao;
    }

    public PipeDiagramModelDao getPipeDiagramModelDao() {
        return pipeDiagramModelDao;
    }

    public PointDao getPointDao() {
        return pointDao;
    }

    public PressureResultBeanDao getPressureResultBeanDao() {
        return pressureResultBeanDao;
    }

    public PressureTestModelDao getPressureTestModelDao() {
        return pressureTestModelDao;
    }

    public UserAgencyPressureInfoDao getUserAgencyPressureInfoDao() {
        return userAgencyPressureInfoDao;
    }

    public UserForeManBeanDao getUserForeManBeanDao() {
        return userForeManBeanDao;
    }

}
