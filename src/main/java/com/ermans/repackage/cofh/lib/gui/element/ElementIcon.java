package com.ermans.repackage.cofh.lib.gui.element;

import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.GuiColor;

import java.util.List;

public class ElementIcon extends ElementBase {

//	protected IIcon icon;
	protected int spriteSheet;
	protected GuiColor color = new GuiColor(-1);
	protected List<String> tooltipText;

	public ElementIcon(GuiBase gui, int posX, int posY) {
		super(gui, posX, posY);
	}

//	public ElementIcon(GuiBase gui, int posX, int posY, IIcon icon) {
//
//		this(gui, posX, posY, icon, 0);
//	}
//
//	public ElementIcon(GuiBase gui, int posX, int posY, IIcon icon, int spriteSheet) {
//
//		super(gui, posX, posY);
//		this.icon = icon;
//		this.spriteSheet = spriteSheet;
//	}

	public ElementIcon setColor(Number color) {

		this.color = new GuiColor(color.intValue());
		return this;
	}

//	public ElementIcon setIcon(IIcon icon) {
//
//		this.icon = icon;
//		return this;
//	}

	public ElementIcon setSpriteSheet(int spriteSheet) {

		this.spriteSheet = spriteSheet;
		return this;
	}

	public int getColor() {

		return color.getColor();
	}

	@Override
	public ElementIcon setTooltipText(List<String> tooltipText){
		this.tooltipText = tooltipText;
		return this;
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {

//		if (icon != null) {
//			GL11.glColor4f(color.getFloatR(), color.getFloatG(), color.getFloatB(), color.getFloatA());
//			gui.drawColorIcon(icon, posX, posY, spriteSheet);
//			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
//		}
//		RenderHelper.setItemTextureSheet();
//		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
//		gui.drawTexturedModelRectFromIcon(posX, posY, icon, 16, 16);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {

	}

	@Override
	public void addTooltip(List<String> list) {
		if (this.tooltipText != null){
			list.addAll(tooltipText);
		}
	}
}
