package com.example.news;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator);

        List<Item> items = new ArrayList<Item>();
        EditText editText_NameNews = findViewById(R.id.editText_NameNews);
        EditText editText_TextNews = findViewById(R.id.editText_TextNews);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Cursor cursor = databaseHelper.getNewsData();
        Adapter adapter;

        final int[] position = new int[1];
        Adapter.OnStateClickListener stateClickListener = new Adapter.OnStateClickListener() {
            @Override
            public void onStateClick(Item items, int pos) {
                editText_NameNews.setText(items.getNewsName());
                editText_TextNews.setText(items.getNewsText());
                position[0] = pos;
            }
        };

        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                items.add(new Item(cursor.getString(0), cursor.getString(1)));
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(stateClickListener, getApplicationContext(), items);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnAddNews).setOnClickListener(view -> {
            if (!editText_NameNews.getText().toString().trim().equals("") && !editText_TextNews.getText().toString().trim().equals("")) {
                Boolean checkInsertData = databaseHelper.insertNews(editText_NameNews.getText().toString(), editText_TextNews.getText().toString());
                if (checkInsertData) {
                    items.add(items.size(), new Item(editText_NameNews.getText().toString(), editText_TextNews.getText().toString()));
                    adapter.notifyItemInserted(items.size());
                    Toast.makeText(getApplicationContext(), "Данные успешно добавлены", Toast.LENGTH_LONG).show();
                } else Toast.makeText(getApplicationContext(), "Произошла ошибка!", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getApplicationContext(), "Не все поля заполнены!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnDeleteNews).setOnClickListener(view -> {
            Boolean checkInsertData = databaseHelper.deleteNews(editText_NameNews.getText().toString());
            if (checkInsertData) {
                items.remove(position[0]);
                adapter.notifyItemRemoved(position[0]);
                Toast.makeText(getApplicationContext(), "Данные успешно удалены", Toast.LENGTH_LONG).show();
            } else Toast.makeText(getApplicationContext(), "Произошла ошибка!", Toast.LENGTH_LONG).show();
        });

        findViewById(R.id.btnEditNews).setOnClickListener(view -> {
            Boolean checkInsertData = databaseHelper.updateNews(editText_NameNews.getText().toString(), editText_TextNews.getText().toString());
            if (checkInsertData){
                items.set(position[0], new Item(editText_NameNews.getText().toString(), editText_TextNews.getText().toString()));
                adapter.notifyItemChanged(position[0]);
                Toast.makeText(getApplicationContext(), "Данные успешно обновлены", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
        });

        findViewById(R.id.btnExit).setOnClickListener(view -> {
            Intent intent = new Intent(Administrator.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
