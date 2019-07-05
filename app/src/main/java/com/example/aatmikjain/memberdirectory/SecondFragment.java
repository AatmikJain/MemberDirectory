package com.example.aatmikjain.memberdirectory;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Tables.EditLogTable;
import Tables.EducationalDetailsTable;

public class SecondFragment extends Fragment implements View.OnClickListener{

    Button addBtn;
    EditText instituteNameEt, degreeEt, boardUniversityEt, fromEt, toEt, resultEt;
    ImageView fromIconIv, toIconIv;
    private Calendar calendar;
    private int year, month, day;
    boolean dateFlag;
    DatePickerDialog date;

    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    OnLastEditChangeListener onLastEditChangeListener;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second, container, false);

        sharedPreferences = getActivity().getSharedPreferences("DIR", Context.MODE_PRIVATE);

        instituteNameEt = view.findViewById(R.id.instituteName);
        degreeEt = view.findViewById(R.id.degree);
        boardUniversityEt = view.findViewById(R.id.boardUniversity);
        fromEt = view.findViewById(R.id.from);
        toEt = view.findViewById(R.id.to);
        fromIconIv = view.findViewById(R.id.from_calendar_icon);
        fromIconIv.setOnClickListener(this);
        toIconIv = view.findViewById(R.id.to_calendar_icon);
        toIconIv.setOnClickListener(this);
        resultEt = view.findViewById(R.id.result);
        addBtn = view.findViewById(R.id.add);
        addBtn.setOnClickListener(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = new DatePickerDialog(getActivity(), myDateListener, year, month, day);

        databaseHelper = DatabaseHelper.getInstance(getActivity());
        return view;
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
            fromEt.setText(day+"/"+month+"/"+year);
        if(!dateFlag)
            toEt.setText(day+"/"+month+"/"+year);
        dateFlag = !dateFlag;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.from_calendar_icon)
        {
            dateFlag=true;
            date.show();
        }
        if(v.getId()==R.id.to_calendar_icon)
        {
            dateFlag=false;
            date.show();
        }
        if(v.getId()==R.id.add)
        {
            String currentDateTimeString = getDateTime();
            EducationalDetailsTable educationalDetailsTable = new EducationalDetailsTable(
                    sharedPreferences.getString("email",""),
                    instituteNameEt.getText().toString(),
                    degreeEt.getText().toString(),
                    boardUniversityEt.getText().toString(),
                    fromEt.getText().toString(),
                    toEt.getText().toString(),
                    resultEt.getText().toString()
            );
            if(databaseHelper.addEducationalDetails(educationalDetailsTable))
            {
                if(databaseHelper.addEditLog(new EditLogTable(sharedPreferences.getString("email",""), currentDateTimeString))) {
                    Toast.makeText(getActivity(), "Details added successfully", Toast.LENGTH_LONG).show();
                    onLastEditChangeListener.updateLastEdit(currentDateTimeString);
                    /*instituteNameEt.setText("");
                    degreeEt.setText("");
                    boardUniversityEt.setText("");
                    fromEt.setText("");
                    toEt.setText("");
                    resultEt.setText("");*/
//                    Intent intent = new Intent(getContext(), ThirdFragment.class);
//                    startActivity(intent);
                }
                else
                    Toast.makeText(getActivity(), "Edit Log failed", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
        }
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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
