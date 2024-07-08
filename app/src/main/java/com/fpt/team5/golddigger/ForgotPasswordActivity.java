package com.fpt.team5.golddigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.team5.golddigger.dal.MyDbContext;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmailPhone;
    private Button btnResetPassword;
    private MyDbContext context;

    private void BingdingView() {
        etEmailPhone = findViewById(R.id.etEmailPhone);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        context = new MyDbContext(this);
    }

    private void BingdingAction() {
        btnResetPassword.setOnClickListener(this::onBtnClickResetPassword);
    }


    private void onBtnClickResetPassword(View view) {
        String email = etEmailPhone.getText().toString();
        if(context.checkEmailExist(email)) {
            Intent i = new Intent(this,ResetPasswordActivity.class);
            i.putExtra("email",email);
            i.putExtra("father","Login");
            startActivity(i);
        }else{
            Toast.makeText(this, "Email is not registered", Toast.LENGTH_SHORT).show();
        }
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
