package com.ermans.repackage.cofh.lib.gui.element;

import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.GuiColor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.lwjgl.opengl.GL11;

public class ElementIcon extends ElementBase {


    protected TextureAtlasSprite icon;
    protected int spriteSheet;
    protected GuiColor color = new GuiColor(-1);

    public ElementIcon(GuiBase gui, int posX, int posY, TextureAtlasSprite icon) {

        this(gui, posX, posY, icon, 0);
    }

    public ElementIcon(GuiBase gui, int posX, int posY, TextureAtlasSprite icon, int spriteSheet) {

        super(gui, posX, posY);
        this.icon = icon;
        this.spriteSheet = spriteSheet;
    }

    public ElementIcon setColor(Number color) {

        this.color = new GuiColor(color.intValue());
        return this;
    }

    public ElementIcon setIcon(TextureAtlasSprite icon) {

        this.icon = icon;
        return this;
    }

    public ElementIcon setSpriteSheet(int spriteSheet) {

        this.spriteSheet = spriteSheet;
        return this;
    }

    public int getColor() {

        return color.getColor();
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {

        if (icon != null) {
            GL11.glColor4f(color.getFloatR(), color.getFloatG(), color.getFloatB(), color.getFloatA());
            gui.drawColorIcon(icon, posX, posY, spriteSheet);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

        return;
    }
}
