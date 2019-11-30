package com.example.waterorderv2;

/**
 * Created by HP on 21.11.2019.
 */

public class Product {
    int _id;
    String _title;
    String _description;
    double _cost;

    public Product(){}

    public Product(int id, String title, String description, double cost){
        this._id = id;
        this._title = title;
        this._description = description;
        this._cost = cost;
    }

    public Product(String title, String description, double cost){
        this._title = title;
        this._description = description;
        this._cost = cost;
    }

    public void set_id(int id){this._id = id;}
    public int get_id(){return this._id;}

    public void set_title(String title){this._title = title;}
    public String get_title(){return this._title;}

    public void set_description(String description){this._description = description;}
    public String get_description(){return this._description;}

    public void set_cost(double cost){this._cost = cost;}
    public double get_cost(){return this._cost;}

}
