package club.varial.prefix.listeners;

import club.varial.prefix.Main;
import club.varial.prefix.manager.ProfilesManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Chat chat = Main.getChat();

        Player player = event.getPlayer();
        World world = player.getWorld();

        String group = chat.getPrimaryGroup(player);
        String prefix = chat.getGroupPrefix(world, group);
        String suffix = chat.getGroupSuffix(world, group);

        if (ProfilesManager.INSTANCE.getProfile(player).getPrefix() != null) {
            String vprefix = ProfilesManager.INSTANCE.getProfile(player).getPrefix().getDisplay();
            event.setFormat(ChatColor.translateAlternateColorCodes('&',
                    vprefix + " " + prefix + event.getPlayer().getName() + suffix + " : &r" + event.getMessage()));
        } else {
            event.setFormat(ChatColor.translateAlternateColorCodes('&',
                    prefix + event.getPlayer().getName() + suffix + " : &r" + event.getMessage()));
        }
    }

}
