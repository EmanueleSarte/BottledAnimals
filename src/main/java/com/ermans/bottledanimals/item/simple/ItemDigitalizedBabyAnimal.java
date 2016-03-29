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

public class ItemDigitalizedBabyAnimal extends ItemBottledAnimals {
    private static final String[] DIGITIALIZED_BABY_ANIMAL_NAME = {"babyPig", "babySheep", "babyCow", "babyChicken", "babySquid", "babyWolf", "babyMoosh", "babyOcelot", "babyHorse"};
    private static final String[] DIGITIALIZED_BABY_ANIMAL_FILE_NAME = {"digitalizedBabyPig", "digitalizedBabySheep", "digitalizedBabyCow", "digitalizedBabyChicken", "digitalizedBabySquid", "digitalizedBabyWolf", "digitalizedBabyMoosh", "digitalizedBabyOcelot", "digitalizedBabyHorse"};

//    @SideOnly(Side.CLIENT)
//    private IIcon[] icons;

    public ItemDigitalizedBabyAnimal() {
        super(Names.Items.DIGITALIZED_BABY_ANIMAL);
        setHasSubtypes(true);
        setMaxDamage(0);
    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public boolean requiresMultipleRenderPasses() {
//        return true;
//    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, DIGITIALIZED_BABY_ANIMAL_NAME.length);
        return super.getUnlocalizedName() + "." + DIGITIALIZED_BABY_ANIMAL_NAME[i];
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(TextureUtils.IIconRegister iconRegister) {
//        this.icons = new IIcon[DIGITIALIZED_BABY_ANIMAL_FILE_NAME.length];
//        for (int i = 0; i < DIGITIALIZED_BABY_ANIMAL_FILE_NAME.length; i++) {
//            this.icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + DIGITIALIZED_BABY_ANIMAL_FILE_NAME[i]);
//        }
//    }
//
//    @Override
//    public IIcon getIcon(ItemStack itemStack, int renderPass) {
//        if ((itemStack.getItemDamage() >= 0) && (itemStack.getItemDamage() < DIGITIALIZED_BABY_ANIMAL_NAME.length)) {
//            return this.icons[itemStack.getItemDamage()];
//        }
//        return null;
//    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        for (int meta = 0; meta < DIGITIALIZED_BABY_ANIMAL_NAME.length; meta++) {
            list.add(new ItemStack(this, 1, meta));
        }
    }
}
