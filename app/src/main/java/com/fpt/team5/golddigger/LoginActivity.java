package com.fpt.team5.golddigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmailPhone;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegister;
    private ImageView loginByFacebook;
    private ImageView loginByGoogle;

    private void bindingView(){
        etEmailPhone = findViewById(R.id.etEmailPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
        loginByFacebook = findViewById(R.id.btnFacebook);
        loginByGoogle = findViewById(R.id.btnGoogle);
    }

    private void bindingAction(){
        btnLogin.setOnClickListener(this::onBtnGoToMainScreen);
        tvRegister.setOnClickListener(this::onBtnGoToRegisterScreen);
        tvForgotPassword.setOnClickListener(this::onBtnGoToForgotPasswordScreen);
    }

    private void onBtnGoToForgotPasswordScreen(View view) {
        Intent i = new Intent(this, ForgotPasswordActivity.class);
        startActivity(i);
    }

    private void onBtnGoToRegisterScreen(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void onBtnGoToMainScreen(View view) {
        String emailPhone = etEmailPhone.getText().toString();
        String password = etPassword.getText().toString();
//        if (emailPhone.equals("admin") && password.equals("123")) {
//            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//        }
//        Intent i = new Intent(this, HomeActivity.class);
//        i.putExtra("data", data);
//        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear the EditText field when the activity is resumed
        etEmailPhone.setText("");
        etPassword.setText("");
    }
}