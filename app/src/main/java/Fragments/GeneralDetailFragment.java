package Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aatmikjain.memberdirectory.GalleryPickerUtil;
import com.example.aatmikjain.memberdirectory.OnLastEditChangeListener;
import com.example.aatmikjain.memberdirectory.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.DatabaseHelper;
import Database.EditLogTable;
import Database.UserTable;


public class GeneralDetailFragment extends Fragment implements View.OnClickListener{

    private Button saveChangesBtn;
    private EditText firstNameEt, lastNameEt, mobileNumberEt, branchEt, dobEt, cityEt, pincodeEt;
    private ImageView cal_icon, profilePhotoIv, galleryIconIv, camerIconIv;
    TextView lastEditTv;

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

    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    OnLastEditChangeListener onLastEditChangeListener;

    public GeneralDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_details, container, false);

        sharedPreferences = getActivity().getSharedPreferences("DIR", Context.MODE_PRIVATE);

        lastEditTv = view.findViewById(R.id.lastEdit);
        firstNameEt = view.findViewById(R.id.firstName);
        firstNameEt.setText(sharedPreferences.getString("firstName",""));
        lastNameEt = view.findViewById(R.id.lastName);
        lastNameEt.setText(sharedPreferences.getString("lastName",""));
        mobileNumberEt = view.findViewById(R.id.mobileNumber);
        mobileNumberEt.setText(sharedPreferences.getString("mobile",""));
        branchEt = view.findViewById(R.id.branch);
        branchEt.setText(sharedPreferences.getString("branch", ""));
        dobEt = view.findViewById(R.id.dob);
        dobEt.setText(sharedPreferences.getString("dob",""));
        cityEt = view.findViewById(R.id.city);
        cityEt.setText(sharedPreferences.getString("city",""));
        pincodeEt = view.findViewById(R.id.pincode);
        pincodeEt.setText(sharedPreferences.getString("pincode",""));

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        cal_icon = view.findViewById(R.id.calendar_icon);
        cal_icon.setOnClickListener(this);

        profilePhotoIv = view.findViewById(R.id.profilePhoto);
        saveChangesBtn = view.findViewById(R.id.saveChanges);
        saveChangesBtn.setOnClickListener(this);

        galleryIconIv = view.findViewById(R.id.galleryIcon);
        galleryIconIv.setOnClickListener(new View.OnClickListener() {
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
        camerIconIv = view.findViewById(R.id.cameraIcon);
        camerIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkPermission()){
                        cameraImageCall();
                    }
                } else {
                    cameraImageCall();
                }
            }
        });
        databaseHelper = DatabaseHelper.getInstance(getContext());
        return view;

    }
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.calendar_icon) {
            DatePickerDialog date =  new DatePickerDialog(getActivity(), myDateListener, year, month, day);
            date.show();
        }
        if(v.getId()==R.id.saveChanges)
        {
            String currentDateTimeString = getDateTime();
            UserTable inputData = new UserTable(
                    firstNameEt.getText().toString(),
                    lastNameEt.getText().toString(),
                    sharedPreferences.getString("email",""),
                    mobileNumberEt.getText().toString(),
                    branchEt.getText().toString(),
                    cityEt.getText().toString(),
                    pincodeEt.getText().toString(),
                    dobEt.getText().toString(),
                    currentDateTimeString
            );
            if(databaseHelper.updateUserData(inputData)) {
                if(databaseHelper.addEditLog(new EditLogTable(sharedPreferences.getString("email",""), currentDateTimeString))) {

                    onLastEditChangeListener.updateLastEdit(currentDateTimeString);
                    Toast.makeText(getContext(), "Update successful", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "Edit Log Failed", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void galleryImageCall() {
        GalleryPickerUtil.launchGallery(GeneralDetailFragment.this);
    }
    private void cameraImageCall(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT > 23) {
            mImageFile = GalleryPickerUtil.createTempFile();
            mImageUri = FileProvider.getUriForFile(
                    getContext(),
                    getActivity().getPackageName() + ".my.package.name.provider",
                    mImageFile
            );
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(
                    android.provider.MediaStore.EXTRA_OUTPUT,
                    mImageUri
            );
            startActivityForResult(intent, GalleryPickerUtil.CAPTURE_PHOTO);
        } else {
            startActivityForResult(intent, GalleryPickerUtil.CAPTURE_PHOTO);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) +
                ContextCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                            getActivity(),
                            Manifest.permission.CAMERA
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                            getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(
                        getActivity().findViewById(android.R.id.content),
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean writeExternalFile = grantResults[2] == PackageManager.PERMISSION_GRANTED;

            if (cameraPermission && readExternalFile && writeExternalFile) {
               /* if (isFromCamera) {
                    cameraImageCall(activity!!)
                } else if (isFromGallery) {
                    galleryImageCall(activity!!)
                }*/
            } else {
                Snackbar.make(
                        getActivity().getWindow().getDecorView(),
                        "Please Grant Permissions to work with camera and gallery.",
                        Snackbar.LENGTH_INDEFINITE
                ).setAction("ENABLE",
                        new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(View view) {
                                requestPermissions(
                                        stringsPermission
                                        ,
                                        PERMISSIONS_MULTIPLE_REQUEST
                                );
                            }

                        }).show();
            }
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0,
                              int arg1, int arg2, int arg3) {
            year = arg1;
            month = arg2+1;
            day = arg3;
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dobEt.setText(day+"/"+month+"/"+year);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity)context;
        try{
            onLastEditChangeListener = (OnLastEditChangeListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" must override OnLastEditListener");
        }
    }
}