package com.fpt.team5.golddigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmailPhone;
    private Button btnResetPassword;
    private Toolbar toolbar;
    private void BingdingView() {
        etEmailPhone = findViewById(R.id.etEmailPhone);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        toolbar = findViewById(R.id.toolbar);
    }

    private void BingdingAction() {
        btnResetPassword.setOnClickListener(this::onBtnClickResetPassword);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        // Navigate back to LoginActivity
        super.onBackPressed();
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onBtnClickResetPassword(View view) {
        String emailPhone = etEmailPhone.getText().toString().trim();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BingdingView();
        BingdingAction();
    }
}
