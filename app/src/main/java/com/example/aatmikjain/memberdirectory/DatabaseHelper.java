package com.example.aatmikjain.memberdirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Tables.EducationalDetailsTable;
import Tables.LoginTable;
import Tables.NotificationTable;
import Tables.ProfessionalDetailsTable;
import Tables.UserTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String dbName = "data_dir";
    private SQLiteDatabase myWritableDb;

    private static DatabaseHelper databaseHelper;

    private DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
    }

    public static DatabaseHelper getInstance(Context context)
    {
        if(databaseHelper == null)
            return databaseHelper = new DatabaseHelper(context);
        else
            return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        new UserTable(sqLiteDatabase);
        new LoginTable(sqLiteDatabase);
        new EducationalDetailsTable(sqLiteDatabase);
        new ProfessionalDetailsTable(sqLiteDatabase);
        new NotificationTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public boolean addEducationalDetails(EducationalDetailsTable educationalDetailsTable)
    {
        try {
            EducationalDetailsTable keyCustID = new EducationalDetailsTable();
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(keyCustID.getTableName(), educationalDetailsTable.getTableName());
            contentValues.put(keyCustID.getCustomerID(), educationalDetailsTable.getCustomerID());
            contentValues.put(keyCustID.getInstituteName(), educationalDetailsTable.getInstituteName());
            contentValues.put(keyCustID.getDegree(), educationalDetailsTable.getDegree());
            contentValues.put(keyCustID.getBoard_University(), educationalDetailsTable.getBoard_University());
            contentValues.put(keyCustID.getStart_Date(), educationalDetailsTable.getStart_Date());
            contentValues.put(keyCustID.getEnd_Date(), educationalDetailsTable.getEnd_Date());
            contentValues.put(keyCustID.getResult(), educationalDetailsTable.getResult());

            sqLiteDatabase.insert(keyCustID.getTableName(), null, contentValues);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getEducationalDetails(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        EducationalDetailsTable educationalDetailsTable = new EducationalDetailsTable();
        return sqLiteDatabase.rawQuery("Select * from "+ educationalDetailsTable.getTableName()+";", null);
    }

    public boolean addProfessionalDetails(ProfessionalDetailsTable professionalDetailsTable)
    {
        try {
            ProfessionalDetailsTable keyCustID = new ProfessionalDetailsTable();
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(keyCustID.getTableName(), professionalDetailsTable.getTableName());
            contentValues.put(keyCustID.getCustomerID(), professionalDetailsTable.getCustomerID());
            contentValues.put(keyCustID.getCompanyName(), professionalDetailsTable.getCompanyName());
            contentValues.put(keyCustID.getPosition(), professionalDetailsTable.getPosition());
            contentValues.put(keyCustID.getEndDate(), professionalDetailsTable.getEndDate());
            contentValues.put(keyCustID.getJoinDate(), professionalDetailsTable.getJoinDate());

            sqLiteDatabase.insert(keyCustID.getTableName(), null, contentValues);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public Cursor getProfessionalDetails(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ProfessionalDetailsTable professionalDetailsTable = new ProfessionalDetailsTable();
        return sqLiteDatabase.rawQuery("Select * from "+ professionalDetailsTable.getTableName()+";", null);
    }

    public boolean addNotification(NotificationTable notificationTable)
    {
        try {
            NotificationTable keyCustID = new NotificationTable();
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(keyCustID.getTitle(), notificationTable.getTitle());
            contentValues.put(keyCustID.getDescription(), notificationTable.getDescription());
            contentValues.put(keyCustID.getSummary(), notificationTable.getSummary());
            contentValues.put(keyCustID.getDomain(), notificationTable.getDomain());
            contentValues.put(keyCustID.getEndDate(), notificationTable.getEndDate());
            contentValues.put(keyCustID.getStartDate(), notificationTable.getStartDate());

            sqLiteDatabase.insert(keyCustID.getTableName(), null, contentValues);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getNotification()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        NotificationTable notificationTable = new NotificationTable();
        return sqLiteDatabase.rawQuery("Select * from "+notificationTable.getTableName()+";", null);
    }

    public boolean addUserData(UserTable userTable)
    {
        try {
            UserTable keyUserName = new UserTable();
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(keyUserName.getFirstName(), userTable.getFirstName());
            contentValues.put(keyUserName.getLastName(), userTable.getLastName());
            contentValues.put(keyUserName.getDob(), userTable.getDob());
            contentValues.put(keyUserName.getEmail(), userTable.getEmail());
            contentValues.put(keyUserName.getPassword(), userTable.getPassword());
            contentValues.put(keyUserName.getMobile(), userTable.getMobile());
            contentValues.put(keyUserName.getCity(), userTable.getCity());
            contentValues.put(keyUserName.getPincode(), userTable.getPincode());
            contentValues.put(keyUserName.getGender(), userTable.getGender());
            contentValues.put(keyUserName.getBranch(), userTable.getBranch());

            sqLiteDatabase.insert(keyUserName.getTableName(), null, contentValues);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
//            Toast.makeText(this,"Enter valid password\nPassword should contain at least 1 alphabet and 1 number", Toast.LENGTH_LONG).show();
        }

    }

    public Cursor getDataFromUser(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        UserTable userTable = new UserTable();
        return sqLiteDatabase.rawQuery("Select * from "+ userTable.getTableName()+";", null);
    }

    public boolean updateUserData(UserTable userTable)
    {
        try {
            UserTable keyUserName = new UserTable();
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(keyUserName.getFirstName(), userTable.getFirstName());
            contentValues.put(keyUserName.getLastName(), userTable.getLastName());
            contentValues.put(keyUserName.getMobile(), userTable.getMobile());
            contentValues.put(keyUserName.getBranch(), userTable.getBranch());
            contentValues.put(keyUserName.getCity(), userTable.getCity());
            contentValues.put(keyUserName.getPincode(), userTable.getPincode());
            contentValues.put(keyUserName.getDob(), userTable.getDob());
            sqLiteDatabase.update(keyUserName.getTableName(), contentValues, "id=?",new String[]{userTable.getId()});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public SQLiteDatabase getMyWritableDatabase()
    {
        if(myWritableDb == null || !myWritableDb.isOpen())
            myWritableDb = this.getWritableDatabase();
        return myWritableDb;
    }


    public void close()
    {
        if(myWritableDb!=null)
        {
            myWritableDb.close();
            myWritableDb=null;
        }
    }
}
