package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.reference.Animals;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BreederManager {

    public static final BreederManager INSTANCE = new BreederManager();
    private final Map<String, BreederRecipe> mapRecipes;

    public BreederManager() {
        this.mapRecipes = new HashMap<String, BreederRecipe>();
        initRecipes();
    }

    public BreederRecipe addRecipe(BreederRecipe recipe) {
        return mapRecipes.put(generateKey(recipe), recipe);
    }

    public BreederRecipe getRecipeIfValid(ItemStack input1, ItemStack input2, ItemStack food) {
        BreederRecipe recipe = mapRecipes.get(generateKey(input1, input2));
        if (recipe != null && recipe.isValid(input1, input2, food)) {
            return recipe;
        }
        return null;
    }

    public static String generateKey(ItemStack input1, ItemStack input2) {
        String key1 = input1 == null ? "" : input1.getUnlocalizedName() + input1.getItemDamage();
        String key2 = input2 == null ? "" : input2.getUnlocalizedName() + input2.getItemDamage();
        if (key1.compareTo(key2) >= 0) {
            return key1 + key2;
        } else {
            return key2 + key1;
        }
    }

    public static String generateKey(BreederRecipe recipe) {
        return generateKey(recipe.input1, recipe.input2);
    }

    protected void initRecipes() {
        int k = 13;
        for (Animals animal : Animals.values()) {
            ItemStack input = new ItemStack(ModItems.itemDigitalizedAnimal, 1, animal.getID());
            ItemStack[] validFoods = animal.getValidFoods();
            ItemStack[] validFoodsCopy = new ItemStack[validFoods.length];
            for (int i = 0; i < validFoods.length; i++) {
                validFoodsCopy[i] = validFoods[i].copy();
                validFoodsCopy[i].stackSize = 2;
            }
            BreederRecipe recipe = new BreederRecipe(input, input.copy(), new ItemStack(ModItems.itemDigitalizedBabyAnimal, 1, animal.getID()), validFoodsCopy);
            recipe.setRecipeTime(4800).setCode(k * 7);
            addRecipe(recipe);
            k += 7;
        }
    }

    public Collection<BreederRecipe> getRecipes(){
        return this.mapRecipes.values();
    }


    public class BreederRecipe extends Recipe {

        protected ItemStack input1;
        protected ItemStack input2;
        protected ItemStack output;
        protected ItemStack[] itemsFood;

        public BreederRecipe(ItemStack input1, ItemStack input2, ItemStack output, ItemStack[] itemsFood) {
            this.input1 = input1;
            this.input2 = input2;
            this.output = output;
            this.itemsFood = itemsFood;
        }

        public boolean isValid(ItemStack input1, ItemStack input2, ItemStack food) {
            if (input1 == null || input2 == null || food == null) {
                return false;
            }

            int mode = 0;
            if (this.input1.isItemEqual(input1)) {
                mode = this.input2.isItemEqual(input2) ? 1 : 0;
            } else if (this.input2.isItemEqual(input2)) {
                mode = this.input1.isItemEqual(input1) ? 2 : 0;
            } else {
                return false;
            }

            if (mode == 1) {
                if (this.input1.stackSize < input1.stackSize || this.input2.stackSize < input2.stackSize) {
                    return false;
                }
            } else if (mode == 2) {
                if (this.input1.stackSize < input2.stackSize || this.input2.stackSize < input1.stackSize) {
                    return false;
                }
            } else {
                return false;
            }

            for (ItemStack is : this.itemsFood) {
                if (is.isItemEqual(food)) {
                    return food.stackSize >= is.stackSize;
                }
            }
            return false;
        }

        public ItemStack getItemFoodEqualsTo(ItemStack food) {
            if (food == null) return null;
            for (ItemStack is : itemsFood) {
                if (is.isItemEqual(food)) {
                    return is;
                }
            }
            return null;
        }

        public ItemStack getInput1() {
            return input1.copy();
        }

        public ItemStack getInput2() {
            return input2.copy();
        }

        public int getInput1StackSize() {
            return input1.stackSize;
        }

        public int getInput2StackSize() {
            return input2.stackSize;
        }

        public ItemStack getOutput() {
            return output.copy();
        }

        public int getOutputStackSize() {
            return output.stackSize;
        }

        public ItemStack[] getItemsFood() {
            return itemsFood;
        }
    }

}
