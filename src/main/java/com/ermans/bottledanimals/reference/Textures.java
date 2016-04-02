package com.ermans.bottledanimals.reference;

import net.minecraft.util.ResourceLocation;

public final class Textures {
    public static final String RESOURCE_PREFIX = Reference.MOD_ID_LOWERCASE + ":";
    public static final String WIDGET_TEXTURE_STRING = Reference.MOD_ID_LOWERCASE + ":textures/gui/widgets.png";
    public static final ResourceLocation WIDGET_TEXTURE = new ResourceLocation(WIDGET_TEXTURE_STRING);


    public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";
    public static final String GUI_LOCATION_PREFIX = TEXTURE_PREFIX + "gui/";
    public static final String ELEMENT_LOCATION_PREFIX = GUI_LOCATION_PREFIX + "elements/";

    public static final class Gui {
        public static final ResourceLocation ANIMAL_DIGITIZER = new ResourceLocation(GUI_LOCATION_PREFIX + "animalDigitizer.png");
        public static final ResourceLocation BREEDER = new ResourceLocation(GUI_LOCATION_PREFIX + "breeder.png");
        public static final ResourceLocation GROWTH_ACCELERATOR = new ResourceLocation(GUI_LOCATION_PREFIX + "growthAccelerator.png");
        public static final ResourceLocation DROP_EXTRACTOR = new ResourceLocation(GUI_LOCATION_PREFIX + "dropExtractor.png");
        public static final ResourceLocation WIRELESS_FEEDER = new ResourceLocation(GUI_LOCATION_PREFIX + "wirelessFeeder.png");
        public static final ResourceLocation RANCHER = new ResourceLocation(GUI_LOCATION_PREFIX + "rancher.png");
        public static final ResourceLocation FOOD_CRUSHER = new ResourceLocation(GUI_LOCATION_PREFIX + "foodCrusher.png");
        public static final ResourceLocation ANIMAL_MATERIALIZER = new ResourceLocation(GUI_LOCATION_PREFIX + "animalMaterializer.png");
        public static final ResourceLocation BASIC_GENERATOR = new ResourceLocation(GUI_LOCATION_PREFIX + "basicGenerator.png");

        public static final ResourceLocation NEI_GUI = new ResourceLocation(GUI_LOCATION_PREFIX + "nei.png");


        //This just return the location string, not a ResourceLocation object
        public static final class Element {
            public static final String ENERGY_BAR = ELEMENT_LOCATION_PREFIX + "Energy.png";
            public static final String FLUID_TANK = ELEMENT_LOCATION_PREFIX + "FluidTank.png";

            public static final String PROGRESS_SAW = ELEMENT_LOCATION_PREFIX + "Progress_Saw.png";
            public static final String PROGRESS_ARROW = ELEMENT_LOCATION_PREFIX + "Progress_Arrow.png";
            public static final String PROGRESS_ARROW_HEART = ELEMENT_LOCATION_PREFIX + "Progress_Arrow_Heart.png";
            public static final String PROGRESS_EXTRACT = ELEMENT_LOCATION_PREFIX + "Progress_Extract.png";
            public static final String PROGRESS_MULT = ELEMENT_LOCATION_PREFIX + "Progress_Multiplier.png";
            public static final String PROGRESS_MILK = ELEMENT_LOCATION_PREFIX + "Progress_Milk.png";
            public static final String PROGRESS_SHEARS = ELEMENT_LOCATION_PREFIX + "Progress_Shears.png";
            public static final String PROGRESS_FIRE = ELEMENT_LOCATION_PREFIX + "Progress_Fire.png";
            public static final String PROGRESS_FLUID_IN = ELEMENT_LOCATION_PREFIX + "Progress_FluidIn.png";

        }

    }

    public static final class Fluid {
        public static final String FLUID_LOCATION_PREFIX = RESOURCE_PREFIX + "blocks/";

        public static final ResourceLocation MILK_STILL = new ResourceLocation(FLUID_LOCATION_PREFIX + "bamilk_still");
        public static final ResourceLocation MILK_FLOW = new ResourceLocation(FLUID_LOCATION_PREFIX + "bamilk_flow");
        public static final ResourceLocation FOOD_STILL = new ResourceLocation(FLUID_LOCATION_PREFIX + "food_still");
        public static final ResourceLocation FOOD_FLOW = new ResourceLocation(FLUID_LOCATION_PREFIX + "food_flow");
    }

}

