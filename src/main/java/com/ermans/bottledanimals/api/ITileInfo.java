package com.ermans.bottledanimals.api;


import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITileInfo {

    //Is a receiver or a provider?
    @SideOnly(Side.CLIENT)
    boolean isReceiver();

    //Is processing/generating?
    @SideOnly(Side.CLIENT)
    boolean isTileActive();

    //Energy receiving/sending this tick
    @SideOnly(Side.CLIENT)
    int getEnergyPerTick();

    //Max Energy input/output
    @SideOnly(Side.CLIENT)
    int getEnergyMaxPerTick();

    //Energy using/generating this tick
    @SideOnly(Side.CLIENT)
    int getActualEnergyPerTick();

    //Percentage of the process operation completed/fuel remaining
    @SideOnly(Side.CLIENT)
    int getPercentage(int scale);
}
