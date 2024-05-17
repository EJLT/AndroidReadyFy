package com.svalero.readyfy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.readyfy.Config.RetrofitClientInstance;
import com.svalero.readyfy.Domain.Book;
import com.svalero.readyfy.Service.BookService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookUpdateActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor, etPublishedDate, etISBN;
    private Button btnUpdate;
    private BookService bookService;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPublishedDate = findViewById(R.id.etPublishedDate);
        etISBN = findViewById(R.id.etISBN);
        btnUpdate = findViewById(R.id.btnUpdate);

        bookService = RetrofitClientInstance.getRetrofitInstance().create(BookService.class);

        // Obtener el ID del libro a actualizar
        bookId = getIntent().getIntExtra("BOOK_ID", 0);

        // Cargar los datos del libro existente
        loadBookData(bookId);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook(bookId);
            }
        });
    }

    private void loadBookData(int id) {
        Call<Book> call = bookService.getBookById(id);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Book book = response.body();
                    etTitle.setText(book.getTitle());
                    etAuthor.setText(book.getAuthor());
                    etPublishedDate.setText(book.getPublishedDate());
                    etISBN.setText(book.getISBN());
                } else {
                    Toast.makeText(BookUpdateActivity.this, "Error al cargar datos del libro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookUpdateActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBook(int id) {
        String title = etTitle.getText().toString();
        String author = etAuthor.getText().toString();
        String publishedDate = etPublishedDate.getText().toString();
        String isbn = etISBN.getText().toString();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishedDate(publishedDate);
        book.setISBN(isbn);

        Call<Book> call = bookService.updateBook(id, book);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookUpdateActivity.this, "Libro actualizado con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(BookUpdateActivity.this, "Error al actualizar el libro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookUpdateActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
