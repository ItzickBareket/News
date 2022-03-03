package com.example.thenews.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.thenews.models.FavoriteArticle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FavoriteArticle.class},version = 1,exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;

    private static final String DB_NAME = "favorites_db";

    private static FavoritesDatabase instance;

    public abstract FavoritesDao favoritesDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized FavoritesDatabase getInstance(Context context){

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),FavoritesDatabase.class,DB_NAME)

                    .fallbackToDestructiveMigration()

                    .build();
        }
        return instance;
    }
}
