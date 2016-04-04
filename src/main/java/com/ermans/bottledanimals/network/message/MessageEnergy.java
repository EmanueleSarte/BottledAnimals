package com.ermans.bottledanimals.network.message;

import com.ermans.api.IEnergyBA;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageEnergy implements IMessage {
    private int x;
    private int y;
    private int z;
    private int energy;

    public MessageEnergy() {
    }

    public MessageEnergy(BlockPos pos, int energy) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
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
            TileEntity entity = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(new BlockPos(message.x, message.y, message.z));
            if (entity instanceof IEnergyBA) {
                ((IEnergyBA) entity).setEnergyStored(message.energy);
            }
            return null;
        }
    }
}
