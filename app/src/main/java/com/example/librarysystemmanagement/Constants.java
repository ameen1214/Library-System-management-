package com.example.librarysystemmanagement;

public class Constants {
    public static final String DB_NAME = "MY_LIBRARY_DB" ;

    public static final int DB_VERSION = 32 ;
    public static final String TABLE_NAME = "MY_USERS_TABLE";

    public static final String TABLE_BOOK = "MY_BOOK_TABLE";

    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_IMAGE="IMAGE";
    public static final String C_PHONE = "PHONE";

    public static final String BOOK_ID = "ID";
    public static final String BOOK_NAME = "NAME";
    public static final String BOOK_IMAGE="IMAGE";
    public static final String BOOK_AUTEUR = "AUTEUR";
    public static final String BOOK_TOOKEN = "TOKEN";


    public static final String C_BOOKTOKEN = "BOOKSTOKEN";
    public static final String C_EMAIL = "EMAIL";
    public static final String C_DATEBIRTH = "DATEOFBIRTHDAY";
    public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_PHONE + " TEXT,"
            + C_BOOKTOKEN + " TEXT,"
            + C_DATEBIRTH + " TEXT,"
            + C_EMAIL + " TEXT"
            + ")" ;

    public static final String CREATE_TABLEBOOK = " CREATE TABLE " + TABLE_BOOK + "("
            + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BOOK_NAME + " TEXT,"
            + BOOK_IMAGE + " TEXT,"
            + BOOK_AUTEUR + " TEXT,"
            + BOOK_TOOKEN + " TEXT"
            + ")" ;

}
