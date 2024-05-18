package com.svalero.readyfy.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.readyfy.Config.RetrofitClientInstance;
import com.svalero.readyfy.R;
import com.svalero.readyfy.Service.BookService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDeleteActivity extends AppCompatActivity {

    private EditText etBookName;
    private Button btnConfirmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        etBookName = findViewById(R.id.etBookName);
        btnConfirmDelete = findViewById(R.id.btnConfirmDelete);

        btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = etBookName.getText().toString().trim();
                if (TextUtils.isEmpty(bookName)) {
                    Toast.makeText(BookDeleteActivity.this, "Please enter a book name", Toast.LENGTH_SHORT).show();
                } else {
                    // Add logic to delete the book from the database
                    deleteBookByName(bookName);
                }
            }
        });
    }

    private void deleteBookByName(String bookName) {

        // Crea una instancia de la interfaz de servicio Retrofit
        BookService service = RetrofitClientInstance.getRetrofitInstance().create(BookService.class);

        // Realiza la solicitud DELETE al servidor
        Call<Void> call = service.deleteBookByName(Integer.parseInt(bookName));

        // Maneja la respuesta de la solicitud
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // La solicitud se completó con éxito, el libro se eliminó
                    Toast.makeText(BookDeleteActivity.this, "Book deleted", Toast.LENGTH_SHORT).show();
                } else {
                    // La solicitud no se completó con éxito, el libro no se encontró o hubo un error en el servidor
                    Toast.makeText(BookDeleteActivity.this, "Book not found or server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Hubo un error de red o en la solicitud
                Toast.makeText(BookDeleteActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
