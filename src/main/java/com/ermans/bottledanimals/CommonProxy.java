package com.ermans.bottledanimals;


import com.ermans.bottledanimals.animal.Animals;
import com.ermans.bottledanimals.block.generator.basicgenerator.TileBasicGenerator;
import com.ermans.bottledanimals.block.machine.animaldigitizer.TileAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.animalmaterializer.TileAnimalMaterializer;
import com.ermans.bottledanimals.block.machine.breeder.TileBreeder;
import com.ermans.bottledanimals.block.machine.dropextractor.TileDropExtractor;
import com.ermans.bottledanimals.block.machine.foodcrusher.TileFoodCrusher;
import com.ermans.bottledanimals.block.machine.growthaccelerator.TileGrowthAccelerator;
import com.ermans.bottledanimals.block.machine.rancher.TileRancher;
import com.ermans.bottledanimals.block.machine.wirelessfeeder.TileWirelessFeeder;
import com.ermans.bottledanimals.helper.FoodHelper;
import com.ermans.bottledanimals.init.ModBlocks;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.recipe.*;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonProxy {


    public void preInit(FMLPreInitializationEvent event) {


        ModFluids.registerFluids();
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        registerTileEntity();

    }

    public void init(FMLInitializationEvent event) {
    }


    public void postInit(FMLPostInitializationEvent event) {
        cyclesItems();
        Animals.initializeAnimals();

        ///Initiliaze Recipe
        AnimalDigitizerManager.INSTANCE.initRecipes();
        AnimalMaterializerManager.INSTANCE.initRecipes();
        BreederManager.INSTANCE.initRecipes();
        DropExtractorManager.INSTANCE.initRecipes();
        GrowthAcceleratorManager.INSTANCE.initRecipes();
        RancherManager.INSTANCE.initRecipes();
    }


    private void cyclesItems() {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for (Iterator<Item> iterator = Item.itemRegistry.iterator(); iterator.hasNext(); ) {
            Item item = iterator.next();
            stacks.clear();
            if (item.getHasSubtypes()) {
                item.getSubItems(item, null, stacks);
            } else {
                stacks.add(new ItemStack(item));
            }

            for (ItemStack stack : stacks) {
                ///////////INSERT METHOD CALLS HERE //////////
                FoodHelper.addIfValid(stack);
            }
        }
    }


    ////////////REGISTER METHODS////////////////
    private static void registerTileEntity() {
        GameRegistry.registerTileEntity(TileAnimalDigitizer.class, Names.Machines.ANIMAL_DIGITIZER);
        GameRegistry.registerTileEntity(TileBreeder.class, Names.Machines.BREEDER);
        GameRegistry.registerTileEntity(TileDropExtractor.class, Names.Machines.DROP_EXTRACTOR);
        GameRegistry.registerTileEntity(TileGrowthAccelerator.class, Names.Machines.GROWTH_ACCELERATOR);
        GameRegistry.registerTileEntity(TileAnimalMaterializer.class, Names.Machines.ANIMAL_MATERIALIZER);
        GameRegistry.registerTileEntity(TileRancher.class, Names.Machines.RANCHER);
        GameRegistry.registerTileEntity(TileFoodCrusher.class, Names.Machines.FOOD_CRUSHER);
        GameRegistry.registerTileEntity(TileWirelessFeeder.class, Names.Machines.WIRELESS_FEEDER);

        GameRegistry.registerTileEntity(TileBasicGenerator.class, Names.Machines.BASIC_GENERATOR);


    }


}
