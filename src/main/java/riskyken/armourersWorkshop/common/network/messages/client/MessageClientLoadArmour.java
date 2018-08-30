package riskyken.armourersWorkshop.common.network.messages.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import riskyken.armourersWorkshop.common.inventory.ContainerArmourer;
import riskyken.armourersWorkshop.common.tileentities.TileEntityArmourer;

public class MessageClientLoadArmour implements IMessage, IMessageHandler<MessageClientLoadArmour, IMessage> {

    String name;
    String tags;
    
    public MessageClientLoadArmour() {}
    
    public MessageClientLoadArmour(String name, String tags) {
        this.name = name;
        this.tags = tags;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
        tags = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        ByteBufUtils.writeUTF8String(buf, tags);
    }
    
    @Override
    public IMessage onMessage(MessageClientLoadArmour message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        if (player == null) { return null; }
        Container container = player.openContainer;

        if (container != null && container instanceof ContainerArmourer) {
            TileEntityArmourer armourerBrain = ((ContainerArmourer) container).getTileEntity();
            
            armourerBrain.saveArmourItem(player, message.name, message.tags);
            
        }
        return null;
    }
}
