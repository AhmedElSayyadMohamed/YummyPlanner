package com.example.yummyplanner.data.model;

public class Category {

     private long id;
     private String name;
     private String description;
     private String imageUrl;

     public Category(long id, String name,String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public void setId(){
        this.id = id;
    }

    public void setName(){
        this.name = name;
    }

    public  void setDescription(){
        this.description = description;
    }

    public void setImageUrl(){
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public  String getDescription(){
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
