package com.seg2505.project.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.seg2505.project.R;
import com.seg2505.project.TimePickerFragment;
import com.seg2505.project.adapters.OwnerHomeAdapter;

import java.util.Calendar;
import java.util.zip.Inflater;

public class OwnerActivity extends AppCompatActivity {

    private EditText searchBox;
    private ConstraintLayout searchFiltersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        searchFiltersLayout = findViewById(R.id.searchFilters);
        populateServiceRecyclerView();

        /*
        searchBox = findViewById(R.id.searchBox);
        final Spinner spinner = findViewById(R.id.searchType);

        final DialogFragment newFragment = new TimePickerFragment();

        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem().equals("Time")) {
                    newFragment.show(getSupportFragmentManager(), "timePicker");
                }
            }
        });

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //performSearch();
                    return true;
                }
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    searchBox.setFocusable(true);
                    searchBox.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (position == 1) {
                    searchBox.setFocusable(false);
                    newFragment.show(getSupportFragmentManager(), "timePicker");
                } else {
                    searchBox.setFocusable(true);
                    searchBox.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void setSearchText(String text) {
        searchBox.setText(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get menu search icon and create a SearchView
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        final MenuItem filterItem = menu.findItem(R.id.menu_filter);

        SearchView searchView = (SearchView) menuItem.getActionView();

        // Listens when SearchView field is changing to filter the products
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // onQueryChange(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //onQueryChange(s);
                return false;
            }
        });

        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (searchFiltersLayout.getVisibility() == View.GONE) {
                    searchFiltersLayout.setVisibility(View.VISIBLE);
                } else {
                    searchFiltersLayout.setVisibility(View.GONE);
                }
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterItem.setVisible(true);
            }
        });

        // Listens when SearchView is closed with the 'X'
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                filterItem.setVisible(false);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void populateServiceRecyclerView() {

        // Gets the recycler view and sets it to have a dataset that may vary in size
        RecyclerView recyclerView = findViewById(R.id.ownerRecyclerView);
        recyclerView.setHasFixedSize(false);

        // Uses a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OwnerActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
        OwnerHomeAdapter serviceAdapter = new OwnerHomeAdapter();
        recyclerView.setAdapter(serviceAdapter);
    }
}

