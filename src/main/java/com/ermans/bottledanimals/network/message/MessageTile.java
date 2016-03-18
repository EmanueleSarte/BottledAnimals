package com.ermans.bottledanimals.network.message;

import com.ermans.bottledanimals.block.TileBottledAnimals;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class MessageTile implements IMessage{

    private TileBottledAnimals tile;
    private int x;
    private int y;
    private int z;
    private boolean updateTexture;

    public MessageTile() {
    }

    public MessageTile(TileBottledAnimals tile, boolean updateTexture) {
        this.tile = tile;
        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.updateTexture = updateTexture;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.updateTexture = buf.readBoolean();
        TileEntity entity = Minecraft.getMinecraft().thePlayer.worldObj.getTileEntity(this.x, this.y, this.z);
        if (entity instanceof TileBottledAnimals){
            ((TileBottledAnimals) entity).fromBytes(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeBoolean(this.updateTexture);
        tile.toBytes(buf);
    }

    public static class Handler implements IMessageHandler<MessageTile, IMessage> {
        @Override
        public IMessage onMessage(MessageTile message, MessageContext ctx) {
            if (message.updateTexture){
                Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(message.x, message.y, message.z);
            }
            return null;
        }
    }
}
