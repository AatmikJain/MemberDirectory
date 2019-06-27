package com.example.aatmikjain.memberdirectory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search);
    }
}
