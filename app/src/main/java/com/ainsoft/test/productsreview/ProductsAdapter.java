package com.ainsoft.test.productsreview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ainsoft.test.productsreview.model.Product;
import com.ainsoft.test.productsreview.model.Products;

import java.util.ArrayList;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyAdapter> {

    private Activity activity;
    private ArrayList<Product> productsList = new ArrayList<>();

    public ProductsAdapter(Activity activity, ArrayList<Product> productsList){
        this.activity = activity;
        this.productsList = productsList;
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {
        holder.id.setText(productsList.get(position).getId());
        holder.name.setText(productsList.get(position).getName());
        holder.price.setText(productsList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView price;
        public MyAdapter(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
        }
    }
}
