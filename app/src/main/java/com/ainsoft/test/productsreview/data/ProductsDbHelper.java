package com.ainsoft.test.productsreview.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public ProductsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductsContract.ProductEntry.TABLE_NAME + " ("

                + ProductsContract.ProductEntry._ID + " TEXT PRIMARY KEY, "
                + ProductsContract.ProductEntry.COLUMN_NAME + " TEXT, "
                + ProductsContract.ProductEntry.COLUMN_PRICE + " INTEGER );";

        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
