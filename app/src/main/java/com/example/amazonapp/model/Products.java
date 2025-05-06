package com.example.amazonapp.model;

public class Products {

    private String id;
    private String name;
    private String category;
    private double price;
    private String image;

    public Products() {

    }

    public Products(String id, String name, String category, double price, String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}

