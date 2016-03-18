package com.ermans.bottledanimals.nei;


import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import com.ermans.bottledanimals.block.machine.animaldigitizer.GuiAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.foodcrusher.TileFoodCrusher;
import com.ermans.bottledanimals.block.machine.rancher.TileRancher;
import com.ermans.bottledanimals.client.render.RenderUtil;
import com.ermans.bottledanimals.helper.FoodHelper;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.recipe.FoodCrusherManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;

public class FoodCrusherRecipeHandler extends HandlerBase {

    private final FoodCrusherManager recManager = FoodCrusherManager.INSTANCE;

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiAnimalDigitizer.class;
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("bottledanimals.nei.foodCrusher");
    }

    @Override
    public String getOverlayIdentifier() {
        return "BAFoodCrusher";
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        //No items output
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("BAFoodCrusher") && getClass() == FoodCrusherRecipeHandler.class) {
            Collection<FoodCrusherManager.FoodCrusherRecipe> recipes = recManager.getRecipes();
            for (FoodCrusherManager.FoodCrusherRecipe recipe : recipes) {
                FoodCrusherRecipeCached res = new FoodCrusherRecipeCached(recipe, recipe.getInput(), recipe.getFoodAmount());
                this.arecipes.add(res);
            }

        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (FoodHelper.getFoodValue(ingredient) > 0) {
            FoodCrusherManager.FoodCrusherRecipe recipeValid = recManager.getRecipeIfValid(ingredient);
            if (recipeValid != null) {
                FoodCrusherRecipeCached res = new FoodCrusherRecipeCached(recipeValid, recipeValid.getInput(), recipeValid.getFoodAmount());
                this.arecipes.add(res);
            }
        }
    }


    @Override
    public void drawBackground(int recipeIndex) {
        super.drawBackground(recipeIndex);
        FoodCrusherRecipeCached recipeBase = (FoodCrusherRecipeCached) arecipes.get(recipeIndex);
        drawEnergy(recipeBase.recipe.getRecipeTime() * 10, TileFoodCrusher.DF_ENERGY_CAPACITY);
        GuiDraw.drawTexturedModalRect(79, 43 + offY, 169, 48, 16, 16);
        drawProgressBar(79, 43 + offY, 185, 48, 16, 16, 48, 3);


        GuiDraw.drawTexturedModalRect(139, 18 + offY, 44, 79, 18, 62);
        RenderUtil.drawFluid(recipeBase.fluidstack, 140, 19 + offY, 16, 60, TileRancher.TANK_CAPACITY);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(140, 18 + offY, 78, 79, 16, 62);


        drawEnergyNeeded(31, 44 + offY, recipeBase.recipe.getRecipeTime() * 10);
        drawLiquidName(31, 61 + offY, recipeBase.fluidstack);
    }


    public class FoodCrusherRecipeCached extends CachedRecipeBase {
        private final PositionedStack input;
        private final PositionedStack container;
        private FluidStack fluidstack;


        @Override
        public PositionedStack getOtherStack() {
            return container;
        }

        @Override
        public PositionedStack getIngredient() {
            return this.input;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }

        public FoodCrusherRecipeCached(IRecipe recipe, ItemStack input, int fluidAmount) {
            super(recipe);
            this.input = new PositionedStack(input, 79, 25 + offY);
            this.fluidstack = new FluidStack(ModFluids.food, fluidAmount);
            if (input.getItem().hasContainerItem(input)) {
                container = new PositionedStack(new ItemStack(input.getItem().getContainerItem()), 115, 47 + offY);
            } else if (input.getItem() == Items.mushroom_stew) {
                container = new PositionedStack(new ItemStack(Items.bowl), 115, 47 + offY);
            }else{
                container = null;
            }
        }
    }
}
