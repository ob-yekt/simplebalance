package com.github.ob_yekt.simplebalance;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.ArmorMaterials;

import java.util.HashMap;
import java.util.Map;

public class ArmorMaterialIdResolver {
    private static final Map<ArmorMaterial, String> ID_MAP = new HashMap<>();

    static {
        ID_MAP.put(ArmorMaterials.LEATHER, "leather");
        ID_MAP.put(ArmorMaterials.CHAIN, "chainmail");
        ID_MAP.put(ArmorMaterials.GOLD, "gold");
        ID_MAP.put(ArmorMaterials.IRON, "iron");
        ID_MAP.put(ArmorMaterials.DIAMOND, "diamond");
        ID_MAP.put(ArmorMaterials.NETHERITE, "netherite");
        ID_MAP.put(ArmorMaterials.TURTLE_SCUTE, "turtle");
    }

    public static String getId(ArmorMaterial material) {
        return ID_MAP.getOrDefault(material, "unknown");
    }
}
