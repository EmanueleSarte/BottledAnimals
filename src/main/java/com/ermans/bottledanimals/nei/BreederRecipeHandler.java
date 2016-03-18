package com.ermans.bottledanimals.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import com.ermans.bottledanimals.block.machine.breeder.GuiBreeder;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.recipe.BreederManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BreederRecipeHandler extends HandlerBase {
    private static final BreederManager recManager = BreederManager.INSTANCE;




    @Override
    public void initialize() {
        this.offY = -10;
        this.offEnergyY = -11;
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("bottledanimals.nei.breeder");
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiBreeder.class;
    }

    @Override
    public String getOverlayIdentifier() {
        return "BABreeder";
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result == null) {
            return;
        }

        Collection<BreederManager.BreederRecipe> recipes = recManager.getRecipes();
        for (BreederManager.BreederRecipe recipe : recipes) {
            ItemStack output = recipe.getOutput();
            if (output.isItemEqual(result)) {
                BreederRecipeCached res = new BreederRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), recipe.getOutput(), recipe.getItemsFood());
                this.arecipes.add(res);
            }
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if ((outputId.equals("BABreeder")) && (getClass() == BreederRecipeHandler.class)) {
            Collection<BreederManager.BreederRecipe> recipes = recManager.getRecipes();
            for (BreederManager.BreederRecipe recipe : recipes) {
                BreederRecipeCached res = new BreederRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), recipe.getOutput(), recipe.getItemsFood());
                this.arecipes.add(res);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        boolean isPattern = ingredient.getItem() == ModItems.itemBlankPattern;

        Collection<BreederManager.BreederRecipe> recipes = recManager.getRecipes();
        for (BreederManager.BreederRecipe recipe : recipes) {
            if (isPattern) {
                BreederRecipeCached res = new BreederRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), recipe.getOutput(), recipe.getItemsFood());
                this.arecipes.add(res);
                continue;
            }

            for (ItemStack is : new ItemStack[]{recipe.getInput1(), recipe.getInput2()}) {
                if (is.isItemEqual(ingredient)) {
                    BreederRecipeCached res = new BreederRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), recipe.getOutput(), recipe.getItemsFood());
                    this.arecipes.add(res);
                    break;
                }
            }

            for (ItemStack is : recipe.getItemsFood()) {
                if (is.isItemEqual(ingredient)) {
                    BreederRecipeCached res = new BreederRecipeCached(recipe, recipe.getInput1(), recipe.getInput2(), recipe.getOutput(), recipe.getItemsFood());
                    this.arecipes.add(res);
                    break;
                }
            }
        }
    }

    @Override
    public void drawBackground(int recipeIndex) {
        super.drawBackground(recipeIndex);
        CachedRecipeBase recipeBase = (CachedRecipeBase) arecipes.get(recipeIndex);
        drawEnergy(recipeBase.recipe.getRecipeTime() * 5, 240000);
        GuiDraw.drawTexturedModalRect(82, 27 + offY, 169, 16, 31, 16);
        drawProgressBar(82, 27 + offY, 200, 16, 31, 16, 48, 0);
        drawEnergyNeeded(26, 63 + offY, recipeBase.recipe.getRecipeTime() * 5);
    }

    public class BreederRecipeCached extends CachedRecipeBase {
        protected List<PositionedStack> inputs;
        protected PositionedStack output;
        protected List<PositionedStack> foods;

        @Override
        public List<PositionedStack> getIngredients() {
            return inputs;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return getCycledIngredients(cycleticks / 20, foods);
        }

        @Override
        public PositionedStack getResult() {
            return this.output;
        }

        public BreederRecipeCached(IRecipe recipe, ItemStack input1, ItemStack input2, ItemStack output, ItemStack[] foods) {
            super(recipe);

            this.inputs = new ArrayList<PositionedStack>();
            this.inputs.add(new PositionedStack(input1, 47, 15 + offY));
            this.inputs.add(new PositionedStack(input2, 47, 38 + offY));
            this.inputs.add(new PositionedStack(new ItemStack(ModItems.itemBlankPattern), 75, 59 + offY));

            this.foods = new ArrayList<PositionedStack>();
            this.foods.add(new PositionedStack(foods, 99, 59 + offY));

            this.output = new PositionedStack(output, 134, 25 + offY);
        }
    }
}
