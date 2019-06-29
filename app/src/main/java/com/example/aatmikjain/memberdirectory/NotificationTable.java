package com.example.aatmikjain.memberdirectory;

import android.database.sqlite.SQLiteDatabase;

public class NotificationTable
{
    private String tableName = "NotificationTable";
    private String title = "Title";
    private String description = "Description";
    private String summary = "Summary";
    private String domain = "Domain";
    private String startDate = "StartDate";
    private String endDate = "EndDate";

    public NotificationTable(SQLiteDatabase sqLiteDatabase)
    {
        UserTable userTable = new UserTable();
        String sql = "create table "+ tableName +" ( "+
                title + " varchar(20), "+
                domain + " varchar(30), "+
                summary + " varchar(50), "+
                startDate + " date, "+
                endDate + " date, "+
                description + " varchar(200));";
        sqLiteDatabase.execSQL(sql);
    }

    public NotificationTable() {
    }

    public NotificationTable(String title, String description, String summary, String domain, String startDate, String endDate) {

        this.title = title;
        this.description = description;
        this.summary = summary;
        this.domain = domain;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
