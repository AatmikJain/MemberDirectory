package com.example.aatmikjain.memberdirectory;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import static android.content.res.Resources.*;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView changePasswordIv;
    Switch modeSw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        changePasswordIv = findViewById(R.id.changePassword);
        changePasswordIv.setOnClickListener(this);

        modeSw = findViewById(R.id.nightMode);
        modeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.changePassword)
        {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.nightMode)
        {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            setTheme(new Theme(R.style.AppTheme_GREY, R.color.lightGrey, R.color.moderateGrey, R.color.darkGrey));
//           Theme t = new Theme();
//            Theme th =  new Theme(R.style.AppTheme_GREY, R.color.lightGrey, R.color.moderateGrey, R.color.darkGrey);
        }

    }
}
