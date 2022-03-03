package com.example.thenews.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thenews.databinding.FragmentArticlesListBinding;
import com.example.thenews.databinding.RvArticlesItemBinding;
import com.example.thenews.models.FavoriteArticle;
import com.example.thenews.viewModels.ArticlesViewModel;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticlesListFragment extends Fragment {

    FragmentArticlesListBinding binding;

    ArticlesViewModel articlesViewModel;

    List<Article> articleList;

    public ArticlesListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        articlesViewModel = new ViewModelProvider(requireActivity()).get(ArticlesViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentArticlesListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        articlesViewModel.getArticleListFromApi().observe(getViewLifecycleOwner(), articles -> {

            if(articles!=null){

                articleList = articles;

                binding.rvArticles.setAdapter(new ArticlesListAdapter());

                binding.rvArticles.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }

    private class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesViewHolder> {

        RvArticlesItemBinding binding;

        @NonNull
        @Override
        public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            binding = RvArticlesItemBinding.inflate(layoutInflater, parent, false);

            return new ArticlesViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {

            holder.bind(articleList.get(position));
        }

        @Override
        public int getItemCount() { return articleList.size(); }
    }

    private class ArticlesViewHolder extends RecyclerView.ViewHolder {

        RvArticlesItemBinding itemBinding;

        public ArticlesViewHolder(@NonNull RvArticlesItemBinding rvArticlesItemBinding) {

            super(rvArticlesItemBinding.getRoot());

            this.itemBinding = rvArticlesItemBinding;
        }

        public void bind(Article article) {

            itemBinding.tvArticleTitle.setText(article.getTitle());

            itemBinding.tvArticleSource.setText(article.getSource().getName());

            Picasso.get().load(article.getUrlToImage()).into(itemBinding.ivArticleImage);

            itemBinding.ivFavorite.setOnClickListener(v->{

                itemBinding.ivFavorite.setVisibility(View.GONE);

                addToFavorites(article);
            });
        }
    }

    private void addToFavorites(Article article) {

        FavoriteArticle favoriteArticle = new FavoriteArticle(article.getTitle(), article.getDescription(), article.getUrl(), article.getUrlToImage());

        articlesViewModel.addToFavorites(favoriteArticle);

        Toast.makeText(getContext(), "Added to Favorites :)", Toast.LENGTH_SHORT).show();
    }
}