package com.example.aatmikjain.memberdirectory;

import android.database.sqlite.SQLiteDatabase;

public class ProfessionalDetailsTable
{
    private String tableName = "ProfessionalDetailsTable";
    private String customerID = "CustomerID";
    private String companyName = "CompanyName";
    private String position  = "Position";
    private String joinDate = "JoinDate";
    private String endDate = "EndDate";

    public ProfessionalDetailsTable(SQLiteDatabase sqLiteDatabase)
    {
        UserTable userTable = new UserTable();
        String sql = "Create table " + tableName + " ( "+
                customerID + " int(7), " +
                companyName + " varchar(30), "+
                position + " varchar(20), "+
                joinDate + " date, "+
                endDate + " date, " +
                "foreign key (" + customerID + ") references " + userTable.getTableName()+"("+userTable.getId()+"));";
        sqLiteDatabase.execSQL(sql);
    }

    public ProfessionalDetailsTable(String companyName, String position, String joinDate, String endDate) {

        this.companyName = companyName;
        this.position = position;
        this.joinDate = joinDate;
        this.endDate = endDate;
    }

    public ProfessionalDetailsTable() {

    }

    public String getTableName() {

        return tableName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCompanyName() {

        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
