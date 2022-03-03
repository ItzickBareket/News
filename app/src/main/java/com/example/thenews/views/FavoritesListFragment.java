package com.example.thenews.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thenews.databinding.FragmentFavoritesListBinding;
import com.example.thenews.databinding.RvFavoritesItemBinding;
import com.example.thenews.models.FavoriteArticle;
import com.example.thenews.viewModels.ArticlesViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesListFragment extends Fragment {

    FragmentFavoritesListBinding binding;

    ArticlesViewModel articlesViewModel;

    List<FavoriteArticle> favoriteArticleList;

    RecyclerView.Adapter adapter;

    public FavoritesListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adapter = new FavoritesListAdapter();

        articlesViewModel = new ViewModelProvider(requireActivity()).get(ArticlesViewModel.class);

        articlesViewModel.getFavoriteArticleListFromRoomDataBase().observe(getViewLifecycleOwner(), favoriteArticles -> {

            favoriteArticleList = favoriteArticles;

            adapter.notifyDataSetChanged();
        });

        binding = FragmentFavoritesListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        binding.rvFavorites.setAdapter(adapter);

        binding.rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

        RvFavoritesItemBinding binding;

        @NonNull
        @Override
        public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            binding = RvFavoritesItemBinding.inflate(layoutInflater, parent, false);

            return new FavoritesViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {

            holder.bind(favoriteArticleList.get(position));
        }

        @Override
        public int getItemCount() { return favoriteArticleList.size(); }
    }

    private class FavoritesViewHolder extends RecyclerView.ViewHolder {

        RvFavoritesItemBinding itemBinding;

        public FavoritesViewHolder(@NonNull RvFavoritesItemBinding rvFavoritesItemBinding) {

            super(rvFavoritesItemBinding.getRoot());

            this.itemBinding = rvFavoritesItemBinding;
        }

        public void bind(FavoriteArticle favoriteArticle) {

            itemBinding.tvFavoriteTitle.setText(favoriteArticle.getTitle());

            itemBinding.tvFavoriteDescription.setText(favoriteArticle.getDescription());

            Picasso.get().load(favoriteArticle.getUrlToImage()).into(itemBinding.ivFavoriteImage);

            itemBinding.ivCancelFavorite.setOnClickListener(v-> articlesViewModel.removeFromFavorites(favoriteArticle));
        }
    }
}