package com.ermans.bottledanimals.client.gui;


import net.minecraft.util.ResourceLocation;

public class GuiIcon {

    protected int width;
    protected int height;
    protected int xTexture;
    protected int yTexture;
    protected ResourceLocation location;


    public GuiIcon(int width, int height, int xTexture, int yTexture, ResourceLocation location) {
        this.width = width;
        this.height = height;
        this.xTexture = xTexture;
        this.yTexture = yTexture;
        this.location = location;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getxTexture() {
        return xTexture;
    }

    public void setxTexture(int xTexture) {
        this.xTexture = xTexture;
    }

    public int getyTexture() {
        return yTexture;
    }

    public void setyTexture(int yTexture) {
        this.yTexture = yTexture;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public void setLocation(ResourceLocation location) {
        this.location = location;
    }
}
