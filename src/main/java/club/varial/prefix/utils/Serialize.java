package club.varial.prefix.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

public class Serialize {
    private Gson gson = (new GsonBuilder())
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
            .create();

    public String serialize(Object object) {
        return this.gson.toJson(object);
    }

    public <T> Object deserialize(String json, Class<T> clazz) {
        return this.gson.fromJson(json, clazz);
    }

    public <T> Object deserialize(String json, Type type) {
        return this.gson.fromJson(json, type);
    }

    public static String itemTo64(ItemStack stack) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(stack);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stack.", e);
        }
    }

    public static ItemStack itemFrom64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            try {
                return (ItemStack) dataInput.readObject();
            } finally {
                dataInput.close();
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}
