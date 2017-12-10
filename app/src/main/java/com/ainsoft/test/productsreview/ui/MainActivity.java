package com.ainsoft.test.productsreview.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ainsoft.test.productsreview.R;
import com.ainsoft.test.productsreview.data.ProductsContract;
import com.ainsoft.test.productsreview.data.ProductsDbHelper;
import com.ainsoft.test.productsreview.model.Product;
import com.ainsoft.test.productsreview.model.Xml;
import com.ainsoft.test.productsreview.network.ApiService;
import com.ainsoft.test.productsreview.network.RestManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button_get_xml)
    Button getXmlButton;
    @BindView(R.id.button_view_data)
    Button viewDataButton;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.button_view_data)
    public void goToReview() {
        startActivity(new Intent(this, ReviewActivity.class));
    }
    @OnClick(R.id.button_get_xml)
    public void getXml() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService service = RestManager.getApiService();
        Call<Xml> call = service.getXml();
        call.enqueue(new Callback<Xml>() {
            @Override
            public void onResponse(@NonNull Call<Xml> call, @NonNull Response<Xml> response) {

                ProductsDbHelper dbHelper = new ProductsDbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                ArrayList<Product> products = response.body().getProducts().getProduct();

                if (products.size() > 0 )
                    db.delete(ProductsContract.ProductEntry.TABLE_NAME, null, null);

                for (Product product : products) {
                    Log.d("RESPONSE", product.getName());

                    values.put(ProductsContract.ProductEntry._ID, product.getId());
                    values.put(ProductsContract.ProductEntry.COLUMN_NAME, product.getName());
                    values.put(ProductsContract.ProductEntry.COLUMN_PRICE, product.getPrice());

                    db.insert(ProductsContract.ProductEntry.TABLE_NAME, null, values);

                }
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Xml> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Updating error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
