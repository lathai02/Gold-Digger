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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.fpt.team5.golddigger.Model.Transaction;
import com.fpt.team5.golddigger.dal.MyDbContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionDetailActivity2 extends AppCompatActivity {
    private NaviagtionBarFragment navigationBarFragment;
    private TextView headerCategoryTv;
    private TextView headerSubCategoryTv;
    private String subCategory;
    private String category;
    private double currentAmount;
    private int transactionId;
    private EditText edtTitle;
    private EditText edtDescription;
    private EditText edtAmount;
    private Button btnSave;
    private Button btnDelete;
    private EditText dateTimePickerCreate;
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
        setContentView(R.layout.transaction_detail2);
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
        btnSave.setOnClickListener(this::onBtnSaveClick);
        btnDelete.setOnClickListener(this::onBtnDeleteClick);
    }

    private void onBtnDeleteClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the deletion action
                        deleteTransaction();
                    }
                })
                .setNegativeButton(android.R.string.no, null) // Do nothing on "No" button click
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteTransaction() {
        // Assuming you have the transaction ID to delete
        int userId = pref.getInt("userId", 0);
        if(context.deleteTransaction(transactionId)){
            context.updateBalance(category,currentAmount*(-1),userId);
            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "Delete failed!", Toast.LENGTH_SHORT).show();
        }
    }


    private void onBtnSaveClick(View view) {
        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        double amount = Double.parseDouble(edtAmount.getText().toString());
        String createDate = dateTimePickerCreate.getText().toString();
        int userId = pref.getInt("userId", 0);
        int categoryId = context.getCategoryByName(category);
        int subCategoryId = context.getSubCategoryByName(subCategory);



        if(title.isEmpty() || amount == 0 || createDate.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }else{

                if(categoryId != -1 && subCategoryId != -1 ){
                    Transaction transaction = new Transaction(title,userId,description,amount,categoryId,subCategoryId,createDate,null);
                    if(context.updateTransaction(transaction,transactionId)){
                        Toast.makeText(this, "Save successfully!", Toast.LENGTH_SHORT).show();
                        if(currentAmount != amount){

                            context.updateBalance(category,amount-currentAmount,userId);
                        }


                        Intent i = new Intent(this, HomeActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(this, "Save failed!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Internal error", Toast.LENGTH_SHORT).show();
                }


        }
    }

    private void onDateTimePickerCreateClick(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TransactionDetailActivity2.this,
                (dateView, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    dateTimePickerCreate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
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
        calendar = Calendar.getInstance();
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtAmount = findViewById(R.id.edtAmount);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        context = new MyDbContext(this);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private void onReceiveIntent() {
        Intent i = getIntent();
        subCategory = i.getStringExtra("subCategory");
        category = i.getStringExtra("category");
        transactionId = i.getIntExtra("transactionId", 0);
        Transaction t = context.getTransactionById(transactionId);
        edtTitle.setText(t.getTitle());
        edtDescription.setText(t.getDescription());
        edtAmount.setText(String.valueOf(t.getStringAmount()));
        dateTimePickerCreate.setText(t.getCreateDate());
        currentAmount = t.getAmount();


        headerCategoryTv.setText(category);
        headerSubCategoryTv.setText(subCategory);
    }
}
