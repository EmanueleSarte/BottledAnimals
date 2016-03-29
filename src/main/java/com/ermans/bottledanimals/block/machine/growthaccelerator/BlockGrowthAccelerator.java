package com.ermans.bottledanimals.block.machine.growthaccelerator;

import com.ermans.bottledanimals.block.machine.BlockMachine;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockGrowthAccelerator extends BlockMachine {

    public BlockGrowthAccelerator() {
        super(Names.Machines.GROWTH_ACCELERATOR);
    }


    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        if ((te instanceof TileGrowthAccelerator)) {
            return new ContainerGrowthAccelerator(player.inventory, (TileGrowthAccelerator) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        return new GuiGrowthAccelerator(player.inventory, (TileGrowthAccelerator) te);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileGrowthAccelerator();
    }
}
