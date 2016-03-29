package com.ermans.bottledanimals.client.gui.tab;

import cofh.api.tileentity.IRedstoneControl;
import com.ermans.bottledanimals.client.gui.button.ElementButtonRedstone;
import com.ermans.bottledanimals.helper.ColorHelper;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TabRedstone extends TabBase {

    protected IRedstoneControl rsControllable;
    private ElementButtonRedstone[] buttonRedstones = new ElementButtonRedstone[3];

    public TabRedstone(GuiBase gui, IRedstoneControl rsControllable, int side) {
        super(gui, side);

        this.rsControllable = rsControllable;

        this.headerColor = ColorHelper.rgb(211,84,137);
        this.subheaderColor = 11186104;
        this.textColor = 0;
        this.backgroundColor = ColorHelper.rgb(253,227,167);

        this.maxHeight = 45;
        this.maxWidth = 85;

        buttonRedstones[0] = new ElementButtonRedstone(gui, 10, 20, 16, 16, 0, 0, 16, 0, 32, 0, Textures.WIDGET_TEXTURE_STRING).setTabRedstone(this).setType(IRedstoneControl.ControlMode.HIGH);
        buttonRedstones[1] = new ElementButtonRedstone(gui, 30, 20, 16, 16, 0, 16, 16, 16, 32, 16, Textures.WIDGET_TEXTURE_STRING).setTabRedstone(this).setType(IRedstoneControl.ControlMode.LOW);
        buttonRedstones[2] = new ElementButtonRedstone(gui, 50, 20, 16, 16, 0, 32, 16, 32, 32, 32, Textures.WIDGET_TEXTURE_STRING).setTabRedstone(this).setType(IRedstoneControl.ControlMode.DISABLED);

        addElement(buttonRedstones[0].setToolTip("Active with Energy"));
        addElement(buttonRedstones[1].setToolTip("Active without Energy"));
        addElement(buttonRedstones[2].setToolTip("Always Active"));
    }


    @Override
    protected void drawForeground() {
//        drawTabIcon("IconRedstone");
        if (!isFullyOpened()) {
            return;
        }

        switch (rsControllable.getControl()){
            case HIGH:
                buttonRedstones[0].setDisabled();
                buttonRedstones[1].setActive();
                buttonRedstones[2].setActive();
                break;
            case LOW:
                buttonRedstones[1].setDisabled();
                buttonRedstones[0].setActive();
                buttonRedstones[2].setActive();
                break;
            case DISABLED:
                buttonRedstones[2].setDisabled();
                buttonRedstones[1].setActive();
                buttonRedstones[0].setActive();
                break;
        }

        getFontRenderer().drawStringWithShadow("Redstone", posXOffset() + 18, this.posY + 6, this.headerColor);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public IRedstoneControl getRsControllable() {
        return rsControllable;
    }


    @Override
    public void addTooltip(List<String> tooltipList) {
        super.addTooltip(tooltipList);
        if (!isFullyOpened()) {
            tooltipList.add("Redstone Control");
        }
    }
}
