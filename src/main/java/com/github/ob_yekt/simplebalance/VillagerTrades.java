package com.github.ob_yekt.simplebalance;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;
import java.util.List;

public class VillagerTrades {
    public static void registerCustomTrades() {

        /// Toolsmith trades
        // Toolsmith - Level 4 (Expert)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 4,
                factories -> {
                    // 1 Diamond → 1 Emerald (12 trades)
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.DIAMOND, 1),
                                    new ItemStack(Items.EMERALD, 1),
                                    12, 30, 0.05F
                            ));
                    // 4 Emerald → 1 Brush (3 trades)
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 4),
                                    new ItemStack(Items.BRUSH, 1),
                                    3, 15, 0.05F
                            ));
                    // 1 Emerald → 2 Honeycomb (12 trades)
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 1),
                                    new ItemStack(Items.HONEYCOMB, 2),
                                    12, 10, 0.05F
                            ));
                });

        // Toolsmith - Level 5 (Master)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 5,
                factories -> {
                    // 24-32 Emerald → 1 Recovery Compass (3 trades) - Fixed max uses
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 24 + random.nextInt(9)), // 24-32 Emerald
                                    new ItemStack(Items.RECOVERY_COMPASS, 1),
                                    3, 30, 0.2F
                            ));
                    // 8 Emerald → 1 Spyglass (3 trades)
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 8),
                                    new ItemStack(Items.SPYGLASS, 1),
                                    3, 15, 0.05F
                            ));
                });

        /// Weaponsmith trades
        // Weaponsmith - Level 4 (Expert)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 4,
                factories -> {
                    // 1 Emerald → 2 Gunpowder (12 trades)
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 1),
                                    new ItemStack(Items.GUNPOWDER, 2),
                                    12, 10, 0.05F
                            ));
                });

        // Weaponsmith - Level 5 (Master)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 5,
                factories -> {
                    // 48-64 Emerald → 1 Trident (3 trades) - Fixed max uses
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 48 + random.nextInt(17)), // 48-64 Emerald
                                    new ItemStack(Items.TRIDENT, 1),
                                    3, 30, 0.2F // Changed from 1 max use to 3, and increased multiplier
                            ));
                    // 4 Emerald → 1 TNT (3 trades) - Fixed max uses
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 4),
                                    new ItemStack(Items.TNT, 1),
                                    3, 20, 0.2F // Changed from 1 max use to 3, and increased multiplier
                            ));
                });

        /// Armorer trades
        // Armorer - Level 1 (Novice)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 1,
                factories -> {
                    // 12 Raw Copper → 1 Emerald (16 trades)
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.RAW_COPPER, 12),
                                    new ItemStack(Items.EMERALD, 1),
                                    16, 2, 0.05F
                            ));
                });

        // Armorer - Level 4 (Expert)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 4,
                factories -> {
                    // 24 Emerald → 1 Anvil (3 trades) - Fixed price from comment
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 24), // Changed from 16 to 24 as per comment
                                    new ItemStack(Items.ANVIL, 1),
                                    3, 15, 0.2F
                            ));
                    // 12 Emerald → 1 Gold Horse Armor (3 trades)
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 12),
                                    new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1),
                                    3, 15, 0.2F
                            ));
                });

        // Armorer - Level 5 (Master)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 5,
                factories -> {
                    // 16-24 Emerald → 1 Wolf Armor (3 trades) - Fixed max uses
                    factories.add((entity, random) ->
                            new TradeOffer(
                                    new TradedItem(Items.EMERALD, 16 + random.nextInt(9)), // 16-24 Emerald
                                    new ItemStack(Items.WOLF_ARMOR, 1),
                                    3, 30, 0.2F
                            ));
                });

        ///  Librarian trades
        // Librarian - Levels 1 to 4 (Novice to Expert)
        for (int level = 1; level <= 4; level++) {
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, level,
                    factories -> {
                        factories.add((entity, random) -> {
                            try {
                                ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
                                int enchantLevel = addRandomApprovedEnchantment(enchantedBook, random, entity);
                                if (enchantLevel > 0) {
                                    // Calculate price: min = 2 + 3 * level, max = 6 + 13 * level, capped at 64
                                    int minPrice = 2 + 3 * enchantLevel;
                                    int maxPrice = Math.min(64, 6 + 13 * enchantLevel);
                                    int emeraldCost = minPrice + (maxPrice > minPrice ? random.nextInt(maxPrice - minPrice + 1) : 0);
                                    return new TradeOffer(
                                            new TradedItem(Items.EMERALD, emeraldCost),
                                            enchantedBook,
                                            12, 5, 0.2F
                                    );
                                }
                            } catch (Exception e) {
                                // Log error or handle gracefully
                            }
                            return null; // Return null if enchantment fails
                        });
                    });
        }
    }

    // Helper method to add approved enchantments with equal probability, returns the selected level
    private static int addRandomApprovedEnchantment(ItemStack book, Random random, net.minecraft.entity.Entity entity) {
        try {
            // List of approved enchantments with their max levels
            List<RegistryKey<Enchantment>> approvedEnchants = new ArrayList<>();
            List<Integer> maxLevels = new ArrayList<>();

            // Protection enchantments
            approvedEnchants.add(net.minecraft.enchantment.Enchantments.PROTECTION);
            maxLevels.add(3); // Max 4, so trade max 3

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.FIRE_PROTECTION);
            maxLevels.add(3); // Max 4, so trade max 3

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.FEATHER_FALLING);
            maxLevels.add(3); // Max 4, so trade max 3

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.BLAST_PROTECTION);
            maxLevels.add(3); // Max 4, so trade max 3

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.PROJECTILE_PROTECTION);
            maxLevels.add(3); // Max 4, so trade max 3

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.RESPIRATION);
            maxLevels.add(2); // Max 3, so trade max 2

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.AQUA_AFFINITY);
            maxLevels.add(1); // Max 1, so trade max 1

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.THORNS);
            maxLevels.add(2); // Max 3, so trade max 2

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.DEPTH_STRIDER);
            maxLevels.add(2); // Max 3, so trade max 2

            // Weapon enchantments
            approvedEnchants.add(net.minecraft.enchantment.Enchantments.SHARPNESS);
            maxLevels.add(4); // Max 5, so trade max 4

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.SMITE);
            maxLevels.add(4); // Max 5, so trade max 4

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.BANE_OF_ARTHROPODS);
            maxLevels.add(4); // Max 5, so trade max 4

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.KNOCKBACK);
            maxLevels.add(1); // Max 2, so trade max 1

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.FIRE_ASPECT);
            maxLevels.add(1); // Max 2, so trade max 1

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.LOOTING);
            maxLevels.add(2); // Max 3, so trade max 2

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.SWEEPING_EDGE);
            maxLevels.add(2); // Max 3, so trade max 2

            // Tool enchantments
            approvedEnchants.add(net.minecraft.enchantment.Enchantments.EFFICIENCY);
            maxLevels.add(4); // Max 5, so trade max 4

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.SILK_TOUCH);
            maxLevels.add(1); // Max 1, so trade max 1

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.UNBREAKING);
            maxLevels.add(2); // Max 3, so trade max 2

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.FORTUNE);
            maxLevels.add(2); // Max 3, so trade max 2

            // Bow enchantments
            approvedEnchants.add(net.minecraft.enchantment.Enchantments.POWER);
            maxLevels.add(4); // Max 5, so trade max 4

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.PUNCH);
            maxLevels.add(1); // Max 2, so trade max 1

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.FLAME);
            maxLevels.add(1); // Max 1, so trade max 1

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.INFINITY);
            maxLevels.add(1); // Max 1, so trade max 1

            // Fishing rod enchantments
            approvedEnchants.add(net.minecraft.enchantment.Enchantments.LUCK_OF_THE_SEA);
            maxLevels.add(2); // Max 3, so trade max 2

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.LURE);
            maxLevels.add(2); // Max 3, so trade max 2

            // Trident enchantments
            approvedEnchants.add(net.minecraft.enchantment.Enchantments.LOYALTY);
            maxLevels.add(2); // Max 3, so trade max 2

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.IMPALING);
            maxLevels.add(4); // Max 5, so trade max 4

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.RIPTIDE);
            maxLevels.add(2); // Max 3, so trade max 2

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.CHANNELING);
            maxLevels.add(1); // Max 1, so trade max 1

            // Crossbow enchantments
            approvedEnchants.add(net.minecraft.enchantment.Enchantments.MULTISHOT);
            maxLevels.add(1); // Max 1, so trade max 1

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.QUICK_CHARGE);
            maxLevels.add(2); // Max 3, so trade max 2

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.PIERCING);
            maxLevels.add(3); // Max 4, so trade max 3

            // Other enchantments
            approvedEnchants.add(net.minecraft.enchantment.Enchantments.BINDING_CURSE);
            maxLevels.add(1); // Max 1, so trade max 1

            approvedEnchants.add(net.minecraft.enchantment.Enchantments.VANISHING_CURSE);
            maxLevels.add(1); // Max 1, so trade max 1

            int index = random.nextInt(approvedEnchants.size());
            RegistryKey<Enchantment> selectedEnchant = approvedEnchants.get(index);
            int maxLevel = maxLevels.get(index);
            int level = 1 + random.nextInt(maxLevel); // Equal chance for each level from 1 to maxLevel

            // Get the RegistryEntry from the entity's world
            RegistryEntry<Enchantment> enchantmentEntry = entity.getWorld().getRegistryManager()
                    .getOrThrow(net.minecraft.registry.RegistryKeys.ENCHANTMENT)
                    .getOrThrow(selectedEnchant);

            book.addEnchantment(enchantmentEntry, level);
            return level; // Return the selected enchantment level
        } catch (Exception e) {
            // Handle any exceptions gracefully
            return 0; // Return 0 if enchantment fails
        }
    }
}