package com.ermans.bottledanimals.item.bucket;

import com.ermans.bottledanimals.BottledAnimalsTab;
import com.ermans.bottledanimals.block.FluidBlockBA;
import com.ermans.bottledanimals.handlers.BucketHandler;
import com.ermans.bottledanimals.item.ItemBottledAnimals;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public abstract class ItemBucketBA extends ItemBucket {


    public ItemBucketBA(FluidBlockBA fluidBlock, String bucketName) {
        super(fluidBlock);
        setUnlocalizedName(ItemBottledAnimals.getUnlocalizedNameFromItem(bucketName));
        setContainerItem(Items.bucket);
        setCreativeTab(BottledAnimalsTab.tabBottledAnimals);

        BucketHandler.INSTANCE.addBucket(fluidBlock.getFluid(), this, Items.bucket, fluidBlock);
    }


}
