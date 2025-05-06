package com.example.amazonapp.model;

public class WeatherResponse {

    private Main main;
    private String name;
    private Sys sys;

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public Sys getSys() {
        return sys;
    }

    public static class Main {
        private double temp;

        public double getTemp() {
            return temp;
        }
    }

    public static class Sys {
        private String country;

        public String getCountry() {
            return country;
        }
    }
}
