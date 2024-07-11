package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.team5.golddigger.Model.Budget;
import com.fpt.team5.golddigger.dal.MyDbContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BalanceActivity extends AppCompatActivity {
    private EditText balanceEt;
    private Button btnStart;
    private EditText titleEt;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private MyDbContext dbContext;
    private String userName;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_balance);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        onReceiveIntent();
        bindingAction();
    }

    private void onReceiveIntent() {
        Intent i = getIntent();
        userName = i.getStringExtra("userName");
        userId = i.getIntExtra("userId", 0);
    }

    private void bindingAction() {
        btnStart.setOnClickListener(this::OnBtnStartClick);
    }

    private void OnBtnStartClick(View view) {
        double amount = Double.parseDouble(balanceEt.getText().toString());
        String title = titleEt.getText().toString();


        LocalDateTime now = null;
        DateTimeFormatter formatter = null;
        String formattedDateTime = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            formattedDateTime = now.format(formatter);
        }

        Budget b = new Budget(title, userId, formattedDateTime, amount);
        dbContext.addBudget(b);

        editor.putInt("userId", userId);
        editor.putString("name", userName);
        editor.commit();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void bindingView() {
        balanceEt = findViewById(R.id.balanceEt);
        titleEt = findViewById(R.id.titleEt);
        btnStart = findViewById(R.id.startBtn);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
        dbContext = new MyDbContext(this);
    }
}
