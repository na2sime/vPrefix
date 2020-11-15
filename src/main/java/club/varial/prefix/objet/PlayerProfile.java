package club.varial.prefix.objet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerProfile {

    private UUID uuid;
    private String name;
    private Prefix prefix;

    public PlayerProfile(UUID uuid, Prefix prefix) {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(this.uuid).getName();
        this.prefix = prefix;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public Prefix getPrefix() {
        return this.prefix;
    }

    public void setPrefix(Prefix prefix) {
        this.prefix = prefix;
    }

}
