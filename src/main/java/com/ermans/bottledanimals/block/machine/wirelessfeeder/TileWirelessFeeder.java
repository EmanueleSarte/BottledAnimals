package com.ermans.bottledanimals.block.machine.wirelessfeeder;

import com.ermans.bottledanimals.block.machine.TileFluidTank;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageWirelessFeederButton;
import com.ermans.bottledanimals.recipe.IRecipe;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.Iterator;
import java.util.List;

public class TileWirelessFeeder extends TileFluidTank {

    private static final int TANK_CAPACITY = 10 * FluidContainerRegistry.BUCKET_VOLUME;
    private static final double MULT = 1.5;

    private static final int COST_FOR_FOOD = (int) (80 * MULT);
    private static final int COST_FOR_HEAL = (int) (40 * MULT);
    private static final int RF_FOR_FOOD = (int) (160 * MULT);
    private static final int RF_FOR_HEAL = (int) (80 * MULT);

    protected Mode mode = Mode.BOTH;

    public enum Mode {
        DISABLED, HEAL, FEED, BOTH;
    }

    @Override
    public void initTile() {
        super.initTile();
        this.storage.setCapacity(DF_ENERGY_CAPACITY);
        this.storage.setMaxReceive(DF_ENERGY_MAX_RCV);
        this.storage.setMaxExtract(DF_ENERGY_MAX_EXT);

        this.tank.setCapacity(TANK_CAPACITY);
        this.fluidTile = ModFluids.food;

        this.updateEntity = false;
        DF_VALID_SIDE = new boolean[][]{
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
        };
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.worldObj.isRemote && mode != Mode.DISABLED && hasPassedRedstoneTest()) {
            if ((checkTick(40)) && (enoughResourceToOperate())) {

                AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(5.0D, 5.0D, 5.0D);
                List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
                Iterator iterator = list.iterator();

                while ((iterator.hasNext()) && (enoughResourceToOperate())) {
                    EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                    boolean fed = false;
                    for (int i = 0; (mode != Mode.HEAL) && (i < 4) && (entityplayer.getFoodStats().getFoodLevel() < 20); i++) {
                        if ((this.tank.getFluidAmount() < COST_FOR_FOOD) || (getEnergyStored(null) < RF_FOR_FOOD)) {
                            break;
                        }
                        entityplayer.getFoodStats().addStats(1, 0.8F);
                        modifyFluidAmount(fluidTile, -1 * COST_FOR_FOOD);
                        this.modifyEnergyStored(-1 * RF_FOR_FOOD);
                        fed = true;
                    }
                    if (!fed && (mode != Mode.FEED)) {
                        boolean doHeal = mode != Mode.BOTH || entityplayer.getFoodStats().getFoodLevel() == 20;

                        for (int i = 0; doHeal && (i < 4) && (entityplayer.getHealth() < 20); i++) {
                            if ((this.tank.getFluidAmount() < COST_FOR_HEAL) || (getEnergyStored(null) < RF_FOR_HEAL)) {
                                break;
                            }
                            entityplayer.setHealth(entityplayer.getHealth() + 1);
                            modifyFluidAmount(fluidTile, -1 * COST_FOR_HEAL);
                            this.modifyEnergyStored(-1 * RF_FOR_HEAL);
                        }
                    }
                }
            }
        }
    }

    private boolean enoughResourceToOperate() {
        return !(this.tank.getFluidAmount() < COST_FOR_HEAL || getEnergyStored(null) < COST_FOR_HEAL);
    }


    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        if (this.mode != mode) {
            this.mode = mode;
            if (worldObj.isRemote) {
                PacketHandler.INSTANCE.sendToServer(new MessageWirelessFeederButton(this, mode.ordinal()));
            } else {
                markDirty();
            }
        }
    }

    @Override
    public String getFluidName() {
        return ModFluids.food.getName();
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return DF_VALID_SIDE[from.ordinal()][facing] && fluid != null;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }


    //We don't need these methods
    @Override
    protected IRecipe getRecipeIfCanStart() {
        return null;
    }

    @Override
    protected boolean canStillProcess(int recipeCode) {
        return false;
    }

    @Override
    protected void finishProcess() {
    }

    @Override
    protected int getNumInput() {
        return 0;
    }

    @Override
    protected int getNumOutput() {
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
    ////

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("mode", (byte) this.mode.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.mode = Mode.values()[nbtTagCompound.getByte("mode")];
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.mode = Mode.values()[buf.readByte()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeByte(this.mode.ordinal());
    }

}
