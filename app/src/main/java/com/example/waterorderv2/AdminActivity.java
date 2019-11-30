package com.example.waterorderv2;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etDescr;
    EditText etCost;
    EditText etIdDelete;

    EditText etTitleC;
    EditText etDescrC;
    EditText etCostC;
    EditText etIdChange;

    TextView tvProductList;
    TextView tvOrderList;

    private DatabaseHelperP mDBHelperP;
    private SQLiteDatabase mDbP;

    private DatabaseHelperO mDBHelperO;
    private SQLiteDatabase mDbO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mDBHelperP = new DatabaseHelperP(this);
        try {
            mDBHelperP.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDbP = mDBHelperP.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        mDBHelperO = new DatabaseHelperO(this);
        try {
            mDBHelperO.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDbO = mDBHelperO.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescr = (EditText) findViewById(R.id.etDescr);
        etCost = (EditText) findViewById(R.id.etCost);
        tvProductList = (TextView) findViewById(R.id.tvProductList);
        etIdDelete = (EditText) findViewById(R.id.etIdDelete);
        etTitleC = (EditText) findViewById(R.id.etTitleС);
        etDescrC = (EditText) findViewById(R.id.etDescrС);
        etCostC = (EditText) findViewById(R.id.etCostС);
        etIdChange = (EditText) findViewById(R.id.etIdChange);
        tvOrderList = (TextView) findViewById(R.id.tvOrderList);

    }

    public void viewOrderList(View view){
        String order1="";
        DatabaseHelperOrder dbHelperOrder = new DatabaseHelperOrder(this);
        List<Order> list = dbHelperOrder.fillingStruct();
        for(Order pr : list){
            order1 += Integer.toString(pr.get_id()) + " " +
                    Integer.toString(pr.get_id_us()) + " " +
                    pr.get_description_ord() + "\n";
        }
        tvOrderList.setText(order1);
    }

    public void changeProduct(View view){

        if(etTitleC.getText().length()>0){
            if(etDescrC.getText().length()>0){
                if(etCostC.getText().length()>0){
                    DatabaseHelperProduct dbHelperProduct = new DatabaseHelperProduct(this);
                    String cost = etCostC.getText().toString();
                    try {
                        Double d = Double.valueOf(cost);
                        String title = etTitleC.getText().toString();
                        String descr = etDescrC.getText().toString();
                        try{
                            int id = Integer.parseInt(etIdChange.getText().toString());
                            dbHelperProduct.changeRecord(id, title, descr, d);
                            Toast toast = Toast.makeText(this, "Внесені зміни успішно збережено",Toast.LENGTH_LONG);
                            toast.show();
                            etTitleC.setText("");
                            etDescrC.setText("");
                            etCostC.setText("");
                            etIdChange.setText("");
                        }
                        catch (NumberFormatException e){
                            Toast toast = Toast.makeText(this, "Невірний формат id або такого продукту не існує",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    } catch (NumberFormatException e) {
                        Toast toast = Toast.makeText(this, "Невірний формат вартості",Toast.LENGTH_LONG);
                        toast.show();
                    }

                }else{Toast toast = Toast.makeText(this, "Введіть вартість",Toast.LENGTH_LONG);
                    toast.show();}
            }else{Toast toast = Toast.makeText(this, "Введіть опис",Toast.LENGTH_LONG);
                toast.show();}
        }else{Toast toast = Toast.makeText(this, "Введіть назву",Toast.LENGTH_LONG);
            toast.show();}
    }

    public void deleteProduct(View view){
        try {
            DatabaseHelperProduct dbHelperProduct = new DatabaseHelperProduct(this);
            int id = Integer.parseInt(etIdDelete.getText().toString());
            dbHelperProduct.deleteRecord(id);
            Toast toast = Toast.makeText(this, "Одиницю товару успішно видалено з каталогу",Toast.LENGTH_LONG);
            toast.show();
            etIdDelete.setText("");
        }
        catch (NumberFormatException e) {
            Toast toast = Toast.makeText(this, "Невірний формат id або такого продукту не існує",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void refreshProductList(View view){
        String product1="";
        DatabaseHelperProduct dbHelperProduct = new DatabaseHelperProduct(this);
        List<Product> list = dbHelperProduct.fillingStruct();
        for(Product pr : list){
            product1 += Integer.toString(pr.get_id()) + " " +
                    pr.get_title() + " " +
                    pr.get_description() + " " +
                    Double.toString(pr.get_cost()) + "\n";
        }
        tvProductList.setText(product1);
    }

    public void addProduct(View view){
        if(etTitle.getText().length()>0){
            if(etDescr.getText().length()>0){
                if(etCost.getText().length()>0){
                    DatabaseHelperProduct dbHelperProduct = new DatabaseHelperProduct(this);
                    String cost = etCost.getText().toString();
                    try {
                        Double d = Double.valueOf(cost);
                        String title = etTitle.getText().toString();
                        String descr = etDescr.getText().toString();
                        dbHelperProduct.addRecord(title, descr, d);
                        Toast toast = Toast.makeText(this, "Одиниця товару успішно додана до каталогу",Toast.LENGTH_LONG);
                        toast.show();
                        etTitle.setText("");
                        etDescr.setText("");
                        etCost.setText("");
                    } catch (NumberFormatException e) {
                        Toast toast = Toast.makeText(this, "Невірний формат вартості",Toast.LENGTH_LONG);
                        toast.show();
                    }

                }else{Toast toast = Toast.makeText(this, "Введіть вартість",Toast.LENGTH_LONG);
                    toast.show();}
            }else{Toast toast = Toast.makeText(this, "Введіть опис",Toast.LENGTH_LONG);
                toast.show();}
        }else{Toast toast = Toast.makeText(this, "Введіть назву",Toast.LENGTH_LONG);
            toast.show();}
    }
}
