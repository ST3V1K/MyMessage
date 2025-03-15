package me.kryz.mymessage.nms.v1_20_R3;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import me.kryz.mymessage.MyMessage;
import me.kryz.mymessage.common.packet.command.CommandBrigadierAdaptation;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandImpl implements CommandBrigadierAdaptation {
    @Override
    @EventHandler
    public void onLoad(ServerLoadEvent event) {
        final var server = (MinecraftServer) ((CraftServer) Bukkit.getServer()).getServer();
        final var dispatcher = server.vanillaCommandDispatcher;
        final CommandDispatcher<CommandListenerWrapper> source = dispatcher.a();
        register(source);
    }

    private void register(CommandDispatcher<CommandListenerWrapper> dispatcher) {
        final var command = literal("mymessage")
                .then(
                        literal("reload")
                                .executes(this::executeReload)
                )
                .then(
                        literal("help")
                                .executes(this::executeHelp)
                )
                .executes(this::executeHelp);

        final var registeredCommand = dispatcher.register(command);

        for(final String alias : aliases) {
            dispatcher.register(literal(alias).redirect(registeredCommand).executes(this::executeHelp));
        }
    }

    private int executeHelp(CommandContext<CommandListenerWrapper> context) {
        MyMessage message = JavaPlugin.getPlugin(MyMessage.class);
        String help = message.getConfig().getString("help");
        (context.getSource()).a(() -> ComponentSerializer.asLegacy(help), false);
        return Command.SINGLE_SUCCESS;
    }

    private int executeReload(CommandContext<CommandListenerWrapper> context) {
        final MyMessage message = JavaPlugin.getPlugin(MyMessage.class);
        if(context.getSource().getBukkitSender().hasPermission("mymessage.reload")){
            message.loadConfig();
            final String msg = message.getConfig().getString("reload", "<green>Plugin reloaded");
            context.getSource().a(() -> ComponentSerializer.asLegacy(msg), false);
        }
        else {
            final String msg = message.getConfig().getString("no-permission", "<red>You don't have permission for this command!");
            context.getSource().a(() -> ComponentSerializer.asLegacy(msg), false);
        }
        return Command.SINGLE_SUCCESS;
    }

    private <E> RequiredArgumentBuilder<CommandListenerWrapper, E> argument(final String name, final ArgumentType<E> type){
        return RequiredArgumentBuilder.argument(name, type);
    }
    private LiteralArgumentBuilder<CommandListenerWrapper> literal(final String literal){
        return LiteralArgumentBuilder.literal(literal);
    }
}
