package com.ermans.bottledanimals.item;

import com.ermans.bottledanimals.BottledAnimalsTab;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraft.item.Item;

public abstract class ItemBottledAnimals extends Item {

    public ItemBottledAnimals(String name) {
        setUnlocalizedName(getUnlocalizedNameFromItem(name));
        setCreativeTab(BottledAnimalsTab.tabBottledAnimals);
    }

    public static String getUnlocalizedNameFromItem(String itemName) {
        return Reference.MOD_ID_LOWERCASE + ":" + itemName;
    }
}
