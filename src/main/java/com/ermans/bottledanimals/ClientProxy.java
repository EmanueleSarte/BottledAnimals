package com.ermans.bottledanimals;

import com.ermans.bottledanimals.reference.Reference;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre paramPre) {
        if (paramPre.map.getTextureType() != 0) {
            if (paramPre.map.getTextureType() == 1) {
                IconRegistry.addIcon("EnergyNow", Reference.MOD_ID_LOWERCASE + ":icons/Energy_Now", paramPre.map);
                IconRegistry.addIcon("EnergyMax", Reference.MOD_ID_LOWERCASE + ":icons/Energy_Max", paramPre.map);
                IconRegistry.addIcon("EnergyOut", Reference.MOD_ID_LOWERCASE + ":icons/Energy_Out", paramPre.map);
                IconRegistry.addIcon("EnergyTime", Reference.MOD_ID_LOWERCASE + ":icons/Energy_Time", paramPre.map);
                IconRegistry.addIcon("IconEnergy", Reference.MOD_ID_LOWERCASE + ":icons/Icon_Energy", paramPre.map);
                IconRegistry.addIcon("IconEnergyOn", Reference.MOD_ID_LOWERCASE + ":icons/Icon_Energy_On", paramPre.map);
                IconRegistry.addIcon("IconMachineInfo", Reference.MOD_ID_LOWERCASE + ":icons/Icon_Machine_Info", paramPre.map);
                IconRegistry.addIcon("IconRedstone", Reference.MOD_ID_LOWERCASE + ":icons/Icon_Redstone", paramPre.map);
                IconRegistry.addIcon("GeneratorOff", Reference.MOD_ID_LOWERCASE + ":icons/Generator_Off", paramPre.map);
                IconRegistry.addIcon("GeneratorBalance", Reference.MOD_ID_LOWERCASE + ":icons/Generator_Balance", paramPre.map);
                IconRegistry.addIcon("GeneratorLowGen", Reference.MOD_ID_LOWERCASE + ":icons/Generator_LowGen", paramPre.map);
                IconRegistry.addIcon("GeneratorRigGen", Reference.MOD_ID_LOWERCASE + ":icons/Generator_RigGen", paramPre.map);
            }
        }
    }
}
