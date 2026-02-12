package com.example.yummyplanner.ui.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import com.example.yummyplanner.data.meals.repository.MealRepositoryImpl;
import com.example.yummyplanner.databinding.FragmentPlannerBinding;
import com.example.yummyplanner.utiles.Constants;

import java.util.Calendar;
import java.util.List;

public class PlannerFragment extends Fragment implements PlannerContract.View, PlannerAdapter.OnPlannedMealClickListener {

    private FragmentPlannerBinding binding;
    private PlannerContract.Presenter presenter;
    private PlannerAdapter adapter;
    private String selectedDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new PlannerPresenter(MealRepositoryImpl.getInstance(requireContext()));
        presenter.attachView(this);

        setupRecyclerView();
        setupCalendar();

        loadMealsForDate(getInitialDateString());
    }

    private void setupRecyclerView() {
        adapter = new PlannerAdapter(this);
        binding.rvPlannedMeals.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPlannedMeals.setAdapter(adapter);
    }

    private void setupCalendar() {
        Calendar calendar = Calendar.getInstance();

        // Set min date to tomorrow
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long minDate = calendar.getTimeInMillis();
        binding.calendarView.setMinDate(minDate);

        // Set max date to 7 days starting from tomorrow (total 7 days)
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        long maxDate = calendar.getTimeInMillis();
        binding.calendarView.setMaxDate(maxDate);

        // Set current selection to tomorrow
        binding.calendarView.setDate(minDate);

        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            binding.tvSelectedDateLabel.setText("Meals for " + selectedDate);
            loadMealsForDate(selectedDate);
        });
    }

    private void loadMealsForDate(String date) {
        this.selectedDate = date;
        presenter.getPlannedMealsByDate(date);
    }

    private String getInitialDateString() {
        Calendar cal = Calendar.getInstance();
        // Default to tomorrow since today is disabled
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return String.format("%04d-%02d-%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void showPlannedMeals(List<PlannedMealEntity> meals) {
        if (meals == null || meals.isEmpty()) {
            binding.rvPlannedMeals.setVisibility(View.GONE);
            binding.emptyState.setVisibility(View.VISIBLE);
        } else {
            binding.emptyState.setVisibility(View.GONE);
            binding.rvPlannedMeals.setVisibility(View.VISIBLE);
            adapter.setPlannedMeals(meals);
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String message) {
        Constants.showErrorSnackbar(binding.getRoot(), message);
    }

    @Override
    public void showMealDeleted() {
        Toast.makeText(requireContext(), "Meal removed from planner", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlannedMealClick(PlannedMealEntity meal) {
        PlannerFragmentDirections.ActionPlannerFragmentToDetailsFragment action =
                PlannerFragmentDirections.actionPlannerFragmentToDetailsFragment(meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDeleteClick(PlannedMealEntity meal) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to remove this meal from your planner?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    presenter.deletePlannedMeal(meal);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        binding = null;
    }
}
