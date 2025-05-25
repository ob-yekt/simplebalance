package com.github.ob_yekt.simplebalance.mixin;


import net.minecraft.entity.mob.MobEntity;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MobEntity.class)
public class MobEntityMixin {

    /**
     * Prevent equipment drops by overriding the dropEquipment method
     */
    @Inject(method = "dropEquipment", at = @At("HEAD"), cancellable = true)
    private void preventEquipmentDrop(ServerWorld world, DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        // Cancel equipment dropping entirely
        ci.cancel();
    }
}