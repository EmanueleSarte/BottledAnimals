package com.ermans.bottledanimals.init;

import com.ermans.bottledanimals.reference.Names;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
    public static Fluid milk;
    public static Fluid food;


    public static void init() {


        Fluid f = new Fluid(Names.Fluid.MILK).setDensity(900).setViscosity(1500).setUnlocalizedName(Reference.MOD_ID_LOWERCASE + "." + Names.Fluid.MILK);
        FluidRegistry.registerFluid(f);
        milk = FluidRegistry.getFluid(f.getName());


        f = new Fluid(Names.Fluid.FOOD).setDensity(2000).setViscosity(3000).setUnlocalizedName(Reference.MOD_ID_LOWERCASE + "." + Names.Fluid.FOOD);
        FluidRegistry.registerFluid(f);
        food = FluidRegistry.getFluid(f.getName());
    }
}
