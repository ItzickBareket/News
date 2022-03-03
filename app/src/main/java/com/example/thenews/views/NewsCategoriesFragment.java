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

import com.example.thenews.R;
import com.example.thenews.databinding.FragmentNewsCategoriesBinding;
import com.example.thenews.databinding.RvCategoriesItemBinding;
import com.example.thenews.models.Category;
import com.example.thenews.viewModels.ArticlesViewModel;

import java.util.List;

public class NewsCategoriesFragment extends Fragment {

    FragmentNewsCategoriesBinding binding;

    ArticlesViewModel articlesViewModel;

    List<Category> categoryList;

    public NewsCategoriesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        articlesViewModel = new ViewModelProvider(requireActivity()).get(ArticlesViewModel.class);

        binding = FragmentNewsCategoriesBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        articlesViewModel.getCategoriesListFromJson().observe(getViewLifecycleOwner(), categories -> {

            if(categories.size()>0) {

                categoryList = categories;

                binding.rvCategories.setAdapter(new NewsCategoriesRecyclerAdapter());

                binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));

                binding.favoritesFab.setOnClickListener(v -> gotoFavorites());
            }
        });
    }

    private void gotoFavorites() {

            articlesViewModel.getFavoriteArticleListFromRoomDataBase().observe(getViewLifecycleOwner(), favoriteArticles -> {

                if (favoriteArticles.size() > 0) {

                    getActivity().getSupportFragmentManager().beginTransaction()

                            .replace(R.id.fragmentContainer, new FavoritesListFragment()).addToBackStack("text").commit();
                }
                else Toast.makeText(getContext(), "Currently, you do not have any favorites saved", Toast.LENGTH_SHORT).show();
            });
    }

    private class NewsCategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesViewHolder>{

       RvCategoriesItemBinding binding;

       @NonNull
       @Override
       public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

           LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

           binding = RvCategoriesItemBinding.inflate(layoutInflater,parent,false);

           return new CategoriesViewHolder(binding);
       }

       @Override
       public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

           holder.bind(categoryList.get(position));
       }

       @Override
       public int getItemCount() { return categoryList.size(); }
   }

    private class CategoriesViewHolder extends RecyclerView.ViewHolder {

        RvCategoriesItemBinding itemBinding;

        public CategoriesViewHolder(@NonNull RvCategoriesItemBinding rvCategoriesItemBinding) {

            super(rvCategoriesItemBinding.getRoot());

            this.itemBinding = rvCategoriesItemBinding;
        }

        public void bind(Category category){

            itemBinding.tvCategoryName.setText(category.getName());

            int imageInt= getContext().getResources().getIdentifier(category.getImageResourceId(), "drawable",getContext().getPackageName());

            itemBinding.ivCategoryImage.setImageResource(imageInt);

            itemBinding.getRoot().setOnClickListener(v-> categoryChosen(category.getName()));
        }
        private void categoryChosen(String categoryName){

            articlesViewModel.setArticleListFromAPI(categoryName);

            articlesViewModel.getArticleListFromApi().observe(getViewLifecycleOwner(), articles -> {

                if(articles!=null)

                getActivity().getSupportFragmentManager().beginTransaction()

                        .replace(R.id.fragmentContainer, new ArticlesListFragment()).addToBackStack("text").commit();
            });
        }
    }
}