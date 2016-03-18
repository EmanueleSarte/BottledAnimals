package com.ermans.bottledanimals.network.message;


import cofh.api.tileentity.IRedstoneControl;
import com.ermans.bottledanimals.block.TileBottledAnimals;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class MessageRedstoneButton implements IMessage{
    private int x;
    private int y;
    private int z;
    private byte state;

    public MessageRedstoneButton() {
    }

    public MessageRedstoneButton(TileBottledAnimals entity) {
        this.x = entity.xCoord;
        this.y = entity.yCoord;
        this.z = entity.zCoord;
        this.state = (byte) entity.getControl().ordinal();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.state = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeByte(this.state);
    }

    public static class Handler implements IMessageHandler<MessageRedstoneButton, IMessage> {
        @Override
        public IMessage onMessage(MessageRedstoneButton message, MessageContext ctx) {
            TileEntity entity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
            if (entity == null || !(entity instanceof TileBottledAnimals)) {
                return null;
            }
            ((TileBottledAnimals) entity).setControl(IRedstoneControl.ControlMode.values()[message.state]);
            return null;
        }
    }
}
