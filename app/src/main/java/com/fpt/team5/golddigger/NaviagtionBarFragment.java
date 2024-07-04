package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NaviagtionBarFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.navigation_bar_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        onNavigationItemSelected();
    }

    private void onNavigationItemSelected() {
        int selectedItemId = pref.getInt("selected_item_id", R.id.navigation_home);
        bottomNavigationView.setSelectedItemId(selectedItemId);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::OnNavigationItemSelected);
    }

    private boolean OnNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        int itemId = item.getItemId();

        editor.putInt("selected_item_id", itemId);
        editor.apply();

        if (itemId == R.id.navigation_home) {
            intent = new Intent(getActivity(), HomeActivity.class);
        } else if (itemId == R.id.navigation_add) {
            intent = new Intent(getActivity(), AddActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            return true;
        }

        return false;
    }

    private void bindingView() {
        if (getView() != null) {
            bottomNavigationView = getView().findViewById(R.id.bottom_navigation);
        } else {
            Log.e("NavBarFragment", "getView() returned null");
        }

        if (getActivity() != null) {
            pref = getActivity().getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        } else {
            Log.e("NavBarFragment", "getActivity() returned null");
        }

        editor = pref.edit();
    }
}
