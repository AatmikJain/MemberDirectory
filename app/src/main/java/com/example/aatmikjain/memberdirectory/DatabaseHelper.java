package com.example.aatmikjain.memberdirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String dbName = "";
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public boolean addUserData(UserTable userTable)
    {

            UserTable keyUserName = new UserTable();
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(keyUserName.getTableName(), userTable.getTableName());
            contentValues.put(keyUserName.getFirstName(), userTable.getFirstName());
            contentValues.put(keyUserName.getLastName(), userTable.getLastName());
            contentValues.put(keyUserName.getDob(), userTable.getDob());
            contentValues.put(keyUserName.getEmail(), userTable.getEmail());
            contentValues.put(keyUserName.getMobile(), userTable.getMobile());
            contentValues.put(keyUserName.getCity(), userTable.getCity());
            contentValues.put(keyUserName.getPincode(), userTable.getPincode());
            contentValues.put(keyUserName.getGender(), userTable.getGender());
            contentValues.put(keyUserName.getBranch(), userTable.getBranch());

            sqLiteDatabase.insert(keyUserName.getTableName(), null, contentValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
//            Toast.makeText(this,"Enter valid password\nPassword should contain at least 1 alphabet and 1 number", Toast.LENGTH_LONG).show();
        }

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
