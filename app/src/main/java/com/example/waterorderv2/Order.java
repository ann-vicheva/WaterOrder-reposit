package com.example.waterorderv2;

/**
 * Created by HP on 24.11.2019.
 */

public class Order {
    int _id;
    int _id_us;
    String _description_ord;

    public Order(){}

    public Order(int id, int id_us, String description_ord){
        this._id = id;
        this._id_us = id_us;
        this._description_ord = description_ord;
    }

    public Order(int id_us, String description_ord){
        this._id_us = id_us;
        this._description_ord = description_ord;
    }

    public void set_id(int id){this._id = id;}
    public int get_id(){return this._id;}

    public void set_id_us(int id_us){this._id_us = id_us;}
    public int get_id_us(){return this._id_us;}

    public void set_description_ord(String description_ord){this._description_ord = description_ord;}
    public String get_description_ord(){return this._description_ord;}

}
