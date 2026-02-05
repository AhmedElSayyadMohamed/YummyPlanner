package com.example.yummyplanner.ui.launcher.onboarding.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.yummyplanner.R;
import com.example.yummyplanner.ui.launcher.onboarding.model.OnboardingItem;

import java.util.List;

public class OnboardingAdapter
        extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private final List<OnboardingItem> items;

    public OnboardingAdapter(List<OnboardingItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboarding, parent, false);

        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull OnboardingViewHolder holder, int position) {

        OnboardingItem item = items.get(position);

        holder.animation.setAnimation(item.lottieFile);
        holder.animation.playAnimation();
        holder.title.setText(item.title);
        holder.description.setText(item.description);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {

        LottieAnimationView animation;
        TextView title;
        TextView description;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            animation = itemView.findViewById(R.id.onboarding_animation);
            title = itemView.findViewById(R.id.onboarding_item_title);
            description = itemView.findViewById(R.id.onboarding_item_description);
        }
    }
}
