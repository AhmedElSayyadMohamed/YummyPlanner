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
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SearchFragment extends Fragment implements SearchContract.View, MealAdapter.OnMealClickListener {

    private FragmentSearchBinding binding;
    private SearchContract.Presenter presenter;
    private MealAdapter adapter;
    private final CompositeDisposable disposables = new CompositeDisposable();

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
    }

    private void setupRecyclerView() {
        adapter = new MealAdapter(this);
        binding.rvSearchResults.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvSearchResults.setAdapter(adapter);
    }

    private void setupSearchEditText() {
        disposables.add(
                Observable.create(emitter -> {
                    TextWatcher watcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            emitter.onNext(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    };
                    binding.etSearch.addTextChangedListener(watcher);
                    emitter.setCancellable(() -> binding.etSearch.removeTextChangedListener(watcher));
                })
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    performSearch(query.toString());
                })
        );
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            showEmptyState();
            return;
        }

        int checkedId = binding.filterChipGroup.getCheckedChipId();
        if (checkedId == R.id.chipCategory) {
            presenter.filterByCategory(query);
        } else if (checkedId == R.id.chipArea) {
            presenter.filterByArea(query);
        } else if (checkedId == R.id.chipIngredient) {
            presenter.filterByIngredient(query);
        } else {
            presenter.searchByName(query);
        }
    }

    private void setupFilters() {
        binding.filterChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String query = binding.etSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                performSearch(query);
            }
        });
    }

    @Override
    public void showResults(List<MealItemModel> meals) {
        binding.searchEmptyState.setVisibility(View.GONE);
        binding.rvSearchResults.setVisibility(View.VISIBLE);
        adapter.setMeals(meals);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        binding.rvSearchResults.setVisibility(View.GONE);
        binding.searchEmptyState.setVisibility(View.GONE);
        // You can add a progress bar in XML if needed
    }

    @Override
    public void hideLoading() {
        // Hide progress bar
    }

    @Override
    public void showEmptyState() {
        binding.rvSearchResults.setVisibility(View.GONE);
        binding.searchEmptyState.setVisibility(View.VISIBLE);
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
        disposables.clear();
        presenter.detachView();
        binding = null;
    }
}
