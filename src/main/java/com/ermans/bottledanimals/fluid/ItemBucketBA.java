package com.ermans.bottledanimals.fluid;

import com.ermans.bottledanimals.BottledAnimalsTab;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

public class ItemBucketBA extends ItemBucket {


    private ItemBucketBA(FluidBlockBA fluidBlock, String bucketName) {
        super(fluidBlock);
        setUnlocalizedName(bucketName);
        setContainerItem(Items.bucket);
        setCreativeTab(BottledAnimalsTab.tabBottledAnimals);
    }

    public static ItemBucketBA create(FluidBlockBA fluidBlock, String bucketName) {
        return new ItemBucketBA(fluidBlock, bucketName);
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister iconRegister) {
//        this.itemIcon = iconRegister.registerIcon(getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
//    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s", Reference.MOD_ID_LOWERCASE + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Reference.MOD_ID_LOWERCASE + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    private String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
