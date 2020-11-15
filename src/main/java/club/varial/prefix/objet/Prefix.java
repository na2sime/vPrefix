package club.varial.prefix.objet;

import org.bukkit.Material;

public class Prefix {

    private String name;
    private String display;
    private Material guiMaterial;

    public Prefix(String name, String display, Material guiMaterial) {
        this.name = name;
        this.display = display;
        this.guiMaterial = guiMaterial;
    }

    public String getName() {
        return name;
    }

    public String getDisplay() {
        return display;
    }

    public Material getGuiMaterial() {
        return guiMaterial;
    }
}
