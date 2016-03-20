package com.ermans.bottledanimals.block.generator.basicgenerator;


import com.ermans.bottledanimals.block.BlockMachine;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBasicGenerator extends BlockMachine{

    public BlockBasicGenerator() {
        super(Names.Machines.BASIC_GENERATOR);
    }


    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return null;
    }
}
