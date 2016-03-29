//package com.ermans.bottledanimals.nei;
//
//import codechicken.lib.gui.GuiDraw;
//import codechicken.nei.PositionedStack;
//import com.ermans.bottledanimals.block.machine.dropextractor.GuiDropExtractor;
//import com.ermans.bottledanimals.block.machine.dropextractor.TileDropExtractor;
//import com.ermans.bottledanimals.init.ModItems;
//import com.ermans.bottledanimals.recipe.DropExtractorManager;
//import com.ermans.bottledanimals.recipe.IRecipe;
//import net.minecraft.client.gui.inventory.GuiContainer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.StatCollector;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class DropExtractorRecipeHandler extends HandlerRecipeBase {
//
//    private static final DropExtractorManager recManager = DropExtractorManager.INSTANCE;
//
//
//    @Override
//    public String getRecipeName() {
//        return StatCollector.translateToLocal("bottledanimals.nei.dropExtractor");
//    }
//
//    @Override
//    public Class<? extends GuiContainer> getGuiClass() {
//        return GuiDropExtractor.class;
//    }
//
//    @Override
//    public String getOverlayIdentifier() {
//        return "BADropExtractor";
//    }
//
//    @Override
//    public void loadCraftingRecipes(ItemStack result) {
//        if (result == null) {
//            return;
//        }
//
//        Collection<DropExtractorManager.DropExtractorRecipe> recipes = recManager.getRecipes();
//
//        boolean isPattern = result.getItem() == ModItems.itemBrokenPattern;
//        for (DropExtractorManager.DropExtractorRecipe recipe : recipes) {
//            if (isPattern) {
//                DropExtractorRecipeCached res = new DropExtractorRecipeCached(recipe, recipe.getInput(), recipe.getOutput1(), recipe.getOutput2(), recipe.getOutput3());
//                this.arecipes.add(res);
//                continue;
//            }
//            if (recipe.getOutput1().isItemEqual(result)) {
//                DropExtractorRecipeCached res = new DropExtractorRecipeCached(recipe, recipe.getInput(), recipe.getOutput1(), recipe.getOutput2(), recipe.getOutput3());
//                this.arecipes.add(res);
//                break;
//            }
//
//            if (recipe.getOutput2() != null) {
//                if (recipe.getOutput2().isItemEqual(result)) {
//                    DropExtractorRecipeCached res = new DropExtractorRecipeCached(recipe, recipe.getInput(), recipe.getOutput1(), recipe.getOutput2(), recipe.getOutput3());
//                    this.arecipes.add(res);
//                    break;
//                }
//            }
//        }
//    }
//
//    @Override
//    public void loadCraftingRecipes(String outputId, Object... results) {
//        if ((outputId.equals("BADropExtractor")) && (getClass() == DropExtractorRecipeHandler.class)) {
//            Collection<DropExtractorManager.DropExtractorRecipe> recipes = recManager.getRecipes();
//            for (DropExtractorManager.DropExtractorRecipe recipe : recipes) {
//                DropExtractorRecipeCached res = new DropExtractorRecipeCached(recipe, recipe.getInput(), recipe.getOutput1(), recipe.getOutput2(), recipe.getOutput3());
//                this.arecipes.add(res);
//            }
//        } else {
//            super.loadCraftingRecipes(outputId, results);
//        }
//    }
//
//    @Override
//    public void loadUsageRecipes(ItemStack ingredient) {
//        Collection<DropExtractorManager.DropExtractorRecipe> recipes = recManager.getRecipes();
//        for (DropExtractorManager.DropExtractorRecipe recipe : recipes) {
//            if (recipe.getInput().isItemEqual(ingredient)) {
//                DropExtractorRecipeCached res = new DropExtractorRecipeCached(recipe, recipe.getInput(), recipe.getOutput1(), recipe.getOutput2(), recipe.getOutput3());
//                this.arecipes.add(res);
//                break;
//            }
//        }
//    }
//
//    private Point resultStackPoint = new Point(140, 33 + offY);
//
//    @Override
//    protected Point shouldDrawStack(StackType type) {
//        if (type == StackType.OTHER) {
//            return resultStackPoint;
//        }
//        return super.shouldDrawStack(type);
//    }
//
//
//    @Override
//    public void drawBackground(int recipeIndex) {
//        super.drawBackground(recipeIndex);
//        CachedRecipeBase recipeBase = (CachedRecipeBase) arecipes.get(recipeIndex);
//        drawEnergy(recipeBase.recipe.getRecipeTime() * 10, TileDropExtractor.DF_ENERGY_CAPACITY);
//
////        //Draw the little rectangle between the two upper output slots
////        GuiDraw.drawRect(137, 40 + offY, 2, 4, 0x8b8b8b);
//
//        GuiDraw.drawTexturedModalRect(77, 29 + offY, 169, 64, 26, 16);
//        drawProgressBar(77, 29 + offY, 195, 64, 26, 16, 48, 0);
//        drawEnergyNeeded(59, 60 + offY, recipeBase.recipe.getRecipeTime() * 10);
//    }
//
//    public class DropExtractorRecipeCached extends CachedRecipeBase {
//        private final PositionedStack input;
//        private final PositionedStack output;
//        private final List<PositionedStack> outputs;
//
//        @Override
//        public PositionedStack getResult() {
//            return output;
//        }
//
//        @Override
//        public List<PositionedStack> getOtherStacks() {
//            return outputs;
//        }
//
//        @Override
//        public PositionedStack getIngredient() {
//            return this.input;
//        }
//
//
//        public DropExtractorRecipeCached(IRecipe recipe, ItemStack input, ItemStack output1, ItemStack output2, ItemStack output3) {
//            super(recipe);
//            this.input = new PositionedStack(input, 53, 29 + offY);
//            this.output = new PositionedStack(output1, 116, 29 + offY);
//
//            this.outputs = new ArrayList<PositionedStack>();
//            if (output2 != null) {
//                this.outputs.add(new PositionedStack(output2, 140, 33 + offY));
//            }
//            this.outputs.add(new PositionedStack(output3, 116, 56 + offY));
//
//        }
//    }
//}
