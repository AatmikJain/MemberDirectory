package com.example.aatmikjain.memberdirectory;

import android.Manifest;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;


public class FirstFragment extends Fragment {

    private Button saveChangesBtn, uploadPhotoBtn;
    private EditText firstNameEt, lastNameEt, mobileNumberEt, dobEt, cityEt, pincodeEt;
    private RadioButton maleRb, femaleRb;
    private ImageView cal_icon, profilePhotoIv;
    private Calendar calendar;
    private int year, month, day;

    private File mImageFile;
    private Uri mImageUri;
    int PERMISSIONS_MULTIPLE_REQUEST = 100;
    String[] stringsPermission = {
            Manifest.permission
                    .READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firstNameEt = getView().findViewById(R.id.firstName);
        lastNameEt = getView().findViewById(R.id.lastName);
        lastNameEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),""+firstNameEt.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

//        calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        profilePhotoIv = findViewById(R.id.profilePhoto);
//        saveChangesBtn = findViewById(R.id.saveChanges);
//        firstNameEt = findViewById(R.id.firstName);
//        lastNameEt = findViewById(R.id.lastName);
//        mobileNumberEt = findViewById(R.id.mobileNumber);
//        dobEt = findViewById(R.id.dob);
//        cityEt = findViewById(R.id.city);
//        pincodeEt = findViewById(R.id.pincode);
//        cal_icon = findViewById(R.id.calendar_icon);
//        uploadPhotoBtn = findViewById(R.id.uploadPhoto);
//
//        uploadPhotoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if(checkPermission()){
//                        galleryImageCall();
//                    }
//                } else {
//                    galleryImageCall();
//                }
//            }
//        });
        return inflater.inflate(R.layout.fragment_first, container, false);


    }
//    public void setDate(View view)
//    {
//        EditProfileActivity editProfileActivity = new EditProfileActivity();
//        editProfileActivity.setDate(view);
//    }
}
