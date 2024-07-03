package com.fpt.team5.golddigger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class OverviewFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {

            }
            return true;
        });
    }

    private void bindingView() {
        bottomNavigationView = getView().findViewById(R.id.bottom_navigation);
    }
}
