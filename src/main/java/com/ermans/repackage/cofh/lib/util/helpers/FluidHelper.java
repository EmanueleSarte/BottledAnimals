package com.ermans.repackage.cofh.lib.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Contains various helper functions to assist with {@link Fluid} and Fluid-related manipulation and interaction.
 *
 * @author King Lemming
 */
public class FluidHelper {

    public static final int BUCKET_VOLUME = FluidContainerRegistry.BUCKET_VOLUME;

    public static final Fluid WATER_FLUID = FluidRegistry.WATER;
    public static final Fluid LAVA_FLUID = FluidRegistry.LAVA;

    public static final FluidStack WATER = new FluidStack(WATER_FLUID, BUCKET_VOLUME);
    public static final FluidStack LAVA = new FluidStack(LAVA_FLUID, BUCKET_VOLUME);

    public static final FluidTankInfo[] NULL_TANK_INFO = new FluidTankInfo[]{};

    private FluidHelper() {

    }

    /* IFluidContainer Interaction */
    public static int fillFluidContainerItem(ItemStack container, FluidStack resource, boolean doFill) {
        return isFluidContainerItem(container) && container.stackSize == 1 ? ((IFluidContainerItem) container.getItem()).fill(container, resource, doFill) : 0;
    }

    public static FluidStack drainFluidContainerItem(ItemStack container, int maxDrain, boolean doDrain) {
        return isFluidContainerItem(container) && container.stackSize == 1 ? ((IFluidContainerItem) container.getItem()).drain(container, maxDrain, doDrain) : null;
    }

    public static FluidStack extractFluidFromHeldContainer(EntityPlayer player, int maxDrain, boolean doDrain) {
        ItemStack container = player.getCurrentEquippedItem();
        return isFluidContainerItem(container) && container.stackSize == 1 ? ((IFluidContainerItem) container.getItem()).drain(container, maxDrain, doDrain) : null;
    }

    public static int insertFluidIntoHeldContainer(EntityPlayer player, FluidStack resource, boolean doFill) {
        ItemStack container = player.getCurrentEquippedItem();
        return isFluidContainerItem(container) && container.stackSize == 1 ? ((IFluidContainerItem) container.getItem()).fill(container, resource, doFill) : 0;
    }

    public static boolean isPlayerHoldingFluidContainerItem(EntityPlayer player) {
        return isFluidContainerItem(player.getCurrentEquippedItem());
    }

    public static boolean isFluidContainerItem(ItemStack container) {
        return container != null && container.getItem() instanceof IFluidContainerItem;
    }

    public static FluidStack getFluidStackFromContainerItem(ItemStack container) {
        return ((IFluidContainerItem) container.getItem()).getFluid(container);
    }

    public static boolean hasFluidTag(ItemStack itemStack) {
        return !(itemStack == null || !itemStack.hasTagCompound()) && itemStack.getTagCompound().hasKey("Fluid") && itemStack.getTagCompound().getString("Fluid") != null;
    }

    public static FluidStack getFluidStackFromFluidTag(ItemStack itemStack){
        Fluid fluid = FluidRegistry.getFluid(itemStack.getTagCompound().getString("Fluid"));
        if (fluid == null){
            return null;
        }
        return new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
    }

    public static boolean isFluidTagEquals(ItemStack itemStack, Fluid fluid){
        return itemStack.getTagCompound().getString("Fluid").equals(fluid.getName());
    }

    public static boolean canGetFluidStack(ItemStack itemStack){
        return hasFluidTag(itemStack) || isFluidContainerItem(itemStack);
    }

    public static ItemStack setDefaultFluidTag(ItemStack container, FluidStack resource) {

        container.setTagCompound(new NBTTagCompound());
        NBTTagCompound fluidTag = resource.writeToNBT(new NBTTagCompound());
        container.stackTagCompound.setTag("Fluid", fluidTag);

        return container;
    }


    public static FluidContainerRegistry.FluidContainerData getFluidContainerData(Fluid fluid, ItemStack container){
        if (fluid == null || container == null){
            return null;
        }
        for (FluidContainerRegistry.FluidContainerData fluidContainerData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if (fluidContainerData.fluid.getFluid() == fluid && fluidContainerData.emptyContainer.isItemEqual(container)){
               return fluidContainerData;
            }
        }
        return null;
    }

