package com.ermans.bottledanimals.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import com.ermans.bottledanimals.block.machine.animaldigitizer.GuiAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.animaldigitizer.TileAnimalDigitizer;
import com.ermans.bottledanimals.recipe.AnimalDigitizerManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class AnimalDigitizerRecipeHandler extends HandlerBase {

    private final AnimalDigitizerManager recManager = AnimalDigitizerManager.INSTANCE;



    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiAnimalDigitizer.class;
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("bottledanimals.nei.animalDigitizer");
    }

    @Override
    public String getOverlayIdentifier() {
        return "BAAnimalDigitizer";
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result == null) {
            return;
        }
        Collection<AnimalDigitizerManager.AnimalDigitizerRecipe> recipes = recManager.getRecipes();
        for (AnimalDigitizerManager.AnimalDigitizerRecipe recipe : recipes) {
            ItemStack output = recipe.getOutput();
            if (output.isItemEqual(result)) {
                AnimalDigitizerRecipeCached res = new AnimalDigitizerRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), output);
                this.arecipes.add(res);
            }
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("BAAnimalDigitizer") && getClass() == AnimalDigitizerRecipeHandler.class) {
            List<AnimalDigitizerManager.AnimalDigitizerRecipe> recipes = new ArrayList<AnimalDigitizerManager.AnimalDigitizerRecipe>(this.recManager.getRecipes());
            for (AnimalDigitizerManager.AnimalDigitizerRecipe recipe : recipes) {
                AnimalDigitizerRecipeCached res = new AnimalDigitizerRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), recipe.getOutput());
                this.arecipes.add(res);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<AnimalDigitizerManager.AnimalDigitizerRecipe> recipes = new ArrayList<AnimalDigitizerManager.AnimalDigitizerRecipe>(this.recManager.getRecipes());

        for (AnimalDigitizerManager.AnimalDigitizerRecipe recipe : recipes) {
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
        GuiDraw.drawTexturedModalRect(80, 30 + offY, 169, 0, 24, 16);
        drawProgressBar(80, 30 + offY, 193, 0, 24, 16, 48, 0);
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
            this.input = new PositionedStack(input, 53, 31 + offY);
            this.input2 = new PositionedStack(input2, 53, 53 + offY);
            this.output = new PositionedStack(result, 116, 31 + offY);
        }
    }
}
