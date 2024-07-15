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

import com.fpt.team5.golddigger.Model.GoldPrice;

public class FinanceActiity extends AppCompatActivity {
    private NaviagtionBarFragment overviewFragment;
    private ImageButton subcategoryIbtn;
    private ImageButton subcategoryIbtn2;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NaviagtionBarFragment) {
            overviewFragment = ((NaviagtionBarFragment) fragment);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finance_actiity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        InjectFragment();
    }

    private void InjectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navBarFragment, overviewFragment)
                .commit();
    }

    private void bindingAction() {
        subcategoryIbtn.setOnClickListener(this::OnClickBankButton);
        subcategoryIbtn2.setOnClickListener(this::OnClickGoldButton);
    }

    private void OnClickGoldButton(View view) {
        Intent i = new Intent(this, PriceActivity.class);
        startActivity(i);
    }

    private void OnClickBankButton(View view) {
        Intent i = new Intent(this, BankInterestRateActivity.class);
        startActivity(i);
    }

    private void bindingView() {
        if (overviewFragment == null) {
            overviewFragment = new NaviagtionBarFragment();
        }
        subcategoryIbtn = findViewById(R.id.subcategoryIbtn);
        subcategoryIbtn2 = findViewById(R.id.subcategoryIbtn2);
    }
}