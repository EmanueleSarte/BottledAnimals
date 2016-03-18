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

public class ItemDigitalizedAnimal extends ItemBottledAnimals {
    private static final String[] DIGITIALIZED_ANIMAL_NAME = {"pig", "sheep", "cow", "chicken", "squid", "wolf", "moosh", "ocelot", "horse"};
    private static final String[] DIGITIALIZED_ANIMAL_FILE_NAME = {"digitalizedPig", "digitalizedSheep", "digitalizedCow", "digitalizedChicken", "digitalizedSquid", "digitalizedWolf", "digitalizedMoosh", "digitalizedOcelot", "digitalizedHorse"};
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemDigitalizedAnimal() {
        super(Names.Items.DIGITALIZED_ANIMAL);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, DIGITIALIZED_ANIMAL_NAME.length);
        return super.getUnlocalizedName() + "." + DIGITIALIZED_ANIMAL_NAME[i];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[DIGITIALIZED_ANIMAL_FILE_NAME.length];
        for (int i = 0; i < DIGITIALIZED_ANIMAL_FILE_NAME.length; i++) {
            this.icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + DIGITIALIZED_ANIMAL_FILE_NAME[i]);
        }
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int renderPass) {
        if ((itemStack.getItemDamage() >= 0) && (itemStack.getItemDamage() < DIGITIALIZED_ANIMAL_NAME.length)) {
            return this.icons[itemStack.getItemDamage()];
        }
        return null;
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
