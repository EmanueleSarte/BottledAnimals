package com.ermans.bottledanimals.client.gui;

import com.ermans.bottledanimals.IconRegistry;
import com.ermans.bottledanimals.api.ITileEnergyInfo;
import com.ermans.bottledanimals.api.ITileFluidInfo;
import com.ermans.bottledanimals.api.ITileInfo;
import com.ermans.bottledanimals.block.TileBottledAnimals;
import com.ermans.bottledanimals.client.gui.tab.TabEnergy;
import com.ermans.bottledanimals.client.gui.tab.TabRedstone;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.ElementEnergyStored;
import com.ermans.repackage.cofh.lib.gui.element.ElementFluidTank;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import com.ermans.repackage.cofh.lib.util.helpers.StringHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBaseAdv extends GuiBase {

    protected TileBottledAnimals tileBA;

    protected TabEnergy tabEnergy;
    protected ElementEnergyStored elementEnergyStored;
    protected ElementFluidTank elementFluidTank;

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
        if (tileBA instanceof ITileInfo) {
            tabEnergy = (TabEnergy) addTab(new TabEnergy(this, (ITileInfo) tileBA, TabBase.LEFT));
        }
        if (tileBA instanceof ITileEnergyInfo) {
            elementEnergyStored = (ElementEnergyStored) addElement(new ElementEnergyStored(this, ((ITileEnergyInfo) tileBA).getEnergyStorage(null)).setAlwaysShow(true));
        }
        if (tileBA instanceof ITileFluidInfo) {
            elementFluidTank = (ElementFluidTank) addElement(new ElementFluidTank(this, ((ITileFluidInfo) tileBA).getFluidTank(null)).setAlwaysShow(true).setGauge(1));
        }

    }

    @Override
    public TextureAtlasSprite getIcon(String iconName) {
        return IconRegistry.getIcon(iconName);
    }

}
