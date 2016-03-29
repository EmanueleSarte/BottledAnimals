package com.ermans.bottledanimals.client.gui;

import com.ermans.bottledanimals.block.TileBottledAnimals;
import com.ermans.bottledanimals.block.generator.TileGenerator;
import com.ermans.bottledanimals.block.machine.TileMachine;
import com.ermans.bottledanimals.client.gui.tab.TabEnergyProvider;
import com.ermans.bottledanimals.client.gui.tab.TabEnergyReceiver;
import com.ermans.bottledanimals.client.gui.tab.TabRedstone;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import com.ermans.repackage.cofh.lib.util.helpers.StringHelper;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBaseAdv extends GuiBase {

    protected TileBottledAnimals tileBA;

    public GuiBaseAdv(Container container, ResourceLocation texture, TileBottledAnimals entity) {
        super(container, texture);
        this.tileBA = entity;
        if (entity.getTileName() != null) {
            this.name = StringHelper.getFancyNameFromCamel(entity.getTileName());
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabRedstone(this, tileBA, TabBase.RIGHT));
        if (tileBA instanceof TileMachine) {
            addTab(new TabEnergyReceiver(this, (TileMachine) tileBA, TabBase.LEFT));
        }else if (tileBA instanceof TileGenerator){
            addTab(new TabEnergyProvider(this, (TileGenerator) tileBA, TabBase.LEFT));
        }
    }

//    @Override
//    public IIcon getIcon(String iconName) {
//        return IconRegistry.getIcon(iconName);
//    }


}
