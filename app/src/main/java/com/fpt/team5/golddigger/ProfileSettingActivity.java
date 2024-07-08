package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.team5.golddigger.Model.User;
import com.fpt.team5.golddigger.dal.MyDbContext;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileSettingActivity extends AppCompatActivity {
    private TextInputEditText edtUsername;
    private TextInputEditText edtPhone;
    private Button btnUpdate;
    private MyDbContext dbContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private ImageView imgBack;

    private void BingdingView() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPhone = findViewById(R.id.edtPhone);
        btnUpdate = findViewById(R.id.btnUpdate);
        imgBack = findViewById(R.id.imgBack);
        dbContext = new MyDbContext(this);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private void BingdingAction() {
        btnUpdate.setOnClickListener(this::onUpdateClick);
        imgBack.setOnClickListener(this::onBackClick);
    }

    private void onBackClick(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }


    private void onUpdateClick(View view) {
        String name = edtUsername.getText().toString();
        String phone = edtPhone.getText().toString();
        if(dbContext.updateUser(pref.getInt("userId",0),name,phone)){
            Toast.makeText(this, "Update successful!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
        }

    }

    private void InitUser() {
        int id = pref.getInt("userId", 0);
        User u = dbContext.getUserById(id);
        if (u != null) {
            edtUsername.setText(u.getName());
            edtPhone.setText(u.getPhone());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BingdingView();
        InitUser();
        BingdingAction();
    }


}
