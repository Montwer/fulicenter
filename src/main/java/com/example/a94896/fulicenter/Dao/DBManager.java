package com.example.a94896.fulicenter.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a94896.fulicenter.bean.User;

/**
 * Created by 94896 on 2016/10/24.
 */

public class DBManager {
    private static DBManager dbMgr=new DBManager();
    private static DBOpenHelper dbHelper;
    public static synchronized DBManager getInstance(){
        return dbMgr;
    }
    public static DBManager onInit(Context context){
        if(dbHelper==null){
            dbHelper=DBOpenHelper.onInit(context);
        }
        return dbMgr;
    }
    public synchronized void closeDB(){
        if (dbHelper!=null){
            dbHelper.closeDB();
        }
    }
    public synchronized boolean saveUser(User user){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(UserDao.USER_COLUMN_NAME,user.getMuserName());
        values.put(UserDao.USER_COLUMN_NICK,user.getMuserNick());
        values.put(UserDao.USER_COLUMN_AVATAR_ID,user.getMavatarId());
        values.put(UserDao.USER_COLUMN_AVATAR_TYPE,user.getMavatarType());
        values.put(UserDao.USER_COLUMN_AVATAR_PATH,user.getMavatarSuffix());
        values.put(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME,user.getMavatarLastUpdateTime());
        if (db.isOpen()){
            return db.replace(UserDao.USER_TABLE_NAME,null,values)!=-1;
        }
        return false;
    }
    public User getUser(String username){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="select*from"+UserDao.USER_TABLE_NAME+"where"
                +UserDao.USER_COLUMN_NAME+"=?";
        User user=null;
        Cursor cursor=db.rawQuery(sql,new String[]{username});
        if (cursor.moveToNext()){
            user=new User();
            user.setMuserName(username);
            user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_ID)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME)));

            return user;
        }
        return user;
    }
    public synchronized boolean updateUser(User user){
        int resule=-1;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String sql=UserDao.USER_COLUMN_NAME+"=?";
        ContentValues values=new ContentValues();
        values.put(UserDao.USER_COLUMN_NICK,user.getMuserNick());
        if (db.isOpen()){
            resule=db.update(UserDao.USER_TABLE_NAME,values,sql,new String[]{user.getMuserName()});
        }
        return resule>0;
    }
}
