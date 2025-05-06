package com.example.amazonapp.model;

public class AddProductModel {

    String pid, name, price, description, category, img, date,time;
    public AddProductModel(){}

    public AddProductModel(String pid, String name, String price, String description,String category,
                           String img, String date, String time){

        this.pid=pid;
        this.name=name;
        this.price=price;
        this.description=description;
        this.category=category;
        this.img=img;
        this.date=date;
        this.time=time;

    }

    public String getName() {
        return name;
    }

    public String getPid() {
        return pid;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getImg() {
        return img;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
