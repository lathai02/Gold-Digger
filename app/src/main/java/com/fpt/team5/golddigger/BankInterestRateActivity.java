package com.fpt.team5.golddigger;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.team5.golddigger.api.goldPriceApi.DanTriApiServices;
import com.fpt.team5.golddigger.api.interestRate.ApiResponse.BankInterestRateResponse;
import com.fpt.team5.golddigger.api.interestRate.CafeFApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankInterestRateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BankInterestRateAdapter adapter;
    private NaviagtionBarFragment navigationBarFragment;

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
        setContentView(R.layout.activity_bank_interest_rate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_bank_interest_rate), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        if (navigationBarFragment == null) {
            navigationBarFragment = new NaviagtionBarFragment();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getGoldPriceByApi();
        InjectFragment();
    }

    private void InjectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navBarFragment, navigationBarFragment)
                .commit();
    }

    private void getGoldPriceByApi() {
        // call api here
        try {
            CafeFApiServices.getCommentApiEndpoint()
                    .getBankInterestRates()
                    .enqueue(new Callback<BankInterestRateResponse>() {
                        @Override
                        public void onResponse(Call<BankInterestRateResponse> call, Response<BankInterestRateResponse> response) {
                            adapter = new BankInterestRateAdapter(response.body().getData());
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<BankInterestRateResponse> call, Throwable t) {
                            Toast.makeText(BankInterestRateActivity.this, "Can't view bank interest rate right now! Network error!", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            Toast.makeText(this, "Can't view bank interest rate right now! Network error!", Toast.LENGTH_SHORT).show();
        }
    }
}