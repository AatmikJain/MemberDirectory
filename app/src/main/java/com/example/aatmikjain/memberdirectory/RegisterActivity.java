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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.DatabaseHelper;
import Database.EditLogTable;
import Database.LoginTable;
import Database.UserTable;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "Register";
    private Button registerBtn;
    private EditText emailEt, passwordEt, firstNameEt, lastNameEt, mobileNumberEt, branchEt, cityEt, pincodeEt, dobEt;
    private ImageView calendarIcon;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    DatabaseHelper databaseHelper;
    RadioGroup genderRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        try{
//            String data = getIntent().getStringExtra("Data:");
//            Toast.makeText(this, data, Toast.LENGTH_LONG);
//            Log.i(TAG, data);
//        }catch(Exception e){ e.printStackTrace();}

//        component initialisation
        registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(this);

        calendarIcon = findViewById(R.id.calendar_icon);

        emailEt = findViewById(R.id.emailAddress);
        passwordEt = findViewById(R.id.password);
        firstNameEt = findViewById(R.id.firstName);
        lastNameEt = findViewById(R.id.lastName);
        mobileNumberEt = findViewById(R.id.mobileNumber);
        cityEt = findViewById(R.id.city);
        pincodeEt = findViewById(R.id.pincode);
        dobEt = findViewById(R.id.dob);
        dobEt.setEnabled(false);
        branchEt = findViewById(R.id.branch);
        genderRg = findViewById(R.id.genderRg);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
//        showDate(year, month+1, day);

        databaseHelper = DatabaseHelper.getInstance(this);
    }
    public void login(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.register)
        {
            String email = emailEt.getText().toString(), password = passwordEt.getText().toString(), firstName = firstNameEt.getText().toString(),
                    lastName = lastNameEt.getText().toString(), mobileNumber = mobileNumberEt.getText().toString(), branch = branchEt.getText().toString(), city = cityEt.getText().toString(), pincode = pincodeEt.getText().toString();

            if(email.isEmpty())
                Toast.makeText(this,"Enter Email Address", Toast.LENGTH_LONG).show();
            else if(password.isEmpty())
                Toast.makeText(this,"Enter Password", Toast.LENGTH_LONG).show();
            else if(firstName.isEmpty())
                Toast.makeText(this,"Enter Name", Toast.LENGTH_LONG).show();
            else if(mobileNumber.isEmpty())
                Toast.makeText(this,"Enter Mobile Number", Toast.LENGTH_LONG).show();
            else if(city.isEmpty())
                Toast.makeText(this,"Enter City", Toast.LENGTH_LONG).show();
            else if(pincode.isEmpty())
                Toast.makeText(this,"Enter Pincode", Toast.LENGTH_LONG).show();

                //Length of inputs < 6
            else if(email.length()<6 || password.length()<6)
                Toast.makeText(this,"Username and Password should have at least 6 characters", Toast.LENGTH_LONG).show();

            else if(!email.contains("@") || !email.contains(".") || email.lastIndexOf('.')<email.indexOf('@') || email.lastIndexOf('.')==email.length()-1 || email.lastIndexOf('.') - email.indexOf('@') <=2)
                Toast.makeText(this,"Enter Valid Email Address", Toast.LENGTH_LONG).show();

            else if(!password.matches(".*\\d+.*") || !password.matches(".*[a-zA-Z].*"))
                Toast.makeText(this,"Enter valid password\nPassword should contain at least 1 alphabet and 1 number", Toast.LENGTH_LONG).show();

            else if(mobileNumber.length()!=10 || mobileNumber.matches(".*\\D+.*"))
                Toast.makeText(this,"Enter Valid Mobile Number", Toast.LENGTH_LONG).show();

            else if(pincode.length()!=6 || pincode.matches(".*\\D+.*"))
                Toast.makeText(this,"Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
//            else if(!gender.isSelected())
//                Toast.makeText(this,"Select Gender", Toast.LENGTH_LONG).show();
            else
            {
                int id = genderRg.getCheckedRadioButtonId();
                String currentDateTimeString = getDateTime();
                String g;
                if(id==R.id.male)
                    g="M";
                else if(id==R.id.female)
                    g = "F";
                else
                    g="O";
                UserTable inputData = new UserTable(firstName, lastName, email, password, mobileNumber, branch, city, pincode, g, dobEt.getText().toString(), currentDateTimeString);
                if(databaseHelper.addUserData(inputData)) {
                    if(databaseHelper.addLoginData(new LoginTable(email, password))) {
                        if (databaseHelper.addEditLog(new EditLogTable(email, currentDateTimeString))) {
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
                else
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
            }

        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                     /*year = arg1;
                     month = arg2+1;
                     day = arg3;*/
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dobEt.setText(day+"/"+month+"/"+year);
    }
}
