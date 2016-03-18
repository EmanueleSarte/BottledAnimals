package com.ermans.bottledanimals.hooks;

import com.ermans.bottledanimals.init.ModBlocks;
import com.ermans.bottledanimals.init.ModFluids;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent.Post;

public class TextureIconHook {

    public static final TextureIconHook INSTANCE = new TextureIconHook();

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void textureHook(Post event) {
        if (event.map.getTextureType() == 0) {
            if (ModFluids.milk != null) {
                ModFluids.milk.setIcons(ModBlocks.milkBlock.getBlockTextureFromSide(1), ModBlocks.milkBlock.getBlockTextureFromSide(2));
            }
            if (ModFluids.food != null) {
                ModFluids.food.setIcons(ModBlocks.foodBlock.getBlockTextureFromSide(1), ModBlocks.foodBlock.getBlockTextureFromSide(2));
            }
        }
    }
}
