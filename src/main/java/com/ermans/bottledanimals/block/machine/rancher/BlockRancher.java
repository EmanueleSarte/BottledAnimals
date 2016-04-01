package com.ermans.bottledanimals.block.machine.rancher;

import com.ermans.bottledanimals.block.BlockTile;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class BlockRancher extends BlockTile {


    public BlockRancher() {
        super(Names.Machines.RANCHER);
    }


    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if ((te instanceof TileRancher)) {
            return new ContainerRancher(player.inventory, (TileRancher) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        return new GuiRancher(player.inventory, (TileRancher) te);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileRancher();
    }
}
