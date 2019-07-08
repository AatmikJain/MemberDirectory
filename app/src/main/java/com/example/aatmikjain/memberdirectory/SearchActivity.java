/**
 * Author		:	VenomVendor
 * Dated		:	6 Dec, 2013 1:19:40 AM, IST.
 * Project		:	SimpleListView-CheckBox
 * Contact		:	info@VenomVendor.com
 * URL			:	https://www.google.co.in/search?q=VenomVendor
 * Copyright(c)	:	WTF.!
 **/

package com.example.aatmikjain.memberdirectory;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.SearchResultAdapter;
import Database.DatabaseHelper;
import Database.UserTable;

public class SearchActivity extends Activity {

    EditText editText;
    SearchView searchViewSv;
    String firstName, lastName, name;
    ArrayList<String> selected = new ArrayList<>();
    private ListView mListView;
    private static int count = 0;
    private static boolean isNotAdded = true;
    private CheckBox checkBox_header;
    final CustomAdapter adapter = new CustomAdapter(this);
    final static String[] textviewContent = {"Indore", "Bhopal", "Moradabad", "Vellore"};
    RecyclerView userRecyclerView;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    /**
     * To save checked items, and <b>re-add</b> while scrolling.
     */
    SparseBooleanArray mChecked = new SparseBooleanArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        userRecyclerView = findViewById(R.id.search_recycler_view);
        databaseHelper = DatabaseHelper.getInstance(this);

        searchViewSv = findViewById(R.id.search);
        searchViewSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String>  names = new ArrayList<>();
                userRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayout.VERTICAL, false));
                userRecyclerView.addItemDecoration(new DividerItemDecoration(SearchActivity.this, LinearLayout.VERTICAL));
                cursor = databaseHelper.getDataFromUser();
                if(cursor!=null)
                {
                    if(cursor.getCount()>0)
                    {
                        UserTable userTable = new UserTable();
                        cursor.moveToFirst();
                        do{
                            firstName = cursor.getString(cursor.getColumnIndex(userTable.getFirstName()));
                            lastName = cursor.getString(cursor.getColumnIndex(userTable.getLastName()));
                            if(firstName.contains(query) || lastName.contains(query))
                            {
                                names.add(firstName+lastName);
                            }
                        }while(cursor.moveToNext());
                    }
                    else
                        Toast.makeText(SearchActivity.this, "Empty Table", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        editText = findViewById(R.id.edit_text);
        editText.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_search_list, null);
                mListView = popupView.findViewById(R.id.list_view);
                if (isNotAdded) {
                    final View headerView = getLayoutInflater().inflate(R.layout.custom_list_view_header,
                            mListView, false);
                    checkBox_header = headerView.findViewById(R.id.checkBox_header);
                    checkBox_header.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < count; i++) {
                                mChecked.put(i, checkBox_header.isChecked());
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                    mListView.addHeaderView(headerView);

//            isNotAdded = false;
                }
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            Toast.makeText(getApplicationContext(),
                                    checkBox_header.getId() + "\n" + checkBox_header.isChecked(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            position = position - 1; // "-1" If Header is Added
                            Toast.makeText(getApplicationContext(),textviewContent[position] + "\n" + mChecked.get(position), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                editText.setText("");
                for(int i=0; i<count; i++)
                {
                    if(mChecked.get(i))
                    {
                        selected.add(textviewContent[i]);
                        editText.append(textviewContent[i]);
                    }
                }

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                int len = selected.size();
                Toast.makeText(SearchActivity.this,len+"", Toast.LENGTH_LONG).show();
                if(len>0) {
                    String[] selectedCitites = new String[len];
                    for(int i=0; i<len; i++)
                        selectedCitites[i] = selected.get(i);

                    cursor = databaseHelper.getDataFromUserByCity(selectedCitites);
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            UserTable userTable = new UserTable();
                            cursor.moveToFirst();
                            ArrayList<String> names = new ArrayList<>();
                            do {
                                firstName = cursor.getString(cursor.getColumnIndex(userTable.getFirstName()));
                                lastName = cursor.getString(cursor.getColumnIndex(userTable.getLastName()));
                                name = firstName + " " + lastName;
                                names.add(name);
                            } while (cursor.moveToNext());
                            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(names);
                            userRecyclerView.setAdapter(searchResultAdapter);
                        } else
                            Toast.makeText(SearchActivity.this, "Empty Education Table", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(SearchActivity.this, "null cursor", Toast.LENGTH_LONG).show();
                }

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });


    }

    public class CustomAdapter extends BaseAdapter {

        Activity sActivity;

        public CustomAdapter(final Activity mActivity) {
            this.sActivity = mActivity;
        }

        @Override
        public int getCount() {

            /*
             * Length of our listView
             */
            count = SearchActivity.textviewContent.length;
            return count;
        }

        @Override
        public Object getItem(int position) {

            /*
             * Current Item
             */
            return position;
        }

        @Override
        public long getItemId(int position) {

            /*
             * Current Item's ID
             */
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View mView = convertView;

            if (mView == null) {

                /*
                 * LayoutInflater
                 */
                final LayoutInflater sInflater = (LayoutInflater) sActivity.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

                /*
                 * Inflate Custom List View
                 */
                mView = sInflater.inflate(R.layout.custom_list_view, null, false);

            }

            /* **************CUSTOM LISTVIEW OBJECTS**************** */

            /*
             * DO NOT MISS TO ADD "mView"
             */
            final TextView sTV1 = mView.findViewById(R.id.textView);
            final ImageView sIMG = mView.findViewById(R.id.imageView);
            final CheckBox mCheckBox = mView.findViewById(R.id.checkBox);

            /* **************CUSTOM LISTVIEW OBJECTS**************** */

            /* **************ADDING CONTENTS**************** */
            sTV1.setText(SearchActivity.textviewContent[position]);
            sIMG.setImageResource(R.drawable.ic_account_circle_black_100dp);

            mCheckBox.setOnCheckedChangeListener(
                    new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {

                                /*
                                 * Saving Checked Position
                                 */
                                mChecked.put(position, isChecked);

                                /*
                                 * Find if all the check boxes are true
                                 */
                                if (isAllValuesChecked()) {

                                    /*
                                     * set HeaderCheck box to true
                                     */
                                    checkBox_header.setChecked(isChecked);
                                }

                            } else {

                                /*
                                 * Removed UnChecked Position
                                 */
                                mChecked.delete(position);

                                /*
                                 * Remove Checked in Header
                                 */
                                checkBox_header.setChecked(isChecked);

                            }

                        }
                    });

            /*
             * Set CheckBox "TRUE" or "FALSE" if mChecked == true
             */
            mCheckBox.setChecked((mChecked.get(position)));

            /* **************ADDING CONTENTS**************** */

            /*
             * Return View here
             */
            return mView;
        }

        /*
         * Find if all values are checked.
         */
        protected boolean isAllValuesChecked() {

            for (int i = 0; i < count; i++) {
                if (!mChecked.get(i)) {
                    return false;
                }
            }

            return true;
        }

    }
}