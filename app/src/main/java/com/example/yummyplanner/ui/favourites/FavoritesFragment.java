package com.example.yummyplanner.ui.favourites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;

import java.util.List;


public class FavoritesFragment extends Fragment implements FavoritesContract.View {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void displayFavorites(List<MealdetailsItemModel> favorites) {

    }

    @Override
    public void showEmptyState() {

    }

    @Override
    public void showError(String message) {

    }
}