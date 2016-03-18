package com.ermans.repackage.cofh.lib.gui.element;

import cofh.api.energy.IEnergyStorage;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.render.RenderHelper;
import com.ermans.repackage.cofh.lib.util.helpers.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ElementEnergyStored extends ElementBase {

    public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Textures.Gui.Element.ENERGY_BAR);
    public static final int defaultPosX = 8;
    public static final int defaultPosY = 8;

    protected IEnergyStorage storage;

    // If this is enabled, 1 pixel of energy will always show in the bar as long as it is non-zero.
    protected boolean alwaysShowMinimum = false;

    public ElementEnergyStored(GuiBase gui, int posX, int posY, IEnergyStorage storage) {

        super(gui, posX, posY);
        this.storage = storage;

        this.texture = DEFAULT_TEXTURE;
        this.sizeX = 16;
        this.sizeY = 42;

        this.texW = 32;
        this.texH = 64;
    }

    public ElementEnergyStored(GuiBase gui, IEnergyStorage storage) {
        this(gui, defaultPosX, defaultPosY, storage);
    }

    public ElementEnergyStored setAlwaysShow(boolean show) {

        alwaysShowMinimum = show;
        return this;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {

        int amount = getScaled();

        RenderHelper.bindTexture(texture);
        drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
        drawTexturedModalRect(posX, posY + sizeY - amount, 16, sizeY - amount, sizeX, amount);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

    @Override
    public void addTooltip(List<String> list) {

        if (storage.getMaxEnergyStored() < 0) {
            list.add("Infinite RF");
        } else {
            list.add(storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF");
        }
    }

    protected int getScaled() {

        if (storage.getMaxEnergyStored() <= 0) {
            return sizeY;
        }
        long fraction = (long) storage.getEnergyStored() * sizeY / storage.getMaxEnergyStored();

        return alwaysShowMinimum && storage.getEnergyStored() > 0 ? Math.max(1, MathHelper.round(fraction)) : MathHelper.round(fraction);
    }

}
