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

        holder.mealCategory.setText(meal.getCategory() != null ? meal.getCategory() : "-");

        holder.mealCountry.setText(meal.getArea() != null ? meal.getArea() : "-");

        Glide.with(holder.itemView.getContext())
                .load(meal.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.plan1)
                .error(R.drawable.plan1)
                .into(holder.mealImage);

//        Glide.with(holder.itemView.getContext())
//                .load(meal.getCountryFlagUrl())
//                .centerCrop()
//                .placeholder(R.drawable.flag)
//                .error(R.drawable.ic_error)
//                .into(holder.countryFlag);

        String flagUrl = meal.getCountryFlagUrl();
        if(flagUrl != null && !flagUrl.isEmpty()){
            Glide.with(holder.itemView.getContext())
                    .load(flagUrl)
                    .centerCrop()
                    .placeholder(R.drawable.flag)
                    .error(R.drawable.flag)
                    .into(holder.countryFlag);
        }else{
            holder.countryFlag.setImageResource(R.drawable.flag);
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
