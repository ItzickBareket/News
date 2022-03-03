package com.example.thenews.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorites")
public class FavoriteArticle {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title, description, url, urlToImage;

    public FavoriteArticle(String title, String description, String url, String urlToImage) {

        this.title = title;

        this.description = description;

        this.url = url;

        this.urlToImage = urlToImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    @Override
    public String toString() {
        return "FavoriteArticle{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                '}';
    }
}
