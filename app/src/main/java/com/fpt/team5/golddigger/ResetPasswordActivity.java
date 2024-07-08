package com.fpt.team5.golddigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.team5.golddigger.dal.MyDbContext;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private Button btnResetPassword;
    private String email;
    private String father;
    private MyDbContext context;

    private void BingdingView() {
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.balanceEt);
        btnResetPassword = findViewById(R.id.startBtn);
        context = new MyDbContext(this);
    }

    private void BingdingAction() {
        btnResetPassword.setOnClickListener(this::onBtnClickResetPassword);
    }

    private void onBtnClickResetPassword(View view) {
        String newPass = etNewPassword.getText().toString();
        String confirmPass = etConfirmPassword.getText().toString();
        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Please fill the field", Toast.LENGTH_SHORT).show();
        }else if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Confirm password is different", Toast.LENGTH_SHORT).show();
        }else{
            if(context.resetPassword(email, newPass)){
                Toast.makeText(this, "Reset password successfully", Toast.LENGTH_SHORT).show();
                Intent i = null;
                if(father.equals("Login")){
                     i = new Intent(this, LoginActivity.class);
                }else{
                     i = new Intent(this, ProfileActivity.class);
                }

                startActivity(i);
            }else{
                Toast.makeText(this, "Reset password failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BingdingView();
        ReceiveIntent();
        BingdingAction();
    }

    private void ReceiveIntent() {
        Intent i = getIntent();
        email = i.getStringExtra("email");
        father = i.getStringExtra("father");
    }
}