    public static boolean isEmptyContainer(ItemStack container){
        if (container == null){
            return false;
        }
        for (FluidContainerRegistry.FluidContainerData fluidContainerData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ( fluidContainerData.emptyContainer.isItemEqual(container)){
                return true;
            }
        }
        return false;
    }

    public static boolean isFilledContainer(ItemStack container){
        if (container == null){
            return false;
        }
        for (FluidContainerRegistry.FluidContainerData fluidContainerData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ( fluidContainerData.filledContainer.isItemEqual(container)){
                return true;
            }
        }
        return false;
    }



    /* PACKETS */
    public static void writeFluidStackToPacket(FluidStack fluid, DataOutput data) throws IOException {

        if (!isValidFluidStack(fluid)) {
            data.writeShort(-1);
        } else {
            byte[] abyte = CompressedStreamTools.compress(fluid.writeToNBT(new NBTTagCompound()));
            data.writeShort((short) abyte.length);
            data.write(abyte);
        }
    }

    public static FluidStack readFluidStackFromPacket(DataInput data) throws IOException {

        short length = data.readShort();

        if (length < 0) {
            return null;
        } else {
            byte[] abyte = new byte[length];
            data.readFully(abyte);
            return FluidStack.loadFluidStackFromNBT(CompressedStreamTools.func_152457_a(abyte, new NBTSizeTracker(2097152L)));
        }
    }

    /* HELPERS */
    public static boolean isValidFluidStack(FluidStack fluid) {

        return fluid == null ? false : FluidRegistry.getFluidName(fluid) != null;
    }

    public static int getFluidLuminosity(FluidStack fluid) {

        return fluid == null ? 0 : getFluidLuminosity(fluid.getFluid());
    }

    public static int getFluidLuminosity(Fluid fluid) {

        return fluid == null ? 0 : fluid.getLuminosity();
    }

    public static FluidStack getFluidFromWorld(World world, int x, int y, int z, boolean doDrain) {

        Block bId = world.getBlock(x, y, z);
        int bMeta = world.getBlockMetadata(x, y, z);

        if (Block.isEqualTo(bId, Blocks.water)) {
            if (bMeta == 0) {
                return WATER.copy();
            } else {
                return null;
            }
        } else if (Block.isEqualTo(bId, Blocks.lava)) {
            if (bMeta == 0) {
                return LAVA.copy();
            } else {
                return null;
            }
        } else if (bId instanceof IFluidBlock) {
            IFluidBlock block = (IFluidBlock) bId;
            return block.drain(world, x, y, z, doDrain);
        }
        return null;
    }

    public static FluidStack getFluidFromWorld(World world, int x, int y, int z) {

        return getFluidFromWorld(world, x, y, z, false);
    }

    public static Fluid lookupFluidForBlock(Block block) {

        if (block == Blocks.flowing_water) {
            return WATER_FLUID;
        }
        if (block == Blocks.flowing_lava) {
            return LAVA_FLUID;
        }
        return FluidRegistry.lookupFluidForBlock(block);
    }

    public static FluidStack getFluidForFilledItem(ItemStack container) {

        if (container != null && container.getItem() instanceof IFluidContainerItem) {
            return ((IFluidContainerItem) container.getItem()).getFluid(container);
        }
        return FluidContainerRegistry.getFluidForFilledItem(container);
    }

    public static boolean isFluidEqualOrNull(FluidStack resourceA, FluidStack resourceB) {

        return resourceA == null || resourceB == null || resourceA.isFluidEqual(resourceB);
    }

    public static boolean isFluidEqualOrNull(Fluid fluidA, FluidStack resourceB) {

        return fluidA == null || resourceB == null || fluidA == resourceB.getFluid();
    }

    public static boolean isFluidEqualOrNull(Fluid fluidA, Fluid fluidB) {

        return fluidA == null || fluidB == null || fluidA == fluidB;
    }

    public static boolean isFluidEqual(FluidStack resourceA, FluidStack resourceB) {

        return resourceA != null && resourceA.isFluidEqual(resourceB);
    }

    public static boolean isFluidEqual(Fluid fluidA, FluidStack resourceB) {

        return fluidA != null && resourceB != null && fluidA == resourceB.getFluid();
    }

    public static boolean isFluidEqual(Fluid fluidA, Fluid fluidB) {

        return fluidA != null && fluidB != null && fluidA.equals(fluidB);
    }

}