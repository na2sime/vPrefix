package club.varial.prefix.listeners;

import club.varial.prefix.manager.PrefixManager;
import club.varial.prefix.manager.ProfilesManager;
import club.varial.prefix.objet.Prefix;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryInteract implements Listener {

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle() == PrefixManager.INSTANCE.guiName.replace("&", "§")) {
            ItemStack item = event.getCurrentItem();

            event.setCancelled(true);

            if (item != null) {

                net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);

                if (itemStack.getTag().hasKey("prefix")) {

                    Prefix prefix = PrefixManager.INSTANCE.getPrefix(itemStack.getTag().getString("prefix"));

                    if (player.hasPermission("vprefix.use." + prefix.getName())) {
                        ProfilesManager.INSTANCE.getProfile(player).setPrefix(prefix);
                        player.sendMessage(PrefixManager.INSTANCE.activated.
                                replace("&", "§").replace("%prefix%", prefix.getName()));
                    } else {
                        player.sendMessage("§cVous n'avez pas la permissions !");
                    }
                }
            }
        }
    }

}
