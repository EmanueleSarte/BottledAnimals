package com.ermans.bottledanimals;

import com.ermans.bottledanimals.block.FluidBlockBA;
import com.ermans.bottledanimals.init.ModBlocks;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.reference.Reference;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends CommonProxy {


    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        if (event.getSide() == Side.CLIENT) {

            for (String blockName : ModBlocks.blocks.keySet()) {
                registerBlockModel(blockName);
            }

            for (FluidBlockBA fluidBlock : ModBlocks.fluidBlocks.values()) {
                registerFluidModel(fluidBlock);
            }

            for (String itemName : ModItems.items.keySet()) {
                registerItemModel(itemName);
            }
        }


    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre paramPre) {
        IconRegistry.addIcon("EnergyNow", Textures.RESOURCE_PREFIX + "icons/Energy_Now", paramPre.map);
        IconRegistry.addIcon("EnergyMax",  Textures.RESOURCE_PREFIX + "icons/Energy_Max", paramPre.map);
        IconRegistry.addIcon("EnergyOut",  Textures.RESOURCE_PREFIX + "icons/Energy_Out", paramPre.map);
        IconRegistry.addIcon("EnergyTime",  Textures.RESOURCE_PREFIX + "icons/Energy_Time", paramPre.map);
        IconRegistry.addIcon("IconEnergy",  Textures.RESOURCE_PREFIX + "icons/Icon_Energy", paramPre.map);
        IconRegistry.addIcon("IconEnergyOn",  Textures.RESOURCE_PREFIX + "icons/Icon_Energy_On", paramPre.map);
        IconRegistry.addIcon("IconMachineInfo",  Textures.RESOURCE_PREFIX + "icons/Icon_Machine_Info", paramPre.map);
        IconRegistry.addIcon("IconRedstone",  Textures.RESOURCE_PREFIX + "icons/Icon_Redstone", paramPre.map);
        IconRegistry.addIcon("GeneratorOff",  Textures.RESOURCE_PREFIX + "icons/Generator_Off", paramPre.map);
        IconRegistry.addIcon("GeneratorBalance",  Textures.RESOURCE_PREFIX + "icons/Generator_Balance", paramPre.map);
        IconRegistry.addIcon("GeneratorLowGen",  Textures.RESOURCE_PREFIX + "icons/Generator_LowGen", paramPre.map);
        IconRegistry.addIcon("GeneratorRigGen",  Textures.RESOURCE_PREFIX + "icons/Generator_RigGen", paramPre.map);
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
                new ModelResourceLocation(Textures.RESOURCE_PREFIX + blockName, "inventory"));
    }

    private void registerItemModel(String itemName) {
        final int DEFAULT_ITEM_SUBTYPE = 0;
        Item item = GameRegistry.findItem(Reference.MOD_ID, itemName);

        if (item.getHasSubtypes()) {
            List<ItemStack> stacks = new ArrayList<ItemStack>();

            item.getSubItems(item, null, stacks);
            for (ItemStack stack : stacks) {
                ModelLoader.setCustomModelResourceLocation(item, stack.getMetadata(),
                        new ModelResourceLocation(Textures.RESOURCE_PREFIX + ItemHelper.getResourceName(stack, true), "inventory"));
            }

        } else {

            ModelLoader.setCustomModelResourceLocation(item, DEFAULT_ITEM_SUBTYPE,
                    new ModelResourceLocation(Textures.RESOURCE_PREFIX + itemName, "inventory"));
        }
    }


    private void registerFluidModel(IFluidBlock fluidBlock) {
        Item item = Item.getItemFromBlock((Block) fluidBlock);

        ModelBakery.registerItemVariants(item);

        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Textures.RESOURCE_PREFIX + "fluid", fluidBlock.getFluid().getName());

        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return modelResourceLocation;
            }
        });

        ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
                return modelResourceLocation;
            }
        });
    }
}
