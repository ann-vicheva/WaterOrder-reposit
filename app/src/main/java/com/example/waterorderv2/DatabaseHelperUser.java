package com.example.waterorderv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 24.11.2019.
 */

public class DatabaseHelperUser {

    //Переменная для работы с БД
    private DatabaseHelperU mDBHelper;
    private SQLiteDatabase mDb;
    //private final Context mContext;

    public DatabaseHelperUser(Context context) {
        mDBHelper = new DatabaseHelperU(context);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public void addRecord(String _login_u, String _password_u) {
        //working add method
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.KEY_LOGIN, _login_u);
        contentValues.put(mDBHelper.KEY_PASSWORD, _password_u);
        mDb.insert(mDBHelper.TABLE_USER, null, contentValues);
    }

    public void deleteRecord(int _id) {
        //working delete by id
        int delCount = mDb.delete(mDBHelper.TABLE_USER, mDBHelper.KEY_ID + "=" + Integer.toString(_id), null);
        Log.d("mLog", "deleted rows count = " + delCount);
    }

    public void changeRecord(int _id, String _login_u, String _password_u){
        deleteRecord(_id);
        addRecord(_login_u, _password_u);
    }

    public boolean verificationU(String _login_u, String _password_u){
        List<User> listUser = new ArrayList<User>();
        listUser = fillingStruct();
        for(User pr : listUser) {
            if(pr.get_login_u().equals(_login_u)){
                if(pr.get_password_u().equals(_password_u)){
                    return true;
                }
            }
        }
        return false;
    }

    public int id_by_log_pas(String _login_u, String _password_u){
        List<User> listUser = new ArrayList<User>();
        listUser = fillingStruct();
        for(User pr : listUser) {
            if(pr.get_login_u().equals(_login_u)){
                if(pr.get_password_u().equals(_password_u)){
                    return pr.get_id_u();
                }
            }
        }
        return -1;
    }

    public List<User> fillingStruct() {
        //working view method
        List<User> listUser = new ArrayList<User>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + mDBHelper.TABLE_USER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2));
            listUser.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return listUser;
    }

}
