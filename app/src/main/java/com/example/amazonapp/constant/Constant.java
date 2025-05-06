package com.example.amazonapp.constant;

import com.example.amazonapp.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Constant {

    public static final List<Integer> QUANTITY_LIST = new ArrayList<>();

    static {
        for (int i = 1; i < 11; i++) {
            QUANTITY_LIST.add(i);
        }
    }

    public static final Product PRODUCT1 = new Product(1, "Titan", BigDecimal.valueOf(3000),
            "Titan Metal \nMechanical Blue \nDial Analog Stainless n Steel Strap Watch for Men",
            "titan");

    public static final Product PRODUCT2 = new Product(2, "Elvfnlik", BigDecimal.valueOf(4500),
            "Smart Watches\n for Women, Smart Watch for\n Iphone Compatible, Android\n Smart Watches Women,1.4 Inches" +
                    "Full Touch Screen\n with Ip68 Waterproof\n Fitness Tracker With\n Heart Rate,Sleep Monitor, Purple", "elvfnlik");


    public static final Product PRODUCT3 = new Product(3, "Fireboltt", BigDecimal.valueOf(1049),
            "Legend Bluetooth \nCalling Smartwatch with\n Dual Button Technology", "Fireboltt");


    public static final List<Product> PRODUCT_LIST = new ArrayList<Product>();

    static{
        PRODUCT_LIST.add(PRODUCT1);
        PRODUCT_LIST.add(PRODUCT2);
        PRODUCT_LIST.add(PRODUCT3);

    }

    public static final String CURRENCY = "₹";





}
