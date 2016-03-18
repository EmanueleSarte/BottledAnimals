package com.ermans.bottledanimals.network.message;

import com.ermans.bottledanimals.block.machine.TilePowered;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class MessageEnergy implements IMessage {
    private int x;
    private int y;
    private int z;
    private int energy;

    public MessageEnergy() {
    }

    public MessageEnergy(int x, int y, int z, int energy) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.energy = energy;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.energy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.energy);
    }

    public static class Handler implements IMessageHandler<MessageEnergy, IMessage> {
        @Override
        public IMessage onMessage(MessageEnergy message, MessageContext ctx) {
            TileEntity entity = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(message.x, message.y, message.z);
            if ((entity instanceof TilePowered)) {
                ((TilePowered) entity).setEnergyStored(message.energy);
            }
            return null;
        }
    }
}
