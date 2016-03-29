//package com.ermans.bottledanimals.nei;
//
//import codechicken.nei.api.API;
//import codechicken.nei.api.IConfigureNEI;
//import com.ermans.bottledanimals.init.ModItems;
//import com.ermans.bottledanimals.reference.Reference;
//import net.minecraft.item.ItemStack;
//
//public class NEIBottledAnimalsConfig implements IConfigureNEI {
//    @Override
//    public void loadConfig() {
//
//        //Move nei items away from tabs
//        API.registerNEIGuiHandler(NEIGuiHandler.instance);
//
//        API.registerRecipeHandler(new AnimalDigitizerRecipeHandler());
//        API.registerUsageHandler(new AnimalDigitizerRecipeHandler());
//
//        API.registerRecipeHandler(new BreederRecipeHandler());
//        API.registerUsageHandler(new BreederRecipeHandler());
//
//        API.registerRecipeHandler(new DropExtractorRecipeHandler());
//        API.registerUsageHandler(new DropExtractorRecipeHandler());
//
//        API.registerRecipeHandler(new GrowthAcceleratorRecipeHandler());
//        API.registerUsageHandler(new GrowthAcceleratorRecipeHandler());
//
//        API.registerRecipeHandler(new RancherRecipeHandler());
//        API.registerUsageHandler(new RancherRecipeHandler());
//
//        API.registerRecipeHandler(new FoodCrusherRecipeHandler());
//        API.registerUsageHandler(new FoodCrusherRecipeHandler());
//
//        API.registerRecipeHandler(new AnimalMaterializerRecipeHandler());
//        API.registerUsageHandler(new AnimalMaterializerRecipeHandler());
//
//
//        API.hideItem(new ItemStack(ModItems.itemModIcon));
//    }
//
//    @Override
//    public String getName() {
//        return "Bottled Animals NEI Plugin";
//    }
//
//    @Override
//    public String getVersion() {
//        return Reference.VERSION;
//    }
//}
