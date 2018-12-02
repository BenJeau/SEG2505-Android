package com.seg2505.project.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.seg2505.project.R;
import com.seg2505.project.fragments.TimePickerFragment;
import com.seg2505.project.adapters.OwnerHelper;
import com.seg2505.project.adapters.OwnerHomeAdapter;
import com.seg2505.project.model.Availability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OwnerActivity extends AppCompatActivity {

    private ConstraintLayout searchFiltersLayout;
    private CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private TextView time;
    private RatingBar rating;
    private View blackOverlay;
    private SearchView searchView;
    private OwnerHomeAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        final DialogFragment newFragment = new TimePickerFragment();

        searchFiltersLayout = findViewById(R.id.searchFilters);

        monday = findViewById(R.id.mondayCheck);
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryChange("");
            }
        });

        tuesday = findViewById(R.id.tuesdayCheck);
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryChange("");
            }
        });

        wednesday = findViewById(R.id.wednesdayCheck);
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryChange("");
            }
        });

        thursday = findViewById(R.id.thursdayCheck);
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryChange("");
            }
        });

        friday = findViewById(R.id.fridayCheck);
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryChange("");
            }
        });

        saturday = findViewById(R.id.saturdayCheck);
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryChange("");
            }
        });

        sunday = findViewById(R.id.sundayCheck);
        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryChange("");
            }
        });

        time = findViewById(R.id.timeContent);

        Button setTimeButton = findViewById(R.id.setTimeButton);
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setText("");
                onQueryChange("");
            }
        });

        blackOverlay = findViewById(R.id.black_overlay);
        blackOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchFiltersLayout.getVisibility() != View.GONE) {
                    removeFilter();
                }
            }
        });

        rating = findViewById(R.id.ratingContent);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                onQueryChange("");
            }
        });

        populateServiceRecyclerView();
    }

    public void setSearchText(String s) {
        time.setText(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get menu search icon and create a SearchView
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        final MenuItem filterItem = menu.findItem(R.id.menu_filter);

        searchView = (SearchView) menuItem.getActionView();

        // Listens when SearchView field is changing to filter the products
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                onQueryChange(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                onQueryChange(s);
                return false;
            }
        });

        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (searchFiltersLayout.getVisibility() == View.GONE) {
                    putFilter();
                } else {
                    removeFilter();
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
                if (searchFiltersLayout.getVisibility() != View.GONE) {
                    removeFilter();
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void removeFilter() {
        searchFiltersLayout.setVisibility(View.GONE);
        blackOverlay.setVisibility(View.GONE);
    }

    private void putFilter() {
        searchFiltersLayout.setVisibility(View.VISIBLE);
        blackOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (searchFiltersLayout.getVisibility() != View.GONE) {
            removeFilter();
        } else if (!searchView.isIconified()){
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    private void populateServiceRecyclerView() {

        // Gets the recycler view and sets it to have a dataset that may vary in size
        recyclerView = findViewById(R.id.ownerRecyclerView);
        recyclerView.setHasFixedSize(false);

        // Uses a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OwnerActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
        adapter = new OwnerHomeAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    public void onQueryChange(String s) {
        ArrayList<OwnerHelper> filteredList = filter(adapter.getDataset(), s);
        adapter.replaceAll(filteredList);
        recyclerView.scrollToPosition(0);
    }

    private ArrayList<OwnerHelper> filter(List<OwnerHelper> oHelpers, String search) {
        String lowerCaseQuery = search.toLowerCase();

        ArrayList<OwnerHelper> list = new ArrayList<>();

        if (oHelpers.isEmpty()) {
            return list;
        }

        boolean filterWeekdays = monday.isChecked() || tuesday.isChecked() ||
                wednesday.isChecked() || thursday.isChecked() || friday.isChecked() ||
                saturday.isChecked() || sunday.isChecked();

        boolean filterTime = time.getText().toString().equals("");

        boolean filterRating = rating.getRating() > 0.001f;

        boolean filterName = lowerCaseQuery.length() != 0;


        for (OwnerHelper oHelper : oHelpers) {
            if (filterWeekdays) {
                if (monday.isChecked() && !oHelper.getWeekdays().toLowerCase().contains("mon") ||
                        tuesday.isChecked() && !oHelper.getWeekdays().toLowerCase().contains("tue") ||
                        wednesday.isChecked() && !oHelper.getWeekdays().toLowerCase().contains("wed") ||
                        thursday.isChecked() && !oHelper.getWeekdays().toLowerCase().contains("thu") ||
                        friday.isChecked() && !oHelper.getWeekdays().toLowerCase().contains("fri") ||
                        saturday.isChecked() && !oHelper.getWeekdays().toLowerCase().contains("sat") ||
                        sunday.isChecked() && !oHelper.getWeekdays().toLowerCase().contains("sun")) {
                    continue;
                }
            }

            if (filterTime) {
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                boolean hasTime = false;

                if (filterWeekdays) {

                    ArrayList<String> dates = new ArrayList<String>();
                    if (monday.isChecked()) {
                        dates.add("mon");
                    }

                    if (tuesday.isChecked()) {
                        dates.add("tue");
                    }

                    if (wednesday.isChecked()) {
                        dates.add("wed");
                    }

                    if (thursday.isChecked()) {
                        dates.add("thu");
                    }

                    if (friday.isChecked()) {
                        dates.add("fri");
                    }

                    if (saturday.isChecked()) {
                        dates.add("sat");
                    }

                    if (sunday.isChecked()) {
                        dates.add("sun");
                    }

                    for (Availability availability : oHelper.getAvailabilities()) {
                        String[] times = availability.getTime().split(" to ");

                        try {
                            Date ten = parser.parse(times[0]);
                            Date eighteen = parser.parse(times[1]);
                            Date userDate = parser.parse(time.getText().toString());
                            if (userDate.after(ten) && userDate.before(eighteen) && checkStringInArrayList(availability.getDay(), dates)) {
                                hasTime = true;
                                break;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    for (Availability availability : oHelper.getAvailabilities()) {
                        String[] times = availability.getTime().split(" to ");

                        try {
                            Date ten = parser.parse(times[0]);
                            Date eighteen = parser.parse(times[1]);
                            Date userDate = parser.parse(time.getText().toString());
                            if (userDate.after(ten) && userDate.before(eighteen)) {
                                hasTime = true;
                                break;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!hasTime) {
                    continue;
                }
            }

            if (filterName) {
                if (!oHelper.getServiceName().toLowerCase().contains(lowerCaseQuery)) {
                    continue;
                }
            }

            if (filterRating) {
                if (!oHelper.getProviderRating().equals("N.A.") && rating.getRating() < Double.parseDouble(oHelper.getProviderRating())) {
                    continue;
                }
            }

            list.add(oHelper);
        }

        return list;
    }

    public boolean checkStringInArrayList(String text, ArrayList<String> list) {
        boolean temp = false;

        for (String i : list) {
            if (text.toLowerCase().contains(i)){
                temp = true;
                break;
            }
        }

        return temp;
    }

}

