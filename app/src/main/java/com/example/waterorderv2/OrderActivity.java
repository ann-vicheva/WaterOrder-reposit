package com.example.waterorderv2;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class OrderActivity extends AppCompatActivity {

    int id_user;
    Double cost;
    String ord;
    CheckBox cbPost;
    CheckBox cbNewPost;
    CheckBox cbDost;
    CheckBox cbCard;
    CheckBox cbNal;
    EditText etUserName;
    EditText etUserAdress;
    EditText etUserPhone;
    TextView tvCost;

    private DatabaseHelperO mDBHelperO;
    private SQLiteDatabase mDbO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        id_user = getIntent().getIntExtra("current_id", 0);
        cost = getIntent().getDoubleExtra("current_cost", 0.0);
        ord = getIntent().getStringExtra("current_order");

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

        tvCost = (TextView) findViewById(R.id.tvCost);
        tvCost.setText("Вартість поточного \n замовлення:  "+Double.toString(cost));

        cbPost = (CheckBox) findViewById(R.id.cbPost);
        cbNewPost = (CheckBox) findViewById(R.id.cbNewPost);
        cbDost = (CheckBox) findViewById(R.id.cbDost);
        cbCard = (CheckBox) findViewById(R.id.cbCard);
        cbNal = (CheckBox) findViewById(R.id.cbNal);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etUserAdress = (EditText) findViewById(R.id.etUserAdress);
        etUserPhone = (EditText) findViewById(R.id.etUserPhone);
    }

    public void RegOrder(View view){
        if(cbPost.isChecked()){
            cbNewPost.setChecked(false);
            cbDost.setChecked(false);
        }
        if(cbNewPost.isChecked()){
            cbPost.setChecked(false);
            cbDost.setChecked(false);
        }
        if(cbDost.isChecked()){
            cbPost.setChecked(false);
            cbNewPost.setChecked(false);
        }
        if(cbCard.isChecked()){cbNal.setChecked(false);}
        if(cbNal.isChecked()){cbCard.setChecked(false);}

        if(etUserName.getText().length()>0){
            if(etUserAdress.getText().length()>0){
                if(etUserPhone.getText().length()>0){
                    DatabaseHelperOrder dbHelperOrder = new DatabaseHelperOrder(this);
                    dbHelperOrder.addRecord(id_user, ord);
                    Toast toast = Toast.makeText(this, "Замовлення успішно зареєстровано",Toast.LENGTH_LONG);
                    toast.show();
                }else{Toast toast = Toast.makeText(this, "Введіть номер телефону",Toast.LENGTH_LONG);
                    toast.show();}
            }else{Toast toast = Toast.makeText(this, "Введіть адресу",Toast.LENGTH_LONG);
                toast.show();}
        }else{Toast toast = Toast.makeText(this, "Введіть ім'я",Toast.LENGTH_LONG);
        toast.show();}


    }

}
