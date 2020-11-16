package club.varial.prefix;

import club.varial.prefix.commands.PrefixCommand;
import club.varial.prefix.listeners.InventoryInteract;
import club.varial.prefix.listeners.PlayerListeners;
import club.varial.prefix.manager.PrefixManager;
import club.varial.prefix.manager.ProfilesManager;
import club.varial.prefix.utils.FileUtils;
import club.varial.prefix.utils.Serialize;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main INSTANCE;

    public FileUtils fileUtils;
    public Serialize serialize;

    private static Chat chat = null;

    @Override
    public void onEnable() {

        INSTANCE = this;

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            getLogger().info("Vault detected.");
            setupChat();
        } else {
            getLogger().severe("Please install Vault. Ultimate Chat Format is disabling...");
            getServer().getPluginManager().disablePlugin((Plugin) this);
        }

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

    public static Chat getChat() {
        return chat;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = (Chat) rsp.getProvider();
        return (chat != null);
    }

}
