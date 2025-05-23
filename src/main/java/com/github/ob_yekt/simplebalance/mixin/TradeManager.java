package com.github.ob_yekt.simplebalance.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(TradeOffers.class)
public class TradeManager {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void onStaticInit(CallbackInfo ci) {
        // Create a dummy random instance for trade creation
        Random random = Random.create();

        // Toolsmith trades
        Int2ObjectMap<TradeOffers.Factory[]> toolsmithTrades = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.TOOLSMITH);
        if (toolsmithTrades != null) {
            replaceTrade(toolsmithTrades, 3, random, Items.DIAMOND_HOE);
            replaceTrade(toolsmithTrades, 4, random, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL);
            replaceTrade(toolsmithTrades, 5, random, Items.DIAMOND_PICKAXE);

            addTrade(toolsmithTrades, 4, new TradeOffers.SellItemFactory(Items.BRUSH, 8, 1, 4, 10, 0.05F));
            addTrade(toolsmithTrades, 4, new TradeOffers.SellItemFactory(Items.SPYGLASS, 8, 1, 4, 10, 0.05F));
            addTrade(toolsmithTrades, 5, new TradeOffers.SellItemFactory(Items.RECOVERY_COMPASS, 24, 1, 2, 15, 0.05F));
        }

        // Weaponsmith trades
        Int2ObjectMap<TradeOffers.Factory[]> weaponsmithTrades = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH);
        if (weaponsmithTrades != null) {
            replaceTrade(weaponsmithTrades, 4, random, Items.DIAMOND_AXE);
            replaceTrade(weaponsmithTrades, 5, random, Items.DIAMOND_SWORD);

            addTrade(weaponsmithTrades, 4, new TradeOffers.SellItemFactory(Items.GUNPOWDER, 1, 2, 8, 7, 0.05F));
            addTrade(weaponsmithTrades, 5, new TradeOffers.SellItemFactory(Items.TRIDENT, 48, 1, 1, 30, 0.05F));
        }

        // Armorer trades
        Int2ObjectMap<TradeOffers.Factory[]> armorerTrades = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.ARMORER);
        if (armorerTrades != null) {
            replaceTrade(armorerTrades, 1, random, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
            replaceTrade(armorerTrades, 3, random, Items.SHIELD);
            replaceTrade(armorerTrades, 4, random, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
            replaceTrade(armorerTrades, 5, random, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE);

            addTrade(armorerTrades, 1, new TradeOffers.BuyItemFactory(Items.COPPER_ORE, 12, 14, 2));
            addTrade(armorerTrades, 4, new TradeOffers.SellItemFactory(Items.ANVIL, 16, 1, 2, 15, 0.05F));
            addTrade(armorerTrades, 4, new TradeOffers.SellItemFactory(Items.GOLDEN_HORSE_ARMOR, 12, 1, 2, 10, 0.05F));
            addTrade(armorerTrades, 5, new TradeOffers.SellItemFactory(Items.WOLF_ARMOR, 14, 1, 1, 20, 0.05F));

        }
    }

    private static void replaceTrade(Int2ObjectMap<TradeOffers.Factory[]> tradeMap, int level, Random random, Item... itemsToRemove) {
        TradeOffers.Factory[] factories = tradeMap.get(level);
        if (factories != null) {
            Set<Item> itemsToRemoveSet = new HashSet<>(Arrays.asList(itemsToRemove));
            List<TradeOffers.Factory> filteredFactories = Arrays.stream(factories)
                    .filter(factory -> {
                        // Skip factories that require a valid entity/world (e.g., SellMapFactory)
                        if (factory instanceof TradeOffers.SellMapFactory || factory instanceof TradeOffers.TypedWrapperFactory) {
                            return true;
                        }
                        // Create trade offer with null entity and provided random
                        TradeOffer offer = factory.create(null, random);
                        if (offer != null) {
                            ItemStack sellItem = offer.getSellItem();
                            return !itemsToRemoveSet.contains(sellItem.getItem());
                        }
                        return true; // Keep factory if trade offer is null
                    })
                    .toList();
            tradeMap.put(level, filteredFactories.toArray(new TradeOffers.Factory[0]));
        }
    }

    @Unique
    private static void addTrade(Int2ObjectMap<TradeOffers.Factory[]> tradeMap, int level, TradeOffers.Factory factory) {
        TradeOffers.Factory[] existingFactories = tradeMap.get(level);
        List<TradeOffers.Factory> newFactories = existingFactories != null
                ? new ArrayList<>(Arrays.asList(existingFactories))
                : new ArrayList<>();
        newFactories.add(factory);
        tradeMap.put(level, newFactories.toArray(new TradeOffers.Factory[0]));
    }
}