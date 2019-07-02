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

import Tables.UserTable;

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

            else
            {
                boolean flag=false;
                String email, pass;
                UserTable userTable = new UserTable();
                Cursor cursor = databaseHelper.getDataFromUser();
                if(cursor!=null)
                {
                    if(cursor.getCount()>0)
                    {
                        cursor.moveToFirst();
                        do{
                            email = cursor.getString(cursor.getColumnIndex(userTable.getEmail()));
                            pass = cursor.getString(cursor.getColumnIndex(userTable.getPassword()));
                            if(username.equalsIgnoreCase(email) && password.equalsIgnoreCase(pass))
                            {
                                flag=true;
                                break;
                            }
                        }while(cursor.moveToNext());

                        if(flag)
                        {
                            String firstName = cursor.getString(cursor.getColumnIndex(userTable.getFirstName())), lastEdit = cursor.getString(cursor.getColumnIndex(userTable.getLastEdit()));
                            sharedPreferences.edit().putString("firstName", firstName).commit();
                            sharedPreferences.edit().putString("email", email).commit();
                            sharedPreferences.edit().putString("lastEdit", lastEdit).commit();
                            Intent intent = new Intent(this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(this, "Email/Password incorrect", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this, "Empty Table", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this, "null cursor", Toast.LENGTH_LONG).show();

//
//                Intent intent = new Intent(this, HomeActivity.class);
////                intent.putExtra("Data:", "Hello World!");
//                startActivity(intent);
            }

        }
    }
}
