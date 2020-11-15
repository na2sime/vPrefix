package club.varial.prefix;

import club.varial.prefix.commands.PrefixCommand;
import club.varial.prefix.listeners.InventoryInteract;
import club.varial.prefix.listeners.PlayerListeners;
import club.varial.prefix.manager.PrefixManager;
import club.varial.prefix.manager.ProfilesManager;
import club.varial.prefix.utils.FileUtils;
import club.varial.prefix.utils.Serialize;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main INSTANCE;

    public FileUtils fileUtils;
    public Serialize serialize;

    @Override
    public void onEnable() {

        INSTANCE = this;

        this.saveDefaultConfig();

        loadClasses();
        loadListeners();
        loadCommands();


        super.onEnable();
    }

    private void loadClasses() {
        this.fileUtils = new FileUtils();
        this.serialize = new Serialize();
        new ProfilesManager();
        new PrefixManager();
    }

    private void loadListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new InventoryInteract(), this);
        pluginManager.registerEvents(new PlayerListeners(), this);
    }

    private void loadCommands() {
        getCommand("prefix").setExecutor(new PrefixCommand());
    }

}
