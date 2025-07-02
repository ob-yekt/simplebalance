package com.github.ob_yekt.simplebalance.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    @ModifyArg(method = "fillRecipes",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/VillagerEntity;fillRecipesFromPool(Lnet/minecraft/village/TradeOfferList;[Lnet/minecraft/village/TradeOffers$Factory;I)V"),
            index = 1)
    private TradeOffers.Factory[] filterTradeFactories(TradeOffers.Factory[] factories) {
        VillagerEntity villager = (VillagerEntity) (Object) this;
        VillagerData villagerData = villager.getVillagerData();

        return Arrays.stream(factories)
                .filter(factory -> {
                    try {
                        TradeOffer offer = factory.create(villager, villager.getRandom());
                        if (offer == null) return true;

                        Item sellItem = offer.getSellItem().getItem();

                        if (villagerData.profession().matchesKey(VillagerProfession.TOOLSMITH)) {
                            return !(sellItem == Items.DIAMOND_HOE ||
                                    sellItem == Items.DIAMOND_AXE ||
                                    sellItem == Items.DIAMOND_SHOVEL ||
                                    sellItem == Items.DIAMOND_PICKAXE);

                        } else if (villagerData.profession().matchesKey(VillagerProfession.WEAPONSMITH)) {
                            return !(sellItem == Items.DIAMOND_SWORD ||
                                    sellItem == Items.DIAMOND_AXE);

                        } else if (villagerData.profession().matchesKey(VillagerProfession.ARMORER)) {
                            return !(sellItem == Items.IRON_HELMET ||
                                    sellItem == Items.IRON_CHESTPLATE ||
                                    sellItem == Items.IRON_LEGGINGS ||
                                    sellItem == Items.IRON_BOOTS ||
                                    sellItem == Items.SHIELD ||
                                    sellItem == Items.DIAMOND_HELMET ||
                                    sellItem == Items.DIAMOND_CHESTPLATE ||
                                    sellItem == Items.DIAMOND_LEGGINGS ||
                                    sellItem == Items.DIAMOND_BOOTS);

                        } else if (villagerData.profession().matchesKey(VillagerProfession.LIBRARIAN)) {
                            if (sellItem == Items.ENCHANTED_BOOK) {
                                return offer.getSecondBuyItem().isEmpty() ||
                                        !offer.getSecondBuyItem().get().item().matches(Items.BOOK.getRegistryEntry());
                            }
                        }

                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toArray(TradeOffers.Factory[]::new);
    }
}