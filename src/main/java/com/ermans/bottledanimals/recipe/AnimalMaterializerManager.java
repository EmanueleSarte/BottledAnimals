package com.ermans.bottledanimals.recipe;


import com.ermans.bottledanimals.animal.Animals;
import com.ermans.bottledanimals.init.ModItems;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AnimalMaterializerManager {

    public static final AnimalMaterializerManager INSTANCE = new AnimalMaterializerManager();
    private final Map<String, AnimalMaterializerRecipe> mapRecipes;

    public AnimalMaterializerManager() {
        this.mapRecipes = new HashMap<String, AnimalMaterializerRecipe>();
    }

    public AnimalMaterializerRecipe addRecipe(AnimalMaterializerRecipe recipe) {
        return mapRecipes.put(generateKey(recipe), recipe);
    }

    public AnimalMaterializerRecipe getRecipeIfValid(ItemStack input1, ItemStack input2) {
        AnimalMaterializerRecipe recipe1 = mapRecipes.get(generateKey(input1, input2));
        if (recipe1 != null) {
            if (recipe1.areStackSizeValid(input1, input2)) {
                return recipe1;
            }
        }


        AnimalMaterializerRecipe recipe2 = mapRecipes.get(generateKey(input1));
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
                key += is.getItem().getUnlocalizedName();
                if (is.hasTagCompound() && is.getTagCompound().hasKey("entity_name", 8)){
                    key += is.getTagCompound().getString("entity_name");
                }else{
                    key += is.getItemDamage();
                }
            }
        }
        return key;
    }

    public static String generateKey(AnimalMaterializerRecipe recipe) {
        return generateKey(recipe.input1, recipe.input2);
    }

    public void initRecipes() {
//        AnimalMaterializerRecipe recipe1 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 0), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 90));
//        recipe1.setRecipeTime(200).setCode(1);
//        addRecipe(recipe1);
//        AnimalMaterializerRecipe recipe2 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 1), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 91));
//        recipe2.setRecipeTime(200).setCode(2);
//        addRecipe(recipe2);
//        AnimalMaterializerRecipe recipe3 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 2), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 92));
//        recipe3.setRecipeTime(200).setCode(3);
//        addRecipe(recipe3);
//        AnimalMaterializerRecipe recipe4 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 3), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 93));
//        recipe4.setRecipeTime(200).setCode(4);
//        addRecipe(recipe4);
//        AnimalMaterializerRecipe recipe5 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 4), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 94));
//        recipe5.setRecipeTime(200).setCode(5);
//        addRecipe(recipe5);
//        AnimalMaterializerRecipe recipe6 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 5), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 95));
//        recipe6.setRecipeTime(200).setCode(6);
//        addRecipe(recipe6);
//        AnimalMaterializerRecipe recipe7 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 6), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 96));
//        recipe7.setRecipeTime(200).setCode(7);
//        addRecipe(recipe7);
//        AnimalMaterializerRecipe recipe8 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 7), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 98));
//        recipe8.setRecipeTime(200).setCode(8);
//        addRecipe(recipe8);
//        AnimalMaterializerRecipe recipe9 = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, 8), new ItemStack(ModItems.itemSpawnEggFrame), new ItemStack(Items.spawn_egg, 1, 100));
//        recipe9.setRecipeTime(200).setCode(9);
//        addRecipe(recipe9);


        for (int i = 0; i < Animals.animalsList.size(); i++) {
            Animals animal = Animals.animalsList.get(i);
            if (animal.getEgg() != null) {
                AnimalMaterializerRecipe recipe = new AnimalMaterializerRecipe(new ItemStack(ModItems.itemDigitalizedAnimal, 1, animal.getID()), new ItemStack(ModItems.itemSpawnEggFrame), animal.getEgg().copy());
                recipe.setRecipeTime(200).setCode(i * 7);
                addRecipe(recipe);
            }
        }
    }

    public Collection<AnimalMaterializerRecipe> getRecipes() {
        return this.mapRecipes.values();
    }


    public class AnimalMaterializerRecipe extends Recipe {

        protected ItemStack input1;
        protected ItemStack input2;
        protected ItemStack output;

        public AnimalMaterializerRecipe(ItemStack input1, ItemStack input2, ItemStack output) {
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
