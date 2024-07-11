package com.fpt.team5.golddigger;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.team5.golddigger.Model.User;
import com.fpt.team5.golddigger.dal.MyDbContext;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etRepass;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPhone;
    private TextView tvLoginPrompt;
    private Button btnRegister;
    private MyDbContext dbContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    private void BingdingView() {
        etName = findViewById(R.id.etName);
        etRepass = findViewById(R.id.etRepass);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        tvLoginPrompt = findViewById(R.id.tvLoginPrompt);
        btnRegister = findViewById(R.id.btnRegister);
        dbContext = new MyDbContext(this);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private void BingdingAction() {
        tvLoginPrompt.setOnClickListener(this::onBtnGoToLoginScreen);
        btnRegister.setOnClickListener(this::onBtnRegister);
    }

    private void onBtnRegister(View view) {
        String name = etName.getText().toString();
        String repass = etRepass.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();

        if (name.isEmpty() || repass.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            return;
        } else {
            if (!repass.equals(password)) {
                Toast.makeText(this, "Repass is different from password", Toast.LENGTH_SHORT).show();
            } else {
                User u = new User(email, phone, name, password, 0);
                int userId = dbContext.addUser(u);
                if (userId != -1) {
                    Intent i = new Intent(this, BalanceActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, "Register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void onBtnGoToLoginScreen(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BingdingView();
        BingdingAction();
    }
}
