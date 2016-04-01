package com.ermans.bottledanimals.fluid;

import com.ermans.bottledanimals.block.BlockBase;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockBA extends BlockFluidClassic {

    public FluidBlockBA(Fluid fluid, Material material) {
        super(fluid, material);
        setUnlocalizedName(BlockBase.getUnlocalizedNameFromBlock(fluid.getName()));
        setRegistryName(Reference.MOD_ID, fluid.getName());
        setCreativeTab(null);
    }

    public FluidBlockBA(Fluid fluid) {
        this(fluid, Material.water);
    }

    public static FluidBlockBA create(Fluid fluid) {
        FluidBlockBA res = new FluidBlockBA(fluid);
        fluid.setBlock(res);
        return res;
    }


}
