package com.example.aatmikjain.memberdirectory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button saveChangesBtn;
    private EditText firstNameEt, lastNameEt, mobileNumberEt, dobEt, cityEt, pincodeEt;
    private RadioButton maleRb, femaleRb;
    private ImageView cal_icon;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        saveChangesBtn = findViewById(R.id.saveChanges);
        firstNameEt = findViewById(R.id.firstName);
        lastNameEt = findViewById(R.id.lastName);
        mobileNumberEt = findViewById(R.id.mobileNumber);
        dobEt = findViewById(R.id.dob);
        cityEt = findViewById(R.id.city);
        pincodeEt = findViewById(R.id.pincode);
        cal_icon = findViewById(R.id.calendar_icon);
        cal_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(year, month, day);
            }
        });
    }

    public void setDate(View view)
    {
        showDialog(999);
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int y, int m, int d) {
            year = y;
            month = m+1;
            day = d;
            showDate(year, month, day);
        }
    };
    public Dialog onCreateDialog(int id)
    {
        if(id==999)
            return new DatePickerDialog(this,myDateListener, year, month,day);
        return null;
    }
    public void showDate(int year, int month, int dayOfMonth)
    {
        dobEt.setText(dayOfMonth+"/"+month+"/"+year);
    }

    @Override
    public void onClick(View v) {

    }
}
