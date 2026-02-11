package com.example.yummyplanner.ui.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.model.response.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients;
    private OnIngredientClickListener listener;

    public interface OnIngredientClickListener {
        void onIngredientClick(Ingredient ingredient);
    }

    public IngredientsAdapter(OnIngredientClickListener listener) {
        this.ingredients = new ArrayList<>();
        this.listener = listener;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.name.setText(ingredient.getName());
        holder.quantity.setText(ingredient.getQuantity() != null ? ingredient.getQuantity() : "");

        Glide.with(holder.itemView.getContext())
                .load(ingredient.getThumbnail())
                .placeholder(R.drawable.plan1)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onIngredientClick(ingredient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView quantity;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgIngredient);
            name = itemView.findViewById(R.id.tvIngredientName);
            quantity = itemView.findViewById(R.id.tvIngredientQuantity);
        }
    }
}
