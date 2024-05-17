package com.svalero.readyfy;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.readyfy.Adapter.BookAdapter;
import com.svalero.readyfy.Config.RetrofitClientInstance;
import com.svalero.readyfy.Domain.Book;
import com.svalero.readyfy.Service.BookService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener los datos
        BookService service = RetrofitClientInstance.getRetrofitInstance().create(BookService.class);
        Call<List<Book>> call = service.getBooks();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    bookAdapter = new BookAdapter(books);
                    recyclerView.setAdapter(bookAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
