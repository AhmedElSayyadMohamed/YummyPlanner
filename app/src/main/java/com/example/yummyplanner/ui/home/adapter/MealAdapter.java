package com.example.yummyplanner.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.model.MealItemModel;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<MealItemModel> meals = new ArrayList<>();
    private OnMealClickListener listener;
    private String currentCategoryFilter;

    public interface OnMealClickListener {
        void onMealClick(MealItemModel meal);
    }

    public MealAdapter(OnMealClickListener listener) {
        this.meals = new ArrayList<>();
        this.listener = listener;
    }

    public void setMeals(List<MealItemModel> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }
    
    public void setMeals(List<MealItemModel> meals, String categoryFilter) {
        this.meals = meals;
        this.currentCategoryFilter = categoryFilter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealItemModel meal = meals.get(position);

        holder.mealName.setText(meal.getName());

        // Use category from meal or fall back to the filter name
        String category = meal.getCategory();
        if (category == null || category.isEmpty()) {
            category = currentCategoryFilter;
        }
        holder.mealCategory.setText(category != null ? category : "");
        holder.mealCategory.setVisibility(category != null ? View.VISIBLE : View.GONE);

        holder.mealCountry.setText(meal.getArea() != null ? meal.getArea() : "");
        holder.mealCountry.setVisibility(meal.getArea() != null ? View.VISIBLE : View.GONE);

        Glide.with(holder.itemView.getContext())
                .load(meal.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.plan1)
                .error(R.drawable.plan1)
                .into(holder.mealImage);

        String flagUrl = meal.getCountryFlagUrl();
        if(flagUrl != null && !flagUrl.isEmpty()){
            holder.countryFlag.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext())
                    .load(flagUrl)
                    .centerCrop()
                    .placeholder(R.drawable.flag)
                    .error(R.drawable.flag)
                    .into(holder.countryFlag);
        }else{
            holder.countryFlag.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAbsoluteAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && listener != null) {
                listener.onMealClick(meals.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImage;
        ImageView countryFlag;
        TextView mealName;
        TextView mealCategory;
        TextView mealCountry;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.meal_image);
            countryFlag = itemView.findViewById(R.id.meal_country_flag);
            mealName = itemView.findViewById(R.id.tv_popular_meal_name);
            mealCategory = itemView.findViewById(R.id.tv_meal_category_type);
            mealCountry = itemView.findViewById(R.id.tv_popular_meal_country);

        }
    }
}
