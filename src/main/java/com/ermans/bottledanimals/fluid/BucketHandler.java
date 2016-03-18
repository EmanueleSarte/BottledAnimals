package com.ermans.bottledanimals.fluid;

import com.ermans.bottledanimals.init.ModBlocks;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.HashMap;
import java.util.Map;

public class BucketHandler {

    public static final BucketHandler INSTANCE = new BucketHandler();
    private final Map<Block, Item> buckets = new HashMap<Block, Item>();


    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        ItemStack result = fillCustomBucket(event.world, event.target);
        if (result == null) {
            return;
        }
        event.result = result;
        event.setResult(Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

        Item bucket = this.buckets.get(block);
        if ((bucket != null) && (world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0)) {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(bucket);
        }
        return null;
    }

    public static void init() {
        FluidContainerRegistry.registerFluidContainer(ModFluids.milk, new ItemStack(ModItems.milkBucket), new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModBlocks.milkBlock, ModItems.milkBucket);
        FluidContainerRegistry.registerFluidContainer(ModFluids.food, new ItemStack(ModItems.foodBucket), new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModBlocks.foodBlock, ModItems.foodBucket);
    }
}
