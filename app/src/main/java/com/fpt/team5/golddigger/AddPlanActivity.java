package com.fpt.team5.golddigger;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.fpt.team5.golddigger.Model.Plan;
import com.fpt.team5.golddigger.Model.Transaction;
import com.fpt.team5.golddigger.dal.MyDbContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPlanActivity extends AppCompatActivity {
    private NaviagtionBarFragment naviagtionBarFragment;
    private Button btnAdd;
    private EditText edtCreateDate;
    private EditText edtDueDate;
    private Calendar calendar;
    private EditText edtTitle;
    private EditText edtDescription;
    private EditText edtAmount;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private MyDbContext context;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NaviagtionBarFragment) {
            naviagtionBarFragment = ((NaviagtionBarFragment) fragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_plan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BindingView();
        setDefaultDateNow();
        BindingAction();
        InjectFragment();
    }

    private void setDefaultDateNow() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(new Date());
        String selectedDay = currentDate.substring(0, 2);
        String selectedMonth = currentDate.substring(3, 5);
        String selectedYear = currentDate.substring(6);

        edtCreateDate.setText(selectedDay + "/" + (selectedMonth) + "/" + selectedYear);
    }

    private void InjectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navBarFragment, naviagtionBarFragment)
                .commit();
    }

    private void BindingAction() {
        edtCreateDate.setOnClickListener(this::onDateTimePickerCreateClick);
        edtDueDate.setOnClickListener(this::onDateTimePickerDueClick);
        btnAdd.setOnClickListener(this::onBtnAddClick);
    }

    private void onBtnAddClick(View view) {
        if (edtAmount.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill amount fields", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtTitle.getText().toString();
            String description = edtDescription.getText().toString();
            double amount = Double.parseDouble(edtAmount.getText().toString());
            String createDate = edtCreateDate.getText().toString();
            String dueDate = edtDueDate.getText().toString();
            int userId = pref.getInt("userId", 0);

            if (title.isEmpty() || amount == 0 || createDate.isEmpty() || dueDate.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (checkDate(createDate, dueDate)) {
                    int status = getPlanStatus(amount, userId);
                    Plan plan = new Plan(title, description, amount, userId, status, createDate, dueDate);
                    if (context.addPlan(plan)) {
                        Toast.makeText(this, "Add successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, PlanActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "Add failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Due date must be after create date", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private int getPlanStatus(double amount, int userId) {
        Double balance = context.getBudgetAmountByUserId(userId);

        if (balance >= amount) {
            return 1;
        } else {
            return 0;
        }
    }

    private void BindingView() {
        if (naviagtionBarFragment == null) {
            naviagtionBarFragment = new NaviagtionBarFragment();
        }
        edtAmount = findViewById(R.id.edtAmount);
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        btnAdd = findViewById(R.id.btnAdd);
        edtCreateDate = findViewById(R.id.edtCreateDate);
        edtDueDate = findViewById(R.id.edtDueDate);
        calendar = Calendar.getInstance();
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
        context = new MyDbContext(this);
    }


    private void onDateTimePickerCreateClick(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddPlanActivity.this,
                (dateView, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    edtCreateDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void onDateTimePickerDueClick(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddPlanActivity.this,
                (dateView, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    edtDueDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private boolean checkDate(String createDate, String dueDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parse the date strings into Date objects
            Date create = dateFormat.parse(createDate);
            Date due = dateFormat.parse(dueDate);

            // Compare the dates
            return due.after(create);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception
            return false;
        }
    }
}
