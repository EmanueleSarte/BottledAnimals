package com.ermans.bottledanimals;

import com.ermans.bottledanimals.fluid.FluidBlockBA;
import com.ermans.bottledanimals.init.ModBlocks;
import com.ermans.bottledanimals.reference.Reference;
import com.ermans.bottledanimals.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
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

            for (String blockName: ModBlocks.blocks.keySet()){
                registerBlockModel(blockName);
            }

            for (FluidBlockBA fluidBlock: ModBlocks.fluidBlocks.values()){
                registerFluidModel(fluidBlock);
            }
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
                new ModelResourceLocation(Textures.RESOURCE_PREFIX + blockName, "inventory"));
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
