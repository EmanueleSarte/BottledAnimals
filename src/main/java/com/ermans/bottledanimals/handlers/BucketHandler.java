package com.ermans.bottledanimals.handlers;

import com.ermans.bottledanimals.block.FluidBlockBA;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class BucketHandler {

    public static final BucketHandler INSTANCE = new BucketHandler();
    private final Map<Block, Item> buckets = new HashMap<Block, Item>();


    private BucketHandler() {
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result == null)
            return;

        event.result = result;
        event.setResult(Event.Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        Block block = world.getBlockState(pos.getBlockPos()).getBlock();


        Item bucket = this.buckets.get(block);
        if (bucket != null) {
            world.setBlockState(pos.getBlockPos(), Blocks.air.getDefaultState());
            return new ItemStack(bucket);
        } else
            return null;

    }

    public void addBucket(Fluid fluid, Item filledBucket, Item emptyBucket, FluidBlockBA fluidBlock){
        FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(filledBucket), new ItemStack(emptyBucket));
        buckets.put(fluidBlock, filledBucket);
    }
}
