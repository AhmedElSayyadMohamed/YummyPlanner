package com.example.yummyplanner.ui.planner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;

import java.util.ArrayList;
import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.ViewHolder> {

    private List<PlannedMealEntity> plannedMeals = new ArrayList<>();
    private final OnPlannedMealClickListener listener;

    public interface OnPlannedMealClickListener {
        void onPlannedMealClick(PlannedMealEntity meal);
        void onDeleteClick(PlannedMealEntity meal);
    }

    public PlannerAdapter(OnPlannedMealClickListener listener) {
        this.listener = listener;
    }

    public void setPlannedMeals(List<PlannedMealEntity> meals) {
        this.plannedMeals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planned_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlannedMealEntity meal = plannedMeals.get(position);
        holder.mealName.setText(meal.getName());
        holder.mealDate.setText(meal.getPlannedDate());
        
        Glide.with(holder.itemView.getContext())
                .load(meal.getThumbnail())
                .placeholder(R.drawable.plan1)
                .into(holder.mealImage);

        holder.itemView.setOnClickListener(v -> listener.onPlannedMealClick(meal));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(meal));
    }

    @Override
    public int getItemCount() {
        return plannedMeals.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        TextView mealDate;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.iv_planner_poster);
            mealName = itemView.findViewById(R.id.tv_planner_name);
            mealDate = itemView.findViewById(R.id.tv_planner_date);
            btnDelete = itemView.findViewById(R.id.btn_delete_planner);
        }
    }
}
