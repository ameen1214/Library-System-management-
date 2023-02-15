package com.example.librarysystemmanagement;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MydbHelperBOOK extends SQLiteOpenHelper {
    public MydbHelperBOOK(@Nullable Context context) {
        super(context, Constants.BOOK_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLEBOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_BOOK);
        onCreate(db);
    }

    public long insertBook(String name, String image, String auteur, String token) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.BOOK_NAME, name);
        values.put(Constants.BOOK_IMAGE, image);
        values.put(Constants.BOOK_AUTEUR, auteur);
        values.put(Constants.BOOK_TOOKEN, token);


        long id = db.insert( Constants.TABLE_BOOK , null, values);
        db.close();
        return id ;

    }

    public ArrayList<Modelbook> getAllbooks(String orderBy) {

        ArrayList<Modelbook> bookList = new ArrayList<>();




        String selectQuery ="SELECT * FROM " +Constants.TABLE_BOOK + " ORDER BY "+ orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
             @SuppressLint("Range") Modelbook modelbook = new Modelbook(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.BOOK_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_AUTEUR)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_TOOKEN)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_IMAGE)));
                bookList.add(modelbook);

            }while (cursor.moveToNext());
        }
        db.close();


        return bookList;
    }
    public void deletebook (String id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(Constants.TABLE_BOOK, Constants.BOOK_ID + " = ?", new String[]{id});
        db.close();
    }



    public  void UpdateBook (String id , String name , String image ,String author , String booktoken
                             ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.BOOK_NAME,name);
        values.put(Constants.BOOK_IMAGE,image);
        values.put(Constants.BOOK_TOOKEN,booktoken);
        values.put(Constants.BOOK_AUTEUR,author);

        db.update(Constants.TABLE_BOOK,values, Constants.BOOK_ID +" = ?", new String[]{id});
        db.close();

    }




    public  ArrayList<Modelbook> SearchAllbooks (String query) {




        ArrayList<Modelbook> bookList = new ArrayList<>();



        String selectQuery = "SELECT * FROM " + Constants.TABLE_BOOK + " WHERE " + Constants.BOOK_NAME +
                " LIKE '%" + query + "%' " ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Modelbook modelbook = new Modelbook(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.BOOK_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_AUTEUR)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_TOOKEN)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BOOK_IMAGE)));
                bookList.add(modelbook);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return bookList;
    }
}