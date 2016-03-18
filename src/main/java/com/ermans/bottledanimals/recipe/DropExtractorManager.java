package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.reference.Animals;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DropExtractorManager {


    public static final DropExtractorManager INSTANCE = new DropExtractorManager();
    private final Map<String, DropExtractorRecipe> mapRecipes;

    public DropExtractorManager() {
        this.mapRecipes = new HashMap<String, DropExtractorRecipe>();
        initRecipes();
    }

    public DropExtractorRecipe addRecipe(DropExtractorRecipe recipe) {
        return mapRecipes.put(generateKey(recipe), recipe);
    }

    public DropExtractorRecipe getRecipeIfValid(ItemStack input) {
        DropExtractorRecipe recipe = mapRecipes.get(generateKey(input));
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

    public static String generateKey(DropExtractorRecipe recipe) {
        return generateKey(recipe.input);
    }


    protected void initRecipes() {
        int i = 13;
        for (Animals animal : Animals.values()) {
            ItemStack[] animalDrops = animal.getDrops();
            if (animalDrops.length > 0) {
                ItemStack input = new ItemStack(ModItems.itemDigitalizedAnimal, 1, animal.getID());
                ItemStack output1 = animalDrops.length > 0 ? animalDrops[0] : null;
                ItemStack output2 = animalDrops.length > 1 ? animalDrops[1] : null;
                ItemStack output3 = new ItemStack(ModItems.itemBrokenPattern);
                DropExtractorRecipe recipe = new DropExtractorRecipe(input.copy(), output1, output2, output3);
                recipe.setRecipeTime(200).setCode(i * 7);
                addRecipe(recipe);
                i += 7;
            }
        }
    }

    public Collection<DropExtractorRecipe> getRecipes(){
        return this.mapRecipes.values();
    }

    public class DropExtractorRecipe extends Recipe {

        protected ItemStack input;
        protected ItemStack output1;
        protected ItemStack output2;
        protected ItemStack output3;


        public DropExtractorRecipe(ItemStack input, ItemStack output1, ItemStack output2, ItemStack output3) {
            this.input = input;
            this.output1 = output1;
            this.output2 = output2;
            this.output3 = output3;
        }

        public boolean areStackSizeValid(ItemStack input) {
            return input != null && input.stackSize >= this.input.stackSize;
        }

        public ItemStack getInput() {
            return input.copy();
        }

        public int getInputStackSize() {
            return input.stackSize;
        }

        public ItemStack getOutput1() {
            return output1;
        }

        public ItemStack getOutput2() {
            return output2;
        }

        public ItemStack getOutput3() {
            return output3;
        }

        public int getOutput1StackSize() {
            return output1.stackSize;
        }

        public int getOutput2StackSize() {
            return output2.stackSize;
        }

        public int getOutput3StackSize() {
            return output3.stackSize;
        }

    }
}
