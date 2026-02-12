package com.example.yummyplanner.data.meals.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;

@Entity(tableName = "planned_meals")
public class PlannedMealEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "id_meal")
    private String idMeal;

    @ColumnInfo(name = "planned_date")
    private String plannedDate;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "area")
    private String area;

    @ColumnInfo(name = "instructions")
    private String instructions;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "youtube")
    private String youtube;

    @ColumnInfo(name = "ingredient1") private String ingredient1;
    @ColumnInfo(name = "ingredient2") private String ingredient2;
    @ColumnInfo(name = "ingredient3") private String ingredient3;
    @ColumnInfo(name = "ingredient4") private String ingredient4;
    @ColumnInfo(name = "ingredient5") private String ingredient5;
    @ColumnInfo(name = "ingredient6") private String ingredient6;
    @ColumnInfo(name = "ingredient7") private String ingredient7;
    @ColumnInfo(name = "ingredient8") private String ingredient8;
    @ColumnInfo(name = "ingredient9") private String ingredient9;
    @ColumnInfo(name = "ingredient10") private String ingredient10;
    @ColumnInfo(name = "ingredient11") private String ingredient11;
    @ColumnInfo(name = "ingredient12") private String ingredient12;
    @ColumnInfo(name = "ingredient13") private String ingredient13;
    @ColumnInfo(name = "ingredient14") private String ingredient14;
    @ColumnInfo(name = "ingredient15") private String ingredient15;
    @ColumnInfo(name = "ingredient16") private String ingredient16;
    @ColumnInfo(name = "ingredient17") private String ingredient17;
    @ColumnInfo(name = "ingredient18") private String ingredient18;
    @ColumnInfo(name = "ingredient19") private String ingredient19;
    @ColumnInfo(name = "ingredient20") private String ingredient20;

    @ColumnInfo(name = "measure1") private String measure1;
    @ColumnInfo(name = "measure2") private String measure2;
    @ColumnInfo(name = "measure3") private String measure3;
    @ColumnInfo(name = "measure4") private String measure4;
    @ColumnInfo(name = "measure5") private String measure5;
    @ColumnInfo(name = "measure6") private String measure6;
    @ColumnInfo(name = "measure7") private String measure7;
    @ColumnInfo(name = "measure8") private String measure8;
    @ColumnInfo(name = "measure9") private String measure9;
    @ColumnInfo(name = "measure10") private String measure10;
    @ColumnInfo(name = "measure11") private String measure11;
    @ColumnInfo(name = "measure12") private String measure12;
    @ColumnInfo(name = "measure13") private String measure13;
    @ColumnInfo(name = "measure14") private String measure14;
    @ColumnInfo(name = "measure15") private String measure15;
    @ColumnInfo(name = "measure16") private String measure16;
    @ColumnInfo(name = "measure17") private String measure17;
    @ColumnInfo(name = "measure18") private String measure18;
    @ColumnInfo(name = "measure19") private String measure19;
    @ColumnInfo(name = "measure20") private String measure20;

    // Offline sync flags
    @ColumnInfo(name = "pending_upload")
    private boolean pendingUpload = false;

    @ColumnInfo(name = "pending_delete")
    private boolean pendingDelete = false;

    @ColumnInfo(name = "updated_at")
    private long updatedAt;

    public PlannedMealEntity() {
        this.idMeal = "";
    }


    // Convert from remote meal to PlannedMealEntity
    public static PlannedMealEntity fromRemoteMeal(MealdetailsItemModel meal, String plannedDate) {
        PlannedMealEntity entity = new PlannedMealEntity();
        entity.setIdMeal(meal.getId());
        entity.setPlannedDate(plannedDate);
        entity.setName(meal.getName());
        entity.setCategory(meal.getCategory());
        entity.setArea(meal.getArea());
        entity.setInstructions(meal.getInstructions());
        entity.setThumbnail(meal.getThumbNail());
        entity.setYoutube(meal.getYoutubeUrl());

        entity.setIngredient1(meal.getIngredient1());
        entity.setIngredient2(meal.getIngredient2());
        entity.setIngredient3(meal.getIngredient3());
        entity.setIngredient4(meal.getIngredient4());
        entity.setIngredient5(meal.getIngredient5());
        entity.setIngredient6(meal.getIngredient6());
        entity.setIngredient7(meal.getIngredient7());
        entity.setIngredient8(meal.getIngredient8());
        entity.setIngredient9(meal.getIngredient9());
        entity.setIngredient10(meal.getIngredient10());
        entity.setIngredient11(meal.getIngredient11());
        entity.setIngredient12(meal.getIngredient12());
        entity.setIngredient13(meal.getIngredient13());
        entity.setIngredient14(meal.getIngredient14());
        entity.setIngredient15(meal.getIngredient15());
        entity.setIngredient16(meal.getIngredient16());
        entity.setIngredient17(meal.getIngredient17());
        entity.setIngredient18(meal.getIngredient18());
        entity.setIngredient19(meal.getIngredient19());
        entity.setIngredient20(meal.getIngredient20());

        entity.setMeasure1(meal.getMeasure1());
        entity.setMeasure2(meal.getMeasure2());
        entity.setMeasure3(meal.getMeasure3());
        entity.setMeasure4(meal.getMeasure4());
        entity.setMeasure5(meal.getMeasure5());
        entity.setMeasure6(meal.getMeasure6());
        entity.setMeasure7(meal.getMeasure7());
        entity.setMeasure8(meal.getMeasure8());
        entity.setMeasure9(meal.getMeasure9());
        entity.setMeasure10(meal.getMeasure10());
        entity.setMeasure11(meal.getMeasure11());
        entity.setMeasure12(meal.getMeasure12());
        entity.setMeasure13(meal.getMeasure13());
        entity.setMeasure14(meal.getMeasure14());
        entity.setMeasure15(meal.getMeasure15());
        entity.setMeasure16(meal.getMeasure16());
        entity.setMeasure17(meal.getMeasure17());
        entity.setMeasure18(meal.getMeasure18());
        entity.setMeasure19(meal.getMeasure19());
        entity.setMeasure20(meal.getMeasure20());

        entity.setPendingUpload(true);
        entity.setPendingDelete(false);
        entity.setUpdatedAt(System.currentTimeMillis());

        return entity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    public void setPlannedDate(String plannedDate) {
        this.plannedDate = plannedDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public void setIngredient1(String ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public void setIngredient2(String ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public void setIngredient3(String ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public void setIngredient4(String ingredient4) {
        this.ingredient4 = ingredient4;
    }

    public void setIngredient5(String ingredient5) {
        this.ingredient5 = ingredient5;
    }

    public void setIngredient6(String ingredient6) {
        this.ingredient6 = ingredient6;
    }

    public void setIngredient7(String ingredient7) {
        this.ingredient7 = ingredient7;
    }

    public void setIngredient8(String ingredient8) {
        this.ingredient8 = ingredient8;
    }

    public void setIngredient9(String ingredient9) {
        this.ingredient9 = ingredient9;
    }

    public void setIngredient10(String ingredient10) {
        this.ingredient10 = ingredient10;
    }

    public void setIngredient11(String ingredient11) {
        this.ingredient11 = ingredient11;
    }

    public void setIngredient12(String ingredient12) {
        this.ingredient12 = ingredient12;
    }

    public void setIngredient13(String ingredient13) {
        this.ingredient13 = ingredient13;
    }

    public void setIngredient14(String ingredient14) {
        this.ingredient14 = ingredient14;
    }

    public void setIngredient15(String ingredient15) {
        this.ingredient15 = ingredient15;
    }

    public void setIngredient16(String ingredient16) {
        this.ingredient16 = ingredient16;
    }

    public void setIngredient17(String ingredient17) {
        this.ingredient17 = ingredient17;
    }

    public void setIngredient18(String ingredient18) {
        this.ingredient18 = ingredient18;
    }

    public void setIngredient19(String ingredient19) {
        this.ingredient19 = ingredient19;
    }

    public void setIngredient20(String ingredient20) {
        this.ingredient20 = ingredient20;
    }

    public void setMeasure1(String measure1) {
        this.measure1 = measure1;
    }

    public void setMeasure2(String measure2) {
        this.measure2 = measure2;
    }

    public void setMeasure3(String measure3) {
        this.measure3 = measure3;
    }

    public void setMeasure4(String measure4) {
        this.measure4 = measure4;
    }

    public void setMeasure5(String measure5) {
        this.measure5 = measure5;
    }

    public void setMeasure6(String measure6) {
        this.measure6 = measure6;
    }

    public void setMeasure7(String measure7) {
        this.measure7 = measure7;
    }

    public void setMeasure8(String measure8) {
        this.measure8 = measure8;
    }

    public void setMeasure9(String measure9) {
        this.measure9 = measure9;
    }

    public void setMeasure10(String measure10) {
        this.measure10 = measure10;
    }

    public void setMeasure11(String measure11) {
        this.measure11 = measure11;
    }

    public void setMeasure12(String measure12) {
        this.measure12 = measure12;
    }

    public void setMeasure13(String measure13) {
        this.measure13 = measure13;
    }

    public void setMeasure14(String measure14) {
        this.measure14 = measure14;
    }

    public void setMeasure15(String measure15) {
        this.measure15 = measure15;
    }

    public void setMeasure16(String measure16) {
        this.measure16 = measure16;
    }

    public void setMeasure17(String measure17) {
        this.measure17 = measure17;
    }

    public void setMeasure18(String measure18) {
        this.measure18 = measure18;
    }

    public void setMeasure19(String measure19) {
        this.measure19 = measure19;
    }

    public void setMeasure20(String measure20) {
        this.measure20 = measure20;
    }

    public void setPendingUpload(boolean pendingUpload) {
        this.pendingUpload = pendingUpload;
    }

    public void setPendingDelete(boolean pendingDelete) {
        this.pendingDelete = pendingDelete;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public String getPlannedDate() {
        return plannedDate;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public String getIngredient3() {
        return ingredient3;
    }

    public String getIngredient4() {
        return ingredient4;
    }

    public String getIngredient5() {
        return ingredient5;
    }

    public String getIngredient6() {
        return ingredient6;
    }

    public String getIngredient7() {
        return ingredient7;
    }

    public String getIngredient8() {
        return ingredient8;
    }

    public String getIngredient9() {
        return ingredient9;
    }

    public String getIngredient10() {
        return ingredient10;
    }

    public String getIngredient11() {
        return ingredient11;
    }

    public String getIngredient12() {
        return ingredient12;
    }

    public String getIngredient13() {
        return ingredient13;
    }

    public String getIngredient14() {
        return ingredient14;
    }

    public String getIngredient15() {
        return ingredient15;
    }

    public String getIngredient16() {
        return ingredient16;
    }

    public String getIngredient17() {
        return ingredient17;
    }

    public String getIngredient18() {
        return ingredient18;
    }

    public String getIngredient19() {
        return ingredient19;
    }

    public String getIngredient20() {
        return ingredient20;
    }

    public String getMeasure1() {
        return measure1;
    }

    public String getMeasure2() {
        return measure2;
    }

    public String getMeasure3() {
        return measure3;
    }

    public String getMeasure4() {
        return measure4;
    }

    public String getMeasure5() {
        return measure5;
    }

    public String getMeasure6() {
        return measure6;
    }

    public String getMeasure7() {
        return measure7;
    }

    public String getMeasure8() {
        return measure8;
    }

    public String getMeasure9() {
        return measure9;
    }

    public String getMeasure10() {
        return measure10;
    }

    public String getMeasure11() {
        return measure11;
    }

    public String getMeasure12() {
        return measure12;
    }

    public String getMeasure13() {
        return measure13;
    }

    public String getMeasure14() {
        return measure14;
    }

    public String getMeasure15() {
        return measure15;
    }

    public String getMeasure16() {
        return measure16;
    }

    public String getMeasure17() {
        return measure17;
    }

    public String getMeasure18() {
        return measure18;
    }

    public String getMeasure19() {
        return measure19;
    }

    public String getMeasure20() {
        return measure20;
    }

    public boolean isPendingUpload() {
        return pendingUpload;
    }

    public boolean isPendingDelete() {
        return pendingDelete;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }
}
