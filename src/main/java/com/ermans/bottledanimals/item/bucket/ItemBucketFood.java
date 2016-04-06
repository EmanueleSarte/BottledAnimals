package com.ermans.bottledanimals.item.bucket;


import com.ermans.bottledanimals.block.machine.wirelessfeeder.TileWirelessFeeder;
import com.ermans.bottledanimals.init.ModBlocks;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;


public class ItemBucketFood extends ItemBucketBA {

    public ItemBucketFood() {
        super(ModBlocks.foodBlock, Names.Items.FOOD_BUCKET);
    }


    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (!playerIn.capabilities.isCreativeMode) {
            --stack.stackSize;
        }

        if (!worldIn.isRemote) {
            int bucketVolume = FluidContainerRegistry.BUCKET_VOLUME;
            int food = Math.min(bucketVolume / TileWirelessFeeder.COST_FOR_FOOD, 20 - playerIn.getFoodStats().getFoodLevel());
            int heal = (bucketVolume - food * TileWirelessFeeder.COST_FOR_FOOD) / TileWirelessFeeder.COST_FOR_HEAL;
            playerIn.getFoodStats().setFoodLevel(playerIn.getFoodStats().getFoodLevel() + food);
            //We don't care if heal is bigger than 20, setHealth lower it to 20.
            playerIn.setHealth(playerIn.getHealth() + heal);
        }

        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return stack.stackSize <= 0 ? new ItemStack(Items.bucket) : stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if (playerIn.getHealth() < playerIn.getMaxHealth() || playerIn.canEat(false)) {
            playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        }

        return itemStackIn;
    }
}
