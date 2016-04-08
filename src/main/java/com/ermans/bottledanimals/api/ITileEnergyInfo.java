package com.ermans.bottledanimals.api;


import cofh.api.energy.IEnergyStorage;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITileEnergyInfo {

    @SideOnly(Side.CLIENT)
    IEnergyStorage getEnergyStorage(EnumFacing facing);
}
