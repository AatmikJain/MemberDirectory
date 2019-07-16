package com.example.aatmikjain.memberdirectory;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DatabaseHelper;
import Database.UserTable;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    EditText oldPasswordTv, newPasswordTv, confirmPasswordTv;
    Button changePasswordBtn;
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sharedPreferences = getSharedPreferences("DIR", Context.MODE_PRIVATE);
        databaseHelper = DatabaseHelper.getInstance(this);

        oldPasswordTv = findViewById(R.id.oldPassword);
        newPasswordTv = findViewById(R.id.newPassword);
        confirmPasswordTv = findViewById(R.id.confirmPassword);
        changePasswordBtn = findViewById(R.id.changePassword);
        changePasswordBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.changePassword){
            if(oldPasswordTv.getText().toString().equalsIgnoreCase(sharedPreferences.getString("password", ""))){
                if(newPasswordTv.getText().toString().equals(confirmPasswordTv.getText().toString())){
                    UserTable userTable = new UserTable(
                            sharedPreferences.getString("email",""),
                            newPasswordTv.getText().toString());
                    if(databaseHelper.updateUserPassword(userTable)){
                        sharedPreferences.edit().putString("password", newPasswordTv.getText().toString()).commit();
                        Toast.makeText(this, "Password changed Successfully", Toast.LENGTH_LONG).show();
                        oldPasswordTv.setText("");
                        Toast.makeText(this, sharedPreferences.getString("password",""), Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(this, "Could not update Password", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this, "Old Password is incorrect", Toast.LENGTH_LONG).show();
            }
            newPasswordTv.setText("");
            confirmPasswordTv.setText("");
        }
    }
}
