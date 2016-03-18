package com.ermans.bottledanimals.fluid;

import com.ermans.bottledanimals.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockBA extends BlockFluidClassic {
    @SideOnly(Side.CLIENT)
    private IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    private IIcon flowingIcon;

    public FluidBlockBA(Fluid fluid, Material material) {
        super(fluid, material);
        setBlockName(fluid.getName());
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

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0) || (side == 1) ? this.stillIcon : this.flowingIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.stillIcon = register.registerIcon(Reference.MOD_ID_LOWERCASE + ":" + getFluid().getName() + "_still");
        this.flowingIcon = register.registerIcon(Reference.MOD_ID_LOWERCASE + ":" + getFluid().getName() + "_flow");
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        }
        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        }
        return super.displaceIfPossible(world, x, y, z);
    }
}
