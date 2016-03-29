package com.ermans.bottledanimals.item.simple;

import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.item.ItemBottledAnimals;
import com.ermans.bottledanimals.reference.Animals;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBottle extends ItemBottledAnimals {

    public ItemBottle() {
        super(Names.Items.BOTTLE);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase entity) {
        if (entity.worldObj.isRemote) {
            return false;
        }
        if (!(entity instanceof EntityAnimal) && !(entity instanceof EntitySquid)) {
            return false;
        }
        if (!EntityList.classToStringMapping.containsKey(entity.getClass())) {
            return false;
        }
        if (entity instanceof EntityAnimal && ((EntityAnimal) entity).getGrowingAge() < 0) {
            return false;
        }
        Animals entityAnimals = Animals.getAnimalsFromEntityName(EntityList.classToStringMapping.get(entity.getClass()));
        if (entityAnimals == null) {
            return false;
        }
        entity.setDead();
        if (entity.isDead) {

            if (itemStack != null && itemStack.stackSize > 0) {
                --itemStack.stackSize;
                if (itemStack.stackSize <= 0) {
                    itemStack = null;
                }

                ItemStack filledBottle = new ItemStack(ModItems.itemAnimalInABottle, 1, entityAnimals.getID());
                if (!player.inventory.addItemStackToInventory(filledBottle)) {
                    player.dropPlayerItemWithRandomChoice(filledBottle, false);
                }

                player.inventoryContainer.detectAndSendChanges();
                return true;


            }
        }


        return true;
    }
}
