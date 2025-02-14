package me.kryz.mymessage.common.audience;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;

public final class BukkitAudiencesImpl {

    private BukkitAudiences audiences;

    public BukkitAudiencesImpl(final Plugin plugin){
        this.audiences = BukkitAudiences.create(plugin);
        new MyAdventureUtils(this.audiences);
    }

    public void closeAudiences() {
        if (this.audiences != null) {
            this.audiences.close();
            this.audiences = null;
        }
    }
}
