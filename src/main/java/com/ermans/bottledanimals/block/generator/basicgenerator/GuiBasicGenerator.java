package com.ermans.bottledanimals.block.generator.basicgenerator;

import com.ermans.bottledanimals.IconRegistry;
import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.ElementIcon;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.Arrays;
import java.util.List;

public class GuiBasicGenerator extends GuiBaseAdv {

    protected TileBasicGenerator tile;
    protected ElementDualScaled speed;
    protected ElementIcon icon;
    protected int lastIcon;

    public GuiBasicGenerator(InventoryPlayer invPlayer, TileBasicGenerator entity) {
        super(new ContainerBasicGenerator(invPlayer, entity), Textures.Gui.BASIC_GENERATOR, entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabInfo(this, TabBase.LEFT, "Generates power from solid fuel"));
        elementEnergyStored.setPosition(119, 21);

        this.speed = (ElementDualScaled) addElement(new ElementDualScaled(this, 78, 47).setMode(0).setSize(14, 14).setTexture(Textures.Gui.Element.PROGRESS_FIRE, 32, 16));

        this.icon = (ElementIcon) addElement(new ElementIcon(this, 25, 27, null));
        this.icon.setSize(16, 16);
        lastIcon = -1;
    }


    private static List<String> iconLowGen = Arrays.asList("No output,", "generating at 1 RF/t");
    private static List<String> iconBalance = Arrays.asList("Reaching the half", "at max speed");
    private static List<String> iconRigGen = Arrays.asList("Generating right", "the energy required");
    private static List<String> iconOff = Arrays.asList("No energy generation");

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();
        this.speed.setQuantity(tile.getPercentage(16));
        if (lastIcon != tile.getState().ordinal()) {
            switch (tile.getState()) {
                case LOW_GEN:
                    icon.setIcon(IconRegistry.getIcon("GeneratorLowGen"));
                    icon.setTooltipText(iconLowGen);
                    break;
                case BALANCING:
                    icon.setIcon(IconRegistry.getIcon("GeneratorBalance"));
                    icon.setTooltipText(iconBalance);
                    break;
                case RIGHT_GEN:
                    icon.setIcon(IconRegistry.getIcon("GeneratorRigGen"));
                    icon.setTooltipText(iconRigGen);
                    break;
                case OFF:
                    icon.setIcon(IconRegistry.getIcon("GeneratorOff"));
                    icon.setTooltipText(iconOff);
                    break;
            }
        }
        lastIcon = tile.getState().ordinal();
    }
}
