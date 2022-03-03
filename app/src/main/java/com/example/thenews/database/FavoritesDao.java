package com.example.thenews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.thenews.models.FavoriteArticle;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("Select * from favorites")
    LiveData<List<FavoriteArticle>> getAllFavoriteArticles();
    @Insert
    void insertFavoriteArticle(FavoriteArticle favoriteArticle);
    @Delete
    void deleteFavoriteArticle(FavoriteArticle favoriteArticle);
}
