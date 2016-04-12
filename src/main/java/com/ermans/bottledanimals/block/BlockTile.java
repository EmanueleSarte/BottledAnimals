package com.ermans.bottledanimals.block;

import com.ermans.bottledanimals.BottledAnimals;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public abstract class BlockTile extends BlockBase implements IGuiHandler, ITileEntityProvider {

    public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool PROPERTY_ACTIVE = PropertyBool.create("active");
    protected int guiId;

    public BlockTile(String machineName) {
        super(machineName);
        setHardness(3.0F);
        setStepSound(soundTypeMetal);
        setHarvestLevel("pickaxe", 1);
        this.guiId = BottledAnimals.guiHandler.addGuiHandler(this);

    }

    //Called when a neighboring block changes.
    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileBottledAnimals) {
            ((TileBottledAnimals) te).onNeighborBlockChange(pos, state, neighborBlock);
        }
    }

    //Called when a tile entity on a side of this block changes is created or is destroyed.
    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileBottledAnimals) {
            ((TileBottledAnimals) te).onNeighborChange(pos, neighbor);
        }
    }

    private boolean openGui(World world, int x, int y, int z, EntityPlayer entityPlayer) {
        entityPlayer.openGui(BottledAnimals.INSTANCE, this.guiId, world, x, y, z);
        return true;
    }


    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileBottledAnimals) {
            TileBottledAnimals tileBA = (TileBottledAnimals) tileEntity;
            return getDefaultState().withProperty(PROPERTY_ACTIVE, tileBA.isActive()).withProperty(PROPERTY_FACING, tileBA.getFacing());
        }
        return state;
    }

    //facing bits XX00
    //is active bit: 00X0
    //unused bit: 000X
    @Override
    public IBlockState getStateFromMeta(int meta) {
//        EnumFacing facing = EnumFacing.getHorizontal((meta & 12) >> 2);
//        boolean active = ((meta & 2) >> 1) == 1;
//        return this.getDefaultState().withProperty(PROPERTY_FACING, facing).withProperty(PROPERTY_ACTIVE, active);
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
//        EnumFacing facing = state.getValue(PROPERTY_FACING);
//        boolean active = state.getValue(PROPERTY_ACTIVE);
//
//        int facingbits = facing.getHorizontalIndex() << 2;
//        int activebit = (active ? 1 : 0) << 1;
//        return facingbits | activebit;
        return 0;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, PROPERTY_FACING, PROPERTY_ACTIVE);
    }


    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing blockFaceClickedOn, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        EnumFacing enumfacing = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw).getOpposite();
        return this.getDefaultState().withProperty(PROPERTY_FACING, enumfacing).withProperty(PROPERTY_ACTIVE, false);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        TileBottledAnimals te = (TileBottledAnimals) world.getTileEntity(pos);
        if (te != null) {
            te.setFacing(EnumFacing.fromAngle(placer.rotationYaw).getOpposite());
            te.setTileName(getBlockName());
        }
        world.markBlockForUpdate(pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && player != null) {
            ItemStack equipped = player.getCurrentEquippedItem();
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity != null) {
                if (tileEntity instanceof TileBase) {
                    if (((TileBase) tileEntity).handleRightClick(player, equipped, hitX, hitY, hitZ)) {
                        return true;
                    }
                    if (player.isSneaking()) {
                        return false;
                    }
                    if (((TileBase) tileEntity).canPlayerAccess(player)) {
                        openGui(world, pos.getX(), pos.getY(), pos.getZ(), player);
                        return true;
                    }
                    return false;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        IInventory inventory = (worldIn.getTileEntity(pos) instanceof IInventory) ? (IInventory) worldIn.getTileEntity(pos) : null;
        if (inventory != null) {
            InventoryHelper.dropInventoryItems(worldIn, pos, inventory);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public int getLightValue(IBlockAccess world, BlockPos pos) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity != null && entity instanceof TileBottledAnimals) {
            return ((TileBottledAnimals) entity).isActive() ? 12 : 0;
        }
        return 0;
    }


    @Override
    public boolean onBlockEventReceived(World world, BlockPos pos, IBlockState state, int eventID, int eventParam) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null) {
            return tile.receiveClientEvent(eventID, eventParam);
        }
        return super.onBlockEventReceived(world, pos, state, eventID, eventParam);
    }


}

