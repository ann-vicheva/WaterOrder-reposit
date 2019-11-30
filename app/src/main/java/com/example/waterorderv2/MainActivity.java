package com.example.waterorderv2;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //Объявим переменные компонентов
    Button button;
    TextView textView;
    EditText etUserLog;
    EditText etUserPas;
    EditText etAdminLog;
    EditText etAdminPas;
    int id_user=-1;

    //Переменная для работы с БД
    private DatabaseHelperP mDBHelper;
    private SQLiteDatabase mDb;

    private DatabaseHelperO mDBHelperO;
    private SQLiteDatabase mDbO;

    private DatabaseHelperU mDBHelperU;
    private SQLiteDatabase mDbU;

    private DatabaseHelperA mDBHelperA;
    private SQLiteDatabase mDbA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelperU = new DatabaseHelperU(this);
        try {
            mDBHelperU.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDbU = mDBHelperU.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        mDBHelperA = new DatabaseHelperA(this);
        try {
            mDBHelperA.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDbA = mDBHelperA.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }


        //Найдем компоненты в XML разметке

        etUserLog = (EditText) findViewById(R.id.etUserLog);
        etUserPas = (EditText) findViewById(R.id.etUserPas);

        etAdminLog = (EditText) findViewById(R.id.etAdminLog);
        etAdminPas = (EditText) findViewById(R.id.etAdminPas);

        //button = (Button) findViewById(R.id.button);
        //textView = (TextView) findViewById(R.id.textView);

        id_user=-1;
    }

    public void LogInAdmin(View view){
        String log = etAdminLog.getText().toString();
        String pas = etAdminPas.getText().toString();
        DatabaseHelperAdmin dbHelperAdmin = new DatabaseHelperAdmin(this);
        boolean res = dbHelperAdmin.verificationA(log, pas);
        if(res){//go to magazin, order------------------------------------------
            Toast toast = Toast.makeText(this, "Авторизація успішна",Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        }
        else {Toast toast = Toast.makeText(this, "Авторизація не успішна",Toast.LENGTH_LONG);
            toast.show();}
        etAdminLog.setText("");
        etAdminPas.setText("");
    }



    public void LogInUser(View view){
        String log = etUserLog.getText().toString();
        String pas = etUserPas.getText().toString();
        DatabaseHelperUser dbHelperUser = new DatabaseHelperUser(this);
        boolean res = dbHelperUser.verificationU(log, pas);
        if(res){//go to magazin, order------------------------------------------
            Toast toast = Toast.makeText(this, "Авторизація успішна",Toast.LENGTH_LONG);
            toast.show();
            int id_u = dbHelperUser.id_by_log_pas(log, pas);
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("current_id", id_u);
            startActivity(intent);
        }
        else {Toast toast = Toast.makeText(this, "Авторизація не успішна",Toast.LENGTH_LONG);
            toast.show();}
        etUserLog.setText("");
        etUserPas.setText("");
    }

    public void RegUser(View view){
        String log = etUserLog.getText().toString();
        String pas = etUserPas.getText().toString();
        DatabaseHelperUser dbHelperUser = new DatabaseHelperUser(this);
        boolean res = dbHelperUser.verificationU(log, pas);
        if(res){//go to magazin, order------------------------------------------
            Toast toast = Toast.makeText(this, "Такий користувач вже існує",Toast.LENGTH_LONG);
            toast.show();}
        else {
            if(!log.equals("")){
                if(!pas.equals("")){
                    dbHelperUser.addRecord(log, pas);
                    Toast toast = Toast.makeText(this, "Користувача зареєстровано, увійдіть до облікового запису",Toast.LENGTH_LONG);
                    toast.show();
                }
            }else{Toast toast = Toast.makeText(this, "Некоректний логін чи пароль",Toast.LENGTH_LONG);
                toast.show();}
            }
    }

    public void card1(View view){
        //go to magazin? dont order
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("current_id", id_user);
        startActivity(intent);
    }

    public void read(View view){
        /*String order1 = "";
        DatabaseHelperOrder dbHelperOrder = new DatabaseHelperOrder(this);
        //dbHelperOrder.addRecord(1, "password");
        //dbHelperOrder.addRecord(1, "password");
        //dbHelperOrder.addRecord(2, "password");
        dbHelperOrder.changeRecord(3, 2, "водица");
        dbHelperOrder.deleteRecord(4);
        List<Order> list = dbHelperOrder.fillingStruct();
        for(Order pr : list){
            order1 += Integer.toString(pr.get_id()) + " " +
                    Integer.toString(pr.get_id_us()) + " " +
                    pr.get_description_ord() + "\n";
        }
        textView.setText(order1);*/
        /*String user1 = "";
        DatabaseHelperUser dbHelperUser = new DatabaseHelperUser(this);
        List<User> list = dbHelperUser.fillingStruct();
        for(User pr : list){
            user1 += Integer.toString(pr.get_id_u()) + " " +
                    pr.get_login_u() + " " +
                    pr.get_password_u() + "\n";
        }
        textView.setText(user1);*/
        /*String admin1 = "";
        DatabaseHelperAdmin dbHelperAdmin = new DatabaseHelperAdmin(this);
        List<Admin> list = dbHelperAdmin.fillingStruct();
        for(Admin pr : list){
            admin1 += Integer.toString(pr.get_id_adm()) + " " +
                    pr.get_login_adm() + " " +
                    pr.get_password_adm() + "\n";
        }
        textView.setText(admin1);*/


        //String product1 = "";
        //DatabaseHelperProduct dbHelperProduct = new DatabaseHelperProduct(this);
        //dbHelperProduct.addRecord("TItle", "Вода по цене золота", 150.0);
        //dbHelperProduct.addRecord("TItle", "ПРОСТО Вода", 15.0);
        //dbHelperProduct.deleteRecord(7);
        //dbHelperProduct.changeRecord(10, "TItle1", "водица", 0.5);
        //List<Product> list = dbHelperProduct.fillingStruct();
        //for(Product pr : list){
        //    product1 += Integer.toString(pr.get_id()) + " " +
        //            pr.get_title() + " " +
        //            pr.get_description() + " " +
        //            Double.toString(pr.get_cost()) + "\n";
        //}
        //textView.setText(product1);

    }

}
