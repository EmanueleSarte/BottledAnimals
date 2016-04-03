package com.ermans.bottledanimals;


import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class IconRegistry {
    private static Map<String, TextureAtlasSprite> icons = new HashMap<String, TextureAtlasSprite>();

    public static void addIcon(String iconName, String location, TextureMap map) {
        icons.put(iconName, map.registerSprite(new ResourceLocation(location)));
    }

    public static void addIcon(String paramString, TextureAtlasSprite paramIIcon) {
        icons.put(paramString, paramIIcon);
    }

    public static TextureAtlasSprite getIcon(String iconName) {
        return icons.get(iconName);
    }
}