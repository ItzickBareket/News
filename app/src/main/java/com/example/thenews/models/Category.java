package com.example.thenews.models;


public class Category {

    private String name, imageResourceId;

    public Category(String name, String imageResourceId) {

        this.name = name;

        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public String toString() {
        return "Category{" +
                ", name='" + name + '\'' +
                ", imageResourceId=" + imageResourceId +
                '}';
    }
}
