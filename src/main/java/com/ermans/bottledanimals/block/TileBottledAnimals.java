package com.ermans.bottledanimals.block;


import cofh.api.tileentity.IRedstoneControl;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageRedstoneButton;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileBottledAnimals extends TileBase implements IRedstoneControl {

    //field for the tileName
    protected String tileName;
    //Field storing the facing of this tileentity
    protected byte facing;

    //Here we are setting a default RSControlMode
    protected ControlMode rsControlMode = ControlMode.DISABLED;
    //Is RS powered?
    protected boolean isPowered;



    public TileBottledAnimals setTileName(String tileName) {
        this.tileName = tileName;
        return this;
    }

    public String getTileName() {
        return this.tileName;
    }

    public byte getFacing() {
        return this.facing;
    }

    public void setFacing(byte facing) {
        this.facing = facing;
    }

    //@formatter:off
    public void setFacing(float playerRotationYaw){
        switch (MathHelper.floor_double(playerRotationYaw * 4.0F / 360.0F + 0.5D) & 0x3){
            case 0: this.facing = (byte) ForgeDirection.NORTH.ordinal(); break;
            case 1: this.facing = (byte) ForgeDirection.EAST.ordinal(); break;
            case 2: this.facing = (byte) ForgeDirection.SOUTH.ordinal(); break;
            case 3: this.facing = (byte) ForgeDirection.WEST.ordinal(); break;
        }
    }
    //@formatter:on


    public boolean hasPassedRedstoneTest() {
        return (this.rsControlMode.isDisabled()) || (this.isPowered == this.rsControlMode.getState());
    }

    public void onNeighborChange() {
        boolean wasPowered = this.isPowered;
        this.isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
        if (wasPowered != this.isPowered && !rsControlMode.isDisabled()) {
            // TODO: 22/01/2016 SYNC CLIENT?
        }
    }

    //RSControl Interface//
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
        return isPowered;
    }

    @Override
    public void setPowered(boolean isPowered) {
        this.isPowered = isPowered;
    }


    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.facing = nbtTagCompound.getByte("facing");
        this.tileName = nbtTagCompound.getString("tileName");
        this.rsControlMode = ControlMode.values()[nbtTagCompound.getByte("rsControlMode")];
        this.isPowered = nbtTagCompound.getBoolean("isPowered");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("facing", this.facing);
        nbtTagCompound.setString("tileName", this.tileName);
        nbtTagCompound.setByte("rsControlMode", (byte) this.rsControlMode.ordinal());
        nbtTagCompound.setBoolean("isPowered", this.isPowered);
    }

    public void fromBytes(ByteBuf buf) {
    }

    public void toBytes(ByteBuf buf) {
    }

}
