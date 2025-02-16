package me.kryz.mymessage.common.packet.command;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public interface CommandBrigadierAdaptation extends Listener {

    String[] aliases = {"mm", "mymsg"};

    @EventHandler
    void onLoad(ServerLoadEvent event);
}
