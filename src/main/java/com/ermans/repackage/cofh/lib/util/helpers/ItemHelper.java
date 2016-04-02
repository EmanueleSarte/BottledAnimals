package com.ermans.repackage.cofh.lib.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Contains various helper functions to assist with {@link Item} and {@link ItemStack} manipulation and interaction.
 *
 * @author King Lemming
 */
public final class ItemHelper {

    private ItemHelper() {

    }

    public static ItemStack cloneStack(Item item, int stackSize) {

        if (item == null) {
            return null;
        }
        ItemStack stack = new ItemStack(item, stackSize);

        return stack;
    }

    public static ItemStack cloneStack(Block item, int stackSize) {

        if (item == null) {
            return null;
        }
        ItemStack stack = new ItemStack(item, stackSize);

        return stack;
    }

    public static ItemStack cloneStack(ItemStack stack, int stackSize) {

        if (stack == null) {
            return null;
        }
        ItemStack retStack = stack.copy();
        retStack.stackSize = stackSize;

        return retStack;
    }

    public static ItemStack cloneStack(ItemStack stack) {

        if (stack == null) {
            return null;
        }
        ItemStack retStack = stack.copy();

        return retStack;
    }

    public static ItemStack damageStack(ItemStack stack, int damage) {
        if (stack == null) {
            return null;
        }
        stack.setItemDamage(stack.getItemDamage() + damage);
        if (stack.getItemDamage() >= stack.getMaxDamage()) {
            stack.stackSize--;
        }
        if (stack.stackSize <= 0) {
            return null;
        }
        return stack;
    }

    public static boolean canPutOn(ItemStack thisStack, ItemStack onThisStack) {
        if (thisStack == null || onThisStack == null) {
            return true;
        }

        if (!onThisStack.isStackable() || !thisStack.isStackable()) {
            return false;
        }
        return thisStack.isItemEqual(onThisStack) && onThisStack.stackSize + thisStack.stackSize <= onThisStack.getMaxStackSize();
    }

    public static ItemStack decreaseStackSize(ItemStack itemStack, int amount) {
        if (itemStack == null || amount == 0) {
            return itemStack;
        }

        itemStack.stackSize -= amount;
        if (itemStack.stackSize <= 0) {
            itemStack = null;
            return null;
        }
        return itemStack;
    }

    public static boolean addItemStackToPlayer(EntityPlayer player, ItemStack itemStack, boolean dropIfFull) {
        if (player == null) {
            return false;
        }
        if (itemStack == null) {
            return true;
        }

        if (!player.inventory.addItemStackToInventory(itemStack)) {
            if (dropIfFull) {
                player.dropPlayerItemWithRandomChoice(itemStack, false);
            }
            return false;
        }
        return true;
    }

    public static String getUnwrappedName(ItemStack stack) {
        return stack.getUnlocalizedName().substring(stack.getUnlocalizedName().lastIndexOf(':') + 1);
    }

    public static String getResourceName(ItemStack stack, boolean replaceDot) {
        String unwrappedName = getUnwrappedName(stack);
        return replaceDot ? unwrappedName.replace('.', '_') : unwrappedName;
    }
}
