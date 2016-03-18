package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.reference.Animals;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
        initRecipes();
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


    protected void initRecipes() {
        RancherRecipe recipe1 = new RancherRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, Animals.COW.getID()), null, new FluidStack(ModFluids.milk, 1000));
        RancherRecipe recipe2 = new RancherRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, Animals.SHEEP.getID()), new ItemStack(Blocks.wool, 2));
        RancherRecipe recipe3 = new RancherRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, Animals.CHICKEN.getID()), new ItemStack(Items.egg));
        RancherRecipe recipe4 = new RancherRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, Animals.MUSHROOM_COW.getID()), null, new FluidStack(ModFluids.milk, 1000));
        RancherRecipe recipe5 =  new RancherRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, Animals.SQUID.getID()), new ItemStack(Items.dye, 3, 0));

        recipe1.setRecipeTime(200).setCode(10001);
        recipe2.setRecipeTime(1200).setCode(10010);
        recipe3.setRecipeTime(2000).setCode(10100);
        recipe4.setRecipeTime(200).setCode(11000);
        recipe5.setRecipeTime(3000).setCode(20000);

        addRecipe(recipe1);
        addRecipe(recipe2);
        addRecipe(recipe3);
        addRecipe(recipe4);
        addRecipe(recipe5);

    }

    public Collection<RancherRecipe> getRecipes(){
        return this.mapRecipes.values();
    }

    public class RancherRecipe extends Recipe {

        protected ItemStack input;
        protected ItemStack output;
        protected FluidStack fluidOutput;

        public RancherRecipe(ItemStack input, ItemStack output) {
            this(input, output, null);
        }

        public RancherRecipe(ItemStack input, ItemStack output, FluidStack fluidOutput) {
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
            return output;
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


        public int getOutputStackSize() {
            return output.stackSize;
        }

        public int getFluidStackAmount() {
            return fluidOutput.amount;
        }
    }
}
