package com.ermans.bottledanimals.block.generator.basicgenerator;


import com.ermans.bottledanimals.block.generator.BlockGenerator;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBasicGenerator extends BlockGenerator {



    public BlockBasicGenerator() {
        super(Names.Machines.BASIC_GENERATOR);
    }

    @Override
    public Object getServerGuiElement(int i, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileBasicGenerator) {
            return new ContainerBasicGenerator(player.inventory, (TileBasicGenerator) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int i,  EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        return new GuiBasicGenerator(player.inventory, (TileBasicGenerator) te);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileBasicGenerator();
    }


}
