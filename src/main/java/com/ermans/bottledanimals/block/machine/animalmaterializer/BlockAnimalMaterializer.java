package com.ermans.bottledanimals.block.machine.animalmaterializer;

import com.ermans.bottledanimals.block.machine.BlockMachine;
import com.ermans.bottledanimals.block.machine.animaldigitizer.ContainerAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.animaldigitizer.GuiAnimalDigitizer;
import com.ermans.bottledanimals.block.machine.animaldigitizer.TileAnimalDigitizer;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class BlockAnimalMaterializer extends BlockMachine {

    public BlockAnimalMaterializer() {
        super(Names.Machines.ANIMAL_MATERIALIZER);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if ((te instanceof TileAnimalMaterializer)) {
            return new ContainerAnimalMaterializer(player.inventory, (TileAnimalMaterializer) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        return new GuiAnimalMaterializer(player.inventory, (TileAnimalMaterializer) te);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAnimalMaterializer();
    }
}
