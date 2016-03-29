package com.ermans.bottledanimals.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    private static final TextureManager engine = Minecraft.getMinecraft().renderEngine;

    public static void drawFluid(FluidStack fluid, int x, int y, int width, int height, int maxCapacity) {
        drawFluid(fluid, x, y, width, height, maxCapacity, false);
    }

    public static void drawFluid(FluidStack fluid, int x, int y, int width, int height, int maxCapacity, boolean showMin) {
        if ((fluid == null) || (fluid.getFluid() == null)) {
            return;
        }
//        IIcon icon = fluid.getFluid().getIcon(fluid);
        engine.bindTexture(TextureMap.locationBlocksTexture);
        int color = fluid.getFluid().getColor(fluid);
        GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));

        GL11.glEnable(3042);
        int fullX = width / 16;
        int fullY = height / 16;
        if (showMin && fullY == 0) fullY = 1;

        int lastX = width - fullX * 16;
        int lastY = height - fullY * 16;
        if (showMin && lastX == height) lastY = height - 1;

        int level = fluid.amount * height / maxCapacity;
        if (level == 0) level = 1;

        int fullLvl = (height - level) / 16;
        int lastLvl = height - level - fullLvl * 16;
//        for (int i = 0; i < fullX; i++) {
//            for (int j = 0; j < fullY; j++) {
//                if (j >= fullLvl) {
//                    drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
//                }
//            }
//        }
//        for (int i = 0; i < fullX; i++) {
//            drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
//        }
//        for (int i = 0; i < fullY; i++) {
//            if (i >= fullLvl) {
//                drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
//            }
//        }
//        drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
//        GL11.glDisable(3042);
//    }
//
//    private static void drawCutIcon(IIcon icon, int x, int y, int width, int height, int cut) {
//        drawCutIcon(icon, x, y, 0.0D, width, height, cut);
    }

//    private static void drawCutIcon(IIcon icon, int x, int y, double zLevel, int width, int height, int cut) {
//        Tessellator tess = Tessellator.getInstance();
//        tess.startDrawingQuads();
//        tess.addVertexWithUV(x, y + height, zLevel, icon.getMinU(), icon.getInterpolatedV(height));
//        tess.addVertexWithUV(x + width, y + height, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
//        tess.addVertexWithUV(x + width, y + cut, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
//        tess.addVertexWithUV(x, y + cut, zLevel, icon.getMinU(), icon.getInterpolatedV(cut));
//        tess.draw();
//    }

}
