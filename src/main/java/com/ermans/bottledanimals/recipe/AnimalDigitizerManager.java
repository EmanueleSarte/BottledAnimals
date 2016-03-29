package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.reference.Animals;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AnimalDigitizerManager {

    public static final AnimalDigitizerManager INSTANCE = new AnimalDigitizerManager();
    private final Map<String, AnimalDigitizerRecipe> mapRecipes;

    public AnimalDigitizerManager() {
        this.mapRecipes = new HashMap<String, AnimalDigitizerRecipe>();
//        initRecipes();
    }

    public AnimalDigitizerRecipe addRecipe(AnimalDigitizerRecipe recipe) {
        return mapRecipes.put(generateKey(recipe), recipe);
    }

    public AnimalDigitizerRecipe getRecipeIfValid(ItemStack input1, ItemStack input2) {
        AnimalDigitizerRecipe recipe1 = mapRecipes.get(generateKey(input1, input2));
        if (recipe1 != null) {
            if (recipe1.areStackSizeValid(input1, input2)) {
                return recipe1;
            }
        }


        AnimalDigitizerRecipe recipe2 = mapRecipes.get(generateKey(input1));
        if (recipe2 != null) {
            if (recipe2.areStackSizeValid(input1, input2)) {
                return recipe2;
            }
        }
        return null;

    }

    public static String generateKey(ItemStack... inputs) {

        String key = "";
        for (ItemStack is : inputs) {
            if (is != null) {
                key = key + is.getItem().getUnlocalizedName() + is.getItemDamage();
            }
        }
        return key;
    }

    public static String generateKey(AnimalDigitizerRecipe recipe) {
        return generateKey(recipe.input1, recipe.input2);
    }

    protected void initRecipes() {
        int i = 13;
        for (Animals animal : Animals.values()) {
            AnimalDigitizerRecipe recipe = new AnimalDigitizerRecipe(new ItemStack(ModItems.itemAnimalInABottle, 1, animal.getID()),
                    new ItemStack(ModItems.itemBlankPattern), new ItemStack(ModItems.itemDigitalizedAnimal, 1, animal.getID()));
            recipe.setRecipeTime(200).setCode(i * 7);
            addRecipe(recipe);
            i += 7;
        }
    }

    public Collection<AnimalDigitizerRecipe> getRecipes(){
        return this.mapRecipes.values();
    }


    public class AnimalDigitizerRecipe extends Recipe {

        protected ItemStack input1;
        protected ItemStack input2;
        protected ItemStack output;

        public AnimalDigitizerRecipe(ItemStack input1, ItemStack input2, ItemStack output) {
            this.input1 = input1;
            this.input2 = input2;
            this.output = output;
        }


        public ItemStack getInput1() {
            return input1;
        }

        public ItemStack getInput2() {
            return input2;
        }

        public ItemStack getOutput() {
            return output;
        }


        public boolean areStackSizeValid(ItemStack input1, ItemStack input2) {
            int size1 = input1.stackSize;
            int size2 = input2 == null ? 0 : input2.stackSize;

            if (this.input1.stackSize > size1) {
                return false;
            }
            return (this.input2 == null) || this.input2.stackSize <= size2;
        }


        public int getInput1StackSize() {
            return input1.stackSize;
        }

        public int getInput2StackSize() {
            return input2.stackSize;
        }

        public int getOutputStackSize() {
            return output.stackSize;
        }
    }

}
