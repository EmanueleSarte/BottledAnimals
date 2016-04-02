package com.ermans.bottledanimals.item;

import com.ermans.bottledanimals.BottledAnimalsTab;
import com.ermans.bottledanimals.block.FluidBlockBA;
import com.ermans.bottledanimals.handlers.BucketHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemBucketBA extends ItemBucket {


    private ItemBucketBA(FluidBlockBA fluidBlock, String bucketName) {
        super(fluidBlock);
        setUnlocalizedName(ItemBottledAnimals.getUnlocalizedNameFromItem(bucketName));
        setContainerItem(Items.bucket);
        setCreativeTab(BottledAnimalsTab.tabBottledAnimals);
    }

    public static ItemBucketBA create(FluidBlockBA fluidBlock, String bucketName) {
        ItemBucketBA bucketBA = new ItemBucketBA(fluidBlock, bucketName);
        BucketHandler.INSTANCE.addBucket(fluidBlock.getFluid(), bucketBA, Items.bucket, fluidBlock);
        return bucketBA;
    }

}
