package com.ermans.bottledanimals.block.machine;

import com.ermans.bottledanimals.BottledAnimals;
import com.ermans.bottledanimals.BottledAnimalsTab;
import com.ermans.bottledanimals.block.BlockBase;
import com.ermans.bottledanimals.block.TileBottledAnimals;
import com.ermans.bottledanimals.reference.Reference;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockMachine extends BlockBase implements IGuiHandler, ITileEntityProvider {


    @SideOnly(Side.CLIENT)
    private static final String MACHINE_SIDE = Reference.MOD_ID_LOWERCASE + ":" + "machineSide";
    private static final String MACHINE_TOP = Reference.MOD_ID_LOWERCASE + ":" + "machineTop";
    private static final String MACHINE_DOWN = Reference.MOD_ID_LOWERCASE + ":" + "machineDown";

    private static final IIcon[][] iconBuffer = new IIcon[2][6];

    private IIcon[] iconMachine;

    private int guiId;


    public BlockMachine(String machineName) {
        super(machineName);
        setHardness(3.0F);
        setStepSound(soundTypeMetal);
        setHarvestLevel("pickaxe", 1);
        setBlockName(machineName);
        setCreativeTab(BottledAnimalsTab.tabBottledAnimals);
        this.guiId = BottledAnimals.guiHandler.addGuiHandler(this);

    }


    protected abstract String getMachineTextureName();

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileBottledAnimals) {
            ((TileBottledAnimals) te).onNeighborChange();
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block tileZ) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileBottledAnimals) {
            ((TileBottledAnimals) te).onNeighborChange();
        }
    }

    private boolean openGui(World world, int x, int y, int z, EntityPlayer entityPlayer) {
        entityPlayer.openGui(BottledAnimals.instance, this.guiId, world, x, y, z);
        return true;
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (entityPlayer.isSneaking()) {
            return false;
        }
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity != null) {
                openGui(world, x, y, z, entityPlayer);
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block block, int par6) {
        IInventory inventory = (worldIn.getTileEntity(x, y, z) instanceof IInventory) ? (IInventory) worldIn.getTileEntity(x, y, z) : null;
        if (inventory != null) {
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                if (inventory.getStackInSlot(i) != null) {
                    EntityItem item = new EntityItem(worldIn, x + 0.5D, y + 0.5D, z + 0.5D, inventory.getStackInSlot(i));
                    float multiplier = 0.1F;
                    float motionX = worldIn.rand.nextFloat() - 0.5F;
                    float motionY = worldIn.rand.nextFloat() - 0.5F;
                    float motionZ = worldIn.rand.nextFloat() - 0.5F;

                    item.motionX = (motionX * multiplier);
                    item.motionY = (motionY * multiplier);
                    item.motionZ = (motionZ * multiplier);

                    worldIn.spawnEntityInWorld(item);
                }
            }
        }

        TileEntity tile = worldIn.getTileEntity(x, y, z);
        if (tile != null) {
            worldIn.removeTileEntity(x, y, z);
        }
    }



    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);

        TileBottledAnimals te = (TileBottledAnimals) world.getTileEntity(x, y, z);
        if (te != null) {
            te.setFacing(player.rotationYaw);
            te.setTileName(getBlockName());
        }
        world.markBlockForUpdate(x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iIconRegister) {

        this.blockIcon = iIconRegister.registerIcon(MACHINE_SIDE);
        //we are registering the frond-side icons for every machine
        this.iconMachine = new IIcon[2];
        this.iconMachine[0] = iIconRegister.registerIcon(Reference.MOD_ID_LOWERCASE + ":" + getMachineTextureName());
        this.iconMachine[1] = iIconRegister.registerIcon(Reference.MOD_ID_LOWERCASE + ":" + getMachineTextureName() + "On");

        //we are registering the side icons (except the front icon, which is different) only once
        if (iconBuffer[0][0] == null) {
            iconBuffer[0][0] = iIconRegister.registerIcon(MACHINE_DOWN);
            iconBuffer[0][1] = iIconRegister.registerIcon(MACHINE_TOP);
            iconBuffer[0][2] = iIconRegister.registerIcon(MACHINE_SIDE);
            iconBuffer[0][4] = iIconRegister.registerIcon(MACHINE_SIDE);
            iconBuffer[0][5] = iIconRegister.registerIcon(MACHINE_SIDE);

            iconBuffer[1][0] = iIconRegister.registerIcon(MACHINE_DOWN);
            iconBuffer[1][1] = iIconRegister.registerIcon(MACHINE_TOP);
            iconBuffer[1][2] = iIconRegister.registerIcon(MACHINE_SIDE);
            iconBuffer[1][4] = iIconRegister.registerIcon(MACHINE_SIDE);
            iconBuffer[1][5] = iIconRegister.registerIcon(MACHINE_SIDE);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 3) return this.iconMachine[1];
        return iconBuffer[0][side];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileMachine) {
            TileMachine tileMachine = (TileMachine) te;
            int iconIndex = sideAndFacingToSpriteOffset[side][tileMachine.getFacing()];
            if (iconIndex == 3) {
                return iconMachine[tileMachine.isActive ? 1 : 0];
            }
            return iconBuffer[tileMachine.isActive ? 1 : 0][iconIndex];
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    private static final int[][] sideAndFacingToSpriteOffset = {
            {3, 2, 0, 0, 0, 0},
            {2, 3, 1, 1, 1, 1},
            {1, 1, 3, 2, 5, 4},
            {0, 0, 2, 3, 4, 5},
            {4, 5, 4, 5, 3, 2},
            {5, 4, 5, 4, 2, 3}
    };

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Reference.MOD_ID_LOWERCASE + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    private String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(":") + 1);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileMachine) {
            if (((TileMachine) tile).isActive) {
                return 14;
            }else{
                return 0;
            }
        }
        return 0;
    }
}
