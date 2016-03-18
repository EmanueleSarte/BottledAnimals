package com.ermans.bottledanimals.network.message;

import com.ermans.bottledanimals.block.machine.TileFluidTank;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MessageFluid implements IMessage {
    private int x;
    private int y;
    private int z;
    private int liquidAmount;
    private String fluidName;

    public MessageFluid() {
    }

    public MessageFluid(TileFluidTank tile, String fluidName) {
        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.liquidAmount = tile.tank.getFluidAmount();
        this.fluidName = fluidName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.liquidAmount = buf.readInt();
        this.fluidName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.liquidAmount);
        ByteBufUtils.writeUTF8String(buf, this.fluidName);
    }

    public static class Handler implements IMessageHandler<MessageFluid, IMessage> {
        @Override
        public IMessage onMessage(MessageFluid message, MessageContext ctx) {
            TileEntity entity = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(message.x, message.y, message.z);
            if ((entity instanceof TileFluidTank)) {
                TileFluidTank tileFluidTank = (TileFluidTank) entity;
                if (tileFluidTank.tank.getFluid() != null) {
                    if (tileFluidTank.tank.getFluidAmount() != message.liquidAmount) {
                        tileFluidTank.tank.setFluid(new FluidStack(tileFluidTank.tank.getFluid(), message.liquidAmount));
                    }
                }else{
                    tileFluidTank.tank.setFluid(FluidRegistry.getFluidStack(message.fluidName, message.liquidAmount));
                }

            }
            return null;
        }
    }
}
