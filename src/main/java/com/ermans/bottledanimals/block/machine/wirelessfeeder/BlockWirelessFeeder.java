package com.ermans.bottledanimals.block.machine.wirelessfeeder;

import com.ermans.bottledanimals.block.machine.BlockMachine;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockWirelessFeeder extends BlockMachine {

    public BlockWirelessFeeder() {
        super(Names.Machines.WIRELESS_FEEDER);
    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerBlockIcons(TextureUtils.IIconRegister iIconRegister) {
//        this.blockIcon = iIconRegister.registerIcon(Reference.MOD_ID_LOWERCASE + ":" + blockName);
//
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public IIcon getIcon(int side, int meta) {
//        return this.blockIcon;
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
//        return this.blockIcon;
//    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        if ((te instanceof TileWirelessFeeder)) {
            return new ContainerWirelessFeeder(player.inventory, (TileWirelessFeeder) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        return new GuiWirelessFeeder(player.inventory, (TileWirelessFeeder) te);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileWirelessFeeder();
    }
}
