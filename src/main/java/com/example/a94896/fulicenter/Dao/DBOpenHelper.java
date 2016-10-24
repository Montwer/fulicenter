package com.example.a94896.fulicenter.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a94896.fulicenter.I;

/**
 * Created by 94896 on 2016/10/24.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static DBOpenHelper instance;

public static  final String FULICENTER_CREATE_USER_TABLE="CREATE TABLE"
        +UserDao.USER_TABLE_NAME+"("
        +UserDao.USER_COLUMN_NAME+"TEXT PRIMARY KEY,"
        +UserDao.USER_COLUMN_NICK+"TEXT"
        +UserDao.USER_COLUMN_AVATAR_ID+"INTEGER,"
        +UserDao.USER_COLUMN_AVATAR_TYPE+"INTEGER"
        +UserDao.USER_COLUMN_AVATAR_PATH+"TEXT"
        +UserDao.USER_COLUMN_AVATAR_SUFFIX+"TEXT"
        +UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME+"TEXT);";
public static DBOpenHelper onInit(Context context){
    if (instance==null){
        instance=new DBOpenHelper(context);
    }
    return instance;
}
    public static DBOpenHelper getInstance(Context context){
        if (instance==null){
            instance=new DBOpenHelper(context.getApplicationContext());
        }
        return instance;
    }
    public DBOpenHelper(Context context) {
        super(context,getUserDatabaseName(),null,DATABASE_VERSION);
    }

    private static String getUserDatabaseName() {
        return I.User.TABLE_NAME+"_demo.dp";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
sqLiteDatabase.execSQL(FULICENTER_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

public void closeDB(){
    if (instance!=null){
        instance.close();
        instance=null;
    }
}
}
