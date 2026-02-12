package com.example.yummyplanner.ui.favourites;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.repository.MealRepositoryImpl;
import com.example.yummyplanner.databinding.FragmentFavoritesBinding;
import com.example.yummyplanner.ui.favourites.presenter.FavoritesPresenter;
import com.example.yummyplanner.ui.favourites.presenter.FavoritesContract;

import java.util.List;

public class FavoritesFragment extends Fragment implements FavoritesContract.View, FavoritesAdapter.OnFavoriteClickListener {

    private FragmentFavoritesBinding binding;
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private FavoritesContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.rvFavoriteMeals;
        setupRecyclerView();

        presenter = new FavoritesPresenter(
                this,
                MealRepositoryImpl.getInstance(getContext())
        );

        presenter.getFavorites();
    }

    private void setupRecyclerView() {
        adapter = new FavoritesAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayFavorites(List<MealdetailsItemModel> favorites) {
        if (favorites != null && !favorites.isEmpty()) {
            adapter.setList(favorites);
            recyclerView.setVisibility(View.VISIBLE);
            binding.errorContainer.setVisibility(View.GONE);
        } else {
            showEmptyState();
        }
    }

    @Override
    public void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
        binding.errorContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        binding.loadingContainer.setVisibility(View.VISIBLE);
        binding.loadingLottie.setVisibility(View.VISIBLE);
        binding.loadingLottie.playAnimation();

    }

    @Override
    public void hideLoading() {
        binding.loadingLottie.cancelAnimation();
        binding.loadingContainer.setVisibility(View.GONE);
    }

    @Override
    public void onRemoveClick(MealdetailsItemModel meal) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to remove this meal from your favorites?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    if (presenter != null) {
                        presenter.deleteFromFavorites(meal);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onItemClick(MealdetailsItemModel meal) {
        FavoritesFragmentDirections.ActionFavoritesFragmentToMealDetailsFragment action =
                FavoritesFragmentDirections.actionFavoritesFragmentToMealDetailsFragment(meal.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
