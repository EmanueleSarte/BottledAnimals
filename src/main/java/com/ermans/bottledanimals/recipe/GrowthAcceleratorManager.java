package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.animal.Animals;
import com.ermans.bottledanimals.init.ModItems;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GrowthAcceleratorManager {

    public static final GrowthAcceleratorManager INSTANCE = new GrowthAcceleratorManager();
    private final Map<String, GrowthAcceleratorRecipe> mapRecipes;

    public GrowthAcceleratorManager() {
        this.mapRecipes = new HashMap<String, GrowthAcceleratorRecipe>();
    }

    public GrowthAcceleratorRecipe addRecipe(GrowthAcceleratorRecipe recipe) {
        return mapRecipes.put(generateKey(recipe), recipe);
    }

    public GrowthAcceleratorRecipe getRecipeIfValid(ItemStack input) {
        GrowthAcceleratorRecipe recipe = mapRecipes.get(generateKey(input));
        if (recipe != null) {
            if (recipe.areStackSizeValid(input)) {
                return recipe;
            }
        }
        return null;
    }

    public static String generateKey(ItemStack input) {
        if (input == null) return "";
        return input.getItem().getUnlocalizedName() + input.getItemDamage();
    }

    public static String generateKey(GrowthAcceleratorRecipe recipe) {
        return generateKey(recipe.input);
    }


    public void initRecipes() {
        int i = 13;
        for (Animals animal : Animals.animalsList) {
            ItemStack input = new ItemStack(ModItems.itemDigitalizedBabyAnimal, 1, animal.getID());
            GrowthAcceleratorRecipe recipe = new GrowthAcceleratorRecipe(input, new ItemStack(ModItems.itemDigitalizedAnimal, 1, animal.getID()));
            recipe.setRecipeTime(20000).setCode(i * 7);
            addRecipe(recipe);
            i += 7;
        }
    }

    public Collection<GrowthAcceleratorRecipe> getRecipes(){
        return this.mapRecipes.values();
    }


    public class GrowthAcceleratorRecipe extends Recipe {

        protected ItemStack input;
        protected ItemStack output;

        public GrowthAcceleratorRecipe(ItemStack input, ItemStack output) {
            this.input = input;
            this.output = output;
        }

        public boolean areStackSizeValid(ItemStack input) {
            return input != null && this.input.stackSize <= input.stackSize;
        }

        public ItemStack getInput() {
            return input.copy();
        }

        public int getInputStackSize() {
            return input.stackSize;
        }

        public ItemStack getOutput() {
            return output.copy();
        }

        public int getOutputStackSize() {
            return output.stackSize;
        }
    }

}
