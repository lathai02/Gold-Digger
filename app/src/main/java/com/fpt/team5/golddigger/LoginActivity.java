package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.team5.golddigger.Model.User;
import com.fpt.team5.golddigger.dal.MyDbContext;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmailPhone;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegister;
    private ImageView loginByFacebook;
    private ImageView loginByGoogle;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private MyDbContext context;

    private void bindingView(){
        etEmailPhone = findViewById(R.id.etEmailPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
        loginByFacebook = findViewById(R.id.btnFacebook);
        loginByGoogle = findViewById(R.id.btnGoogle);
        context = new MyDbContext(this);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
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
        int userId = context.checkLogin(emailPhone, password);
        if(userId != 0){
            Intent i = new Intent(this, HomeActivity.class);
            User u = context.getUserById(userId);
            editor.putInt("userId", userId);
            editor.putString("name", u.getName());
            editor.commit();
            startActivity(i);
            finish();
        }else{
            Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
        }
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
    protected void onStart() {
        super.onStart();

        if (pref.getInt("userId",0) != 0) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear the EditText field when the activity is resumed
        etEmailPhone.setText("");
        etPassword.setText("");
    }
}