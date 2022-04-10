package com.example.news;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        List<Item> items = new ArrayList<Item>();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getNewsData();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Adapter adapter;

        Administrator admin = new Administrator();
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                items.add(new Item(cursor.getString(0), cursor.getString(1)));
            }
        }
        Adapter.OnStateClickListener stateClickListener = new Adapter.OnStateClickListener() {
            @Override
            public void onStateClick(Item items, int pos) {
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(stateClickListener, getApplicationContext(), items);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnExit).setOnClickListener(view -> {
            Intent intent = new Intent(User.this, MainActivity.class);
            startActivity(intent);
        });
    }
}