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

public class ItemDigitalizedAnimal extends ItemBottledAnimals {
    private static final String[] DIGITIALIZED_ANIMAL_NAME = {"pig", "sheep", "cow", "chicken", "squid", "wolf", "moosh", "ocelot", "horse"};

    public ItemDigitalizedAnimal() {
        super(Names.Items.DIGITALIZED_ANIMAL);
        setHasSubtypes(true);
        setMaxDamage(0);
    }


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, DIGITIALIZED_ANIMAL_NAME.length);
        return super.getUnlocalizedName() + "." + DIGITIALIZED_ANIMAL_NAME[i];
    }


    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        for (int meta = 0; meta < DIGITIALIZED_ANIMAL_NAME.length; meta++) {
            list.add(new ItemStack(this, 1, meta));
        }
    }
}
