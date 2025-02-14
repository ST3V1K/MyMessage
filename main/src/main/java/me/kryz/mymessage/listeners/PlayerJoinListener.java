package me.kryz.mymessage.listeners;

import me.kryz.mymessage.MyMessage;
import me.kryz.mymessage.loader.NMSLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener {

    private final MyMessage myMessage;

    public PlayerJoinListener(MyMessage myMessage) {
        this.myMessage = myMessage;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event){
        myMessage.getLoader().getPacketHandler().interceptPackets(event.getPlayer());
    }
}
