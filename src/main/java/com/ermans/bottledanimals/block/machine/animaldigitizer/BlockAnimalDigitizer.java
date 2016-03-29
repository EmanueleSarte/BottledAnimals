package com.ermans.bottledanimals.block.machine.animaldigitizer;

import com.ermans.bottledanimals.block.machine.BlockMachine;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockAnimalDigitizer extends BlockMachine {

    public BlockAnimalDigitizer() {
        super(Names.Machines.ANIMAL_DIGITIZER);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        if ((te instanceof TileAnimalDigitizer)) {
            return new ContainerAnimalDigitizer(player.inventory, (TileAnimalDigitizer) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        return new GuiAnimalDigitizer(player.inventory, (TileAnimalDigitizer) te);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAnimalDigitizer();
    }
}
