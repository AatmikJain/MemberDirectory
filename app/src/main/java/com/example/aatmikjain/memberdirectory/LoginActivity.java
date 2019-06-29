package com.example.aatmikjain.memberdirectory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mLoginBtn;
    private EditText usernameEt;
    private EditText passwordEt;
    private TextView noAccYetTv, forgotPasswordTv;
    SharedPreferences sharedPreferences;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginBtn = findViewById(R.id.loginBtn);
        mLoginBtn.setOnClickListener(this);

        noAccYetTv = findViewById(R.id.noAccYet);
        noAccYetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);

        forgotPasswordTv = findViewById(R.id.forgotPassword);
        forgotPasswordTv.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("DIR", Context.MODE_PRIVATE);

        databaseHelper = DatabaseHelper.getInstance(this);
//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(LoginActivity.this,"Hello World", Toast.LENGTH_LONG).show();
//            }
//        });
    }

//    public void performLogin(View view)
//    {
//        Toast.makeText(this,"Login Success", Toast.LENGTH_LONG).show();
//    }

//    public void showRegister(View view)
//    {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        intent.putExtra("Data:", "Hello World!");
//        startActivity(intent);
//    }


    @Override
    public void onBackPressed() {
        //disabling back button
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.forgotPassword)
        {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.loginBtn)
        {
            String username = usernameEt.getText().toString();
            String password = passwordEt.getText().toString();
            //Empty fileds
            if(username.isEmpty())
                Toast.makeText(this,"Enter Username", Toast.LENGTH_LONG).show();
            else if(password.isEmpty())
                Toast.makeText(this,"Enter Password", Toast.LENGTH_LONG).show();

            //Length of inputs < 6
            else if(username.length()<6 || password.length()<6)
                Toast.makeText(this,"Username and Password should have at least 6 characters", Toast.LENGTH_LONG).show();

            //Username should start with an alphabet
            else if((username.charAt(0)>90 && username.charAt(0)<97) || username.charAt(0)<65 || username.charAt(0)>122 )
                Toast.makeText(this,"Enter valid username\nUsername can only start with an alphabet", Toast.LENGTH_LONG).show();

            //Password should contain at least 1 alphabet and 1 number
            else if(!password.matches(".*\\d+.*") || !password.matches(".*[a-zA-Z].*"))
                Toast.makeText(this,"Enter valid password\nPassword should contain at least 1 alphabet and 1 number", Toast.LENGTH_LONG).show();

            else
            {

                boolean flag=true;
                Cursor cursor = databaseHelper.getDataFromUser();
                cursor.moveToFirst();
                do{
                    String email = cursor.getString(3);
                    String pass = cursor.getString(4);
                    if(username.equalsIgnoreCase(email) && password.equalsIgnoreCase(pass))
                    {
                        flag=false;
//                        cursor = databaseHelper.getNotification();
//                        cursor.moveToFirst();
//                        do{
//                            Toast.makeText(this, cursor.getString(1), Toast.LENGTH_LONG).show();
//                        }while(cursor.moveToNext());
                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    }
                    else
                        Toast.makeText(this, ""+email + " "+pass, Toast.LENGTH_LONG).show();
                }while(cursor.moveToNext());


//                sharedPreferences.edit().putString("email", username).commit();
//
//                Intent intent = new Intent(this, HomeActivity.class);
////                intent.putExtra("Data:", "Hello World!");
//                startActivity(intent);
            }

        }
    }
}
