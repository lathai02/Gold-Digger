package com.fpt.team5.golddigger;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class PlanDetailActivity extends AppCompatActivity {
    private NaviagtionBarFragment navigationBarFragment;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private EditText edtTitle;
    private EditText edtAmount;
    private EditText edtCreateDate;
    private EditText edtDueDate;
    private EditText edtDescription;
    private int planId;
    private MyDbContext context;
    private Button btnSave;
    private Button btnDelete;
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
        setContentView(R.layout.activity_plan_detail);
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

    private void onDateTimePickerCreateClick(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                PlanDetailActivity.this,
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
                PlanDetailActivity.this,
                (dateView, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    edtDueDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void BindingAction() {
        btnSave.setOnClickListener(this::onBtnSaveClick);
        edtCreateDate.setOnClickListener(this::onDateTimePickerCreateClick);
        edtDueDate.setOnClickListener(this::onDateTimePickerDueClick);
        btnDelete.setOnClickListener(this::onBtnDeleteClick);
    }

    private void onBtnDeleteClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this plan?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the deletion action
                        deletePlan();
                    }
                })
                .setNegativeButton(android.R.string.no, null) // Do nothing on "No" button click
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deletePlan() {
        // Assuming you have the transaction ID to delete
        int userId = pref.getInt("userId", 0);
        if (context.deletePlan(planId)) {
            Intent i = new Intent(this, PlanActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Delete failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onReceiveIntent() {
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String createDate = i.getStringExtra("createDate");
        String dueDate = i.getStringExtra("dueDate");
        String description = i.getStringExtra("description");
        String amount = i.getStringExtra("amount");
        planId = i.getIntExtra("id", 0);

        edtTitle.setText(title);
        edtAmount.setText(amount);
        edtCreateDate.setText(createDate);
        edtDueDate.setText(dueDate);
        edtDescription.setText(description);
    }

    private void BindingView() {
        if (navigationBarFragment == null) {
            navigationBarFragment = new NaviagtionBarFragment();
        }
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
        edtTitle = findViewById(R.id.edtTitle);
        context = new MyDbContext(this);
        edtAmount = findViewById(R.id.edtAmount);
        btnDelete = findViewById(R.id.btnDelete);
        edtCreateDate = findViewById(R.id.edtCreateDate);
        calendar = Calendar.getInstance();
        edtDueDate = findViewById(R.id.edtDueDate);
        edtDescription = findViewById(R.id.edtDescription);
        btnSave = findViewById(R.id.btnSave);
    }

    private void onBtnSaveClick(View view) {
        if (edtAmount.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill amount fields", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtTitle.getText().toString();
            String description = edtDescription.getText().toString();
            double amount = Double.parseDouble(edtAmount.getText().toString());
            String createDate = edtCreateDate.getText().toString();
            String dueDate = edtDueDate.getText().toString();
            int userId = pref.getInt("userId", 0);

            if (title.isEmpty() || amount == 0 || createDate.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (checkDate(createDate, dueDate)) {
                    int status = getPlanStatus(amount, userId);
                    Plan plan = new Plan(title, description, amount, userId, status, createDate, dueDate);
                    if (context.updateStatus(plan, planId)) {
                        Toast.makeText(this, "Save successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, PlanActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "Save failed!", Toast.LENGTH_SHORT).show();
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
