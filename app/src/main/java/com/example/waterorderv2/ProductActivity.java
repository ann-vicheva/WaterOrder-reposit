package com.example.waterorderv2;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    int cur_id=-2;
    String ord = "";
    Double costOrder;

    List<Product> products = new ArrayList();
    ProductAdapter adapter;

    ListView productList;

    //TextView tv1;
    TextView tv;
    Button butOrder;

    private DatabaseHelperP mDBHelperP;
    private SQLiteDatabase mDbP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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
        costOrder=0.0;
        cur_id = getIntent().getIntExtra("current_id", 0);
        //tv1 = (TextView) findViewById(R.id.curId);
        //tv1.setText(Integer.toString(cur_id));

        tv = (TextView) findViewById(R.id.curCost);
        butOrder = (Button) findViewById(R.id.butOrder);


        DatabaseHelperProduct dbHelperProduct = new DatabaseHelperProduct(this);
        products = dbHelperProduct.fillingStruct();

        productList = (ListView) findViewById(R.id.productList);
        adapter = new ProductAdapter(this, R.layout.list_item, products);
        productList.setAdapter(adapter);
        costOrder = adapter.returnCurrentCost();
        tv.setText(Double.toString(costOrder));

    }

    public void LogOut(View view){
        if(cur_id>-1){cur_id=-2;}
    }

    public void Order(View view){
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("current_id", cur_id);
        intent.putExtra("current_cost", costOrder);
        intent.putExtra("current_order", ord);
        startActivity(intent);
    }

    public void press(View view){
        costOrder = adapter.returnCurrentCost();
        tv.setText(Double.toString(costOrder));
        ord = adapter.returnCurrentOrder();
        if(costOrder>0&&cur_id>-1){butOrder.setVisibility(View.VISIBLE);}
    }

}
