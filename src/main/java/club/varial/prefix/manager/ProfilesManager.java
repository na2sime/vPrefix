package club.varial.prefix.manager;

import club.varial.prefix.Main;
import club.varial.prefix.objet.PlayerProfile;
import club.varial.prefix.utils.FileUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProfilesManager {

    public static ProfilesManager INSTANCE;
    private Map<UUID, PlayerProfile> profiles;
    private List<PlayerProfile> profilesList;

    public ProfilesManager() {

        this.INSTANCE = this;
        this.profiles = new HashMap<>();

    }

    public void loadProfile(Player player) {
        String json = FileUtils.loadFile(new File(Main.INSTANCE.getDataFolder() + "/players", player.getName() + ".json"));
        PlayerProfile profile = (PlayerProfile) (Main.INSTANCE).serialize.deserialize(json, PlayerProfile.class);
        if (profile == null) {
            profile = new PlayerProfile(player.getUniqueId(), null);
        }
        this.profiles.put(player.getUniqueId(), profile);
    }

    public void saveProfile(Player player) {
        PlayerProfile profile = getProfile(player);
        String json = (Main.INSTANCE).serialize.serialize(profile);
        FileUtils.saveFile(new File(Main.INSTANCE.getDataFolder() + "/players", player.getName() + ".json"), json);
        this.profiles.remove(profile);
    }

    public PlayerProfile getProfile(Player player) {
        return this.profiles.get(player.getUniqueId());
    }

}
