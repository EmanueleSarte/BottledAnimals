package com.ermans.bottledanimals;

import com.ermans.bottledanimals.handlers.BucketHandler;
import com.ermans.bottledanimals.helper.FoodHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.recipe.FoodCrusherManager;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class BottledAnimals {
    @Mod.Instance(Reference.MOD_ID)
    public static BottledAnimals INSTANCE;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    public static final GuiHandler guiHandler = new GuiHandler();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Log.info("Starting Pre Initialization");

        MinecraftForge.EVENT_BUS.register(proxy);
        proxy.preInit(event);
        PacketHandler.init();

        Log.info("Pre Initialization Complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Log.info("Starting Initialization");

        proxy.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

//        MinecraftForge.EVENT_BUS.register(TextureIconHook.INSTANCE);
        Log.info("Initialization Complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);

        FoodHelper.initItemValue();
        FoodCrusherManager.init();
        Log.info("Starting Post Initialization");
        Log.info("Post Initialization Complete");
    }
}


    //TODO Add support for other mods
    // TODO: 05/04/2016 add support for 1.8 things
    // TODO: 05/04/2016 new Animals class
    // TODO: 05/04/2016 remove milkBucket and use fluid from dictionary
    // TODO: 05/04/2016 generator divides energy and cached system
    // TODO: 05/04/2016 lang italiano
// TODO: 05/04/2016 feed platform
// TODO: 05/04/2016 active doesn't work
// TODO: 06/04/2016 join energy powered and energy provider
