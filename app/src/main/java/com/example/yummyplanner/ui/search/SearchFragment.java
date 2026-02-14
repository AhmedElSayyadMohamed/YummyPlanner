package com.example.yummyplanner.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.repository.MealRepositoryImpl;
import com.example.yummyplanner.databinding.FragmentSearchBinding;
import com.example.yummyplanner.ui.home.adapter.MealAdapter;

import java.util.List;

public class SearchFragment extends Fragment implements SearchContract.View, MealAdapter.OnMealClickListener {

    private FragmentSearchBinding binding;
    private SearchContract.Presenter presenter;
    private MealAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new SearchPresenter(this, MealRepositoryImpl.getInstance(requireContext()));

        setupRecyclerView();
        setupSearchEditText();
        setupFilters();
        setupBackButton();

        handleIncomingArgs();
    }

    private void handleIncomingArgs() {
        if (getArguments() != null) {
            SearchFragmentArgs args = SearchFragmentArgs.fromBundle(getArguments());
            if (args.getCategoryName() != null) {
                binding.chipCategory.setChecked(true);
                binding.etSearch.setText(args.getCategoryName());
            } else if (args.getAreaName() != null) {
                binding.chipArea.setChecked(true);
                binding.etSearch.setText(args.getAreaName());
            }
        }
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(requireView()).navigateUp());
    }

    private void setupRecyclerView() {
        adapter = new MealAdapter(this);
        binding.rvSearchResults.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvSearchResults.setAdapter(adapter);
    }

    private void setupSearchEditText() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onSearchQueryChanged(s.toString(), getSelectedSearchType());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupFilters() {
        binding.filterChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String query = binding.etSearch.getText().toString();
            presenter.onFilterTypeChanged(getSelectedSearchType(), query);
        });
    }

    private SearchContract.SearchType getSelectedSearchType() {
        int checkedId = binding.filterChipGroup.getCheckedChipId();
        if (checkedId == R.id.chipCategory) return SearchContract.SearchType.CATEGORY;
        if (checkedId == R.id.chipArea) return SearchContract.SearchType.AREA;
        if (checkedId == R.id.chipIngredient) return SearchContract.SearchType.INGREDIENT;
        return SearchContract.SearchType.NAME;
    }

    @Override
    public void showResults(List<MealItemModel> meals) {
        if (binding == null) return;
        binding.searchProgressBar.setVisibility(View.GONE);
        binding.searchEmptyState.setVisibility(View.GONE);
        binding.rvSearchResults.setVisibility(View.VISIBLE);
        
        String currentFilter = binding.etSearch.getText().toString().trim();
        if (binding.chipCategory.isChecked()) {
            adapter.setMeals(meals, currentFilter);
        } else {
            adapter.setMeals(meals);
        }
    }

    @Override
    public void showError(String message) {
        if (binding == null) return;
        binding.searchProgressBar.setVisibility(View.GONE);
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (binding == null) return;
        binding.searchProgressBar.setVisibility(View.VISIBLE);
        binding.rvSearchResults.setVisibility(View.GONE);
        binding.searchEmptyState.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        if (binding == null) return;
        binding.searchProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyState() {
        if (binding == null) return;
        binding.rvSearchResults.setVisibility(View.GONE);
        binding.searchEmptyState.setVisibility(View.VISIBLE);
        binding.searchProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMealClick(MealItemModel meal) {
        SearchFragmentDirections.ActionSearchFragmentToDetailsFragment action =
                SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meal.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        binding = null;
    }
}
