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

public class DatabaseHelperAdmin {

    //Переменная для работы с БД
    private DatabaseHelperA mDBHelper;
    private SQLiteDatabase mDb;
    //private final Context mContext;

    public DatabaseHelperAdmin(Context context) {
        mDBHelper = new DatabaseHelperA(context);

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

    public void addRecord(String _login_adm, String _password_adm) {
        //working add method
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.KEY_LOGIN, _login_adm);
        contentValues.put(mDBHelper.KEY_PASSWORD, _password_adm);
        mDb.insert(mDBHelper.TABLE_ADMIN, null, contentValues);
    }

    public void deleteRecord(int _id) {
        //working delete by id
        int delCount = mDb.delete(mDBHelper.TABLE_ADMIN, mDBHelper.KEY_ID + "=" + Integer.toString(_id), null);
        Log.d("mLog", "deleted rows count = " + delCount);
    }

    public void changeRecord(int _id, String _login_adm, String _password_adm){
        deleteRecord(_id);
        addRecord(_login_adm, _password_adm);
    }

    public boolean verificationA(String _login_adm, String _password_adm){
        List<Admin> listAdmin = new ArrayList<Admin>();
        listAdmin = fillingStruct();
        for(Admin pr : listAdmin) {
            if(pr.get_login_adm().equals(_login_adm)){
                if(pr.get_password_adm().equals(_password_adm)){
                    return true;
                }
            }
        }
        return false;
    }

    public List<Admin> fillingStruct() {
        //working view method
        List<Admin> listAdmin = new ArrayList<Admin>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + mDBHelper.TABLE_ADMIN, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Admin admin = new Admin(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2));
            listAdmin.add(admin);
            cursor.moveToNext();
        }
        cursor.close();
        return listAdmin;
    }

}
