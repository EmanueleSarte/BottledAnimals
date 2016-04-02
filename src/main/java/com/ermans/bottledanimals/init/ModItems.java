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

    public static final Map<String, Item> items = new HashMap<String, Item>();

    public static void registerItems() {
        itemBottle = (ItemBottle) registerItem(new ItemBottle(), Names.Items.BOTTLE);


        milkBucket = registerBucket(ModBlocks.milkBlock, ModFluids.milk);
        foodBucket = registerBucket(ModBlocks.foodBlock, ModFluids.food);


//        itemAnimalInABottle = new ItemAnimalInABottle();
//        itemDigitalizedAnimal = new ItemDigitalizedAnimal();
//        itemDigitalizedBabyAnimal = new ItemDigitalizedBabyAnimal();
//        itemBlankPattern = new ItemBlankPattern();
//        itemBrokenPattern = new ItemBrokenPattern();
//        itemAnimalCircuit = new ItemAnimalCircuit();
//        itemSquidFood = new ItemSquidFood();
//        itemRancherGear = new ItemRancherGear();
//        itemSpawnEggFrame = new ItemSpawnEggFrame();
//
//        itemModIcon = new ItemModIcon();


//        GameRegistry.registerItem(itemBottle, Names.Items.BOTTLE);
//        GameRegistry.registerItem(itemAnimalInABottle, Names.Items.ANIMAL_IN_A_BOTTLE);
//        GameRegistry.registerItem(itemDigitalizedAnimal, Names.Items.DIGITALIZED_ANIMAL);
//        GameRegistry.registerItem(itemBlankPattern, Names.Items.BLANK_PATTERN);
//        GameRegistry.registerItem(itemDigitalizedBabyAnimal, Names.Items.DIGITALIZED_BABY_ANIMAL);
//        GameRegistry.registerItem(itemBrokenPattern, Names.Items.BROKEN_PATTERN);
//        GameRegistry.registerItem(itemAnimalCircuit, Names.Items.ANIMAL_CIRCUIT);
//        GameRegistry.registerItem(itemSquidFood, Names.Items.SQUID_FOOD);
//        GameRegistry.registerItem(itemRancherGear, Names.Items.RANCHER_GEAR);
//        GameRegistry.registerItem(itemSpawnEggFrame, Names.Items.SPAWN_EGG_FRAME);
//
//        GameRegistry.registerItem(itemModIcon, "ModIcon");
//        GameRegistry.registerItem(milkBucket, ModFluids.milk.getName() + "Bucket");
//        GameRegistry.registerItem(foodBucket, ModFluids.food.getName() + "Bucket");

        //OreDictionary.registerOre("bucketMilk", milkBucket);
    }

    private static Item registerItem(Item item, String itemName) {
        items.put(itemName, item);
        GameRegistry.registerItem(item, itemName);
        return item;
    }

    private static ItemBucketBA registerBucket(FluidBlockBA fluidBlock, Fluid fluid){
        ItemBucketBA bucket = ItemBucketBA.create(fluidBlock, fluid.getName() + Names.Items.BUCKET_SUFFIX);
        items.put(fluid.getName() + Names.Items.BUCKET_SUFFIX, bucket);
        GameRegistry.registerItem(bucket, fluid.getName() + Names.Items.BUCKET_SUFFIX);
        return bucket;
    }
}

