package com.ainsoft.test.productsreview.model;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "products")
public class Products {

    @ElementList(inline = true)
    private ArrayList<Product> product;

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }
}
