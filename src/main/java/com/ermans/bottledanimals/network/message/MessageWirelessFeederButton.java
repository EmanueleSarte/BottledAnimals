package com.ermans.bottledanimals.network.message;


import com.ermans.bottledanimals.block.machine.wirelessfeeder.TileWirelessFeeder;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class MessageWirelessFeederButton implements IMessage {
    private int x;
    private int y;
    private int z;
    private byte buttonClicked;

    public MessageWirelessFeederButton() {
    }

    public MessageWirelessFeederButton(TileWirelessFeeder entity, int buttonClicked) {
        this.x = entity.xCoord;
        this.y = entity.yCoord;
        this.z = entity.zCoord;
        this.buttonClicked = (byte) buttonClicked;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.buttonClicked = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeByte(this.buttonClicked);
    }

    public static class Handler implements IMessageHandler<MessageWirelessFeederButton, IMessage> {
        @Override
        public IMessage onMessage(MessageWirelessFeederButton message, MessageContext ctx) {
            TileEntity entity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
            if (entity == null || !(entity instanceof TileWirelessFeeder)) {
                return null;
            }
            ((TileWirelessFeeder) entity).setMode(TileWirelessFeeder.Mode.values()[message.buttonClicked]);

            return null;
        }
    }
}
