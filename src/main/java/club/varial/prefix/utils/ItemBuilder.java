package club.varial.prefix.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private Material material;
    private int amount;
    private short id;
    private String name;
    private List<String> lore;

    public ItemBuilder(Material material, int amount, short id, String name, String... lore) {
        this.material = material;
        this.amount = amount;
        this.id = id;
        this.name = name;
        this.lore = new ArrayList<String>();
        for (String l : lore) {
            this.lore.add(l);
        }
    }

    public ItemStack create() {
        ItemStack item = new ItemStack(this.material, this.amount, this.id);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(this.lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack createInSkull(String playerName) {
        ItemStack item = new ItemStack(this.material, this.amount, this.id);
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        itemMeta.setOwner(playerName);
        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(this.lore);
        item.setItemMeta(itemMeta);
        return item;
    }

}
