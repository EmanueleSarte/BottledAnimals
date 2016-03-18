package com.ermans.bottledanimals;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_ANIMAL_DIGITIZER = 11;
    public static final int GUI_BREEDER = 12;
    public static final int GUI_GROWTH_ACCELERATOR = 13;
    public static final int GUI_DROP_EXTRACTOR = 14;
    public static final int GUI_RANCHER = 15;
    public static final int GUI_FOOD_CRUSHER = 16;
    public static final int WIRELESS_FEEDER = 20;

    private final Map<Integer, IGuiHandler> guiMap = new HashMap<Integer, IGuiHandler>();
    private int lastID = 100;

    public void addGuiHandler(int id, IGuiHandler handler) {
        this.guiMap.put(id, handler);
    }

    public int addGuiHandler(IGuiHandler handler) {
        lastID++;
        this.guiMap.put(lastID, handler);
        return lastID;
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        IGuiHandler handler = this.guiMap.get(id);
        if (handler != null) {
            return handler.getServerGuiElement(id, player, world, x, y, z);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        IGuiHandler handler = this.guiMap.get(id);
        if (handler != null) {
            return handler.getClientGuiElement(id, player, world, x, y, z);
        }
        return null;
    }
}
