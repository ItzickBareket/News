package com.example.thenews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thenews.views.NewsCategoriesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NewsCategoriesFragment()).commit();
    }
}