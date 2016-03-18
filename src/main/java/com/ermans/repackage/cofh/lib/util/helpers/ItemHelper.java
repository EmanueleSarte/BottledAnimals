package com.ermans.repackage.cofh.lib.util.helpers;

import net.minecraft.block.Block;
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

    public static ItemStack damageStack(ItemStack stack, int damage){
        if (stack == null){
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
}
