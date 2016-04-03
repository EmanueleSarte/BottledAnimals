package com.ermans.bottledanimals.init;

import com.ermans.bottledanimals.block.FluidBlockBA;
import com.ermans.bottledanimals.block.generator.basicgenerator.BlockBasicGenerator;
import com.ermans.bottledanimals.block.machine.animaldigitizer.BlockAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.animalmaterializer.BlockAnimalMaterializer;
import com.ermans.bottledanimals.block.machine.breeder.BlockBreeder;
import com.ermans.bottledanimals.block.machine.dropextractor.BlockDropExtractor;
import com.ermans.bottledanimals.block.machine.foodcrusher.BlockFoodCrusher;
import com.ermans.bottledanimals.block.machine.growthaccelerator.BlockGrowthAccelerator;
import com.ermans.bottledanimals.block.machine.rancher.BlockRancher;
import com.ermans.bottledanimals.block.machine.wirelessfeeder.BlockWirelessFeeder;
import com.ermans.bottledanimals.block.simple.BlockMachineFrame;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {

    public static BlockAnimalDigitizer blockAnimalDigitizer;
    public static BlockBreeder blockBreeder;
    public static BlockGrowthAccelerator blockGrowthAccelerator;
    public static BlockDropExtractor blockDropExtractor;
    public static BlockWirelessFeeder blockWirelessFeeder;
    public static BlockRancher blockRancher;
    public static BlockFoodCrusher blockFoodCrusher;
    public static BlockAnimalMaterializer blockAnimalMaterializer;

    public static BlockMachineFrame blockMachineFrame;

    public static BlockBasicGenerator blockBasicGenerator;

    public static FluidBlockBA milkBlock;
    public static FluidBlockBA foodBlock;

    public static Map<String, Block> blocks = new HashMap<String, Block>();
    public static Map<String, FluidBlockBA> fluidBlocks = new HashMap<String, FluidBlockBA>();


    public static void registerBlocks() {

        ////SIMPLE BLOCKS
        blockMachineFrame = (BlockMachineFrame) registerBlock(new BlockMachineFrame(), Names.Blocks.MACHINE_FRAME);


        ////MACHINES
        blockAnimalDigitizer = (BlockAnimalDigitizer) registerBlock(new BlockAnimalDigitizer(), Names.Machines.ANIMAL_DIGITIZER);
        blockBreeder = (BlockBreeder) registerBlock(new BlockBreeder(), Names.Machines.BREEDER);
        blockGrowthAccelerator = (BlockGrowthAccelerator) registerBlock(new BlockGrowthAccelerator(), Names.Machines.GROWTH_ACCELERATOR);
        blockDropExtractor = (BlockDropExtractor) registerBlock(new BlockDropExtractor(), Names.Machines.DROP_EXTRACTOR);
        blockRancher = (BlockRancher) registerBlock(new BlockRancher(), Names.Machines.RANCHER);
        blockFoodCrusher = (BlockFoodCrusher) registerBlock(new BlockFoodCrusher(), Names.Machines.FOOD_CRUSHER);
        blockAnimalMaterializer = (BlockAnimalMaterializer) registerBlock(new BlockAnimalMaterializer(), Names.Machines.ANIMAL_MATERIALIZER);
        blockWirelessFeeder = (BlockWirelessFeeder) registerBlock(new BlockWirelessFeeder(), Names.Machines.WIRELESS_FEEDER);

        blockBasicGenerator = (BlockBasicGenerator) registerBlock(new BlockBasicGenerator(), Names.Machines.BASIC_GENERATOR);


        ////FLUID BLOCKS
        milkBlock = registerFluidBlock(FluidBlockBA.create(ModFluids.milk));
        foodBlock = registerFluidBlock(FluidBlockBA.create(ModFluids.food));

    }

    private static Block registerBlock(Block block, String blockName) {
        blocks.put(blockName, block);
        return GameRegistry.registerBlock(block, blockName);
    }

    private static FluidBlockBA registerFluidBlock(FluidBlockBA block) {
        fluidBlocks.put(block.getRegistryName(), block);
        return (FluidBlockBA) GameRegistry.registerBlock(block);
    }
}
