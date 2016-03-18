package com.ermans.bottledanimals;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

public class CommonProxy {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre paramPre) {}
}
