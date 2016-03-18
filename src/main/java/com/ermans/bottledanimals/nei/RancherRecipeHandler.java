package com.ermans.bottledanimals.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import com.ermans.bottledanimals.block.machine.rancher.GuiRancher;
import com.ermans.bottledanimals.block.machine.rancher.TileRancher;
import com.ermans.bottledanimals.client.render.RenderUtil;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.recipe.IRecipe;
import com.ermans.bottledanimals.recipe.RancherManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RancherRecipeHandler extends HandlerBase {
    private final RancherManager recManager = RancherManager.INSTANCE;



    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiRancher.class;
    }


    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("bottledanimals.nei.rancher");
    }

    @Override
    public String getOverlayIdentifier() {
        return "BARancher";
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result == null) {
            return;
        }
        Collection<RancherManager.RancherRecipe> recipes = recManager.getRecipes();
        for (RancherManager.RancherRecipe recipe : recipes) {
            if (recipe.getOutput() != null) {
                if (recipe.getOutput().isItemEqual(result)) {
                    RancherRecipeCached res = new RancherRecipeCached(recipe, recipe.getInput(), recipe.getOutput(), recipe.getFluidOutput());
                    this.arecipes.add(res);
                    break;
                }

            }
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if ((outputId.equals("BARancher")) && (getClass() == RancherRecipeHandler.class)) {
            Collection<RancherManager.RancherRecipe> recipes = recManager.getRecipes();
            for (RancherManager.RancherRecipe recipe : recipes) {
                RancherRecipeCached res = new RancherRecipeCached(recipe, recipe.getInput(), recipe.getOutput(), recipe.getFluidOutput());
                this.arecipes.add(res);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Collection<RancherManager.RancherRecipe> recipes = recManager.getRecipes();

        boolean isGear = ingredient.getItem() == ModItems.itemRancherGear;
        for (RancherManager.RancherRecipe recipe : recipes) {
            if (isGear) {
                RancherRecipeCached res = new RancherRecipeCached(recipe, recipe.getInput(), recipe.getOutput(), recipe.getFluidOutput());
                this.arecipes.add(res);
                continue;
            }
            if (recipe.getInput().isItemEqual(ingredient)) {
                RancherRecipeCached res = new RancherRecipeCached(recipe, recipe.getInput(), recipe.getOutput(), recipe.getFluidOutput());
                this.arecipes.add(res);
                break;
            }

        }
    }


    private boolean isBucket(int damage) {
        switch (damage) {
            case 2:
            case 6:
                return true;
            default:
                return false;
        }
    }

    private Point resultStackPoint = new Point(114, 45 + offY);

    @Override
    protected Point shouldDrawStack(StackType type) {
        if (type == StackType.RESULT) {
            return resultStackPoint;
        }
        return super.shouldDrawStack(type);
    }

    @Override
    public void drawBackground(int recipeIndex) {
        super.drawBackground(recipeIndex);

        RancherRecipeCached rancherRecipe = (RancherRecipeCached) arecipes.get(recipeIndex);
        drawEnergy(rancherRecipe.recipe.getRecipeTime() * 4, TileRancher.DF_ENERGY_CAPACITY);

        if (isBucket(rancherRecipe.getIngredients().get(0).item.getItemDamage())) {
            GuiDraw.drawTexturedModalRect(79, 40 + offY, 169, 32, 16, 13);
            drawProgressBar(79, 40 + offY, 185, 32, 16, 13, 48, 3);
        } else {
            GuiDraw.drawTexturedModalRect(79, 40 + offY, 202, 32, 16, 12);
            drawProgressBar(79, 40 + offY, 218, 32, 16, 13, 48, 0);
        }

        GuiDraw.drawTexturedModalRect(139, 18 + offY, 44, 79, 18, 62);
        RenderUtil.drawFluid(rancherRecipe.fluid, 140, 19 + offY, 16, 60, TileRancher.TANK_CAPACITY);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(140, 18 + offY, 78, 79, 16, 62);


        drawEnergyNeeded(27, 21 + offY, rancherRecipe.recipe.getRecipeTime() * 4);
        drawLiquidName(60, 70 + offY, rancherRecipe.fluid);

    }


    public class RancherRecipeCached extends CachedRecipeBase {
        private final List<PositionedStack> inputs;
        private PositionedStack output;
        private FluidStack fluid;

        @Override
        public List<PositionedStack> getIngredients() {
            return this.inputs;
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }

        @Override
        public PositionedStack getOtherStack() {
            return inputs.get(1);
        }

        public RancherRecipeCached(IRecipe recipe, ItemStack input1, ItemStack output, FluidStack fluid) {
            super(recipe);

            this.inputs = new ArrayList<PositionedStack>();

            this.inputs.add(new PositionedStack(input1, 79, 19 + offY));
            this.inputs.add(new PositionedStack(new ItemStack(ModItems.itemRancherGear), 44, 45 + offY));

            if (output != null) {
                this.output = new PositionedStack(output, 114, 45 + offY);
            }
            this.fluid = fluid;
        }
    }
}
