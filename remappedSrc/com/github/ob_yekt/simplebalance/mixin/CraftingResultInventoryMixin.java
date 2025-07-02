package com.github.ob_yekt.simplebalance.mixin;

import com.github.ob_yekt.simplebalance.LoreManager;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultInventory.class)
public class CraftingResultInventoryMixin {

    @Inject(method = "setStack", at = @At("TAIL"))
    private void onSetStack(int slot, ItemStack stack, CallbackInfo ci) {
        if (!stack.isEmpty()) {
            LoreManager.applyArmorLore(stack);
        }
    }
}