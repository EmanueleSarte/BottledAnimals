package com.ermans.bottledanimals.block;

import cofh.api.tileentity.IEnergyInfo;

public interface IEnergyInfoReceiver extends IEnergyInfo{

    int getInfoMaxReceiveEnergyPerTick();

    int getInfoTimePercentage();
}
