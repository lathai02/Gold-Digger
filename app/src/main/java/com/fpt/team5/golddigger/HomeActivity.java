package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class HomeActivity extends AppCompatActivity {

    private NaviagtionBarFragment overviewFragment;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private Fragment navigationBarFragment;

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
    }

    private void setDefaultNavigationTab() {
        editor.putInt("selected_item_id", R.id.navigation_home);
        editor.apply();
    }

    private void InjectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, overviewFragment)
                .commit();
    }

    private void BindingView() {
        if (overviewFragment == null) {
            overviewFragment = new NaviagtionBarFragment();
        }

        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private void BindingAction() {

    }
}
