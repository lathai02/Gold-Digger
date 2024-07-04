package com.fpt.team5.golddigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    private EditText etLastName;
    private EditText etFirstName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPhone;
    private TextView tvLoginPrompt;
    private Button btnRegister;

    private void BingdingView(){
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        tvLoginPrompt = findViewById(R.id.tvLoginPrompt);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void BingdingAction(){
        tvLoginPrompt.setOnClickListener(this::onBtnGoToLoginScreen);
        btnRegister.setOnClickListener(this::onBtnRegister);
    }

    private void onBtnRegister(View view) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
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
