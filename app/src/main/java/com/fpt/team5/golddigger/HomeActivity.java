package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.team5.golddigger.Model.Budget;
import com.fpt.team5.golddigger.Model.Transaction;
import com.fpt.team5.golddigger.dal.MyDbContext;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private NaviagtionBarFragment overviewFragment;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private Fragment navigationBarFragment;
    private TextView welcomeTv;
    private TextView balanceTv;
    private MyDbContext dbContext;
    private Budget b;
    private RecyclerView rcv;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;

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
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BindingView();
        BindingAction();
        setDefaultNavigationTab();
        InjectFragment();
        setValues();
        initRcv();
    }

    private void initRcv() {
        transactions = dbContext.getAllTransactions();
        adapter = new TransactionAdapter(transactions, this);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new GridLayoutManager(this, 1));
    }

    private void setValues() {
       Cursor c = dbContext.getBudgetByUserId(pref.getInt("userId", 0));
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String title = c.getString(1);
                float amount = c.getFloat(2);
                int userId = c.getInt(3);
                String date = c.getString(4);

                b = new Budget(title, userId, date, amount);

            } while (c.moveToNext());

        } else {
            Toast.makeText(this, "Không có bản ghi nào cả!", Toast.LENGTH_SHORT).show();
        }

        welcomeTv.setText("Welcome " + pref.getString("name", ""));
        balanceTv.setText(b.getFormattedAmount() + " đ");
    }

    private void setDefaultNavigationTab() {
        editor.putInt("selected_item_id", R.id.navigation_home);
        editor.apply();
    }

    private void InjectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navBarFragment, overviewFragment)
                .commit();
    }

    private void BindingView() {
        if (overviewFragment == null) {
            overviewFragment = new NaviagtionBarFragment();
        }
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
        welcomeTv = findViewById(R.id.welcomeTv);
        balanceTv = findViewById(R.id.balanceTv);
        dbContext = new MyDbContext(this);
        rcv = findViewById(R.id.transactionRcv);
        transactions = new ArrayList<>();
    }

    private void BindingAction() {

    }
}
