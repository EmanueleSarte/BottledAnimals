package com.ermans.bottledanimals.fluid;

import com.ermans.bottledanimals.init.ModBlocks;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        event.setResult(Event.Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

        IBlockState block = world.getBlockState(pos.getBlockPos());

        Item bucket = this.buckets.get(block.getBlock());
        if ((bucket != null) && (block.getBlock().getMetaFromState(block) == 0)) {
            world.setBlockToAir(pos.getBlockPos());
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
