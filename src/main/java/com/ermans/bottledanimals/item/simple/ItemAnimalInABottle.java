package com.ermans.bottledanimals.item.simple;

import com.ermans.bottledanimals.item.ItemBottledAnimals;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAnimalInABottle extends ItemBottledAnimals {
    private static final String[] BOTTLED_ANIMAL_NAME = {"pig", "sheep", "cow", "chicken", "squid", "wolf", "moosh", "ocelot", "horse", "rabbit"};
//    private static final String[] BOTTLED_ANIMAL_FILE_NAME = {"animalBottle_pig", "animalBottle_sheep", "animalBottle_cow", "animalBottle_chicken", "animalBottle_squid", "animalBottle_wolf", "animalBottle_moosh", "animalBottle_ocelot", "animalBottle_horse"};


    public ItemAnimalInABottle() {
        super(Names.Items.ANIMAL_IN_A_BOTTLE);
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(16);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, BOTTLED_ANIMAL_NAME.length);
        return getUnlocalizedName() + "." + BOTTLED_ANIMAL_NAME[i];
    }


    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        for (int i = 0; i < BOTTLED_ANIMAL_NAME.length; ++i) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }


}
