package com.ermans.bottledanimals.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import com.ermans.bottledanimals.block.machine.growthaccelerator.GuiGrowthAccelerator;
import com.ermans.bottledanimals.recipe.GrowthAcceleratorManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import com.ermans.bottledanimals.reference.Animals;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GrowthAcceleratorRecipeHandler extends HandlerBase {
    private final GrowthAcceleratorManager recManager = GrowthAcceleratorManager.INSTANCE;


    @Override
    public void initialize() {
        this.offY = -7;
        this.offEnergyY = -10;
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiGrowthAccelerator.class;
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("bottledanimals.nei.growthAccelerator");
    }

    @Override
    public String getOverlayIdentifier() {
        return "BAGrowthAccelerator";
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result == null) {
            return;
        }
        Collection<GrowthAcceleratorManager.GrowthAcceleratorRecipe> recipes = recManager.getRecipes();
        for (GrowthAcceleratorManager.GrowthAcceleratorRecipe recipe : recipes) {
            if (recipe.getOutput().isItemEqual(result)) {
                GrowthAcceleratorRecipeCached res = new GrowthAcceleratorRecipeCached(recipe, recipe.getInput(), recipe.getOutput());
                this.arecipes.add(res);
            }
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if ((outputId.equals("BAGrowthAccelerator")) && (getClass() == GrowthAcceleratorRecipeHandler.class)) {
            Collection<GrowthAcceleratorManager.GrowthAcceleratorRecipe> recipes = recManager.getRecipes();
            for (GrowthAcceleratorManager.GrowthAcceleratorRecipe recipe : recipes) {
                GrowthAcceleratorRecipeCached res = new GrowthAcceleratorRecipeCached(recipe, recipe.getInput(), recipe.getOutput());
                this.arecipes.add(res);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Collection<GrowthAcceleratorManager.GrowthAcceleratorRecipe> recipes = recManager.getRecipes();
        for (GrowthAcceleratorManager.GrowthAcceleratorRecipe recipe : recipes) {
            if (recipe.getInput().isItemEqual(ingredient)) {
                GrowthAcceleratorRecipeCached res = new GrowthAcceleratorRecipeCached(recipe, recipe.getInput(), recipe.getOutput());
                this.arecipes.add(res);
            }
        }
    }

    @Override
    public void drawBackground(int recipeIndex) {
        super.drawBackground(recipeIndex);
        CachedRecipeBase recipeBase = (CachedRecipeBase) arecipes.get(recipeIndex);
        drawEnergy(recipeBase.recipe.getRecipeTime() * 2, 400000);
        GuiDraw.drawTexturedModalRect(73, 38 + offY, 169, 0, 24, 16);
        drawProgressBar(73, 38 + offY, 193, 0, 24, 16, 48, 0);
        drawEnergyNeeded(62, 61 + offY, recipeBase.recipe.getRecipeTime() * 2);
    }


    @Override
    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe) {
        GrowthAcceleratorRecipeCached gaRecipe = (GrowthAcceleratorRecipeCached) this.arecipes.get(recipe);
        if (gui.isMouseOver(gaRecipe.foods.get(0), recipe)) {
            currenttip.add("Used just to accelerate");
            currenttip.add("the process");
        }
        return currenttip;
    }

    public class GrowthAcceleratorRecipeCached extends CachedRecipeBase {
        private final PositionedStack input;
        private final PositionedStack output;
        private final List<PositionedStack> foods;

        @Override
        public List<PositionedStack> getOtherStacks() {
            return getCycledIngredients(GrowthAcceleratorRecipeHandler.this.cycleticks / 20, this.foods);
        }

        @Override
        public PositionedStack getIngredient() {
            return this.input;
        }

        @Override
        public PositionedStack getResult() {
            return this.output;
        }

        public GrowthAcceleratorRecipeCached(IRecipe recipe, ItemStack input, ItemStack result) {
            super(recipe);
            this.input = new PositionedStack(input, 45, 38 + offY);
            this.output = new PositionedStack(result, 116, 38 + offY);
            this.foods = new ArrayList<PositionedStack>();

            Animals animal = Animals.getAnimalsFromID(input.getItemDamage());
            if (animal != null) {
                ItemStack[] validFoods = animal.getValidFoods();
                this.foods.add(new PositionedStack(validFoods, 45, 16 + offY));
            }
        }
    }
}
