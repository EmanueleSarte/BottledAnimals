package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.animal.AnimalStack;
import com.ermans.bottledanimals.animal.Animals;
import com.ermans.bottledanimals.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RancherManager {

    public static final RancherManager INSTANCE = new RancherManager();
    private final Map<String, RancherRecipe> mapRecipes;

    public RancherManager() {
        this.mapRecipes = new HashMap<String, RancherRecipe>();
    }

    public RancherRecipe addRecipe(RancherRecipe recipe) {
        return mapRecipes.put(generateKey(recipe), recipe);
    }

    public RancherRecipe getRecipeIfValid(ItemStack input) {
        RancherRecipe recipe = mapRecipes.get(generateKey(input));
        if (recipe != null) {
            if (recipe.areStackSizeValid(input)) {
                return recipe;
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

    public static String generateKey(RancherRecipe recipe) {
        return generateKey(recipe.input);
    }


    public void initRecipes() {

        for (int i = 0; i < Animals.animalsList.size(); i++) {
            Animals animal = Animals.animalsList.get(i);
            if (animal.getRanchableItem() != null) {
                RancherRecipe recipe;
                if (animal.getRanchableFluid() != null) {
                    recipe = new RancherRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, animal.getID()), animal.getRanchableItem(), animal.getRanchableFluid());
                } else {
                    recipe = new RancherRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, animal.getID()), animal.getRanchableItem(), animal.getRanchableFluid());
                }
                recipe.setCode(i * 7).setRecipeTime(animal.getRanchableTime());
                addRecipe(recipe);
            }
        }
    }

    public Collection<RancherRecipe> getRecipes() {
        return this.mapRecipes.values();
    }

    public class RancherRecipe extends Recipe {

        protected ItemStack input;
        protected AnimalStack output;
        protected FluidStack fluidOutput;

        public RancherRecipe(ItemStack input, AnimalStack output) {
            this(input, output, null);
        }

        public RancherRecipe(ItemStack input, AnimalStack output, FluidStack fluidOutput) {
            this.input = input;
            this.output = output;
            this.fluidOutput = fluidOutput;
        }


        public boolean hasFluidOutput() {
            return fluidOutput != null;
        }


        public ItemStack getInput() {
            return input;
        }


        public ItemStack getOutput() {
            return output.get();
        }

        public ItemStack getOutput(boolean original) {
            return original ? output.getOriginal() : output.get();
        }

        public FluidStack getFluidOutput() {
            return fluidOutput;
        }


        public boolean areStackSizeValid(ItemStack input) {
            return this.input.stackSize <= input.stackSize;
        }

        public int getInputStackSize() {
            return input.stackSize;
        }


        public int getOutputMaxSize() {
            return output == null ? 0 : output.getMaxQuantity();
        }

        public int getFluidStackAmount() {
            return fluidOutput.amount;
        }
    }
}
