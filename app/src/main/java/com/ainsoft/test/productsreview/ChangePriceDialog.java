package com.ainsoft.test.productsreview;


import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ainsoft.test.productsreview.data.ProductsContract;
import com.ainsoft.test.productsreview.data.ProductsDbHelper;

public class ChangePriceDialog extends DialogFragment implements View.OnClickListener {

    private EditText priceEditText;
    private String selectedId;
    private Bundle args;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    NoticeDialogListener dialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            dialogListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Change price");
        View view = inflater.inflate(R.layout.dialog_price_change_, null);
        view.findViewById(R.id.button_cancel_change_price).setOnClickListener(this);
        view.findViewById(R.id.button_change_price).setOnClickListener(this);

        priceEditText = view.findViewById(R.id.edit_text_new_price);

        selectedId = "";
        String selectedPrice = "";
        args = getArguments();
        if (args != null) {
            selectedId = args.getString(Consts.SELECTED_ID);
            selectedPrice = args.getString(Consts.SELECTED_PRICE);
        }

        priceEditText.setText(selectedPrice);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel_change_price:
                dismiss();
                break;
            case R.id.button_change_price:
                String newPrice = priceEditText.getText().toString();
                args.putString(Consts.NEW_PRICE, newPrice);

                ProductsDbHelper dbHelper = new ProductsDbHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put(ProductsContract.ProductEntry.COLUMN_PRICE, newPrice);

                db.update(ProductsContract.ProductEntry.TABLE_NAME,
                        cv,
                        ProductsContract.ProductEntry._ID + " = ?",
                        new String[]{selectedId});
                dialogListener.onDialogPositiveClick(ChangePriceDialog.this);
                dismiss();
                break;

        }
    }
}
