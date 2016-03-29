package com.ermans.bottledanimals;


import com.ermans.bottledanimals.block.machine.animaldigitizer.BlockAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.animaldigitizer.TileAnimalDigitizer;
import com.ermans.bottledanimals.reference.Names;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public static BlockAnimalDigitizer blockAnimalDigitizer;


    public void preInit(FMLPreInitializationEvent event){

        blockAnimalDigitizer = new BlockAnimalDigitizer();





        GameRegistry.registerBlock(blockAnimalDigitizer, Names.Machines.ANIMAL_DIGITIZER);
        GameRegistry.registerTileEntity(TileAnimalDigitizer.class, Names.Machines.ANIMAL_DIGITIZER);


    }

    public void init(FMLInitializationEvent event){
    }


    public void postInit(FMLPostInitializationEvent event){
    }

}
