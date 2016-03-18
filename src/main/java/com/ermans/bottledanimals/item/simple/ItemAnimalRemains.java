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


public class ItemAnimalRemains extends ItemBottledAnimals {
    private static final String[] ANIMAL_REMAINS_NAME = {"pig", "sheep", "cow", "chicken", "squid", "wolf", "moosh", "ocelot", "horse"};
    private static final String[] ANIMAL_REMAINS_FILE_NAME = {"remainsPig", "remainsSheep", "remainsCow", "remainsChicken", "remainsSquid", "remainsWolf", "remainsMoosh", "remainsOcelot", "remainsHorse"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;


    public ItemAnimalRemains() {
        super(Names.Items.ANIMAL_REMAINS);
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
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, ANIMAL_REMAINS_NAME.length);
        return super.getUnlocalizedName() + "." + ANIMAL_REMAINS_NAME[i];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[ANIMAL_REMAINS_FILE_NAME.length];

        for (int i = 0; i < ANIMAL_REMAINS_FILE_NAME.length; i++) {
            this.icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + ANIMAL_REMAINS_FILE_NAME[i]);
        }
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int renderPass) {
        if ((itemStack.getItemDamage() >= 0) && (itemStack.getItemDamage() < ANIMAL_REMAINS_NAME.length)) {
            return this.icons[itemStack.getItemDamage()];
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        for (int meta = 0; meta < ANIMAL_REMAINS_NAME.length; meta++) {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    //Debug purpouse
//    @Override
//    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer player) {
//        player.setHealth(1.0F);
//        player.getFoodStats().setFoodLevel(1);
//        return p_77659_1_;
//    }
}


