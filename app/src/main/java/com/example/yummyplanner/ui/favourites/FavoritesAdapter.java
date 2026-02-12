package com.example.yummyplanner.ui.favourites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<MealdetailsItemModel> favoriteMeals;
    private final OnFavoriteClickListener listener;

    public interface OnFavoriteClickListener {
        void onRemoveClick(MealdetailsItemModel meal);
        void onItemClick(MealdetailsItemModel meal);
    }

    public FavoritesAdapter(OnFavoriteClickListener listener) {
        this.favoriteMeals = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealdetailsItemModel meal = favoriteMeals.get(position);
        holder.bind(meal, listener);
    }

    @Override
    public int getItemCount() {
        return favoriteMeals.size();
    }

    public void setList(List<MealdetailsItemModel> meals) {
        this.favoriteMeals = meals;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        TextView mealCategory;
        TextView mealCountry;
        ImageView mealCountryFlag;
        ImageView btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.iv_fav_poster);
            mealName = itemView.findViewById(R.id.tv_fav_name);
            mealCategory = itemView.findViewById(R.id.tv_fav_category);
            mealCountry = itemView.findViewById(R.id.tv_fav_meal_country);
            mealCountryFlag = itemView.findViewById(R.id.fav_meal_country_flag);
            btnRemove = itemView.findViewById(R.id.iv_remove);
        }

        public void bind(final MealdetailsItemModel meal, final OnFavoriteClickListener listener) {
            if (mealName != null) mealName.setText(meal.getName());
            if (mealCategory != null) mealCategory.setText(meal.getCategory());
            if (mealCountry != null) mealCountry.setText(meal.getArea());

            Glide.with(itemView.getContext())
                    .load(meal.getThumbNail())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(mealImage);

            Glide.with(itemView.getContext())
                    .load(meal.getCountryFlagUrl())
                    .into(mealCountryFlag);

            if (btnRemove != null) {
                btnRemove.setOnClickListener(v -> listener.onRemoveClick(meal));
            }

            itemView.setOnClickListener(v -> listener.onItemClick(meal));
        }
    }
}