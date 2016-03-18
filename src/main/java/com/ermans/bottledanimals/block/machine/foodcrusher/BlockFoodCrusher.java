package com.ermans.bottledanimals.block.machine.foodcrusher;

import com.ermans.bottledanimals.block.machine.BlockMachine;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFoodCrusher extends BlockMachine {

    public BlockFoodCrusher() {
        super(Names.Machines.FOOD_CRUSHER);
    }

    @Override
    protected String getMachineTextureName() {
        return Names.Machines.FOOD_CRUSHER;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if ((te instanceof TileFoodCrusher)) {
            return new ContainerFoodCrusher(player.inventory, (TileFoodCrusher) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        return new GuiFoodCrusher(player.inventory, (TileFoodCrusher) te);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileFoodCrusher();
    }
}
