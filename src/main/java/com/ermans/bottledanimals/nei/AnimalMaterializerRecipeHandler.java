package com.ermans.bottledanimals.nei;


import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import com.ermans.bottledanimals.block.machine.animaldigitizer.TileAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.animalmaterializer.GuiAnimalMaterializer;
import com.ermans.bottledanimals.recipe.AnimalMaterializerManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.Collection;

public class AnimalMaterializerRecipeHandler extends HandlerRecipeBase {

    private final AnimalMaterializerManager recManager = AnimalMaterializerManager.INSTANCE;


    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiAnimalMaterializer.class;
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("bottledanimals.nei.animalMaterializer");
    }

    @Override
    public String getOverlayIdentifier() {
        return "BAAnimalMaterializer";
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result == null) {
            return;
        }
        Collection<AnimalMaterializerManager.AnimalMaterializerRecipe> recipes = recManager.getRecipes();
        for (AnimalMaterializerManager.AnimalMaterializerRecipe recipe : recipes) {
            ItemStack output = recipe.getOutput();
            if (output.isItemEqual(result)) {
                AnimalDigitizerRecipeCached res = new AnimalDigitizerRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), output);
                this.arecipes.add(res);
            }
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("BAAnimalMaterializer") && getClass() == AnimalMaterializerRecipeHandler.class) {
            Collection<AnimalMaterializerManager.AnimalMaterializerRecipe> recipes = this.recManager.getRecipes();
            for (AnimalMaterializerManager.AnimalMaterializerRecipe recipe : recipes) {
                AnimalDigitizerRecipeCached res = new AnimalDigitizerRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), recipe.getOutput());
                this.arecipes.add(res);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Collection<AnimalMaterializerManager.AnimalMaterializerRecipe> recipes = this.recManager.getRecipes();

        for (AnimalMaterializerManager.AnimalMaterializerRecipe recipe : recipes) {
            if (recipe.getInput1().isItemEqual(ingredient) || recipe.getInput2().isItemEqual(ingredient)) {
                AnimalDigitizerRecipeCached res = new AnimalDigitizerRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), recipe.getOutput());
                this.arecipes.add(res);
            }
        }
    }


    @Override
    public void drawBackground(int recipeIndex) {
        super.drawBackground(recipeIndex);
        CachedRecipeBase recipeBase = (CachedRecipeBase) arecipes.get(recipeIndex);
        drawEnergy(recipeBase.recipe.getRecipeTime() * 10, TileAnimalDigitizer.DF_ENERGY_CAPACITY);
        GuiDraw.drawTexturedModalRect(97, 36 + offY, 169, 0, 24, 16);
        drawProgressBar(97, 36 + offY, 193, 0, 24, 16, 48, 0);
        drawEnergyNeeded(85, 60 + offY, recipeBase.recipe.getRecipeTime() * 10);
    }


    public class AnimalDigitizerRecipeCached extends CachedRecipeBase {
        private final PositionedStack input;
        private final PositionedStack input2;
        private final PositionedStack output;

        @Override
        public PositionedStack getOtherStack() {
            return this.input2;
        }

        @Override
        public PositionedStack getIngredient() {
            return this.input;
        }

        @Override
        public PositionedStack getResult() {
            return this.output;
        }

        public AnimalDigitizerRecipeCached(IRecipe recipe, ItemStack input, ItemStack input2, ItemStack result) {
            super(recipe);
            this.input = new PositionedStack(input, 46, 36 + offY);
            this.input2 = new PositionedStack(input2, 68, 36 + offY);
            this.output = new PositionedStack(result, 134, 36 + offY);
        }
    }
}