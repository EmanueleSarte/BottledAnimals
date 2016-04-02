package com.ermans.bottledanimals;


import com.ermans.bottledanimals.block.machine.animaldigitizer.TileAnimalDigitizer;
import com.ermans.bottledanimals.init.ModBlocks;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.reference.Names;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
    }


    ////////////REGISTER METHODS


    private static void registerTileEntity() {
        GameRegistry.registerTileEntity(TileAnimalDigitizer.class, Names.Machines.ANIMAL_DIGITIZER);
    }


}
