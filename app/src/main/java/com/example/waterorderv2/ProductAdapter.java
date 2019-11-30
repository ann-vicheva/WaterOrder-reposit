package com.example.waterorderv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP on 26.11.2019.
 */

class ProductAdapter extends ArrayAdapter<Product> {

    private LayoutInflater inflater;
    private int layout;
    private List<Product> productList;

    Double current_cost=0.0;
    String order="";

    ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Product product = productList.get(position);


        viewHolder.titleView.setText(product.get_title());
        viewHolder.descrView.setText(product.get_description());
        viewHolder.costView.setText(Double.toString(product.get_cost()));
        //viewHolder.countView.setText(formatValue(product.getCount(), product.getUnit()));

        /*viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = product.getCount()-1;
                if(count<0) count=0;
                product.setCount(count);
                viewHolder.countView.setText(formatValue(count, product.getUnit()));
            }
        });*/
        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_cost += product.get_cost();
                order += Integer.toString(product.get_id())+" ";
                //viewHolder.curcostView.setText(Double.toString(current_cost));
                /*int count = product.getCount()+1;
                product.setCount(count);
                viewHolder.countView.setText(formatValue(count, product.getUnit()));*/
            }
        });

        return convertView;
    }

    public Double returnCurrentCost(){
        return current_cost;
    }
    public String returnCurrentOrder(){
        return order;
    }

    private class ViewHolder {
        final Button addButton;
        final TextView titleView, descrView, costView;// curcostView;
        ViewHolder(View view){
            addButton = (Button) view.findViewById(R.id.addButton);
            titleView = (TextView) view.findViewById(R.id.titleView);
            descrView = (TextView) view.findViewById(R.id.descrView);
            costView = (TextView) view.findViewById(R.id.costView);
            //curcostView = (TextView) view.findViewById(R.id.curcostView);
        }
    }

}
