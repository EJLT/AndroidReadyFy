package com.svalero.readyfy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.svalero.readyfy.Activities.BookAddActivity;
import com.svalero.readyfy.Activities.BookDeleteActivity;
import com.svalero.readyfy.Activities.BookListActivity;
import com.svalero.readyfy.Activities.BookUpdateActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAddBook, btnUpdateBook, btnDeleteBook, btnListBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddBook = findViewById(R.id.btnAddBook);
        btnUpdateBook = findViewById(R.id.btnUpdateBook);
        btnDeleteBook = findViewById(R.id.btnDeleteBook);
        btnListBooks = findViewById(R.id.btnListBooks);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookAddActivity.class);
                startActivity(intent);
            }
        });

        btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookUpdateActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookDeleteActivity.class);
                startActivity(intent);
            }
        });

        btnListBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });
    }
}
