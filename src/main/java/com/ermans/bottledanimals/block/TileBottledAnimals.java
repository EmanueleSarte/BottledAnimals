package com.ermans.bottledanimals.block;


import cofh.api.tileentity.IRedstoneControl;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageRedstoneButton;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileBottledAnimals extends TileBase implements IRedstoneControl {

    protected String tileName;
    protected byte facing;

    protected ControlMode rsControlMode = ControlMode.DISABLED;
    protected boolean isRedstonePowered;


    public TileBottledAnimals setTileName(String tileName) {
        this.tileName = tileName;
        return this;
    }


    public void setFacing(float playerRotationYaw) {
        switch (MathHelper.floor_double(playerRotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) {
            case 0:
                this.facing = (byte) ForgeDirection.NORTH.ordinal();
                break;
            case 1:
                this.facing = (byte) ForgeDirection.EAST.ordinal();
                break;
            case 2:
                this.facing = (byte) ForgeDirection.SOUTH.ordinal();
                break;
            case 3:
                this.facing = (byte) ForgeDirection.WEST.ordinal();
                break;
        }
    }


    public boolean hasPassedRedstoneTest() {
        return (this.rsControlMode.isDisabled()) || (this.isRedstonePowered == this.rsControlMode.getState());
    }

    public void onNeighborChange() {
        boolean wasPowered = this.isRedstonePowered;
        this.isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
        if (wasPowered != this.isRedstonePowered && !rsControlMode.isDisabled()) {
            // TODO: 22/01/2016 SYNC CLIENT?
        }
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

    public void setFacing(byte facing) {
        this.facing = facing;
    }

    public String getTileName() {
        return this.tileName;
    }

    public byte getFacing() {
        return this.facing;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.facing = nbtTagCompound.getByte("facing");
        this.tileName = nbtTagCompound.getString("tileName");
        this.rsControlMode = ControlMode.values()[nbtTagCompound.getByte("rsControlMode")];
        this.isRedstonePowered = nbtTagCompound.getBoolean("isRedstonePowered");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("facing", this.facing);
        nbtTagCompound.setString("tileName", this.tileName);
        nbtTagCompound.setByte("rsControlMode", (byte) this.rsControlMode.ordinal());
        nbtTagCompound.setBoolean("isRedstonePowered", this.isRedstonePowered);
    }

    public void fromBytes(ByteBuf buf) {
    }

    public void toBytes(ByteBuf buf) {
    }

}
