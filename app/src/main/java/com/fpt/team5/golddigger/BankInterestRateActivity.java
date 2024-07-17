package com.fpt.team5.golddigger;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

import com.fpt.team5.golddigger.api.interestRate.ApiResponse.BankInterestRateResponse;
import com.fpt.team5.golddigger.api.interestRate.CafeFApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankInterestRateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BankInterestRateAdapter adapter;
    private NaviagtionBarFragment navigationBarFragment;
    TextView bankNameHeader;
    TextView header1m;
    TextView header3m;
    TextView header6m;
    TextView header9m;
    TextView header12m;
    TextView header18m;
    TextView header24m;

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
        bankNameHeader = findViewById(R.id.bank_header);
        header1m = findViewById(R.id.header_1m);
        header3m = findViewById(R.id.header_3m);
        header6m = findViewById(R.id.header_6m);
        header9m = findViewById(R.id.header_9m);
        header12m = findViewById(R.id.header_12m);
        header18m = findViewById(R.id.header_18m);
        header24m = findViewById(R.id.header_24m);
        if (navigationBarFragment == null) {
            navigationBarFragment = new NaviagtionBarFragment();
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getGoldPriceByApi();
        injectFragment();

        setEventListener();
    }

    private void setupHeaderClickListener(View headerView, int depositPeriod) {
        headerView.setOnClickListener(v -> {
            TextView headerTextView = (TextView) headerView;
            String text = headerTextView.getText().toString();
            boolean isAscending = !text.contains(getString(R.string.up));

            adapter.sortByDeposit(depositPeriod, isAscending);
            // reset text
            resetHeaderTextView();
            headerTextView.setText(isAscending ? getString(getAscendingStringId(depositPeriod)) :
                    getString(getDescendingStringId(depositPeriod)));
        });
    }
    private void setupBankNameClickListener() {
        bankNameHeader.setOnClickListener(v -> {
            if (!bankNameHeader.getText().toString().contains(getString(R.string.up))) {
                adapter.sortByBankName(true);
                resetHeaderTextView();
                bankNameHeader.setText(R.string.bank_header_up);
            } else {
                adapter.sortByBankName(false);
                resetHeaderTextView();
                bankNameHeader.setText(R.string.bank_header_down);
            }
        });
    }
    private void resetHeaderTextView() {
        bankNameHeader.setText(getString(R.string.bank));
        header1m.setText(getString(R.string.header_1m));
        header3m.setText(getString(R.string.header_3m));
        header6m.setText(getString(R.string.header_6m));
        header9m.setText(getString(R.string.header_9m));
        header12m.setText(getString(R.string.header_12m));
        header18m.setText(getString(R.string.header_18m));
        header24m.setText(getString(R.string.header_24m));
    }

    private int getAscendingStringId(int depositPeriod) {
        switch (depositPeriod) {
            case 1:
                return R.string.header_1m_up;
            case 3:
                return R.string.header_3m_up;
            case 6:
                return R.string.header_6m_up;
            case 9:
                return R.string.header_9m_up;
            case 12:
                return R.string.header_12m_up;
            case 18:
                return R.string.header_18m_up;
            case 24:
                return R.string.header_24m_up;
            default:
                return 0;
        }
    }

    private int getDescendingStringId(int depositPeriod) {
        switch (depositPeriod) {
            case 1:
                return R.string.header_1m_down;
            case 3:
                return R.string.header_3m_down;
            case 6:
                return R.string.header_6m_down;
            case 9:
                return R.string.header_9m_down;
            case 12:
                return R.string.header_12m_down;
            case 18:
                return R.string.header_18m_down;
            case 24:
                return R.string.header_24m_down;
            default:
                return 0;
        }
    }

    private void setEventListener() {
        setupHeaderClickListener(header1m, 1);
        setupHeaderClickListener(header3m, 3);
        setupHeaderClickListener(header6m, 6);
        setupHeaderClickListener(header9m, 9);
        setupHeaderClickListener(header12m, 12);
        setupHeaderClickListener(header18m, 18);
        setupHeaderClickListener(header24m, 24);

        setupBankNameClickListener();
        /*
        header1m.setOnClickListener(v -> {
            String text = header1m.getText().toString();
            if (!text.contains(getString(R.string.header_1m_up))) {
                adapter.sortByDeposit(1, true);
                header1m.setText(R.string.header_1m_up);
            } else {
                adapter.sortByDeposit(1, false);
                header1m.setText(R.string.header_1m_down);
            }
        });
        header3m.setOnClickListener(v -> {
            String text = header3m.getText().toString();
            if (!text.contains(getString(R.string.header_3m_up))) {
                adapter.sortByDeposit(3, true); // Sort ascending
                header3m.setText(R.string.header_3m_up);
            } else {
                adapter.sortByDeposit(3, false); // Sort descending
                header3m.setText(R.string.header_3m_down);
            }
        });

        header6m.setOnClickListener(v -> {
            String text = header6m.getText().toString();
            if (!text.contains(getString(R.string.header_6m_up))) {
                adapter.sortByDeposit(6, true); // Sort ascending
                header6m.setText(R.string.header_6m_up);
            } else {
                adapter.sortByDeposit(6, false); // Sort descending
                header6m.setText(R.string.header_6m_down);
            }
        });

        header9m.setOnClickListener(v -> {
            String text = header9m.getText().toString();
            if (!text.contains(getString(R.string.header_9m_up))) {
                adapter.sortByDeposit(9, true); // Sort ascending
                header9m.setText(R.string.header_9m_up);
            } else {
                adapter.sortByDeposit(9, false); // Sort descending
                header9m.setText(R.string.header_9m_down);
            }
        });
        header12m.setOnClickListener(v -> {
            String text = header12m.getText().toString();
            if (!text.contains(getString(R.string.header_12m_up))) {
                adapter.sortByDeposit(12, true); // Sort ascending
                header12m.setText(R.string.header_12m_up);
            } else {
                adapter.sortByDeposit(12, false); // Sort descending
                header12m.setText(R.string.header_12m_down);
            }
        });

        header18m.setOnClickListener(v -> {
            String text = header18m.getText().toString();
            if (!text.contains(getString(R.string.header_18m_up))) {
                adapter.sortByDeposit(18, true); // Sort ascending
                header18m.setText(R.string.header_18m_up);
            } else {
                adapter.sortByDeposit(18, false); // Sort descending
                header18m.setText(R.string.header_18m_down);
            }
        });

        header24m.setOnClickListener(v -> {
            String text = header24m.getText().toString();
            if (!text.contains(getString(R.string.header_24m_up))) {
                adapter.sortByDeposit(24, true); // Sort ascending
                header24m.setText(R.string.header_24m_up);
            } else {
                adapter.sortByDeposit(24, false); // Sort descending
                header24m.setText(R.string.header_24m_down);
            }
        });
        */

    }

    private void injectFragment() {
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