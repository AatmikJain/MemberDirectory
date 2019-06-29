package com.example.aatmikjain.memberdirectory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText titleEt, domainEt, summaryEt, startDateEt, endDateEt, descriptionEt;
    private Button addBtn;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    boolean dateFlag;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        dateFlag=false;
        titleEt = findViewById(R.id.title);
        domainEt = findViewById(R.id.domain);
        summaryEt = findViewById(R.id.summary);
        startDateEt = findViewById(R.id.startDate);
        endDateEt = findViewById(R.id.endDate);
        descriptionEt = findViewById(R.id.description);
        addBtn = findViewById(R.id.add);
        addBtn.setOnClickListener(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        databaseHelper = DatabaseHelper.getInstance(this);

    }
    public void setDate(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == R.id.startDateIv) {
            dateFlag=true;
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        if(id == R.id.endDateIv)
        {
            dateFlag=false;
            return new DatePickerDialog(this, myDateListener, year, month, day);

        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0,
                              int arg1, int arg2, int arg3) {
            year = arg1;
            month = arg2+1;
            day = arg3;
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day)
    {
        if(dateFlag)
            startDateEt.setText(day+"/"+month+"/"+year);
        if(!dateFlag)
            endDateEt.setText(day+"/"+month+"/"+year);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.add)
        {
            if(titleEt.getText().toString().isEmpty())
                Toast.makeText(this, "Enter Title", Toast.LENGTH_LONG).show();
            else if(domainEt.getText().toString().isEmpty())
                Toast.makeText(this, "Enter Domain", Toast.LENGTH_LONG).show();
            else if(summaryEt.getText().toString().isEmpty())
                Toast.makeText(this, "Enter Summary", Toast.LENGTH_LONG).show();
            else if(startDateEt.getText().toString().isEmpty())
                Toast.makeText(this, "Enter Start Date", Toast.LENGTH_LONG).show();
            else if (endDateEt.getText().toString().isEmpty())
                Toast.makeText(this, "Enter End Date", Toast.LENGTH_LONG).show();
            else if(descriptionEt.getText().toString().isEmpty())
                Toast.makeText(this, "Enter Description", Toast.LENGTH_LONG).show();
            else
            {
                NotificationTable inputNotification = new NotificationTable(
                        titleEt.getText().toString(),
                        descriptionEt.getText().toString(),
                        summaryEt.getText().toString(),
                        domainEt.getText().toString(),
                        startDateEt.getText().toString(),
                        endDateEt.getText().toString()
                );

                if(databaseHelper.addNotification(inputNotification))
                {
                    Toast.makeText(this, "Notification added successfully", Toast.LENGTH_LONG).show();
                    titleEt.setText("");
                }
                else
                    Toast.makeText(this, "Failed to add Notification", Toast.LENGTH_LONG).show();
            }
        }
    }
}
