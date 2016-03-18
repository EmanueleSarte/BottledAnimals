package com.ermans.repackage.cofh.lib.gui.element;

import com.ermans.repackage.cofh.lib.gui.GuiBase;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ElementButtonOption extends ElementButtonManaged {

    private final Map<Integer, SubElement> _values = new HashMap<Integer, SubElement>();
    private int _currentValue = 0;
    private int _maxValue;

    public ElementButtonOption(GuiBase containerScreen, int x, int y, int width, int height) {
        super(containerScreen, x, y, width, height, "");
    }

    public void setValue(int value, int sheetX, int sheetY, String label) {
        _values.put(value, new SubElement(sheetX, sheetY, label));
        if (value > _maxValue) {
            _maxValue = value;
        }
    }

    public void setDefaultValue(int value){
        _currentValue = value;
    }


    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {
        super.drawBackground(mouseX, mouseY, gameTicks);

        gui.bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        SubElement sub = _values.get(_currentValue);

        drawTexturedModalRect(posX, posY, sub.sheetX, sub.sheetY, sizeX, sizeY);
    }

    @Override
    public void onClick() {

        int nextValue = _currentValue;
        do {
            nextValue++;
            if (nextValue > _maxValue) {
                nextValue = 0;
            }
        } while (_values.get(nextValue) == null);
        setSelectedIndex(nextValue);
    }

    @Override
    public void onRightClick() {

        int nextValue = _currentValue;

        do {
            nextValue--;
            if (nextValue < 0) {
                nextValue = _maxValue;
            }
        } while (_values.get(nextValue) == null);
        setSelectedIndex(nextValue);
    }

    public int getSelectedIndex() {

        return _currentValue;
    }

    public void setSelectedIndex(int index) {

        _currentValue = index;
        onValueChanged(_currentValue);
    }

    @Override
    public void addTooltip(List<String> list) {
        list.add(_values.get(_currentValue).tooltipText);
    }

    public abstract void onValueChanged(int value);


    class SubElement {
        protected int sheetX;
        protected int sheetY;
        protected String tooltipText;

        public SubElement(int sheetX, int sheetY, String tooltipText) {
            this.sheetX = sheetX;
            this.sheetY = sheetY;
            this.tooltipText = tooltipText;
        }
    }
}
