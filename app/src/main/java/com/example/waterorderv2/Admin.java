package com.example.waterorderv2;

/**
 * Created by HP on 24.11.2019.
 */

public class Admin {
    int _id_adm;
    String _login_adm;
    String _password_adm;

    public Admin(){}

    public Admin(int id_adm, String login_adm, String password_adm){
        this._id_adm = id_adm;
        this._login_adm = login_adm;
        this._password_adm = password_adm;
    }

    public Admin(String login_adm, String password_adm){
        this._login_adm = login_adm;
        this._password_adm = password_adm;
    }

    public void set_id_adm(int id_adm){this._id_adm = id_adm;}
    public int get_id_adm(){return this._id_adm;}

    public void set_login_adm(String login_adm){this._login_adm = login_adm;}
    public String get_login_adm(){return this._login_adm;}

    public void set_password_adm(String password_adm){this._password_adm = password_adm;}
    public String get_password_adm(){return this._password_adm;}

}
