package com.ermans.bottledanimals.init;

import com.ermans.bottledanimals.Log;
import com.ermans.bottledanimals.reference.Names;
import com.ermans.bottledanimals.reference.Textures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;
import java.util.Map;

public class ModFluids {


    public static Fluid milk;
    public static Fluid food;


    public static Map<String, Fluid> fluids = new HashMap<String, Fluid>();

    public static void registerFluids() {

        milk = registerFluid(Names.Fluid.MILK, Textures.Fluid.MILK_STILL, Textures.Fluid.MILK_FLOW).setDensity(900)
                .setUnlocalizedName(Textures.RESOURCE_PREFIX + Names.Fluid.MILK).setLuminosity(1);

        food = registerFluid(Names.Fluid.FOOD, Textures.Fluid.FOOD_STILL, Textures.Fluid.FOOD_FLOW).setDensity(2000).setViscosity(2000)
                .setUnlocalizedName(Textures.RESOURCE_PREFIX + Names.Fluid.FOOD).setLuminosity(1);

    }

    private static Fluid registerFluid(String fluidName, ResourceLocation still, ResourceLocation flow) {
        Fluid fluid = new Fluid(fluidName, still, flow);
        if (!FluidRegistry.registerFluid(fluid)) {
            Log.error("Error registering " + fluidName + " fluid!");
        }
        fluids.put(fluidName, fluid);
        return fluid;
    }

}
