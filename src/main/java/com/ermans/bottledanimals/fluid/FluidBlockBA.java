package com.ermans.bottledanimals.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockBA extends BlockFluidClassic {
//    @SideOnly(Side.CLIENT)
//    private IIcon stillIcon;
//    @SideOnly(Side.CLIENT)
//    private IIcon flowingIcon;

    public FluidBlockBA(Fluid fluid, Material material) {
        super(fluid, material);
        setUnlocalizedName(fluid.getName());
        setCreativeTab(null);
    }

    public FluidBlockBA(Fluid fluid) {
        this(fluid, Material.water);
    }

    public static FluidBlockBA create(Fluid fluid, Material material) {
        FluidBlockBA res = new FluidBlockBA(fluid, material);
        fluid.setBlock(res);
        return res;
    }

//    @Override
//    public IIcon getIcon(int side, int meta) {
//        return (side == 0) || (side == 1) ? this.stillIcon : this.flowingIcon;
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerBlockIcons(TextureUtils.IIconRegister register) {
//        this.stillIcon = register.registerIcon(Reference.MOD_ID_LOWERCASE + ":" + getFluid().getName() + "_still");
//        this.flowingIcon = register.registerIcon(Reference.MOD_ID_LOWERCASE + ":" + getFluid().getName() + "_flow");
//    }


    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock().getMaterial().isLiquid()) {
            return false;
        }
        return super.canDisplace(world, pos);
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock().getMaterial().isLiquid()) {
            return false;
        }
        return super.displaceIfPossible(world, pos);
    }

}
