package com.example.aatmikjain.memberdirectory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

import Adapters.NotificationAdapter;
import Tables.NotificationTable;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nameTv, emailTv;
    SearchView searchSv;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButtonFab;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        databaseHelper = DatabaseHelper.getInstance(this);
        cursor = databaseHelper.getNotification();
        searchSv = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButtonFab = findViewById(R.id.fab);
        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<ArrayList<String>>  notificationData = new ArrayList<>();
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayout.VERTICAL, false));
                recyclerView.addItemDecoration(new DividerItemDecoration(HomeActivity.this, LinearLayout.VERTICAL));
                if(cursor!=null)
                {
                    if(cursor.getCount()>0)
                    {
                        NotificationTable keys = new NotificationTable();
                        String title, summary, domain, desc;
                        cursor.moveToFirst();
                        do{
                            ArrayList<String> notification = new ArrayList<>();
                            title = cursor.getString(cursor.getColumnIndex(keys.getTitle()));
                            domain = cursor.getString(cursor.getColumnIndex(keys.getDomain()));
                            summary = cursor.getString(cursor.getColumnIndex(keys.getSummary()));
                            desc = cursor.getString(cursor.getColumnIndex(keys.getDescription()));
                            notification.add(title);
                            notification.add(domain);
                            notification.add(summary);
                            notification.add(cursor.getString(3));
                            notification.add(cursor.getString(4));
                            notification.add(desc);
                            if(title.contains(query) || desc.contains(query))
                            {
                                notificationData.add(notification);
                            }
                        }while(cursor.moveToNext());
//                        Toast.makeText(HomeActivity.this, notificationData.get(0).get(0), Toast.LENGTH_LONG).show();
                        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationData);
                        recyclerView.setAdapter(notificationAdapter);
//                        Toast.makeText(HomeActivity.this, notificationAdapter.getItemCount() + "", Toast.LENGTH_LONG).show();
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

        nameTv = header.findViewById(R.id.name);
        emailTv = header.findViewById(R.id.email);
        SharedPreferences sp = getSharedPreferences("DIR", Context.MODE_PRIVATE);
        nameTv.setText(sp.getString("firstName",""));
        emailTv.setText(sp.getString("email",""));

//        email = findViewById(R.id.emailTv);
//        String str = sp.getString("email", "");
////        email.setText(str.toCharArray(), 0, str.length());

        /*recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        ArrayList<ArrayList<String>>  notificationData = new ArrayList<>();
        databaseHelper = DatabaseHelper.getInstance(this);
        Cursor cursor = databaseHelper.getNotification();
            cursor.moveToFirst();
            do {
                ArrayList<String> notification = new ArrayList<>();
                notification.add(cursor.getString(0));
                notification.add(cursor.getString(1));
                notification.add(cursor.getString(2));
                notification.add(cursor.getString(3));
                notification.add(cursor.getString(4));
                notificationData.add(notification);
            } while (cursor.moveToNext());

            NotificationAdapter notificationAdapter = new NotificationAdapter(notificationData);
            recyclerView.setAdapter(notificationAdapter);
            Toast.makeText(this, notificationAdapter.getItemCount() + "", Toast.LENGTH_LONG).show();*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_settings) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
