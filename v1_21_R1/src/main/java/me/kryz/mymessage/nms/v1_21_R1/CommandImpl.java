package me.kryz.mymessage.nms.v1_21_R1;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import me.kryz.mymessage.MyMessage;
import me.kryz.mymessage.common.packet.command.CommandBrigadierAdaptation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandImpl implements CommandBrigadierAdaptation {
    @Override
    @EventHandler
    public void onLoad(ServerLoadEvent event) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        CommandDispatcher<CommandSourceStack> dispatcher = server.getCommands().getDispatcher();
        register(dispatcher);
    }


    private void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal("mymessage")
                        .executes(this::executeHelp)
                        .then(
                                literal("reload")
                                        .executes(this::executeReload)
                        )
                        .then(
                                literal("help")
                                        .executes(this::executeHelp)
                        )
        );
    }

    private int executeHelp(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> net.minecraft.network.chat.Component.literal("¡Has ejecutado el comando!"), false);
        return Command.SINGLE_SUCCESS;
    }

    private int executeReload(CommandContext<CommandSourceStack> context) {
        final MyMessage message = JavaPlugin.getPlugin(MyMessage.class);
        if(context.getSource().getBukkitEntity().hasPermission("mymessage.reload")){
            message.loadConfig();
            final String msg = message.getConfig().getString("reload", "<green>Plugin reloaded");
            context.getSource().sendSuccess(() -> ComponentSerializer.asLegacy(msg), false);
        }
        else {
            final String msg = message.getConfig().getString("no-permission", "<red>You don't have permission for this command!");
            context.getSource().sendSuccess(() -> ComponentSerializer.asLegacy(msg), false);
        }
        return Command.SINGLE_SUCCESS;
    }

    private <E> RequiredArgumentBuilder<CommandSourceStack, E> argument(final String name, final ArgumentType<E> type){
        return RequiredArgumentBuilder.argument(name, type);
    }
    private LiteralArgumentBuilder<CommandSourceStack> literal(final String literal){
        return LiteralArgumentBuilder.literal(literal);
    }
}
