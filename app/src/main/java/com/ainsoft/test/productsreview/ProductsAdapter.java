package com.ainsoft.test.productsreview;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ainsoft.test.productsreview.model.Product;

import java.util.ArrayList;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyAdapter> {

    private Activity activity;
    private ArrayList<Product> productsList = new ArrayList<>();

    public ProductsAdapter(Activity activity, ArrayList<Product> productsList) {
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

        final int pos = position;
        holder.id.setText(productsList.get(position).getId());
        holder.name.setText(productsList.get(position).getName());
        holder.price.setText(productsList.get(position).getPrice());

        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment changePriceDialog = new ChangePriceDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Consts.SELECTED_ID, productsList.get(pos).getId());
                bundle.putString(Consts.SELECTED_PRICE, productsList.get(pos).getPrice());
                bundle.putInt(Consts.SELECTED_POSITION, pos);
                changePriceDialog.setArguments(bundle);
                changePriceDialog.show(activity.getFragmentManager(), "");
            }
        });
    }

    public void setNewPriceValue(int position, String newPrice) {
        productsList.get(position).setPrice(newPrice);
        notifyItemChanged(position);

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class MyAdapter extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView price;
        CardView itemCardView;

        MyAdapter(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
            itemCardView = itemView.findViewById(R.id.card_view);
        }
    }
}
