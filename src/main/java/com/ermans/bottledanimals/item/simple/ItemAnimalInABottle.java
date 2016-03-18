package com.ermans.bottledanimals.item.simple;

import com.ermans.bottledanimals.item.ItemBottledAnimals;
import com.ermans.bottledanimals.reference.Names;
import com.ermans.bottledanimals.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import java.util.List;

public class ItemAnimalInABottle extends ItemBottledAnimals {
    private static final String[] BOTTLED_ANIMAL_NAME = {"pig", "sheep", "cow", "chicken", "squid", "wolf", "moosh", "ocelot", "horse"};
    private static final String[] BOTTLED_ANIMAL_FILE_NAME = {"animalBottle_pig", "animalBottle_sheep", "animalBottle_cow", "animalBottle_chicken", "animalBottle_squid", "animalBottle_wolf", "animalBottle_moosh", "animalBottle_ocelot", "animalBottle_horse"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemAnimalInABottle() {
        super(Names.Items.ANIMAL_IN_A_BOTTLE);
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(16);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, BOTTLED_ANIMAL_NAME.length);
        return super.getUnlocalizedName() + "." + BOTTLED_ANIMAL_NAME[i];
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[BOTTLED_ANIMAL_FILE_NAME.length];
        for (int i = 0; i < BOTTLED_ANIMAL_FILE_NAME.length; i++) {
            this.icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + BOTTLED_ANIMAL_FILE_NAME[i]);
        }
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int renderPass) {
        if ((itemStack.getItemDamage() >= 0) && (itemStack.getItemDamage() < BOTTLED_ANIMAL_NAME.length)) {
            return this.icons[itemStack.getItemDamage()];
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        for (int meta = 0; meta < BOTTLED_ANIMAL_NAME.length; meta++) {
            list.add(new ItemStack(this, 1, meta));
        }
    }
}
