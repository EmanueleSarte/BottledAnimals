package com.ermans.bottledanimals;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import java.util.HashMap;
import java.util.Map;

public class IconRegistry {
    private static Map<String, IIcon> icons = new HashMap<String, IIcon>();

    public static void addIcon(String iconName, String location, IIconRegister paramIIconRegister) {
        icons.put(iconName, paramIIconRegister.registerIcon(location));
    }

    public static void addIcon(String paramString, IIcon paramIIcon) {
        icons.put(paramString, paramIIcon);
    }

    public static IIcon getIcon(String iconName) {
        return icons.get(iconName);
    }
}