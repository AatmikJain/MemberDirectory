package com.example.aatmikjain.memberdirectory;

import android.database.sqlite.SQLiteDatabase;
//import com.example.aatmikjain.memberdirectory.UserTable;

public class EducationalDetailsTable
{
    private String tableName = "EducationalDetailsTable";
    private String customerID = "CustomerID";
    private String instituteName = "InstituteName";
    private String degree = "Degree";
    private String board_University = "Board_University";
    private String start_Date = "Start_Date";
    private String end_Date = "End_Date";
    private String result = "Result";

    public EducationalDetailsTable(SQLiteDatabase sqLiteDatabase)
    {
        UserTable userTable = new UserTable();
        String sql = "create table "+tableName+" ( "+
        customerID + " int(7), " +
        instituteName + " varchar(50), " +
        degree + " varchar(20), " +
        board_University + " varchar(30), " +
        start_Date + " date, " +
        end_Date + " date, " +
        result + " varchar(8), " +
        "foreign key ("+customerID+") references " +userTable.getTableName()+"("+userTable.getId()+"));";

        sqLiteDatabase.execSQL(sql);
    }

    public EducationalDetailsTable(String instituteName, String degree, String board_University, String start_Date, String end_Date, String result) {
        this.instituteName = instituteName;
        this.degree = degree;
        this.board_University = board_University;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.result = result;
    }

    public EducationalDetailsTable() {
    }

    public String getTableName() {
        return tableName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBoard_University() {
        return board_University;
    }

    public void setBoard_University(String board_University) {
        this.board_University = board_University;
    }

    public String getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(String start_Date) {
        this.start_Date = start_Date;
    }

    public String getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(String end_Date) {
        this.end_Date = end_Date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}