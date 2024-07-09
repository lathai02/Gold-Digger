package com.fpt.team5.golddigger;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class TransactionActivity extends AppCompatActivity {
    private NaviagtionBarFragment navigationBarFragment;
    private TextView headerCategoryTv;
    private TextView headerSubCategoryTv;
    private String subCategory;
    private String category;
    private EditText dateTimePickerCreate;
    private EditText dateTimePickerDue;
    private Calendar calendar;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NaviagtionBarFragment) {
            navigationBarFragment = ((NaviagtionBarFragment) fragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        BindingView();
        onReceiveIntent();
        BindingAction();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, navigationBarFragment)
                .commit();
    }

    private void SetValues() {
        headerCategoryTv.setText(getIntent().getStringExtra("category"));
        headerSubCategoryTv.setText(getIntent().getStringExtra("category"));
    }

    private void BindingAction() {
        dateTimePickerCreate.setOnClickListener(this::onDateTimePickerCreateClick);
        dateTimePickerDue.setOnClickListener(this::onDateTimePickerDueClick);
    }

    private void onDateTimePickerCreateClick(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TransactionActivity.this,
                (dateView, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    dateTimePickerCreate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void onDateTimePickerDueClick(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TransactionActivity.this,
                (dateView, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    dateTimePickerDue.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void BindingView() {
        if (navigationBarFragment == null) {
            navigationBarFragment = new NaviagtionBarFragment();
        }
        headerCategoryTv = findViewById(R.id.headerCategoryTv);
        headerSubCategoryTv = findViewById(R.id.headerSubCategoryTv);
        dateTimePickerCreate = findViewById(R.id.dateTimePickerCreate);
        dateTimePickerDue = findViewById(R.id.dateTimePickerDue);
        calendar = Calendar.getInstance();
    }

    private void onReceiveIntent() {
        Intent i = getIntent();
        subCategory = i.getStringExtra("subCategory");
        category = i.getStringExtra("category");

        headerCategoryTv.setText(category);
        headerSubCategoryTv.setText(subCategory);
    }
}
