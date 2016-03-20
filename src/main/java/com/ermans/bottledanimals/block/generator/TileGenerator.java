package com.ermans.bottledanimals.block.generator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileGenerator extends TileEnergyProvider {

    protected int remaining;
    protected int totalFuel;


    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (remaining > 0) {
                int energyOut = lastOutEnergy;
                if (energyOut == 0) {
                    energyOut = Math.min(MAX_RF, remaining);
                }
                remaining -= energyOut;
                modifyEnergyStored(energyOut);

            } else {
                if (hasPassedRedstoneTest()) {
                    if (canStart()) {
                        remaining = totalFuel = startProcess();
                    }
                }
            }
        }
    }


    abstract protected boolean canStart();

    //Return the fuelValue
    abstract protected int startProcess();


    @SideOnly(Side.CLIENT)
    public int getFuelScaled(int scale) {
        if (remaining == 0) return 0;
        return remaining * scale / totalFuel;
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        remaining = nbt.getInteger("remaining");
        totalFuel = nbt.getInteger("totalFuel");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("remaining",remaining);
        nbt.setInteger("totalFuel",totalFuel);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(remaining);
        buf.writeInt(totalFuel);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        remaining = buf.readInt();
        totalFuel = buf.readInt();
    }


}
