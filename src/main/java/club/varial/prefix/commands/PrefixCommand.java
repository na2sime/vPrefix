package club.varial.prefix.commands;

import club.varial.prefix.Main;
import club.varial.prefix.manager.PrefixManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class PrefixCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginDescriptionFile pdf = Main.INSTANCE.getDescription();
        if (sender instanceof Player) {
            if (args.length == 0) {
                PrefixManager.INSTANCE.openGui((Player) sender);
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("version")) {
                    sender.sendMessage("§cvPrefix by §f" + pdf.getAuthors());
                    sender.sendMessage("§cRunning Version: §f" + pdf.getVersion());
                    sender.sendMessage("§cDiscord: §fhttps://discord.gg/QvV3mVtfPX");
                } else {
                    sender.sendMessage("§cUsage: /prefix [version]");
                }
            } else {
                sender.sendMessage("§cUsage: /prefix [version]");
            }
        } else {
            sender.sendMessage("§cvPrefix by §f" + pdf.getAuthors());
            sender.sendMessage("§cRunning Version: §f" + pdf.getVersion());
            sender.sendMessage("§cDiscord: §fhttps://discord.gg/QvV3mVtfPX");
        }
        return false;
    }

}
