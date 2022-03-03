package com.example.thenews.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.thenews.models.Category;
import com.example.thenews.models.FavoriteArticle;
import com.example.thenews.repository.FavoritesRepository;
import com.kwabenaberko.newsapilib.models.Article;

import java.util.List;

public class ArticlesViewModel extends AndroidViewModel {

    private final FavoritesRepository favoritesRepository;

    private final LiveData<List<Category>> categoriesListFromJson;

    private final LiveData<List<Article>> articleListFromAPI;

    private final LiveData<List<FavoriteArticle>> favoriteArticleListFromRoomDataBase;

    public ArticlesViewModel(Application application) {

        super(application);

        favoritesRepository = FavoritesRepository.getInstance(application);

        favoriteArticleListFromRoomDataBase = favoritesRepository.getListOfFavoritesLiveData();

        categoriesListFromJson = favoritesRepository.getCategoryListFromJson();

        articleListFromAPI = favoritesRepository.getArticleListFromAPI();
    }

    public void setArticleListFromAPI(String chosenCategoryName){

        favoritesRepository.setArticleListFromAPI(chosenCategoryName);
    }

    public LiveData<List<Article>> getArticleListFromApi() {

        return articleListFromAPI;
    }

    public LiveData<List<FavoriteArticle>> getFavoriteArticleListFromRoomDataBase() {

        return favoriteArticleListFromRoomDataBase;
    }

    public LiveData<List<Category>> getCategoriesListFromJson() {

        return categoriesListFromJson;
    }

    public void addToFavorites(FavoriteArticle favoriteArticle){

        favoritesRepository.addToFavorites(favoriteArticle);
    }

    public void removeFromFavorites(FavoriteArticle favoriteArticle){

        favoritesRepository.removeFromFavorites(favoriteArticle);
    }
}
