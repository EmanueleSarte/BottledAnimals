package com.ermans.bottledanimals.block.machine.breeder;

import com.ermans.bottledanimals.block.machine.BlockMachine;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockBreeder extends BlockMachine {

    public BlockBreeder() {
        super(Names.Machines.BREEDER);
    }


    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        if ((te instanceof TileBreeder)) {
            return new ContainerBreeder(player.inventory, (TileBreeder) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        return new GuiBreeder(player.inventory, (TileBreeder) te);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBreeder();
    }
}
