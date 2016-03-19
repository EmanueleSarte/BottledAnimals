package com.ermans.bottledanimals.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.ermans.bottledanimals.recipe.IRecipe;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.util.helpers.StringHelper;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public abstract class HandlerRecipeBase extends TemplateRecipeHandler {


    protected int offY;
    protected int offEnergyX;
    protected int offEnergyY;

    @Override
    public void loadTransferRects() {
        initialize();
        int nChars = getRecipeName().length() * 7;
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84 - nChars / 2, -8, nChars, 8), getOverlayIdentifier()));
    }

    @Override
    public String getGuiTexture() {
        return Textures.Gui.NEI_GUI.getResourceDomain() + ":" + Textures.Gui.NEI_GUI.getResourcePath();
    }

    public void initialize() {
        offY = -17;
        this.offEnergyX = 0;
        this.offEnergyY = 0;
    }


    public enum StackType {
        INGREDIENT, RESULT, OTHER;
    }

    protected Point shouldDrawStack(StackType type) {
        return null;
    }

    @Override
    public void drawBackground(int recipeIndex) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 168, 77);

        CachedRecipe cachedRecipe = arecipes.get(recipeIndex);
        if (cachedRecipe != null) {
            if (cachedRecipe.getIngredient() != null) {
                PositionedStack ing = cachedRecipe.getIngredient();
                drawSlot(ing.relx - 1, ing.rely - 1);
            } else if (cachedRecipe.getIngredients() != null) {
                for (PositionedStack stack : cachedRecipe.getIngredients()) {
                    drawSlot(stack.relx - 1, stack.rely - 1);
                }
            } else {
                Point point = shouldDrawStack(StackType.INGREDIENT);
                if (point != null) {
                    drawSlot(point.x - 1, point.y - 1);
                }
            }
            if (cachedRecipe.getResult() != null) {
                PositionedStack ing = cachedRecipe.getResult();
                drawSlot(ing.relx - 5, ing.rely - 5, true);
            } else {
                Point point = shouldDrawStack(StackType.RESULT);
                if (point != null) {
                    drawSlot(point.x - 5, point.y - 5, true);
                }
            }

            if (cachedRecipe.getOtherStack() != null) {
                PositionedStack ing = cachedRecipe.getOtherStack();
                drawSlot(ing.relx - 1, ing.rely - 1);
            } else if (cachedRecipe.getOtherStacks() != null) {
                for (PositionedStack stack : cachedRecipe.getOtherStacks()) {
                    drawSlot(stack.relx - 1, stack.rely - 1);
                }
            } else {
                Point point = shouldDrawStack(StackType.OTHER);
                if (point != null) {
                    drawSlot(point.x - 1, point.y - 1);
                }
            }
        }
    }



    protected void drawEnergy(int energy, int capacity) {
        GuiDraw.drawTexturedModalRect(9 + offEnergyX, 25 + offY + offEnergyY, 94, 79, 14, 42);
        int offset = 40 - (energy * 40 / capacity);
        GuiDraw.drawTexturedModalRect(9 + offEnergyX, 26 + offY + offset + offEnergyY, 108, 80 + offset, 14, offset);
    }


    protected void drawSlot(int x, int y) {
        drawSlot(x, y, false);
    }


    protected void drawSlot(int x, int y, boolean bigSlot) {
        if (bigSlot) {
            GuiDraw.drawTexturedModalRect(x, y, 18, 79, 26, 26);
        } else {
            GuiDraw.drawTexturedModalRect(x, y, 0, 79, 18, 18);
        }
    }

    //THERE IS NO METHOD TO GET THE X AND Y OF THE NEIGUI, so no (custom) tooltips
//
//    @Override
//    public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> tooltipList, int recipeIndex) {
//        int i = 9;
//        int j = i + 14;
//        int k = 25 + offY;
//        int m = k + 42;
//        Point p = GuiDraw.getMousePosition();
//        if ((p.x >= i + p.x) && (p.x < j + guiRecipe.width) && (p.y >= k + guiRecipe.height) && (p.y < m + guiRecipe.height)) {
//            handleTooltipEnergyBar(tooltipList, recipeIndex);
//        }
//        return super.handleTooltip(guiRecipe, tooltipList, recipeIndex);
//    }
//
//    protected void handleTooltipEnergyBar(List<String> tooltipList, int recipeIndex) {
//    }

    protected void drawEnergyNeeded(int x, int y, int energy) {
        GuiDraw.drawString(energy + " RF", x, y, 9671571, false);
    }

    protected void drawLiquidName(int x, int y, FluidStack fluid) {
        if (fluid != null && fluid.getFluid() != null) {
            GuiDraw.drawString(StringHelper.titleCase(fluid.getFluid().getName()) + " " + fluid.amount + " mB", x, y, 9671571, false);
        }
    }


    public abstract class CachedRecipeBase extends CachedRecipe {
        protected IRecipe recipe;

        public CachedRecipeBase(IRecipe recipe) {
            this.recipe = recipe;
        }

        public IRecipe getRecipe() {
            return recipe;
        }

        public void setRecipe(IRecipe recipe) {
            this.recipe = recipe;
        }


    }

}
