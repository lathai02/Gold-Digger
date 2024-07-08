package com.fpt.team5.golddigger;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.team5.golddigger.Model.SubCategory;
import com.fpt.team5.golddigger.dal.MyDbContext;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private List<SubCategory> subCategories;
    private SubcategoryAdapter adapter;
    private MyDbContext dbContext;
    private int cateId;
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
        setContentView(R.layout.activity_subcategory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        InjectFragment();
        onReceiveIntent();
        initRcv();
    }
    private void InjectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, navigationBarFragment)
                .commit();
    }

    private void bindingView() {
        rcv = findViewById(R.id.subCateRcv);
        dbContext = new MyDbContext(this);
        subCategories = new ArrayList<>();
        if (navigationBarFragment == null) {
            navigationBarFragment = new NaviagtionBarFragment();
        }
    }

    private void initRcv() {
        getSubCategories();
        adapter = new SubcategoryAdapter(subCategories, this);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getSubCategories() {
        Cursor c = dbContext.getAllSubCateByCateId(cateId);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                int cateId = c.getInt(1);
                String title = c.getString(2);

                SubCategory subCategory = new SubCategory(id, cateId, title);
                subCategories.add(subCategory);
            } while (c.moveToNext());

        } else {
            Toast.makeText(this, "Không có bản ghi nào cả!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onReceiveIntent() {
        Intent i = getIntent();
        cateId = i.getIntExtra("cateId", 1);
    }
}
