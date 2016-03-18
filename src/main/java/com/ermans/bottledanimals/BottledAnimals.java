package com.ermans.bottledanimals;

import com.ermans.bottledanimals.fluid.BucketHandler;
import com.ermans.bottledanimals.helper.FoodHelper;
import com.ermans.bottledanimals.hooks.TextureIconHook;
import com.ermans.bottledanimals.init.*;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.recipe.FoodCrusherManager;
import com.ermans.bottledanimals.reference.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class BottledAnimals {
    @Mod.Instance(Reference.MOD_ID)
    public static BottledAnimals instance;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    public static final GuiHandler guiHandler = new GuiHandler();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Log.info("Starting Pre Initialization");
        MinecraftForge.EVENT_BUS.register(proxy);
        PacketHandler.init();

        Log.info("Pre Initialization Complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Log.info("Starting Initialization");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);

        ModFluids.init();
        ModBlocks.init();
        ModItems.init();
        ModTiles.init();
        ModRecipes.init();

        BucketHandler.init();
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

        MinecraftForge.EVENT_BUS.register(TextureIconHook.INSTANCE);
        Log.info("Initialization Complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        FoodHelper.initItemValue();
        FoodCrusherManager.init();
        Log.info("Starting Post Initialization");
        Log.info("Post Initialization Complete");
    }
}
