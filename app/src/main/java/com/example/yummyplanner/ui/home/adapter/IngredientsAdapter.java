//package com.example.yummyplanner.ui.home.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.yummyplanner.R;
//
//import com.example.yummyplanner.data.meals.model.Ingredient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class IngredientsAdapter RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>{
//
//private List<Ingredient> ingredients;
//private OnIngredientClickListener listener;
//
//public interface OnIngredientClickListener {
//    void onIngredientClick(Ingredient ingredient);
//}
//
//public IngredientsAdapter(OnIngredientClickListener listener) {
//    this.ingredients = new ArrayList<>();
//    this.listener = listener;
//}
//
//public void setingredients(List<Ingredient> ingredients) {
//    this.ingredients = ingredients;
//    notifyDataSetChanged();
//}
//
//@Override
//public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ite, parent, false);
//    return new IngredientViewHolder(view);
//}
//
//@Override
//public void onBindViewHolder(com.example.yummyplanner.ui.home.adapter.CategoryAdapter.CategoryViewHolder holder, int position) {
//    Ingredient category = ingredients.get(position);
//
//    holder.categoryName.setText(category.getName());
//
//    Glide.with(holder.itemView)
//            .load(category.getThumbnail())
//            .placeholder(R.drawable.plan1)
//            .into(holder.categoryImage);
//
//    holder.itemView.setOnClickListener(v -> {
//        if (listener != null) {
//            listener.onCategoryClick(category);
//        }
//    });
//}
//
//@Override
//public int getItemCount() {
//    return categories != null ? categories.size() : 0;
//}
//
//public class IngredientViewHolder extends RecyclerView.ViewHolder {
//    ImageView image;
//    TextView Name;
//
//    public CategoryViewHolder(View itemView) {
//        super(itemView);
//        image = itemView.findViewById(R.id.categoryImage);
//        name = itemView.findViewById(R.id.categoryName);
//    }
//}
//    }
//            }
