package com.example.aatmikjain.memberdirectory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mLoginBtn;
    private EditText usernameEt;
    private EditText passwordEt;
    private TextView noAccYetTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginBtn = findViewById(R.id.loginBtn);
        mLoginBtn.setOnClickListener(this);

        noAccYetTv = findViewById(R.id.noAccYet);
        noAccYetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);

//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Hello World", Toast.LENGTH_LONG).show();
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
    public void onClick(View v)
    {
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
                Intent intent = new Intent(this, HomeActivity.class);
//                intent.putExtra("Data:", "Hello World!");
                startActivity(intent);
            }

        }
    }
}
