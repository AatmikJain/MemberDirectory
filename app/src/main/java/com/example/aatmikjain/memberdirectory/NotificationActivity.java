package com.example.aatmikjain.memberdirectory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText titleEt, domainEt, summaryEt, startDateEt, endDateEt, descriptionEt;
    private Button addBtn;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
         flag=false;
        titleEt = findViewById(R.id.title);
        domainEt = findViewById(R.id.domain);
        summaryEt = findViewById(R.id.summary);
        startDateEt = findViewById(R.id.startDate);
        endDateEt = findViewById(R.id.endDate);
        descriptionEt = findViewById(R.id.description);
        addBtn = findViewById(R.id.add);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

    }
    public void setDate(View view) {
        showDialog(view.getId());
//        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == R.id.startDateIv) {
            flag=true;
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        if(id == R.id.endDateIv)
        {
            flag=false;
            return new DatePickerDialog(this, myDateListener, year, month, day);

        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0,
                              int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            year = arg1;
            month = arg2+1;
            day = arg3;
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day)
    {
        if(flag)
            startDateEt.setText(day+"/"+month+"/"+year);
        if(!flag)
            endDateEt.setText(day+"/"+month+"/"+year);
    }

    @Override
    public void onClick(View v) {

    }
}
