package com.example.librarysystemmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button btn , btnbooks ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= findViewById(R.id.btnUsers);
        btnbooks= findViewById(R.id.goTObooks);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),user.class);
                startActivity(i);
            }
        });
        btnbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),books.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        MenuItem item = menu.findItem(R.id.addmaps);
        MenuItem item1 = menu.findItem(R.id.seetings);
       item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               Intent intent = new Intent(getApplicationContext(),ActivityMaps.class);
               startActivity(intent);
               return false;
           }
       });
       item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               Intent intent = new Intent(getApplicationContext(),Webview.class);
               startActivity(intent);
               return false;
           }
       });
        return super.onCreateOptionsMenu(menu);


    }
}