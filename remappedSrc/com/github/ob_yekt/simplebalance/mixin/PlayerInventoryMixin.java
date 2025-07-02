package com.github.ob_yekt.simplebalance.mixin;

import com.github.ob_yekt.simplebalance.LoreManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(method = "setStack", at = @At("TAIL"))
    private void onSetStack(int slot, ItemStack stack, CallbackInfo ci) {
        if (!stack.isEmpty()) {
            LoreManager.applyArmorLore(stack);
        }
    }

    @Inject(method = "markDirty", at = @At("HEAD"))
    private void onMarkDirty(CallbackInfo ci) {
        PlayerInventory inventory = (PlayerInventory) (Object) this;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                LoreManager.applyArmorLore(stack);
            }
        }
    }
}