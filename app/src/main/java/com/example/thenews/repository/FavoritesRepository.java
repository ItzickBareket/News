package com.example.thenews.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.thenews.R;
import com.example.thenews.database.FavoritesDao;
import com.example.thenews.database.FavoritesDatabase;
import com.example.thenews.models.Category;
import com.example.thenews.models.FavoriteArticle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class FavoritesRepository extends Application {

    private Context context;

    private static FavoritesRepository instance;

    private final FavoritesDao favoritesDao;

    private final LiveData<List<FavoriteArticle>> listOfFavoritesLiveData;

    private final MutableLiveData<List<Category>> categoryListFromJson = new MutableLiveData<>();

    private final MutableLiveData<List<Article>> articleListFromAPI = new MutableLiveData<>();

    private FavoritesRepository(Application application) {

        context = application.getApplicationContext();

        FavoritesDatabase favoritesDatabase = FavoritesDatabase.getInstance(application);

        favoritesDao = favoritesDatabase.favoritesDao();

        listOfFavoritesLiveData = favoritesDao.getAllFavoriteArticles();

        setCategoryListFromJson();
    }

    public static synchronized FavoritesRepository getInstance(Application application){

        if (instance == null) {

            instance = new FavoritesRepository(application);
        }
        return instance;
    }

    public void setCategoryListFromJson(){

        Gson gson = new Gson();

        try{
            InputStream inputStream = context.getAssets().open("Categories.Json");

            int size = inputStream.available();

            byte[] buffer = new byte[size];

            inputStream.read(buffer);

            inputStream.close();

            String text = new String(buffer);

            Type categoryTypeList = new TypeToken<List<Category>>(){}.getType();

            categoryListFromJson.postValue(gson.fromJson(text,categoryTypeList));
        }
        catch (IOException e){
            // Should never happen!
            throw new RuntimeException(e);
        }
    }

    public LiveData<List<Category>> getCategoryListFromJson(){

        return categoryListFromJson;
    }

    public void setArticleListFromAPI(String chosenCategoryName) {

        articleListFromAPI.postValue(null);

        NewsApiClient newsApiClient = new NewsApiClient(context.getString(R.string.API_KEY));

        newsApiClient.getEverything(new EverythingRequest.Builder().q(chosenCategoryName).build(),

                new NewsApiClient.ArticlesResponseCallback() {

                    @Override
                    public void onSuccess(ArticleResponse articleResponse) {

                        articleListFromAPI.postValue(articleResponse.getArticles());
                    }
                    @Override
                    public void onFailure(Throwable throwable) {

                        System.out.println(throwable.toString());
                    }
                });
    }

    public LiveData<List<Article>> getArticleListFromAPI(){
        return articleListFromAPI;
    }

    public LiveData<List<FavoriteArticle>> getListOfFavoritesLiveData(){

        return listOfFavoritesLiveData;
    }

    public void addToFavorites(FavoriteArticle favoriteArticle){

        FavoritesDatabase.databaseWriteExecutor.execute(() ->

            favoritesDao.insertFavoriteArticle(favoriteArticle));
    }

    public void removeFromFavorites(FavoriteArticle favoriteArticle){

        FavoritesDatabase.databaseWriteExecutor.execute(() ->

                favoritesDao.deleteFavoriteArticle(favoriteArticle));
    }
}
