package com.fpt.team5.golddigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class PlanActivity extends AppCompatActivity {
    private NaviagtionBarFragment naviagtionBarFragment;
    private ImageButton addPlanIb;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NaviagtionBarFragment) {
            naviagtionBarFragment = ((NaviagtionBarFragment) fragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BindingView();
        BindingAction();
        InjectFragment();
    }


    private void InjectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navBarFragment, naviagtionBarFragment)
                .commit();
    }

    private void BindingAction() {
        addPlanIb.setOnClickListener(this::OnAddPlanClick);
    }

    private void OnAddPlanClick(View view) {
        Intent i = new Intent(this, AddPlanActivity.class);
        startActivity(i);
        finish();
    }

    private void BindingView() {
        if (naviagtionBarFragment == null) {
            naviagtionBarFragment = new NaviagtionBarFragment();
        }
        addPlanIb = findViewById(R.id.addPlanIb);
    }
}
