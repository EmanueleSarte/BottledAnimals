package com.ermans.bottledanimals.item;

import com.ermans.bottledanimals.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemModIcon extends ItemBottledAnimals {
    public ItemModIcon() {
        super("ModIcon");
        setCreativeTab(null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID_LOWERCASE + ":digitalizedCow");
    }
}
