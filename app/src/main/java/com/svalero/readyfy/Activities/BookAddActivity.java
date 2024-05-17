package com.svalero.readyfy.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.readyfy.Config.RetrofitClientInstance;
import com.svalero.readyfy.Domain.Book;
import com.svalero.readyfy.R;
import com.svalero.readyfy.Service.BookService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAddActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor, etPublishedDate, etISBN;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPublishedDate = findViewById(R.id.etPublishedDate);
        etISBN = findViewById(R.id.etISBN);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
    }

    private void addBook() {
        String title = etTitle.getText().toString();
        String author = etAuthor.getText().toString();
        String publishedDate = etPublishedDate.getText().toString();
        String ISBN = etISBN.getText().toString();

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setPublishedDate(publishedDate);
        newBook.setISBN(ISBN);

        BookService service = RetrofitClientInstance.getRetrofitInstance().create(BookService.class);
        Call<Book> call = service.addBook(newBook);

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookAddActivity.this, "Libro agregado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(BookAddActivity.this, "Error al agregar el libro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookAddActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
