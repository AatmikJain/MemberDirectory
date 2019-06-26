package com.example.aatmikjain.memberdirectory;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.File;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity{

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        profilePhotoIv = findViewById(R.id.profilePhoto);
        saveChangesBtn = findViewById(R.id.saveChanges);
        firstNameEt = findViewById(R.id.firstName);
        lastNameEt = findViewById(R.id.lastName);
        mobileNumberEt = findViewById(R.id.mobileNumber);
        dobEt = findViewById(R.id.dob);
        cityEt = findViewById(R.id.city);
        pincodeEt = findViewById(R.id.pincode);
        cal_icon = findViewById(R.id.calendar_icon);
        uploadPhotoBtn = findViewById(R.id.uploadPhoto);

        uploadPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkPermission()){
                        galleryImageCall();
                    }
                } else {
                    galleryImageCall();
                }

            }
        });
    }

    private void galleryImageCall() {
        GalleryPickerUtil.launchGallery(EditProfileActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPickerUtil.GALLERY_PHOTO){
            if(resultCode== Activity.RESULT_OK){
                profilePhotoIv.setImageURI(data.getData());
            }
        }else if(requestCode==GalleryPickerUtil.CAPTURE_PHOTO){
            if(resultCode==Activity.RESULT_OK){
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath(),bmOptions);
                bitmap = Bitmap.createScaledBitmap(bitmap,100,100,true);
                profilePhotoIv.setImageBitmap(bitmap);

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) +
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.CAMERA
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(
                        this.findViewById(android.R.id.content),
                        "Please Grant Permissions to  Application for using camera and gallery",
                        Snackbar.LENGTH_INDEFINITE
                ).setAction("ENABLE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                requestPermissions(
                                        stringsPermission
                                        ,
                                        PERMISSIONS_MULTIPLE_REQUEST
                                );
                            }
                        }
                ).show();
            } else {
                requestPermissions(
                        stringsPermission
                        ,
                        PERMISSIONS_MULTIPLE_REQUEST
                );
            }
        } else {

            return true;
            /*if (isFromCamera) {
                cameraImageCall(activity!!)
            } else if (isFromGallery) {
                galleryImageCall(activity!!)
            }*/
        }

        return false;
    }



    public void setDate(View view) {
        showDialog(view.getId());
//        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == R.id.calendar_icon) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
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

    private void showDate(int year, int month, int day) {
        dobEt.setText(day+"/"+month+"/"+year);
    }
}
