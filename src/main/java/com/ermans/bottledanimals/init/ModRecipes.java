package com.ermans.bottledanimals.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {
    public static void init() {
        initCraftingRecipes();
    }

    private static void initCraftingRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemBottle, " W ", "GRG", "GGG", 'W', Blocks.planks, 'G', Blocks.glass, 'R', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemAnimalCircuit, "ILR", "IGG", "ILR", 'I', Items.iron_ingot, 'L', new ItemStack(Items.dye, 1, 4), 'R', Items.redstone, 'G', Items.gold_nugget));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemBlankPattern, "GBG", "BCB", "GBG", 'G', Blocks.glass_pane, 'B', Blocks.iron_bars, 'C', ModItems.itemAnimalCircuit));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemBlankPattern, " G ", "GPG", " G ", 'G', Blocks.glass_pane, 'P', ModItems.itemBrokenPattern));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemRancherGear, " B ", "BIB", " B ", 'B', Blocks.iron_bars, 'I', Items.iron_ingot));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemSquidFood, 3), new ItemStack(Items.fish, 1, 0), new ItemStack(Items.dye, 1, 15)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemSquidFood, 3), new ItemStack(Items.fish, 1, 1), new ItemStack(Items.dye, 1, 15)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemSquidFood, 3), new ItemStack(Items.fish, 1, 2), new ItemStack(Items.dye, 1, 15)));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockMachineFrame, "ILI", "LGL", "ILI", 'I', Items.iron_ingot, 'L', new ItemStack(Items.dye, 1, 4), 'G', Blocks.glass));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockAnimalDigitizer, " P ", "BMB", "GCG", 'P', ModItems.itemBlankPattern, 'B', ModItems.itemBottle, 'M', ModBlocks.blockMachineFrame, 'G', Items.gold_ingot, 'C', ModItems.itemAnimalCircuit));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockBreeder, " L ", "PMP", "GCG", 'L', Items.speckled_melon, 'P', ModItems.itemBlankPattern, 'M', ModBlocks.blockMachineFrame, 'G', Items.gold_ingot, 'C', ModItems.itemAnimalCircuit));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockBreeder, " L ", "PMP", "GCG", 'L', Items.golden_carrot, 'P', ModItems.itemBlankPattern, 'M', ModBlocks.blockMachineFrame, 'G', Items.gold_ingot, 'C', ModItems.itemAnimalCircuit));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockGrowthAccelerator, " R ", "FMF", "GCG", 'R', Items.redstone, 'F', Items.fire_charge, 'M', ModBlocks.blockMachineFrame, 'G', Items.gold_ingot, 'C', ModItems.itemAnimalCircuit));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockDropExtractor, " R ", "SMS", "GCG", 'R', Items.redstone, 'S', Items.iron_sword, 'M', ModBlocks.blockMachineFrame, 'G', Items.gold_ingot, 'C', ModItems.itemAnimalCircuit));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockRancher, " S ", "BMB", "GCG", 'S', Items.shears, 'B', Items.bucket, 'M', ModBlocks.blockMachineFrame, 'G', Items.gold_ingot, 'C', ModItems.itemAnimalCircuit));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockWirelessFeeder, " E ", "GMG", " C ", 'E', Items.ender_pearl, 'M', ModBlocks.blockMachineFrame, 'G', Items.gold_ingot, 'C', ModItems.itemAnimalCircuit));

        GameRegistry.addRecipe(new ShapelessOreRecipe(Items.milk_bucket, ModItems.milkBucket, Items.bucket));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.milkBucket, Items.milk_bucket, Items.bucket));

        GameRegistry.addRecipe(new ShapedOreRecipe(Items.cake, "BBB", "SES", "WWW", 'B', "bucketMilk", 'S', Items.sugar, 'E', Items.egg, 'W', Items.wheat));
    }

}
