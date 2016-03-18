package com.ermans.bottledanimals.item.simple;

import com.ermans.bottledanimals.item.ItemBottledAnimals;
import com.ermans.bottledanimals.reference.Names;


public class ItemRancherGear extends ItemBottledAnimals {


    public ItemRancherGear() {
        super(Names.Items.RANCHER_GEAR);
        setMaxDamage(64);
        setMaxStackSize(1);
    }

}
