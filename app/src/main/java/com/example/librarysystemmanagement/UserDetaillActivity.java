package com.example.librarysystemmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

public class UserDetaillActivity extends AppCompatActivity {
private CircularImageView profileiv;
private TextView NameU , dateofbrithU , phoneU , booktokenU , emailU;
private  String userID;
private  MyDbHelper myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detaill);

        profileiv = findViewById(R.id.userpic);
        NameU = findViewById(R.id.nameUU);
        phoneU = findViewById(R.id.PhoneUU);
        dateofbrithU = findViewById(R.id.dateofbirthUU);
        booktokenU = findViewById(R.id.BooktokenUU);
        emailU = findViewById(R.id.emailUU);

        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        myDbHelper = new MyDbHelper(this);

        showuserdetaill();
    }

    private void showuserdetaill() {
        String selectquery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID +" =\"" + userID+"\"";

        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectquery,null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String id = ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                @SuppressLint("Range") String name = ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME));
                @SuppressLint("Range") String dateofbirth = ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATEBIRTH));
                @SuppressLint("Range") String email = ""+cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL));
                @SuppressLint("Range") String image = ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                @SuppressLint("Range") String phone = ""+cursor.getString(cursor.getColumnIndex(Constants.C_PHONE));
                @SuppressLint("Range") String token = ""+cursor.getString(cursor.getColumnIndex(Constants.C_BOOKTOKEN));

                NameU.setText(name);
                phoneU.setText(phone);
                booktokenU.setText(token);
                emailU.setText(email);
                dateofbrithU.setText(dateofbirth);
                if(image.equals("null")){
                    profileiv.setImageResource(R.drawable.ic_p);
                }
                else
                {
                    profileiv.setImageURI(Uri.parse(image));
                }


            } while (cursor.moveToNext());
        }
        db.close();

    }
}