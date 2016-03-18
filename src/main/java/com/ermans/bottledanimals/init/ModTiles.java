package com.ermans.bottledanimals.init;

import com.ermans.bottledanimals.block.machine.animaldigitizer.TileAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.breeder.TileBreeder;
import com.ermans.bottledanimals.block.machine.dropextractor.TileDropExtractor;
import com.ermans.bottledanimals.block.machine.foodcrusher.TileFoodCrusher;
import com.ermans.bottledanimals.block.machine.growthaccelerator.TileGrowthAccelerator;
import com.ermans.bottledanimals.block.machine.rancher.TileRancher;
import com.ermans.bottledanimals.block.machine.wirelessfeeder.TileWirelessFeeder;
import com.ermans.bottledanimals.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModTiles {
    public static void init() {
        GameRegistry.registerTileEntity(TileAnimalDigitizer.class, Names.Machines.ANIMAL_DIGITIZER);
        GameRegistry.registerTileEntity(TileBreeder.class, Names.Machines.BREEDER);
        GameRegistry.registerTileEntity(TileGrowthAccelerator.class, Names.Machines.GROWTH_ACCELERATOR);
        GameRegistry.registerTileEntity(TileDropExtractor.class, Names.Machines.DROP_EXTRACTOR);
        GameRegistry.registerTileEntity(TileRancher.class, Names.Machines.RANCHER);
        GameRegistry.registerTileEntity(TileFoodCrusher.class, Names.Machines.FOOD_CRUSHER);
        GameRegistry.registerTileEntity(TileWirelessFeeder.class, Names.Machines.WIRELESS_FEEDER);
    }
}
