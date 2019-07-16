package com.example.aatmikjain.memberdirectory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.EducationalAdapter;
import Database.DatabaseHelper;
import Database.EducationalDetailsTable;
import Database.UserTable;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener{

    TextView nameTv, dobTv, emailTv, mobileTv, cityPincodeTv, branchTv;
    Button editProfileBtn;
    RecyclerView educationRecyclerView;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        sharedPreferences = getSharedPreferences("DIR", Context.MODE_PRIVATE);
        editProfileBtn = findViewById(R.id.editProfile);
        editProfileBtn.setOnClickListener(this);
        nameTv = findViewById(R.id.name);
        dobTv = findViewById(R.id.dob);
        emailTv = findViewById(R.id.email);
        mobileTv = findViewById(R.id.mobile);
        cityPincodeTv = findViewById(R.id.cityPincode);
        branchTv = findViewById(R.id.branch);
        educationRecyclerView = findViewById(R.id.educational_recycler_view);
        databaseHelper = DatabaseHelper.getInstance(this);
        cursor = databaseHelper.getDataFromUser();
        String email = sharedPreferences.getString("email", "");

        nameTv.setText(sharedPreferences.getString("firstName","")+sharedPreferences.getString("lastName",""));
        dobTv.setText(sharedPreferences.getString("dob",""));
        emailTv.setText(email);
        mobileTv.setText(sharedPreferences.getString("mobile",""));
        cityPincodeTv.setText(sharedPreferences.getString("city", "")+" - " +sharedPreferences.getString("pincode",""));
        branchTv.setText(sharedPreferences.getString("branch", ""));

        educationRecyclerView.setLayoutManager(new LinearLayoutManager(MyProfileActivity.this, LinearLayout.VERTICAL, false));
        educationRecyclerView.addItemDecoration(new DividerItemDecoration(MyProfileActivity.this, LinearLayout.VERTICAL));
        cursor = databaseHelper.getEducationalDetails();
        ArrayList<ArrayList<String>> educationDetails = new ArrayList<>();
        if(cursor != null)
        {
            if(cursor.getCount()>0)
            {
                String degree, institute, board_University, from, to, result, tempMail;
                EducationalDetailsTable educationalDetailsTable = new EducationalDetailsTable();
                cursor.moveToFirst();
                do{
                    ArrayList<String> rowDetails = new ArrayList<>();
                    tempMail = cursor.getString(cursor.getColumnIndex((educationalDetailsTable.getEmail())));
                    if(tempMail.equals(email))
                    {
                        degree = cursor.getString(cursor.getColumnIndex(educationalDetailsTable.getDegree()));
                        institute = cursor.getString(cursor.getColumnIndex(educationalDetailsTable.getInstituteName()));
                        board_University = cursor.getString(cursor.getColumnIndex(educationalDetailsTable.getBoard_University()));
                        from = cursor.getString(cursor.getColumnIndex(educationalDetailsTable.getStart_Date()));
                        to = cursor.getString(cursor.getColumnIndex(educationalDetailsTable.getEnd_Date()));
                        result = cursor.getString(cursor.getColumnIndex(educationalDetailsTable.getResult()));
                        rowDetails.add(degree);
                        rowDetails.add(institute);
                        rowDetails.add(board_University);
                        rowDetails.add(from);
                        rowDetails.add(to);
                        rowDetails.add(result);
                        educationDetails.add(rowDetails);
                    }
                }while(cursor.moveToNext());
                EducationalAdapter educationalAdapter = new EducationalAdapter(educationDetails, this);
                educationRecyclerView.setAdapter(educationalAdapter);
            }
            else
                Toast.makeText(MyProfileActivity.this, "Empty Education Table", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(MyProfileActivity.this, "null cursor", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.editProfile){
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        }
    }
    public void refresh(){

    }
}
