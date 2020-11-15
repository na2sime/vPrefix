package club.varial.prefix.listeners;

import club.varial.prefix.manager.ProfilesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ProfilesManager.INSTANCE.loadProfile(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ProfilesManager.INSTANCE.saveProfile(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String prefix = ProfilesManager.INSTANCE.getProfile(event.getPlayer()).getPrefix().getDisplay();
        event.setFormat(prefix.replace("&", "§") + " §r" + event.getPlayer().getName() + " : " + event.getMessage());
    }

}
