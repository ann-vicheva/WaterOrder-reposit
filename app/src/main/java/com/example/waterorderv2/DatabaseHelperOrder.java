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

public class DatabaseHelperOrder {

    //Переменная для работы с БД
    private DatabaseHelperO mDBHelper;
    private SQLiteDatabase mDb;
    //private final Context mContext;

    public DatabaseHelperOrder(Context context) {
        mDBHelper = new DatabaseHelperO(context);

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

    public void addRecord(int _id_us, String _description) {
        //working add method
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.KEY_ID_US, _id_us);
        contentValues.put(mDBHelper.KEY_DESCRIPTION, _description);
        mDb.insert(mDBHelper.TABLE_ORDER, null, contentValues);
    }

    public void deleteRecord(int _id) {
        //working delete by id
        int delCount = mDb.delete(mDBHelper.TABLE_ORDER, mDBHelper.KEY_ID + "=" + Integer.toString(_id), null);
        Log.d("mLog", "deleted rows count = " + delCount);
    }

    public void changeRecord(int _id, int _id_us, String _description){
        deleteRecord(_id);
        addRecord(_id_us, _description);
    }

    public List<Order> fillingStruct() {
        //working view method
        List<Order> listOrder = new ArrayList<Order>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + mDBHelper.TABLE_ORDER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Order order = new Order(cursor.getInt(0), cursor.getInt(1),
                    cursor.getString(2));
            listOrder.add(order);
            cursor.moveToNext();
        }
        cursor.close();
        return listOrder;
    }

}
