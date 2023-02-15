package com.example.librarysystemmanagement;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(@Nullable Context context) {
        super(context, Constants.C_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);
    onCreate(db);
    }
    public  long insertUser (String name , String image ,String phone , String booktoken ,
                             String dateofbirth , String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_NAME,name);
        values.put(Constants.C_IMAGE,image);
        values.put(Constants.C_PHONE,phone);
        values.put(Constants.C_BOOKTOKEN,booktoken);
        values.put(Constants.C_DATEBIRTH,dateofbirth);
        values.put(Constants.C_EMAIL,email);

        long id = db.insert(Constants.TABLE_NAME,null,values);
        db.close();
        return id ;
    }


    public  ArrayList<Modeluser> getAllusers (String orderBy) {

    ArrayList<Modeluser> userList = new ArrayList<>();



    String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

    SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Modeluser modeluser = new Modeluser(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATEBIRTH)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_BOOKTOKEN)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)));
                userList.add(modeluser);

            }
            while (cursor.moveToNext());
        }
    db.close();


        return userList;
}

    public  ArrayList<Modeluser> SearchAllusers (String query) {




        ArrayList<Modeluser> userList = new ArrayList<>();



        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_NAME +
                " LIKE '%" + query + "%' " ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Modeluser modeluser = new Modeluser(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATEBIRTH)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_BOOKTOKEN)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)));
                userList.add(modeluser);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }
public void deleteuser (String id){
        SQLiteDatabase db = getReadableDatabase();
        db.delete(Constants.TABLE_NAME , Constants.C_ID + " = ?", new String[]{id});
        db.close();
}
    public  int getUserCount (){
        String countQuery= "SELECT * FROM " + Constants.TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count ;
    }
    public  void UpdateUser (String id , String name , String image ,String phone , String booktoken ,
                             String dateofbirth , String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_NAME,name);
        values.put(Constants.C_IMAGE,image);
        values.put(Constants.C_PHONE,phone);
        values.put(Constants.C_BOOKTOKEN,booktoken);
        values.put(Constants.C_DATEBIRTH,dateofbirth);
        values.put(Constants.C_EMAIL,email);

      db.update(Constants.TABLE_NAME,values, Constants.C_ID +" = ?", new String[]{id});
        db.close();

    }

}
