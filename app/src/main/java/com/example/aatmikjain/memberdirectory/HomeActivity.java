package com.example.aatmikjain.memberdirectory;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import Adapters.NotificationAdapter;
import Database.DatabaseHelper;
import Database.EditLogTable;
import Database.NotificationTable;

import static android.os.Build.VERSION_CODES.M;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nameTv, emailTv;
    SearchView searchSv;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    ArrayList<EditLogTable> editLogTables = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        searchSv = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        SharedPreferences sp = getSharedPreferences("DIR", Context.MODE_PRIVATE);
        databaseHelper = DatabaseHelper.getInstance(this);
        cursor = databaseHelper.getNotification();

        //search view
        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<ArrayList<String>>  notificationData = new ArrayList<>();
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayout.VERTICAL, false));
                recyclerView.addItemDecoration(new DividerItemDecoration(HomeActivity.this, LinearLayout.VERTICAL));
                cursor = databaseHelper.getNotification();
                if(cursor!=null)
                {
                    if(cursor.getCount()>0)
                    {
                        NotificationTable keys = new NotificationTable();
                        String title, summary, startDate, desc;
                        cursor.moveToFirst();
                        do{
                            ArrayList<String> notification = new ArrayList<>();
                            title = cursor.getString(cursor.getColumnIndex(keys.getTitle()));
                            summary = cursor.getString(cursor.getColumnIndex(keys.getSummary()));
                            startDate = cursor.getString(cursor.getColumnIndex(keys.getStartDate()));
                            desc = cursor.getString(cursor.getColumnIndex(keys.getDescription()));
                            notification.add(title);
                            notification.add(summary);
                            notification.add(startDate);
                            notification.add(desc);
                            if(title.contains(query) || desc.contains(query))
                            {
                                notificationData.add(notification);
                            }
                        }while(cursor.moveToNext());
                        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationData, HomeActivity.this);
                        recyclerView.setAdapter(notificationAdapter);
                    }
                    else
                        Toast.makeText(HomeActivity.this, "Empty Table", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Edit Log Table
        cursor = databaseHelper.getEditLog();
        if(cursor!=null)
        {
            if(cursor.getCount()>0)
            {
                EditLogTable keys = new EditLogTable();
                String email, lastEdit;
                cursor.moveToFirst();
                do{
                    email = cursor.getString(cursor.getColumnIndex(keys.getEmail()));
                    lastEdit = cursor.getString(cursor.getColumnIndex(keys.getLastEdit()));
                    editLogTables.add(new EditLogTable(email, lastEdit));
                }while(cursor.moveToNext());
            }
        }

        nameTv = header.findViewById(R.id.name);
        emailTv = header.findViewById(R.id.email);
        nameTv.setText(sp.getString("firstName",""));
        emailTv.setText(sp.getString("email",""));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_profile) {
            Intent intent = new Intent(this, MyProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_editProfile) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_notifications) {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_generate_pdf) {
            callPdf();
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callPdf(){
        String fileName=System.currentTimeMillis()+"sgsits.pdf";
        new MyPdf().write(this,fileName,editLogTables);

        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);

        try {
            if (Build.VERSION.SDK_INT > M) {
                try {
                    Uri photoURI = FileProvider.getUriForFile(
                            this,
                            getApplicationContext().getPackageName() +".my.package.name.provider",
                            new File( "/sdcard/Download/"+fileName)
                    );
                    pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pdfOpenintent.setDataAndType(
                            photoURI,
                            "application/pdf"
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                pdfOpenintent.setDataAndType(
                        Uri.parse("file://" + "/sdcard/Download"+"/" + fileName),
                        "application/pdf"
                );

            }
            startActivity(pdfOpenintent);
        } catch (ActivityNotFoundException e) {

        }
    }
}