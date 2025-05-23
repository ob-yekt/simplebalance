package com.github.ob_yekt.simplebalance;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class LoreManager {
    private static final String LORE_PREFIX = "simplebalance: ";

    public static void applyArmorLore(ItemStack stack) {
        if (!isArmorItem(stack)) return;

        EquipmentType equipmentType = getEquipmentType(stack);
        if (equipmentType == null) return;

        String materialId = getMaterialIdFromItem(stack);
        int defense = ConfigLoader.getDefense(materialId, equipmentType);

        // Only add lore if we have a custom defense value
        if (defense == -1) return;

        // Remove existing SimpleBalance lore if present
        removeExistingLore(stack);

        // Create the lore text
        MutableText loreText = Text.literal(LORE_PREFIX)
                .setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(false))
                .append(Text.literal("+" + defense + " Armor")
                        .setStyle(Style.EMPTY.withColor(Formatting.BLUE).withItalic(false)));

        // Get existing lore or create new list
        LoreComponent existingLore = stack.get(DataComponentTypes.LORE);
        List<Text> loreLines = new ArrayList<>();

        if (existingLore != null) {
            loreLines.addAll(existingLore.lines());
        }

        // Add our lore at the beginning
        loreLines.add(0, loreText);

        // Apply the lore component
        stack.set(DataComponentTypes.LORE, new LoreComponent(loreLines));
    }

    private static void removeExistingLore(ItemStack stack) {
        LoreComponent existingLore = stack.get(DataComponentTypes.LORE);
        if (existingLore == null) return;

        List<Text> filteredLines = existingLore.lines().stream()
                .filter(line -> !line.getString().startsWith(LORE_PREFIX))
                .toList();

        if (filteredLines.isEmpty()) {
            stack.remove(DataComponentTypes.LORE);
        } else {
            stack.set(DataComponentTypes.LORE, new LoreComponent(filteredLines));
        }
    }

    private static boolean isArmorItem(ItemStack stack) {
        return getEquipmentType(stack) != null;
    }

    private static EquipmentType getEquipmentType(ItemStack stack) {
        String itemName = stack.getItem().toString().toLowerCase();

        if (itemName.contains("helmet")) return EquipmentType.HELMET;
        if (itemName.contains("chestplate")) return EquipmentType.CHESTPLATE;
        if (itemName.contains("leggings")) return EquipmentType.LEGGINGS;
        if (itemName.contains("boots")) return EquipmentType.BOOTS;

        return null;
    }

    private static String getMaterialIdFromItem(ItemStack stack) {
        String itemName = stack.getItem().toString().toLowerCase();

        if (itemName.contains("leather")) return "leather";
        if (itemName.contains("chainmail") || itemName.contains("chain")) return "chainmail";
        if (itemName.contains("golden") || itemName.contains("gold")) return "gold";
        if (itemName.contains("iron")) return "iron";
        if (itemName.contains("diamond")) return "diamond";
        if (itemName.contains("netherite")) return "netherite";
        if (itemName.contains("turtle")) return "turtle";

        return "unknown";
    }
}