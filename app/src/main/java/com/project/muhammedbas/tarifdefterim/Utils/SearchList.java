package com.project.muhammedbas.tarifdefterim.Utils;

public class SearchList {

    String personCount;
    String preparationTime;
    String cookingTime;
    String category;
    String recipeName;
    String materials;
    String preparation;

    public SearchList() {

    }

    public SearchList(String personCount, String preparationTime, String cookingTime, String category, String recipeName, String materials, String preparation) {
        this.personCount = personCount;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.category = category;
        this.recipeName = recipeName;
        this.materials = materials;
        this.preparation = preparation;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
}
