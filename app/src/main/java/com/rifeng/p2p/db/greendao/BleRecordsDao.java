package com.rifeng.p2p.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.rifeng.p2p.entity.BleRecords;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BLE_RECORDS".
*/
public class BleRecordsDao extends AbstractDao<BleRecords, String> {

    public static final String TABLENAME = "BLE_RECORDS";

    /**
     * Properties of entity BleRecords.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property TestId = new Property(0, String.class, "testId", true, "TEST_ID");
        public final static Property GroupId = new Property(1, String.class, "groupId", false, "GROUP_ID");
        public final static Property TestRoundTime = new Property(2, String.class, "testRoundTime", false, "TEST_ROUND_TIME");
        public final static Property TestRoundStartPressure = new Property(3, String.class, "testRoundStartPressure", false, "TEST_ROUND_START_PRESSURE");
        public final static Property Memo = new Property(4, String.class, "memo", false, "MEMO");
        public final static Property Result = new Property(5, String.class, "result", false, "RESULT");
        public final static Property TestRoundCount = new Property(6, String.class, "testRoundCount", false, "TEST_ROUND_COUNT");
        public final static Property GroupName = new Property(7, String.class, "groupName", false, "GROUP_NAME");
        public final static Property LastUpdateDate = new Property(8, java.util.Date.class, "lastUpdateDate", false, "LAST_UPDATE_DATE");
        public final static Property Test_method = new Property(9, String.class, "test_method", false, "TEST_METHOD");
        public final static Property Post_code = new Property(10, String.class, "post_code", false, "POST_CODE");
        public final static Property Test_method_code = new Property(11, String.class, "test_method_code", false, "TEST_METHOD_CODE");
    }


    public BleRecordsDao(DaoConfig config) {
        super(config);
    }
    
    public BleRecordsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BLE_RECORDS\" (" + //
                "\"TEST_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: testId
                "\"GROUP_ID\" TEXT," + // 1: groupId
                "\"TEST_ROUND_TIME\" TEXT," + // 2: testRoundTime
                "\"TEST_ROUND_START_PRESSURE\" TEXT," + // 3: testRoundStartPressure
                "\"MEMO\" TEXT," + // 4: memo
                "\"RESULT\" TEXT," + // 5: result
                "\"TEST_ROUND_COUNT\" TEXT," + // 6: testRoundCount
                "\"GROUP_NAME\" TEXT," + // 7: groupName
                "\"LAST_UPDATE_DATE\" INTEGER," + // 8: lastUpdateDate
                "\"TEST_METHOD\" TEXT," + // 9: test_method
                "\"POST_CODE\" TEXT," + // 10: post_code
                "\"TEST_METHOD_CODE\" TEXT);"); // 11: test_method_code
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BLE_RECORDS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BleRecords entity) {
        stmt.clearBindings();
 
        String testId = entity.getTestId();
        if (testId != null) {
            stmt.bindString(1, testId);
        }
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(2, groupId);
        }
 
        String testRoundTime = entity.getTestRoundTime();
        if (testRoundTime != null) {
            stmt.bindString(3, testRoundTime);
        }
 
        String testRoundStartPressure = entity.getTestRoundStartPressure();
        if (testRoundStartPressure != null) {
            stmt.bindString(4, testRoundStartPressure);
        }
 
        String memo = entity.getMemo();
        if (memo != null) {
            stmt.bindString(5, memo);
        }
 
        String result = entity.getResult();
        if (result != null) {
            stmt.bindString(6, result);
        }
 
        String testRoundCount = entity.getTestRoundCount();
        if (testRoundCount != null) {
            stmt.bindString(7, testRoundCount);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(8, groupName);
        }
 
        java.util.Date lastUpdateDate = entity.getLastUpdateDate();
        if (lastUpdateDate != null) {
            stmt.bindLong(9, lastUpdateDate.getTime());
        }
 
        String test_method = entity.getTest_method();
        if (test_method != null) {
            stmt.bindString(10, test_method);
        }
 
        String post_code = entity.getPost_code();
        if (post_code != null) {
            stmt.bindString(11, post_code);
        }
 
        String test_method_code = entity.getTest_method_code();
        if (test_method_code != null) {
            stmt.bindString(12, test_method_code);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BleRecords entity) {
        stmt.clearBindings();
 
        String testId = entity.getTestId();
        if (testId != null) {
            stmt.bindString(1, testId);
        }
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(2, groupId);
        }
 
        String testRoundTime = entity.getTestRoundTime();
        if (testRoundTime != null) {
            stmt.bindString(3, testRoundTime);
        }
 
        String testRoundStartPressure = entity.getTestRoundStartPressure();
        if (testRoundStartPressure != null) {
            stmt.bindString(4, testRoundStartPressure);
        }
 
        String memo = entity.getMemo();
        if (memo != null) {
            stmt.bindString(5, memo);
        }
 
        String result = entity.getResult();
        if (result != null) {
            stmt.bindString(6, result);
        }
 
        String testRoundCount = entity.getTestRoundCount();
        if (testRoundCount != null) {
            stmt.bindString(7, testRoundCount);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(8, groupName);
        }
 
        java.util.Date lastUpdateDate = entity.getLastUpdateDate();
        if (lastUpdateDate != null) {
            stmt.bindLong(9, lastUpdateDate.getTime());
        }
 
        String test_method = entity.getTest_method();
        if (test_method != null) {
            stmt.bindString(10, test_method);
        }
 
        String post_code = entity.getPost_code();
        if (post_code != null) {
            stmt.bindString(11, post_code);
        }
 
        String test_method_code = entity.getTest_method_code();
        if (test_method_code != null) {
            stmt.bindString(12, test_method_code);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public BleRecords readEntity(Cursor cursor, int offset) {
        BleRecords entity = new BleRecords( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // testId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // groupId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // testRoundTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // testRoundStartPressure
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // memo
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // result
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // testRoundCount
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // groupName
            cursor.isNull(offset + 8) ? null : new java.util.Date(cursor.getLong(offset + 8)), // lastUpdateDate
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // test_method
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // post_code
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // test_method_code
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BleRecords entity, int offset) {
        entity.setTestId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setGroupId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTestRoundTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTestRoundStartPressure(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMemo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setResult(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTestRoundCount(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGroupName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setLastUpdateDate(cursor.isNull(offset + 8) ? null : new java.util.Date(cursor.getLong(offset + 8)));
        entity.setTest_method(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPost_code(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setTest_method_code(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    @Override
    protected final String updateKeyAfterInsert(BleRecords entity, long rowId) {
        return entity.getTestId();
    }
    
    @Override
    public String getKey(BleRecords entity) {
        if(entity != null) {
            return entity.getTestId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BleRecords entity) {
        return entity.getTestId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
