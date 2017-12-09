package com.ainsoft.test.productsreview.ui;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ainsoft.test.productsreview.ProductsAdapter;
import com.ainsoft.test.productsreview.R;
import com.ainsoft.test.productsreview.data.ProductsContract;
import com.ainsoft.test.productsreview.data.ProductsDbHelper;
import com.ainsoft.test.productsreview.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView productsRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        ProductsAdapter adapter = new ProductsAdapter(this, getProductsList());
        productsRecView.setLayoutManager(new LinearLayoutManager(this));
        productsRecView.setAdapter(adapter);

    }

    private ArrayList<Product> getProductsList() {
        ArrayList<Product> productArrayList = new ArrayList<>();

        ProductsDbHelper dbHelper = new ProductsDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ProductsContract.ProductEntry._ID,
                ProductsContract.ProductEntry.COLUMN_NAME,
                ProductsContract.ProductEntry.COLUMN_PRICE };

        Cursor cursor = db.query(
                ProductsContract.ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int idColumnIndex = cursor.getColumnIndex(ProductsContract.ProductEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ProductsContract.ProductEntry.COLUMN_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductsContract.ProductEntry.COLUMN_PRICE);

            while (cursor.moveToNext()) {
                Product currentProduct = new Product();

                currentProduct.setId(cursor.getString(idColumnIndex));
                currentProduct.setName(cursor.getString(nameColumnIndex));
                currentProduct.setPrice(cursor.getString(priceColumnIndex));

                productArrayList.add(currentProduct);
            }

        } finally {
            cursor.close();
        }

        return productArrayList;
    }
}
