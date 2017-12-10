package com.ainsoft.test.productsreview.data;


public class ProductsContract {

    private ProductsContract() {
    }

    public static final class ProductEntry {

        public final static String TABLE_NAME = "products";

        public final static String _ID = "id";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_PRICE = "price";

    }
}
