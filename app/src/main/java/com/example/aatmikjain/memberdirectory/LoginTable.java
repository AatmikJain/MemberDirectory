package com.example.aatmikjain.memberdirectory;

import android.database.sqlite.SQLiteDatabase;

public class LoginTable
{
    private String tableName = "LoginTable";
    private String username = "Username";
    private String password = "Password";

    public LoginTable(SQLiteDatabase sqLiteDatabase)
    {
        String sql = "create table " + tableName +"( " + username + " varchar(30), "+ password + " varchar(30));";
        sqLiteDatabase.execSQL(sql);
    }

    public LoginTable() {
    }

    public LoginTable(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
