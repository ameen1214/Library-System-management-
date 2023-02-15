package com.example.librarysystemmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

public class book_detaill_activity extends AppCompatActivity {
    private CircularImageView profileiv ;
    private TextView NameBB , auteurBB , TokenB ;
    private  String bookID ;
    private  MydbHelperBOOK myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detaill);
        profileiv = findViewById(R.id.boookpic);
        NameBB  = findViewById(R.id.nameBB);
        auteurBB = findViewById(R.id.AuteurBB);
        TokenB=findViewById(R.id.tokenBB);

        Intent intent = getIntent();
        bookID = intent.getStringExtra("BOOK_ID");

        myDbHelper = new MydbHelperBOOK(this);

        showuserdetaill();
    }

    private void showuserdetaill() {
        String selectquery = "SELECT * FROM " + Constants.TABLE_BOOK
                + " WHERE " + Constants.BOOK_ID +" =\"" + bookID+"\"";

        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectquery,null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String id = ""+cursor.getInt(cursor.getColumnIndex(Constants.BOOK_ID));
                @SuppressLint("Range") String name = ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_NAME));
                @SuppressLint("Range") String author = ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_AUTEUR));
                @SuppressLint("Range") String token = ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_TOOKEN));
                @SuppressLint("Range") String image = ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_IMAGE));


                NameBB.setText(name);
                auteurBB.setText(author);
                TokenB.setText(token);

                if(image.equals("null")){
                    profileiv.setImageResource(R.drawable.ic_boook);
                } else {
                    profileiv.setImageURI(Uri.parse(image));
                }


            } while (cursor.moveToNext());
        }
        db.close();

    }
}