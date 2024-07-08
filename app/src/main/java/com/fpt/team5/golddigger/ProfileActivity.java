package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.fpt.team5.golddigger.Model.User;
import com.fpt.team5.golddigger.dal.MyDbContext;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvEmail;
    private TextView tvName;
    private RelativeLayout rlChangePass;
    private RelativeLayout rlLogOut;
    private Button btnEditProfile;
    private MyDbContext dbContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private NaviagtionBarFragment overviewFragment;
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NaviagtionBarFragment) {
            overviewFragment = ((NaviagtionBarFragment) fragment);
        }
    }
    private void BingdingView(){
        tvEmail = findViewById(R.id.tvEmail);
        tvName = findViewById(R.id.tvName);
        rlChangePass = findViewById(R.id.rlChangePass);
        rlLogOut = findViewById(R.id.rlLogOut);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        dbContext = new MyDbContext(this);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
        if (overviewFragment == null) {
            overviewFragment = new NaviagtionBarFragment();
        }
    }

    private void BingdingAction(){
        btnEditProfile.setOnClickListener(this::onEditProfileClick);
        rlLogOut.setOnClickListener(this::onLogOutClick);
        rlChangePass.setOnClickListener(this::onChangePassClick);
    }

    private void onChangePassClick(View view) {


    }

    private void onLogOutClick(View view) {
        editor.clear();
        editor.apply();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void onEditProfileClick(View view) {
        Intent i = new Intent(this, ProfileSettingActivity.class);
        startActivity(i);

    }

    private void InitUser() {
        int id = pref.getInt("userId", 0);
        User u = dbContext.getUserById(id);
        if(u != null){
            tvEmail.setText(u.getEmail());
            tvName.setText(u.getName());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BingdingView();
        InjectFragment();
        InitUser();
        BingdingAction();
    }

    private void InjectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, overviewFragment)
                .commit();
    }


}
