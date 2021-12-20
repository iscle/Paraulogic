package me.iscle.paraulgic;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iscle.paraulgic.model.Solucions;

public class SolucionsAdapter implements JsonDeserializer<Solucions> {
    @Override
    public Solucions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        List<String> lletres = new ArrayList<>();
        for (JsonElement lletra : object.get("lletres").getAsJsonArray()) {
            lletres.add(lletra.getAsString());
        }

        Map<String, String> paraules = new HashMap<>();
        for (Map.Entry<String, JsonElement> paraula : object.get("paraules").getAsJsonObject().entrySet()) {
            paraules.put(paraula.getKey(), paraula.getValue().getAsString());
        }

        int min = object.get("min").getAsInt();

        return new Solucions(lletres, paraules, min);
    }
}
