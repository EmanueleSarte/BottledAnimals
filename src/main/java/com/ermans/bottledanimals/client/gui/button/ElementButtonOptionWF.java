package com.ermans.bottledanimals.client.gui.button;

import com.ermans.bottledanimals.block.machine.wirelessfeeder.GuiWirelessFeeder;
import com.ermans.bottledanimals.block.machine.wirelessfeeder.TileWirelessFeeder;
import com.ermans.repackage.cofh.lib.gui.element.ElementButtonOption;


public class ElementButtonOptionWF extends ElementButtonOption {

    TileWirelessFeeder tile;

    public ElementButtonOptionWF(GuiWirelessFeeder containerScreen, int x, int y, int width, int height) {
        super(containerScreen, x, y, width, height);

        setValue(0, 0, 48, "Disabled");
        setValue(1, 16, 48, "Heals Only");
        setValue(2, 32, 48, "Feeds Only");
        setValue(3, 48, 48, "Feeds and Heals");
        tile = containerScreen.getTile();
    }

    @Override
    public void onValueChanged(int value) {
        tile.setMode(TileWirelessFeeder.Mode.values()[value]);
    }
}
