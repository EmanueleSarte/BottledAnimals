package com.ermans.bottledanimals.init;

import com.ermans.bottledanimals.block.FluidBlockBA;
import com.ermans.bottledanimals.item.ItemBucketBA;
import com.ermans.bottledanimals.item.ItemModIcon;
import com.ermans.bottledanimals.item.simple.*;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    public static ItemBottle itemBottle;
    public static ItemAnimalInABottle itemAnimalInABottle;
    public static ItemDigitalizedAnimal itemDigitalizedAnimal;
    public static ItemDigitalizedBabyAnimal itemDigitalizedBabyAnimal;
    public static ItemBlankPattern itemBlankPattern;
    public static ItemBrokenPattern itemBrokenPattern;
    public static ItemAnimalCircuit itemAnimalCircuit;
    public static ItemSquidFood itemSquidFood;
    public static ItemModIcon itemModIcon = new ItemModIcon();
    public static ItemRancherGear itemRancherGear;
    public static ItemBucketBA milkBucket;
    public static ItemSpawnEggFrame itemSpawnEggFrame;
    public static ItemBucketBA foodBucket;

    public static Map<String, Item> items = new HashMap<String, Item>();

    public static void registerItems() {

        itemBottle = (ItemBottle) registerItem(new ItemBottle(), Names.Items.BOTTLE);
        itemAnimalInABottle = (ItemAnimalInABottle) registerItem(new ItemAnimalInABottle(), Names.Items.ANIMAL_IN_A_BOTTLE);
        itemDigitalizedAnimal = (ItemDigitalizedAnimal) registerItem(new ItemDigitalizedAnimal(), Names.Items.DIGITALIZED_ANIMAL);
        itemDigitalizedBabyAnimal = (ItemDigitalizedBabyAnimal) registerItem(new ItemDigitalizedBabyAnimal(), Names.Items.DIGITALIZED_BABY_ANIMAL);
        itemBlankPattern = (ItemBlankPattern) registerItem(new ItemBlankPattern(), Names.Items.BLANK_PATTERN);
        itemBrokenPattern = (ItemBrokenPattern) registerItem(new ItemBrokenPattern(), Names.Items.BROKEN_PATTERN);
        itemAnimalCircuit = (ItemAnimalCircuit) registerItem(new ItemAnimalCircuit(), Names.Items.ANIMAL_CIRCUIT);
        itemSquidFood = (ItemSquidFood) registerItem(new ItemSquidFood(), Names.Items.SQUID_FOOD);
        itemRancherGear = (ItemRancherGear) registerItem(new ItemRancherGear(), Names.Items.RANCHER_GEAR);
        itemSpawnEggFrame = (ItemSpawnEggFrame) registerItem(new ItemSpawnEggFrame(), Names.Items.SPAWN_EGG_FRAME);

        itemModIcon = (ItemModIcon) registerItem(new ItemModIcon(), "modIcon");


        milkBucket = registerBucket(ModBlocks.milkBlock, ModFluids.milk);
        foodBucket = registerBucket(ModBlocks.foodBlock, ModFluids.food);

    }

    private static Item registerItem(Item item, String itemName) {
        items.put(itemName, item);
        GameRegistry.registerItem(item, itemName);
        return item;
    }

    private static ItemBucketBA registerBucket(FluidBlockBA fluidBlock, Fluid fluid) {
        ItemBucketBA bucket = ItemBucketBA.create(fluidBlock, fluid.getName() + Names.Items.BUCKET_SUFFIX);
        items.put(fluid.getName() + Names.Items.BUCKET_SUFFIX, bucket);
        GameRegistry.registerItem(bucket, fluid.getName() + Names.Items.BUCKET_SUFFIX);
        return bucket;
    }
}

