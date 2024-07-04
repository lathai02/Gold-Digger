package com.fpt.team5.golddigger;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class RevenueActivity extends AppCompatActivity {
    private NaviagtionBarFragment navigationBarFragment;
    private ImageButton imgBtnDone;
    private EditText edtAmount;
    private EditText edtCategory;
    private EditText edtDescription;
    private EditText edtDate;

    private Button btnSubmit;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NaviagtionBarFragment) {
            navigationBarFragment = ((NaviagtionBarFragment) fragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_revenue);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_revenue), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        BindingView();
        BindingAction();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, navigationBarFragment)
                .commit();
    }

    private void BindingAction() {
        imgBtnDone.setOnClickListener(this::onSubmit);
        btnSubmit.setOnClickListener(this::onSubmit);
    }

    private void onSubmit(View view) {

    }

    private void BindingView() {
        edtAmount = findViewById(R.id.edtAmountRevenue);
        edtCategory = findViewById(R.id.edtCategory);
        edtDate = findViewById(R.id.edtDate);
        edtDescription = findViewById(R.id.edtDescription);
        btnSubmit = findViewById(R.id.btnSubmit);
        imgBtnDone = findViewById(R.id.imgBtnDone);
        if (navigationBarFragment == null) {
            navigationBarFragment = new NaviagtionBarFragment();
        }
    }
}
