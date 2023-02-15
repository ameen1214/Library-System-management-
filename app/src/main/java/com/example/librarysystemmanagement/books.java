package com.example.librarysystemmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class books extends AppCompatActivity {
    private FloatingActionButton addbook ;
    RecyclerView bookRV ;
    MydbHelperBOOK myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        addbook=findViewById(R.id.addrbook);
        bookRV = findViewById(R.id.bookrv);
        myDbHelper = new MydbHelperBOOK(this);
        Loadbooks();

        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddBookActivity.class);
                startActivity(i);
            }
    });


    }
    private void Loadbooks() {
        Adapterbook adapterbook = new Adapterbook(books.this,
                myDbHelper.getAllbooks(Constants.BOOK_NAME + " ASC "));
        bookRV.setAdapter(adapterbook);
    }
    private void searchbooks(String query) {
        Adapterbook adapterbook = new Adapterbook(books.this,
                myDbHelper.SearchAllbooks(query));
        bookRV.setAdapter(adapterbook);
    }





    @Override
    protected void onResume() {
        super.onResume();
        Loadbooks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchbooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchbooks(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    }


