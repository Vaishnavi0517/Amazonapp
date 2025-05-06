package com.example.amazonapp.constant;

import java.util.Random;

public class CouponGenerator {

    public static String generateCoupon() {
        Random random = new Random();
        StringBuilder coupon = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            coupon.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        return coupon.toString();
    }

    public static int calculateSum(String coupon) {
        int sum = 0;
        for (char c : coupon.toCharArray()) {
            sum += Character.getNumericValue(c);
        }
        return sum;
    }
}
