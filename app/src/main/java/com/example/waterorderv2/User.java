package com.example.waterorderv2;

/**
 * Created by HP on 24.11.2019.
 */

public class User {

    int _id_u;
    String _login_u;
    String _password_u;

    public User(){}

    public User(int id_u, String login_u, String password_u){
        this._id_u = id_u;
        this._login_u = login_u;
        this._password_u = password_u;
    }

    public User(String login_u, String password_u){
        this._login_u = login_u;
        this._password_u = password_u;
    }

    public void set_id_u(int id_u){this._id_u = id_u;}
    public int get_id_u(){return this._id_u;}

    public void set_login_u(String login_u){this._login_u = login_u;}
    public String get_login_u(){return this._login_u;}

    public void set_password_u(String password_u){this._password_u = password_u;}
    public String get_password_u(){return this._password_u;}

}
