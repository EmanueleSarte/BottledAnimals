package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.animal.AnimalStack;
import com.ermans.bottledanimals.animal.Animals;
import com.ermans.bottledanimals.init.ModItems;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DropExtractorManager {


    public static final DropExtractorManager INSTANCE = new DropExtractorManager();
    private final Map<String, DropExtractorRecipe> mapRecipes;

    public DropExtractorManager() {
        this.mapRecipes = new HashMap<String, DropExtractorRecipe>();
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


    public void initRecipes() {
        int i = 13;
        for (Animals animal : Animals.animalsList) {
            AnimalStack[] animalDrops = animal.getDropItems();
            if (animalDrops != null && animalDrops.length > 0) {
                ItemStack input = new ItemStack(ModItems.itemDigitalizedAnimal, 1, animal.getID());
                ItemStack brokenPattern = new ItemStack(ModItems.itemBrokenPattern);

                DropExtractorRecipe recipe = new DropExtractorRecipe(input.copy(), brokenPattern, animalDrops);

                recipe.setRecipeTime(200).setCode(i * 7);
                if (animal.getRareDrop() != null) {
                    recipe.setRare(animal.getRareDrop());
                }

                addRecipe(recipe);
                i += 7;
            }
        }
    }

    public Collection<DropExtractorRecipe> getRecipes() {
        return this.mapRecipes.values();
    }

    public class DropExtractorRecipe extends Recipe {

        protected ItemStack input;
        protected ItemStack brokenPattern;
        protected AnimalStack output1;
        protected AnimalStack output2;
        protected AnimalStack output3;
        protected AnimalStack outputRare;


        public DropExtractorRecipe(ItemStack input, ItemStack brokenPattern, AnimalStack... outputs) {
            this.input = input;

            this.brokenPattern = brokenPattern;
            if (outputs.length > 0) {
                this.output1 = outputs[0];
            }
            if (outputs.length > 1) {
                this.output2 = outputs[1];
            }
            if (outputs.length > 2) {
                this.output3 = outputs[2];
            }
        }

        public boolean areStackSizeValid(ItemStack input) {
            return input != null && input.stackSize >= this.input.stackSize;
        }

        public DropExtractorRecipe setRare(AnimalStack stack) {
            this.outputRare = stack;
            return this;
        }

        public ItemStack getInput() {
            return input.copy();
        }

        public int getInputStackSize() {
            return input.stackSize;
        }

        public ItemStack getBrokenPattern() {
            return brokenPattern.copy();
        }

        public int getBrokenPatternStackSize() {
            return brokenPattern.stackSize;
        }

        public ItemStack getOutput1() {
            return getOutput1(false);
        }

        public ItemStack getOutput2() {
            return getOutput2(false);
        }

        public ItemStack getOutput3() {
            return getOutput3(false);
        }

        public ItemStack getOutputRare() {
            return getOutputRare(false);
        }

        public int getOutput1MaxSize() {
            return output1 == null ? 0 : output1.getMaxQuantity();
        }

        public int getOutput2MaxSize() {
            return output2 == null ? 0 : output2.getMaxQuantity();
        }

        public int getOutput3MaxSize() {
            return output3 == null ? 0 : output3.getMaxQuantity();
        }

        public int getOutputRareMaxSize() {
            return outputRare == null ? 0 : outputRare.getMaxQuantity();
        }


        public ItemStack getOutput1(boolean original) {
            return output1 == null ? null : original ? output1.getOriginal() : output1.get();
        }

        public ItemStack getOutput2(boolean original) {
            return output2 == null ? null : original ? output2.getOriginal() : output2.get();
        }

        public ItemStack getOutput3(boolean original) {
            return output3 == null ? null : original ? output3.getOriginal() : output3.get();
        }

        public ItemStack getOutputRare(boolean original) {
            return outputRare == null ? null : original ? outputRare.getOriginal() : outputRare.get();
        }

    }
}
