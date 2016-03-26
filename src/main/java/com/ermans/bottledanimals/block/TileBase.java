package com.ermans.bottledanimals.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileBase extends TileEntity {


    public TileBase() {
        initTile();
    }

    public void initTile() {
    }

    //Who call this want a packet (for world-saving) that describe this TE, so we call writeToNbt to get all the information
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbtTagCompound);
    }

    //Who call this want to restore this TE giving us the data to do that, so we call readFromNBT to restore all information
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }


    //We are checking if a player can access at this TE (could be useful for a security system, now it just returns true)
    //But keep in mind to use this.
    public boolean canPlayerAccess(EntityPlayer player) {
        return true;
    }


    protected boolean checkTick(int tick){
        return worldObj.getTotalWorldTime() % tick == 0;
    }



    public boolean handleRightClick(EntityPlayer player, ItemStack itemStack, float xClicked, float yClicked, float zClicked){
        return false;
    }
}
