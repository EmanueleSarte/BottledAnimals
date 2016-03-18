package com.ermans.bottledanimals.reference;

import net.minecraft.util.ResourceLocation;

public final class Textures {
    public static final String RESOURCE_PREFIX = Reference.MOD_ID_LOWERCASE + ":";
    public static final String WIDGET_TEXTURE_STRING = Reference.MOD_ID_LOWERCASE + ":textures/gui/widgets.png";
    public static final ResourceLocation WIDGET_TEXTURE = new ResourceLocation(Reference.MOD_ID_LOWERCASE, "textures/gui/widgets.png");



    public static final String PATH_GFX = Reference.MOD_ID_LOWERCASE + ":textures/";
    public static final String PATH_ARMOR = PATH_GFX + "armor/";
    public static final String PATH_GUI = PATH_GFX + "gui/";
    public static final String PATH_RENDER = PATH_GFX + "blocks/";
    public static final String PATH_ELEMENTS = PATH_GUI + "elements/";
    public static final String PATH_ICON = PATH_GUI + "icons/";

    public static final class Gui {
        public static final ResourceLocation ANIMAL_DIGITIZER = getResourceLocation("animalDigitizer.png");
        public static final ResourceLocation BREEDER = getResourceLocation("breeder.png");
        public static final ResourceLocation GROWTH_ACCELERATOR = getResourceLocation("growthAccelerator.png");
        public static final ResourceLocation DROP_EXTRACTOR = getResourceLocation("dropExtractor.png");
        public static final ResourceLocation WIRELESS_FEEDER = getResourceLocation("wirelessFeeder.png");
        public static final ResourceLocation RANCHER = getResourceLocation("rancher.png");
        public static final ResourceLocation FOOD_CRUSHER = getResourceLocation("foodCrusher.png");

        public static final ResourceLocation NEI_GUI = getResourceLocation("nei.png");


        //This just return the location string, not a ResourceLocation object
        public static final class Element {
            public static final String ENERGY_BAR = getElementsLocation("Energy.png");
            public static final String FLUID_TANK = getElementsLocation("FluidTank.png");

            public static final String PROGRESS_SAW = getElementsLocation("Progress_Saw.png");
            public static final String PROGRESS_ARROW = getElementsLocation("Progress_Arrow.png");
            public static final String PROGRESS_ARROW_HEART = getElementsLocation("Progress_Arrow_Heart.png");
            public static final String PROGRESS_EXTRACT = getElementsLocation("Progress_Extract.png");
            public static final String PROGRESS_MULT = getElementsLocation("Progress_Multiplier.png");
            public static final String PROGRESS_MILK = getElementsLocation("Progress_Milk.png");
            public static final String PROGRESS_SHEARS = getElementsLocation("Progress_Shears.png");

        }

    }

    private static String getElementsLocation(String name){
        return PATH_ELEMENTS + name;
    }

    private static ResourceLocation getResourceLocation(String name) {
        return new ResourceLocation(PATH_GUI + name);
    }


}
