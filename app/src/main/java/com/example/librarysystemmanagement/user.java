package com.example.librarysystemmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class user extends AppCompatActivity {
private FloatingActionButton addrecord ;
RecyclerView userRV ;
MyDbHelper myDbHelper;
ActionBar actionBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);



        addrecord=findViewById(R.id.addrecord);
        userRV = findViewById(R.id.userrv);
        myDbHelper = new MyDbHelper(this);
        LoadUsers ();
        addrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AdduserActivity.class);
                i.putExtra("isEditMode",false);
                startActivity(i);
            }
        });
    }

    private void LoadUsers() {

        Adapteruser adapteruser = new Adapteruser(user.this,
                myDbHelper.getAllusers(Constants.C_NAME + " ASC "));
        userRV.setAdapter(adapteruser);
    }

    private  void  searchUsers(String query){
        Adapteruser adapteruser = new Adapteruser(user.this,
                myDbHelper.SearchAllusers(query));
        userRV.setAdapter(adapteruser);
    }


    @Override
    protected void onResume() {
        super.onResume();
        LoadUsers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUsers(newText);
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