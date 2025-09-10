package me.kryz.mymessage.nms.v1_21_R5;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.kryz.mymessage.common.packet.command.CommandBrigadierAdaptation;
import me.kryz.mymessage.MyMessage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.animal.sheep.Sheep;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandImpl implements CommandBrigadierAdaptation {
    @Override
    @EventHandler
    public void onLoad(ServerLoadEvent event) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        CommandDispatcher<CommandSourceStack> dispatcher = server.getCommands().getDispatcher();
        register(dispatcher);
    }

    private void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        final var command = literal("mymessage")
                .then(
                        literal("reload")
                                .requires(source -> source.getSender().hasPermission("mymessage.reload"))
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
    private int executeHelp(CommandContext<CommandSourceStack> context) {
        final MyMessage message = JavaPlugin.getPlugin(MyMessage.class);
        final String help = message.getConfig().getString("help");
        context.getSource().sendSuccess(() -> ComponentSerializer.asLegacy(help), false);
        return Command.SINGLE_SUCCESS;
    }

    private int executeReload(CommandContext<CommandSourceStack> context) {
        final MyMessage message = JavaPlugin.getPlugin(MyMessage.class);
        if(context.getSource().getSender().hasPermission("mymessage.reload")){
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
