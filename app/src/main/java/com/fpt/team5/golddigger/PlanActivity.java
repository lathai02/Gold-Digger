package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.team5.golddigger.Model.Plan;
import com.fpt.team5.golddigger.dal.MyDbContext;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    private NaviagtionBarFragment naviagtionBarFragment;
    private ImageButton addPlanIb;
    private RecyclerView rcv;
    private PlanAdapter adapter;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private int userId;
    private List<Plan> plans;
    private MyDbContext dbContext;


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
        initRcv();
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
        rcv = findViewById(R.id.planRcv);
        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        editor = pref.edit();
        plans = new ArrayList<>();
        dbContext = new MyDbContext(this);
    }

    private void initRcv() {
        userId = pref.getInt("userId", 0);
        plans = dbContext.getPlanByUserId(userId);
        adapter = new PlanAdapter(plans, this);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new GridLayoutManager(this, 1));
    }
}
