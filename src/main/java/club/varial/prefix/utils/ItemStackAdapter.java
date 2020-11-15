package club.varial.prefix.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.bukkit.inventory.ItemStack;

public class ItemStackAdapter extends TypeAdapter<ItemStack> {
    public void write(JsonWriter jsonWriter, ItemStack item) throws IOException {
        if (item == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(Serialize.itemTo64(item));
    }

    public ItemStack read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return Serialize.itemFrom64(jsonReader.nextString());
    }
}
