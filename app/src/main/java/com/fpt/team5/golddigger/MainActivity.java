package com.fpt.team5.golddigger;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.team5.golddigger.dal.MyDbContext;

public class MainActivity extends AppCompatActivity {

    private Button homeBtn;
    private MyDbContext myDbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BindingView();
        BindingAction();
    }

    private void BindingView(){
        homeBtn = findViewById(R.id.homeBtn);
        myDbContext = new MyDbContext(this);
    }

    private void BindingAction(){
        homeBtn.setOnClickListener(this::onClickHomeBtn);
        Cursor c = myDbContext.getAllContact();

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String title = c.getString(1);

                Log.d("THAILA.debug", id + " --- " + title);

            } while (c.moveToNext());

        } else {
            Toast.makeText(this, "Không có bản ghi nào cả!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickHomeBtn(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}