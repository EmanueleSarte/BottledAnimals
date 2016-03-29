package com.ermans.bottledanimals.block;


import cofh.api.tileentity.IRedstoneControl;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageRedstoneButton;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileBottledAnimals extends TileBase implements IRedstoneControl {

    protected String tileName;
    protected EnumFacing facing;

    protected ControlMode rsControlMode = ControlMode.DISABLED;
    protected boolean isRedstonePowered;


    public TileBottledAnimals setTileName(String tileName) {
        this.tileName = tileName;
        return this;
    }


    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }


    public boolean hasPassedRedstoneTest() {
        return (this.rsControlMode.isDisabled()) || (this.isRedstonePowered == this.rsControlMode.getState());
    }

    public void onNeighborChange() {
//        boolean wasPowered = this.isRedstonePowered;
        this.isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(getPos()) > 0;
//        if (wasPowered != this.isRedstonePowered && !rsControlMode.isDisabled()) {
//            // TODO: 22/01/2016 SYNC CLIENT?
//        }
    }

    //RSControl Interface
    //Here we edit the RSControlMode, probably when a user click on the RSTab Button
    @Override
    public void setControl(ControlMode control) {
        if (control != rsControlMode) {
            this.rsControlMode = control;
            if (worldObj.isRemote) {
                PacketHandler.INSTANCE.sendToServer(new MessageRedstoneButton(this));
            } else {
                markDirty();
            }
        }

    }


    @Override
    public ControlMode getControl() {
        return this.rsControlMode;
    }

    @Override
    public boolean isPowered() {
        return isRedstonePowered;
    }

    @Override
    public void setPowered(boolean isPowered) {
        this.isRedstonePowered = isPowered;
    }

    public String getTileName() {
        return this.tileName;
    }


    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.facing = EnumFacing.values()[nbtTagCompound.getByte("facing")];
        this.tileName = nbtTagCompound.getString("tileName");
        this.rsControlMode = ControlMode.values()[nbtTagCompound.getByte("rsControlMode")];
        this.isRedstonePowered = nbtTagCompound.getBoolean("isRedstonePowered");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("facing", (byte) this.facing.ordinal());
        nbtTagCompound.setString("tileName", this.tileName);
        nbtTagCompound.setByte("rsControlMode", (byte) this.rsControlMode.ordinal());
        nbtTagCompound.setBoolean("isRedstonePowered", this.isRedstonePowered);
    }

    public void fromBytes(ByteBuf buf) {
    }

    public void toBytes(ByteBuf buf) {
    }

}
