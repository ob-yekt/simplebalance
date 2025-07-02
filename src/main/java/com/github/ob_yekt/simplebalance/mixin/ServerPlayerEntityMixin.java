package com.github.ob_yekt.simplebalance.mixin;

import com.github.ob_yekt.simplebalance.IronmanMode;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource source, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (IronmanMode.isPlayerInIronmanTeam(player)) {
            player.getInventory().clear();
            player.setExperienceLevel(0);
            player.setExperiencePoints(0);
        }
    }
}