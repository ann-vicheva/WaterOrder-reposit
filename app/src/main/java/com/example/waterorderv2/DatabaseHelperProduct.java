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
 * Created by HP on 23.11.2019.
 */

public class DatabaseHelperProduct {

    //Переменная для работы с БД
    private DatabaseHelperP mDBHelper;
    private SQLiteDatabase mDb;
    //private final Context mContext;

    public DatabaseHelperProduct(Context context) {
        mDBHelper = new DatabaseHelperP(context);

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

    public void addRecord(String _title, String _description, double _cost) {
        //working add method
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.KEY_TITLE, _title);
        contentValues.put(mDBHelper.KEY_DESCRIPTION, _description);
        contentValues.put(mDBHelper.KEY_COST, _cost);
        mDb.insert(mDBHelper.TABLE_PRODUCTS, null, contentValues);
    }

    public void deleteRecord(int _id) {
        //working delete by id
        int delCount = mDb.delete(mDBHelper.TABLE_PRODUCTS, mDBHelper.KEY_ID + "=" + Integer.toString(_id), null);
        Log.d("mLog", "deleted rows count = " + delCount);
    }

    public void changeRecord(int _id, String _title, String _description, double _cost){
        deleteRecord(_id);
        addRecord(_title, _description, _cost);
    }

    public List<Product> fillingStruct() {
        //List<Product>
        //working view method
        List<Product> listProduct = new ArrayList<Product>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + mDBHelper.TABLE_PRODUCTS, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = new Product(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getDouble(3));
            listProduct.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return listProduct;
    }

}
