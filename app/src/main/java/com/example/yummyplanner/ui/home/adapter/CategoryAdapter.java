package com.example.yummyplanner.ui.home.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.model.Category;

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(OnCategoryClickListener listener) {
        this.categories = new ArrayList<>();
        this.listener = listener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.categoryName.setText(category.getName());

        Glide.with(holder.itemView)
                .load(category.getThumbnail())
                .placeholder(R.drawable.plan1)
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}