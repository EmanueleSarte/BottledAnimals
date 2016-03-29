package com.ermans.bottledanimals;

import com.ermans.bottledanimals.reference.Names;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {



    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        if (event.getSide() == Side.CLIENT) {
            registerBlockModel(Names.Machines.ANIMAL_DIGITIZER);
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }


    private void registerBlockModel(String blockName) {
        final int DEFAULT_ITEM_SUBTYPE = 0;

        ModelLoader.setCustomModelResourceLocation(
                GameRegistry.findItem(Reference.MOD_ID, blockName),
                DEFAULT_ITEM_SUBTYPE,
                new ModelResourceLocation(Reference.MOD_ID_LOWERCASE + ":" + blockName, "inventory"));
    }
}
