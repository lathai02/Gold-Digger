package com.fpt.team5.golddigger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class TransactionActivity2 extends AppCompatActivity {
    private NaviagtionBarFragment navigationBarFragment;
    private TextView headerCategoryTv;
    private TextView headerSubCategoryTv;
    private String subCategory;
    private String category;

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
        setContentView(R.layout.activity_transaction2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        BindingView();
        onReceiveIntent();
        BindingAction();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, navigationBarFragment)
                .commit();
    }

    private void SetValues() {
        headerCategoryTv.setText(getIntent().getStringExtra("category"));
        headerSubCategoryTv.setText(getIntent().getStringExtra("category"));
    }

    private void BindingAction() {
    }

    private void BindingView() {
        if (navigationBarFragment == null) {
            navigationBarFragment = new NaviagtionBarFragment();
        }
        headerCategoryTv = findViewById(R.id.headerCategoryTv);
        headerSubCategoryTv = findViewById(R.id.headerSubCategoryTv);
    }

    private void onReceiveIntent() {
        Intent i = getIntent();
        subCategory = i.getStringExtra("subCategory");
        category = i.getStringExtra("category");

        headerCategoryTv.setText(category);
        headerSubCategoryTv.setText(subCategory);
    }
}
