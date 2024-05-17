package com.svalero.readyfy.Activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.readyfy.Config.RetrofitClientInstance;
import com.svalero.readyfy.R;
import com.svalero.readyfy.Service.BookService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDeleteActivity extends AppCompatActivity {

    private Button btnConfirmDelete;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        btnConfirmDelete = findViewById(R.id.btnConfirmDelete);

        bookId = getIntent().getIntExtra("BOOK_ID", -1);

        btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    private void deleteBook() {
        BookService service = RetrofitClientInstance.getRetrofitInstance().create(BookService.class);
        Call<Void> call = service.deleteBook(bookId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookDeleteActivity.this, "Book successfully deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(BookDeleteActivity.this, "Error deleting book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookDeleteActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
