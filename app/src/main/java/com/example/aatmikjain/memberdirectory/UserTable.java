package com.example.aatmikjain.memberdirectory;

import android.database.sqlite.SQLiteDatabase;

public class UserTable
{
    private String tableName = "UserTable";
    private String id = "id";
    private String firstName = "firstName";
    private String lastName = "lastName";
    private String email = "email";
    private String password = "password";
    private String mobile = "mobile";
    private String branch = "branch";
    private String city = "city";
    private String pincode = "pincode";
    private String gender = "Gender";
    private String dob = "dob";

    public UserTable(SQLiteDatabase sqLiteDatabase)
    {
        String sql = "Create table " + tableName +" ( " + id + " integer primary key autoincrement, " + firstName + " varchar(20), " + lastName + " varchar(20), " + email + " varchar(30), "+ password + " varchar(30), " + mobile + " varchar(10), " + branch + " varchar(20), " + city + " varchar(20), " + pincode + " varchar(6), " + gender + " varchar(1), " + dob + " date);";
        sqLiteDatabase.execSQL(sql);

    }

    public UserTable( String firstName, String lastName, String email, String password, String mobile, String branch, String city, String pincode, String gender, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.branch = branch;
        this.city = city;
        this.pincode = pincode;
        this.gender = gender;
        this.dob = dob;
    }

    public UserTable() {
    }

    public String getTableName() {
        return tableName;
    }

//    public void setTableName(String tableName) {
//        this.tableName = tableName;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
