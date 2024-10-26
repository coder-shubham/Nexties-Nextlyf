package com.globalhackathon.nextlyf.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.UserIntroBasicBinding;
import com.globalhackathon.nextlyf.model.UserSignUpData;

import java.util.Calendar;

public class UserIntroBasicFragment extends Fragment {

    UserIntroBasicBinding userIntroBasicBinding;

    UserSignUpData userSignUpData;

    Calendar calendar;

    private Handler handler = new Handler();

    private Runnable runnable;


    public UserIntroBasicFragment(UserSignUpData userSignUpData) {
        this.userSignUpData = userSignUpData;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userIntroBasicBinding = UserIntroBasicBinding.inflate(inflater);

        calendar = Calendar.getInstance();

        userIntroBasicBinding.userIntroName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                runnable = () ->{
                    userIntroBasicBinding.userIntroName.removeTextChangedListener(this);

                    // Preserve cursor position
                    int cursorPosition = userIntroBasicBinding.userIntroName.getSelectionStart();

                    userIntroBasicBinding.userIntroName.setText(editable.toString());

                    userSignUpData.setName(editable.toString());

                    userIntroBasicBinding.userIntroName.setSelection(cursorPosition);

                    userIntroBasicBinding.userIntroName.addTextChangedListener(this);
                };
                // Delay of 300ms to allow user to stop typing
                handler.postDelayed(runnable, 300);

//                userIntroBasicBinding.userIntroName.setText(editable.toString());
            }
        });

        userIntroBasicBinding.userIntroDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // Initialize AutoCompleteTextView
        AutoCompleteTextView gender = userIntroBasicBinding.userIntroGender;
        AutoCompleteTextView language = userIntroBasicBinding.userIntroLanguage;
        AutoCompleteTextView country = userIntroBasicBinding.userIntroOriginCountry;
        AutoCompleteTextView city = userIntroBasicBinding.userIntroOriginCity;


        // Define a list of items for the dropdown
        String[] items = {"Male", "Female", "Trans", "Don't want to disclose"};
        String[] languages = {};

        // Set up ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items);

        // Set the adapter to the AutoCompleteTextView
        gender.setAdapter(adapter);

        // Set an item click listener to detect item selection from the dropdown
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = (String) parent.getItemAtPosition(position);

                userIntroBasicBinding.userIntroGender.setText(selectedItem);
                userSignUpData.setGender(selectedItem);
            }
        });

        language.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = (String) parent.getItemAtPosition(position);

                language.setText(selectedItem);
                userSignUpData.setLanguage(selectedItem);
            }
        });

        country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = (String) parent.getItemAtPosition(position);

                country.setText(selectedItem);
                userSignUpData.setCountry(selectedItem);

                int cityArrayId = 0;
                switch (selectedItem){
                        case "United States":
                            cityArrayId = R.array.us_city_list;
                            break;
                        case "Canada":
                            cityArrayId = R.array.canada_city_list;
                            break;
                        case "United Kingdom":
                            cityArrayId = R.array.uk_city_list;
                            break;
                        case "Australia":
                            cityArrayId = R.array.australia_city_list;
                            break;
                        case "India":
                            cityArrayId = R.array.india_city_list;
                            break;
                        case "Germany":
                            cityArrayId = R.array.germany_city_list;
                            break;
                        case "France":
                            cityArrayId = R.array.france_city_list;
                            break;
                        case "China":
                            cityArrayId = R.array.china_city_list;
                            break;
                        case "Japan":
                            cityArrayId = R.array.japan_city_list;
                            break;
                        case "Singapore":
                            cityArrayId = R.array.singapore_city_list;
                            break;
                        case "South Korea":
                            cityArrayId = R.array.south_korea_city_list;
                            break;

                        // Add more cases for other countries

                }

                if(cityArrayId != 0){
                    String[] cityItems = getResources().getStringArray(cityArrayId);
                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, cityItems);
                    city.setAdapter(cityAdapter);
                }

                // Set up ArrayAdapter

            }
        });

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = (String) parent.getItemAtPosition(position);

                city.setText(selectedItem);
                userSignUpData.setCity(selectedItem);
            }
        });

        userIntroBasicBinding.userIntroPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                runnable = () ->{
                    userIntroBasicBinding.userIntroPhone.removeTextChangedListener(this);

                    // Preserve cursor position
                    int cursorPosition = userIntroBasicBinding.userIntroPhone.getSelectionStart();

                    userIntroBasicBinding.userIntroPhone.setText(editable.toString());

                    userSignUpData.setMobile(editable.toString());

                    userIntroBasicBinding.userIntroPhone.setSelection(cursorPosition);

                    userIntroBasicBinding.userIntroPhone.addTextChangedListener(this);
                };
                // Delay of 300ms to allow user to stop typing
                handler.postDelayed(runnable, 300);
            }
        });



        userIntroBasicBinding.userIntroReferralCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                runnable = () ->{
                    userIntroBasicBinding.userIntroReferralCode.removeTextChangedListener(this);

                    // Preserve cursor position
                    int cursorPosition = userIntroBasicBinding.userIntroReferralCode.getSelectionStart();

                    userIntroBasicBinding.userIntroReferralCode.setText(editable.toString());

                    userSignUpData.setReferralCode(editable.toString());

                    userIntroBasicBinding.userIntroReferralCode.setSelection(cursorPosition);

                    userIntroBasicBinding.userIntroReferralCode.addTextChangedListener(this);
                };
                // Delay of 300ms to allow user to stop typing
                handler.postDelayed(runnable, 300);

            }
        });


        return userIntroBasicBinding.getRoot();
    }


    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date and set it in the EditText
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    userIntroBasicBinding.userIntroDob.setText(selectedDate);
                    userSignUpData.setDob(selectedDate);
                }, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // Show the DatePickerDialog
        datePickerDialog.show();

    }
}
