package com.ainsoft.test.productsreview.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "product")
public class Product {
    @Element(name = "id")
    private String id;

    @Element(name = "price")
    private String price;

    @Element(name = "name")
    private String name;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

}
