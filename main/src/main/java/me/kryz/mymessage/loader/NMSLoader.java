package me.kryz.mymessage.loader;

import lombok.Getter;
import me.kryz.mymessage.MyMessage;
import me.kryz.mymessage.common.packet.PacketHandler;
import me.kryz.mymessage.common.packet.PacketRegister;
import me.kryz.mymessage.common.packet.command.CommandBrigadierAdaptation;
import me.kryz.mymessage.logger.MyLogger;
import me.kryz.mymessage.utils.ReflectionUtils;
import me.kryz.mymessage.utils.VersionUtil;
import me.kryz.mymessage.version.MinecraftVersion;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;

@Getter
public final class NMSLoader {
    private PacketHandler packetHandler;
    private PacketRegister packetRegister;
    private CommandBrigadierAdaptation adaptation;
    private final MyMessage myMessage;

    public NMSLoader(MyMessage myMessage) {
        this.myMessage = myMessage;
    }

    public void load(){
        final var log = "Enabling NMS packets for {}";
        final var version = MinecraftVersion.getFormatted();
        if(VersionUtil.isLessThanOrEqualTo("1.20.4")) {
            try {
                MyLogger.getLogger().info(log, version);
                packetHandler = (PacketHandler) ReflectionUtils.getClass(searchClass(version, "PacketHandler"))
                        .getConstructor(Logger.class)
                        .newInstance(MyLogger.getLogger());
                packetRegister = (PacketRegister) ReflectionUtils.getClass(searchClass(version, "PacketRegister"))
                        .getConstructor()
                        .newInstance();
                adaptation = (CommandBrigadierAdaptation) ReflectionUtils.getClass(searchClass(version, "CommandImpl"))
                        .getConstructor()
                        .newInstance();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                packetHandler = (PacketHandler) ReflectionUtils.getClass(searchClass("remapped", "PacketHandler"))
                        .getConstructor(Logger.class)
                        .newInstance(MyLogger.getLogger());
                packetRegister = (PacketRegister) ReflectionUtils.getClass(searchClass("remapped", "PacketRegister"))
                        .getConstructor()
                        .newInstance();
                adaptation = (CommandBrigadierAdaptation) ReflectionUtils.getClass(searchClass("remapped", "CommandImpl"))
                        .getConstructor()
                        .newInstance();
            }
            catch (final NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException ignored){
            }
        }
    }

    public String searchClass(final String ver, final String clazz) {
        return "me.kryz.mymessage.nms."+ver+"."+clazz;
    }
}
