package com.ermans.api;

public interface IEnergyInfoProvider extends IEnergyInfoBA{

    //Returns the percentage of fuel left
    int getInfoFuelPercentage();

    int getEnergyDrainPerTick();


}
