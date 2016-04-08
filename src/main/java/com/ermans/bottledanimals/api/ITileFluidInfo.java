package com.ermans.bottledanimals.api;


import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITileFluidInfo {

    @SideOnly(Side.CLIENT)
    IFluidTank getFluidTank(EnumFacing facing);
}
