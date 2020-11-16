package club.varial.prefix.manager;

import club.varial.prefix.Main;
import club.varial.prefix.objet.Prefix;
import club.varial.prefix.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixManager {

    public static PrefixManager INSTANCE;

    private File prefixConfigFile;
    private FileConfiguration prefixConfig;

    private List<Prefix> prefixesLoaded;
    private Map<String, Prefix> prefixMap;

    public String guiName;
    private String shopLink;
    private String available;
    private String unavailable;
    public String activated;
    public String noPermission;
    public String desactivated;
    public String guiDesactivator;

    public PrefixManager() {

        this.INSTANCE = this;

        createCustomConfig();

        this.prefixesLoaded = new ArrayList<>();
        this.prefixMap = new HashMap<>();

        Bukkit.getConsoleSender().sendMessage("§7Downloading prefixes from config...");

        for (String prefixes : getPrefixConfig().getConfigurationSection("Prefix-List").getKeys(false)) {
            String name = prefixes;
            String display = getPrefixConfig().getString("Prefix-List." + prefixes + ".Display");
            Material material = Material.getMaterial(getPrefixConfig().getString("Prefix-List." + prefixes + ".Material"));
            prefixesLoaded.add(new Prefix(name, display, material));
            this.prefixMap.put(name, new Prefix(name, display, material));
        }

        Bukkit.getConsoleSender().sendMessage("§7Loaded prefixes: ");

        for (Prefix prefix : prefixesLoaded) {
            Bukkit.getServer().getConsoleSender().sendMessage("§7-> §a" + prefix.getName());
        }

        this.guiName = Main.INSTANCE.getConfig().getString("gui-name").replace("&", "§");
        this.shopLink = Main.INSTANCE.getConfig().getString("shop-link");
        this.available = Main.INSTANCE.getConfig().getString("available");
        this.unavailable = Main.INSTANCE.getConfig().getString("unavailable");
        this.activated = Main.INSTANCE.getConfig().getString("activated-prefix");
        this.noPermission = Main.INSTANCE.getConfig().getString("no-permission");
        this.desactivated = Main.INSTANCE.getConfig().getString("desactivated-prefix");
        this.guiDesactivator = Main.INSTANCE.getConfig().getString("gui-desactivator");

    }

    public Prefix getPrefix(String name) {
        return this.prefixMap.get(name);
    }

    public void openGui(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 54, this.guiName);

        for (int i = 0; i < prefixesLoaded.size(); i++) {

            ItemStack itemStack = new ItemStack(prefixesLoaded.get(i).getGuiMaterial(), 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§7» §f" + prefixesLoaded.get(i).getName());
            List<String> lore = new ArrayList<>();
            lore.add("§c  ");
            lore.add("§7Display: " + prefixesLoaded.get(i).getDisplay().replace("&", "§"));
            lore.add("§c  ");
            if (player.hasPermission("vprefix.use." + prefixesLoaded.get(i).getName())) {
                lore.add(this.available.replace("&", "§"));
                lore.add("§c  ");
            } else {
                lore.add(this.unavailable.replace("&", "§"));
                lore.add(this.shopLink.replace("&", "§"));
                lore.add("§c  ");
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            net.minecraft.server.v1_8_R3.ItemStack itemData = CraftItemStack.asNMSCopy(itemStack);
            NBTTagCompound comp = itemData.getTag();

            if (comp == null)
                comp = new NBTTagCompound();
            comp.setString("prefix", prefixesLoaded.get(i).getName());

            itemData.setTag(comp);
            itemStack = CraftItemStack.asBukkitCopy(itemData);

            inventory.setItem(i, itemStack);

        }

        inventory.setItem(49,
                new ItemBuilder(Material.BARRIER, 1, (short) 0,
                        this.guiDesactivator.replace("&", "§"), "").create());

        player.openInventory(inventory);

    }

    public FileConfiguration getPrefixConfig() {
        return this.prefixConfig;
    }

    private void createCustomConfig() {
        prefixConfigFile = new File(Main.INSTANCE.getDataFolder(), "prefix.yml");
        if (!prefixConfigFile.exists()) {
            prefixConfigFile.getParentFile().mkdirs();
            Main.INSTANCE.saveResource("prefix.yml", false);
        }
        prefixConfig = new YamlConfiguration();
        try {
            prefixConfig.load(prefixConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
