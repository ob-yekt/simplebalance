package com.github.ob_yekt.simplebalance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.equipment.EquipmentType;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {
    private static final Map<String, Map<String, Integer>> armorValues = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "armor_stats.json";
    private static final Type CONFIG_TYPE = new TypeToken<Map<String, Map<String, Integer>>>() {}.getType();

    static {
        try {
            Path configPath = getJarDirectory().resolve(FILE_NAME);

            if (Files.notExists(configPath)) {
                // Create default config
                createDefaultConfig(configPath);
            }

            try (Reader reader = Files.newBufferedReader(configPath)) {
                armorValues.putAll(GSON.fromJson(reader, CONFIG_TYPE));
            }


        } catch (Exception e) {
            System.err.println("Failed to load armor config: " + e);
        }
    }

    public static int getDefense(String materialName, EquipmentType type) {
        return armorValues
                .getOrDefault(materialName.toLowerCase(), Map.of())
                .getOrDefault(type.getName(), -1);
    }

    private static void createDefaultConfig(Path path) throws IOException {
        Map<String, Map<String, Integer>> defaultConfig = Map.of(
                "leather", Map.of("helmet", 1, "chestplate", 2, "leggings", 2, "boots", 1),
                "chainmail", Map.of("helmet", 1, "chestplate", 3, "leggings", 3, "boots", 1),
                "gold", Map.of("helmet", 2, "chestplate", 3, "leggings", 3, "boots", 2),
                "iron", Map.of("helmet", 3, "chestplate", 4, "leggings", 4, "boots", 3),
                "diamond", Map.of("helmet", 4, "chestplate", 5, "leggings", 5, "boots", 4),
                "netherite", Map.of("helmet", 4, "chestplate", 6, "leggings", 6, "boots", 4),
                "turtle", Map.of("helmet", 3)
        );
        Files.createDirectories(path.getParent());
        try (Writer writer = Files.newBufferedWriter(path)) {
            GSON.toJson(defaultConfig, writer);
        }
    }

    private static Path getJarDirectory() throws URISyntaxException {
        Path path = Paths.get(ConfigLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        if (Files.isRegularFile(path)) {
            return path.getParent(); // Running from JAR
        }
        return Path.of(".").toAbsolutePath(); // Fallback to current directory in dev
    }
}
