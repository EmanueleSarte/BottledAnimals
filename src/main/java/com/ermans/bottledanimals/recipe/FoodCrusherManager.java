package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.helper.FoodHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FoodCrusherManager {

    public static final FoodCrusherManager INSTANCE = new FoodCrusherManager();
    private final Map<String, FoodCrusherRecipe> mapRecipes;
    private Random random = new Random();

    public FoodCrusherManager() {
        this.mapRecipes = new HashMap<String, FoodCrusherRecipe>();
    }


    public FoodCrusherRecipe addRecipe(FoodCrusherRecipe recipe) {
        return mapRecipes.put(generateKey(recipe), recipe);
    }

    public FoodCrusherRecipe getRecipeIfValid(ItemStack input) {

        FoodCrusherRecipe recipe = mapRecipes.get(generateKey(input));
        if (recipe != null) {
            return recipe;
        }

        double foodValue = FoodHelper.getFoodValue(input);
        if (foodValue == 0) {
            return null;
        }
        ItemStack inputCopy = input.copy();
        inputCopy.stackSize = 1;

        FoodCrusherRecipe crusherRecipe = new FoodCrusherRecipe(inputCopy, (int) (foodValue * 100));
        crusherRecipe.setRecipeTime(200).setCode(random.nextInt());
        addRecipe(crusherRecipe);
        return crusherRecipe;
    }

    public static String generateKey(ItemStack input) {
        if (input == null) return "";
        return input.getItem().getUnlocalizedName() + input.getItemDamage();
    }

    public static void init(){
        for (Object o : GameData.getItemRegistry()) {
            Item item = (Item) o;

            if (item instanceof ItemFood && !item.getHasSubtypes()) {
                INSTANCE.getRecipeIfValid(new ItemStack(item));
            }
        }
        INSTANCE.getRecipeIfValid(new ItemStack(Items.cake));
    }


    public static String generateKey(FoodCrusherRecipe recipe) {
        return generateKey(recipe.input);
    }


    public Collection<FoodCrusherRecipe> getRecipes(){
        return this.mapRecipes.values();
    }

    public class FoodCrusherRecipe extends Recipe {

        protected ItemStack input;
        protected int foodAmount;

        public FoodCrusherRecipe(ItemStack input, int foodAmount) {
            this.input = input;
            this.foodAmount = foodAmount;
        }

        public ItemStack getInput() {
            return input;
        }

        public int getInputStackSize() {
            return input.stackSize;
        }

        public int getFoodAmount() {
            return foodAmount;
        }


    }
}
