package com.example.yummyplanner.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.model.Area;

import java.util.ArrayList;
import java.util.List;

public class CuisinesAdapter extends RecyclerView.Adapter<CuisinesAdapter.CuisinesViewHolder> {

    private List<Area> cuisines;
    private OnCuisineClickListener listener;

    public interface OnCuisineClickListener {
        void onCuisineClicked(Area cuisine);
    }

    public CuisinesAdapter(OnCuisineClickListener listener) {
        this.cuisines = new ArrayList<>();
        this.listener = listener;
    }

    public void setCuisines(List<Area> cuisines) {
        this.cuisines = cuisines;
        notifyDataSetChanged();
    }

    @Override
    public CuisinesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CuisinesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CuisinesViewHolder holder, int position) {
        Area cuisine = cuisines.get(position);

        holder.name.setText(cuisine.getName());

        Glide.with(holder.itemView)
                .load(cuisine.getFlagUrl()) // assuming Area has a getThumbnail() method
                .placeholder(R.drawable.plan1)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCuisineClicked(cuisine);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cuisines != null ? cuisines.size() : 0;
    }

    public static class CuisinesViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public CuisinesViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_country);
            name = itemView.findViewById(R.id.tv_country_name);
        }
    }
}
