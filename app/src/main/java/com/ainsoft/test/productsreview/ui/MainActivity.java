package com.ainsoft.test.productsreview.ui;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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
    @BindView(R.id.get_xml_button)
    Button getXmlButton;
    @BindView(R.id.view_data_button)
    Button viewDataButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.get_xml_button)
    public void getXml() {
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
                    db.execSQL("DROP TABLE " + ProductsContract.ProductEntry.TABLE_NAME);

                for (Product product : products) {
                    Log.d("RESPONSE", product.getName());

                    values.put(ProductsContract.ProductEntry._ID, product.getId());
                    values.put(ProductsContract.ProductEntry.COLUMN_NAME, product.getName());
                    values.put(ProductsContract.ProductEntry.COLUMN_PRICE, product.getPrice());

                    db.insert(ProductsContract.ProductEntry.TABLE_NAME, null, values);

                }

            }

            @Override
            public void onFailure(@NonNull Call<Xml> call, @NonNull Throwable t) {

            }
        });
    }
}
