package com.ermans.api;


import cofh.api.energy.IEnergyStorage;

public interface IEnergyBA {

    //Called when a MessageEnergy is received by the client
    void setEnergyStored(int amount);

    IEnergyStorage getEnergyStorage();
}
