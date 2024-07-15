package com.fpt.team5.golddigger;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.List;

public class TransactionActivity extends AppCompatActivity {
    private NaviagtionBarFragment navigationBarFragment;
    private TextView headerCategoryTv;
    private TextView headerSubCategoryTv;
    private String subCategory;
    private String category;
    private int UserId;
    private EditText edtTitle;
    private EditText edtDescription;
    private EditText edtAmount;
    private Button btnAdd;
    private EditText dateTimePickerCreate;
    private EditText dateTimePickerDue;
    private Calendar calendar;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private MyDbContext context;

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
        setDefaultDateNow();
        BindingAction();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navBarFragment, navigationBarFragment)
                .commit();
    }

    private void setDefaultDateNow() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(new Date());
        String selectedDay = currentDate.substring(0, 2);
        String selectedMonth = currentDate.substring(3, 5);
        String selectedYear = currentDate.substring(6);

        dateTimePickerCreate.setText(selectedDay + "/" + (selectedMonth) + "/" + selectedYear);
    }

    private void SetValues() {
        headerCategoryTv.setText(getIntent().getStringExtra("category"));
        headerSubCategoryTv.setText(getIntent().getStringExtra("category"));
    }

    private void BindingAction() {
        dateTimePickerCreate.setOnClickListener(this::onDateTimePickerCreateClick);
        dateTimePickerDue.setOnClickListener(this::onDateTimePickerDueClick);
        btnAdd.setOnClickListener(this::onBtnAddClick);
    }

    private void onBtnDeleteClick(View view) {

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

    private void onBtnAddClick(View view) {
        if (edtAmount.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill amount fields", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtTitle.getText().toString();
            String description = edtDescription.getText().toString();
            Double amount = Double.parseDouble(edtAmount.getText().toString());
            String createDate = dateTimePickerCreate.getText().toString();
            String dueDate = dateTimePickerDue.getText().toString();
            int userId = pref.getInt("userId", 0);
            int categoryId = context.getCategoryByName(category);
            int subCategoryId = context.getSubCategoryByName(subCategory);


            if (title.isEmpty() || amount == 0 || createDate.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (checkDate(createDate, dueDate)) {
                    if (categoryId != -1 && subCategoryId != -1) {
                        Transaction transaction = new Transaction(title, userId, description, amount, categoryId, subCategoryId, createDate, dueDate);
                        if (context.addTransaction2(transaction)) {
                            Toast.makeText(this, "Add successfully!", Toast.LENGTH_SHORT).show();

                            context.updateBalance(category, amount, userId);
                            UpdatePlanSatus(userId);

                            Intent i = new Intent(this, HomeActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(this, "Add failed!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Internal error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Due date must be after create date", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void UpdatePlanSatus(int userId) {
        double currentBudget = context.getBudgetAmountByUserId(userId);
        List<Plan> plans = context.getPlanByUserId(userId);
        for (Plan p : plans) {
            if (currentBudget >= p.getAmount()) {
                p.setStatus(1);
                context.updateStatus(p, p.getId());
            } else {
                p.setStatus(0);
                context.updateStatus(p, p.getId());
            }
        }
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
        dateTimePickerCreate = findViewById(R.id.edtCreateDate);
        dateTimePickerDue = findViewById(R.id.edtDueDate);
        calendar = Calendar.getInstance();
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtAmount = findViewById(R.id.edtAmount);
        btnAdd = findViewById(R.id.btnAdd);
        context = new MyDbContext(this);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private void onReceiveIntent() {
        Intent i = getIntent();
        subCategory = i.getStringExtra("subCategory");
        category = i.getStringExtra("category");

        headerCategoryTv.setText(category);
        headerSubCategoryTv.setText(subCategory);
    }
}
