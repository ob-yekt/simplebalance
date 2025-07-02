package com.github.ob_yekt.simplebalance.mixin;

import com.github.ob_yekt.simplebalance.LoreManager;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class InventoryMixin {

    @Inject(method = "sendContentUpdates", at = @At("HEAD"))
    private void onContentUpdate(CallbackInfo ci) {
        ScreenHandler handler = (ScreenHandler) (Object) this;

        // Apply lore to all items in the inventory when content updates
        for (int i = 0; i < handler.slots.size(); i++) {
            ItemStack stack = handler.slots.get(i).getStack();
            if (!stack.isEmpty()) {
                LoreManager.applyArmorLore(stack);
            }
        }
    }
}