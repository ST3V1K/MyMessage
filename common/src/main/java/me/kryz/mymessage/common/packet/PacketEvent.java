package me.kryz.mymessage.common.packet;

public final class PacketEvent<T> {
    private T packet;
    private boolean cancelled;

    public PacketEvent(T packet) {
        this.packet = packet;
    }

    public T getPacket() {
        return this.packet;
    }

    public void setPacket(T packet) {
        this.packet = packet;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
