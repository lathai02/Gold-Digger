package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.team5.golddigger.Model.Budget;
import com.fpt.team5.golddigger.Model.User;
import com.fpt.team5.golddigger.dal.MyDbContext;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmailPhone;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegister;
    private static final String PREF_FIRST_RUN = "FirstRun";
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private MyDbContext context;

    private void bindingView() {
        etEmailPhone = findViewById(R.id.etEmailPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
        context = new MyDbContext(this);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private void bindingAction() {
        btnLogin.setOnClickListener(this::onBtnGoToMainScreen);
        tvRegister.setOnClickListener(this::onBtnGoToRegisterScreen);
        tvForgotPassword.setOnClickListener(this::onBtnGoToForgotPasswordScreen);
    }

    private void onBtnGoToForgotPasswordScreen(View view) {
        Intent i = new Intent(this, ForgotPasswordActivity.class);
        startActivity(i);
    }

    private void onBtnGoToRegisterScreen(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void onBtnGoToMainScreen(View view) {
        String emailPhone = etEmailPhone.getText().toString();
        String password = etPassword.getText().toString();
        int userId = context.checkLogin(emailPhone, password);

        if (userId != 0) {
            Budget b = null;
            Cursor c = context.getBudgetByUserId(userId);
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(0);
                    String title = c.getString(1);
                    float amount = c.getFloat(2);
                    int uId = c.getInt(3);
                    String date = c.getString(4);

                    b = new Budget(title, uId, date, amount);
                } while (c.moveToNext());
            }

            if (b != null) {
                Intent i = new Intent(this, HomeActivity.class);
                User u = context.getUserById(userId);
                editor.putInt("userId", userId);
                editor.putString("name", u.getName());
                editor.commit();
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(this, BalanceActivity.class);
                User u = context.getUserById(userId);
                i.putExtra("userName", u.getName());
                i.putExtra("userId", userId);
                startActivity(i);
                finish();
            }
        } else {
            Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        if (isFirstRun()) {
            initializeCategories();
            setFirstRunFlag();
        }
    }

    private boolean isFirstRun() {
        SharedPreferences prefs = getSharedPreferences("my_pref", MODE_PRIVATE);
        return prefs.getBoolean(PREF_FIRST_RUN, true);
    }

    private void setFirstRunFlag() {
        SharedPreferences prefs = getSharedPreferences("my_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREF_FIRST_RUN, false);
        editor.apply();
    }

    private void initializeCategories() {
        int[] imageIds = {R.drawable.ic_category_income, R.drawable.ic_category_expense, R.drawable.ic_category_borrow, R.drawable.ic_category_lending};

        int[] categoryIds = {1, 2, 3, 4};

        for (int i = 0; i < 4; i++) {
            context.updateCategoryImageId(categoryIds[i], imageIds[i]);
        }

        int[] imageIds2 = {R.drawable.ic_subcategory_income_salary, R.drawable.ic_subcategory_income_family
                , R.drawable.ic_subcategory_income_bonus, R.drawable.ic_subcategory_income_interest, R.drawable.ic_subcategory_income_others
                , R.drawable.ic_subcategory_expense_foodanddining, R.drawable.ic_subcategory_expense_utilities, R.drawable.ic_subcategory_expense_transport,
                R.drawable.ic_subcategory_expense_clothing, R.drawable.ic_subcategory_expense_personal, R.drawable.ic_subcategory_expense_entertainment
                , R.drawable.ic_subcategory_expense_home, R.drawable.ic_subcategory_expense_kids, R.drawable.ic_category_borrow, R.drawable.ic_category_lending};
        int[] subcategoryIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        for (int i = 0; i < 15; i++) {
            context.updateSubcategoryImageId(subcategoryIds[i], imageIds2[i]);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (pref.getInt("userId", 0) != 0) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear the EditText field when the activity is resumed
        etEmailPhone.setText("");
        etPassword.setText("");
    }
}