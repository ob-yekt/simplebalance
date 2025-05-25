package com.github.ob_yekt.simplebalance.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalDifficulty.class)
public class LocalDifficultyMixin {

    @Inject(method = "getLocalDifficulty", at = @At("HEAD"), cancellable = true)
    private void injectMaxDifficulty(CallbackInfoReturnable<Float> cir) {
        LocalDifficulty self = (LocalDifficulty)(Object)this;
        Difficulty difficulty = self.getGlobalDifficulty();

        float maxDifficulty;
        switch (difficulty) {
            case PEACEFUL:
                maxDifficulty = 0.0f;
                break;
            case EASY:
                maxDifficulty = 1.5f;
                break;
            case NORMAL:
                maxDifficulty = 4.0f;
                break;
            case HARD:
            default:
                maxDifficulty = 6.75f;
                break;
        }

        cir.setReturnValue(maxDifficulty);
    }

    @Inject(method = "getClampedLocalDifficulty", at = @At("HEAD"), cancellable = true)
    private void injectClampedDifficulty(CallbackInfoReturnable<Float> cir) {
        LocalDifficulty self = (LocalDifficulty)(Object)this;
        Difficulty difficulty = self.getGlobalDifficulty();

        float clampedDifficulty;
        switch (difficulty) {
            case PEACEFUL:
                clampedDifficulty = 0.0f;
                break;
            case EASY:
                clampedDifficulty = 1.5f;
                break;
            case NORMAL:
                clampedDifficulty = 4.0f;
                break;
            case HARD:
            default:
                clampedDifficulty = 1.0f; // Because clamped max local difficulty is always between 0.0 and 1.0
                break;
        }

        cir.setReturnValue(clampedDifficulty);
    }
}
