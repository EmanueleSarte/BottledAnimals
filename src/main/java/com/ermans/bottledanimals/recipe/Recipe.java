package com.ermans.bottledanimals.recipe;

public abstract class Recipe implements IRecipe {

    protected int recipeTime = 200;
    protected int experience;
    protected int code;


    public Recipe setRecipeTime(int recipeTime) {
        this.recipeTime = recipeTime;
        return this;
    }

    public Recipe setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public Recipe setCode(int code){
        this.code = code;
        return this;
    }

    @Override
    public int getRecipeTime() {
        return recipeTime;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public int getCode() {
        return code;
    }
}
