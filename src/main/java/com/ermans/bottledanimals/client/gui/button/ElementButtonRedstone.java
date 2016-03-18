package com.ermans.bottledanimals.client.gui.button;

import cofh.api.tileentity.IRedstoneControl;
import com.ermans.bottledanimals.client.gui.tab.TabRedstone;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.ElementButton;

import java.util.List;

public class ElementButtonRedstone extends ElementButton {

    protected TabRedstone tabRedstone;
    protected IRedstoneControl.ControlMode type;

    public ElementButtonRedstone(GuiBase gui, int posX, int posY, int sizeX, int sizeY, int sheetX, int sheetY, int hoverX, int hoverY, String texture) {
        super(gui, posX, posY, sizeX, sizeY, sheetX, sheetY, hoverX, hoverY, texture);
    }

    public ElementButtonRedstone(GuiBase gui, int posX, int posY, int sizeX, int sizeY, int sheetX, int sheetY, int hoverX, int hoverY, int disabledX, int disabledY, String texture) {
        super(gui, posX, posY, sizeX, sizeY, sheetX, sheetY, hoverX, hoverY, disabledX, disabledY, texture);
    }

    public ElementButtonRedstone(GuiBase gui, int posX, int posY, String name, int sheetX, int sheetY, int hoverX, int hoverY, int sizeX, int sizeY, String texture) {
        super(gui, posX, posY, name, sheetX, sheetY, hoverX, hoverY, sizeX, sizeY, texture);
    }

    public ElementButtonRedstone(GuiBase gui, int posX, int posY, String name, int sheetX, int sheetY, int hoverX, int hoverY, int disabledX, int disabledY, int sizeX, int sizeY, String texture) {
        super(gui, posX, posY, name, sheetX, sheetY, hoverX, hoverY, disabledX, disabledY, sizeX, sizeY, texture);
    }

    public ElementButtonRedstone setTabRedstone(TabRedstone tabRedstone) {
        this.tabRedstone = tabRedstone;
        return this;
    }

    public ElementButtonRedstone setType(IRedstoneControl.ControlMode type){
        this.type = type;
        return this;
    }

    @Override
    public void addTooltip(List<String> list) {
        super.addTooltip(list);
    }

    @Override
    public void onClick() {
        super.onClick();
        tabRedstone.getRsControllable().setControl(type);
    }


}
