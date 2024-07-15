package com.fpt.team5.golddigger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.fpt.team5.golddigger.Model.Notification;
import com.fpt.team5.golddigger.Model.Transaction;
import com.fpt.team5.golddigger.dal.MyDbContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private NaviagtionBarFragment overviewFragment;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private Fragment navigationBarFragment;
    private TextView welcomeTv;
    private TextView balanceTv;
    private MyDbContext dbContext;
    private int userId;
    private Budget b;
    private RecyclerView rcv;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;
    private ImageView ivNotification;


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
        userId = pref.getInt("userId", 0);
        transactions = dbContext.getTransactionByUserId(userId);
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
                double amount = c.getDouble(2);
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

    @Override
    protected void onResume() {
        super.onResume();
        int userId = pref.getInt("userId", 0);
        List<Transaction> transactions = dbContext.getNearDueDateTransactions(userId);
        List<Notification> notifications = convertToNotification(transactions,userId);
        if(dbContext.saveNotifications(notifications) && notifications.size() > 0){
            showNotificationDialog(notifications.size());
        }
    }

    private List<Notification> convertToNotification(List<Transaction> transactions,int userId) {
        List<Notification> notifications = new ArrayList<Notification>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        for(Transaction t : transactions){
            String title = "";
            if (t.getCategoryId() == 3) {
                title = "You have to return your loan soon for transaction: " + t.getTitle();
            } else if (t.getCategoryId() == 4) {
                title = "You need to collect the loan you provided soon for transaction: " + t.getTitle();
            }
            if (!title.isEmpty()) {
                Notification n = new Notification(title,userId, currentDate, t.getId());
                notifications.add(n);
            }
        }
        return notifications;
    }

    private void showNotificationDialog(int notificationCount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification")
                .setMessage("You have " + notificationCount + " notifications. Please check them.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the OK button click if necessary
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
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
        ivNotification = findViewById(R.id.ivNotification);
    }

    private void BindingAction() {
        ivNotification.setOnClickListener(this::onNotificationClick);
    }

    private void onNotificationClick(View view) {
        Intent i = new Intent(this, NotificationActivity.class);
        startActivity(i);
    }
}
