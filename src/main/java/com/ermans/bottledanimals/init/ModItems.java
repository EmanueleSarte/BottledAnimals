package com.ermans.bottledanimals.init;

import com.ermans.bottledanimals.fluid.ItemBucketBA;
import com.ermans.bottledanimals.item.ItemModIcon;
import com.ermans.bottledanimals.item.simple.*;
import com.ermans.bottledanimals.reference.Names;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
    public static Item itemBottle;
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

    public static void init() {
        itemBottle = new ItemBottle();
        itemAnimalInABottle = new ItemAnimalInABottle();
        itemDigitalizedAnimal = new ItemDigitalizedAnimal();
        itemDigitalizedBabyAnimal = new ItemDigitalizedBabyAnimal();
        itemBlankPattern = new ItemBlankPattern();
        itemBrokenPattern = new ItemBrokenPattern();
        itemAnimalCircuit = new ItemAnimalCircuit();
        itemSquidFood = new ItemSquidFood();
        itemRancherGear = new ItemRancherGear();
        itemSpawnEggFrame = new ItemSpawnEggFrame();

        itemModIcon = new ItemModIcon();
//        milkBucket = ItemBucketBA.create(ModBlocks.milkBlock, ModFluids.milk.getName() + "Bucket");
//        foodBucket = ItemBucketBA.create(ModBlocks.foodBlock, ModFluids.food.getName() + "Bucket");

        GameRegistry.registerItem(itemBottle, Names.Items.BOTTLE);
        GameRegistry.registerItem(itemAnimalInABottle, Names.Items.ANIMAL_IN_A_BOTTLE);
        GameRegistry.registerItem(itemDigitalizedAnimal, Names.Items.DIGITALIZED_ANIMAL);
        GameRegistry.registerItem(itemBlankPattern, Names.Items.BLANK_PATTERN);
        GameRegistry.registerItem(itemDigitalizedBabyAnimal, Names.Items.DIGITALIZED_BABY_ANIMAL);
        GameRegistry.registerItem(itemBrokenPattern, Names.Items.BROKEN_PATTERN);
        GameRegistry.registerItem(itemAnimalCircuit, Names.Items.ANIMAL_CIRCUIT);
        GameRegistry.registerItem(itemSquidFood, Names.Items.SQUID_FOOD);
        GameRegistry.registerItem(itemRancherGear, Names.Items.RANCHER_GEAR);
        GameRegistry.registerItem(itemSpawnEggFrame, Names.Items.SPAWN_EGG_FRAME);

        GameRegistry.registerItem(itemModIcon, "ModIcon");
//        GameRegistry.registerItem(milkBucket, ModFluids.milk.getName() + "Bucket");
//        GameRegistry.registerItem(foodBucket, ModFluids.food.getName() + "Bucket");

        //OreDictionary.registerOre("bucketMilk", milkBucket);
    }
}

