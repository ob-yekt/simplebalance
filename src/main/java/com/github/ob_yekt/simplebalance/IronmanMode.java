package com.github.ob_yekt.simplebalance;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.particle.ParticleTypes;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import java.util.Objects;

public class IronmanMode {
    private static final Identifier IRONMAN_HEALTH_MODIFIER_ID = Identifier.of(simplebalance.MOD_ID, "ironman_health_reduction");

    public static void init() {
        // Register death event listener (inventory clearing handled in mixin)
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                isPlayerInIronmanTeam(oldPlayer);
            }// No inventory clearing here; handled in ServerPlayerEntityMixin
        });

        // Register respawn event listener to remove from team
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (!alive && isPlayerInIronmanTeam(oldPlayer)) {
                onPlayerRespawnAfterDeath(newPlayer);
            }
        });

        // Apply health modifier and ensure team on join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (isPlayerInIronmanTeam(player)) {
                applyHealthReduction(player);
            }
        });
    }

    public static int enableIronman(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendFeedback(() -> Text.literal("This command can only be used by players.").formatted(Formatting.RED), false);
            return 0;
        }

        if (isPlayerInIronmanTeam(player)) {
            source.sendFeedback(() -> Text.literal("You have already enabled Ironman Mode.").formatted(Formatting.YELLOW), false);
            return 0;
        }

        // Add player to Ironman team
        assignPlayerToIronmanTeam(player);
        applyHealthReduction(player);

        // Inform the player
        source.sendFeedback(() -> Text.literal("Ironman Mode enabled! All items and XP will be lost upon death. Your health has been halved.").formatted(Formatting.YELLOW), false);

        // Broadcast to all players
        String broadcastMessage = String.format("Player %s has enabled Ironman Mode.", player.getName().getString());
        Objects.requireNonNull(player.getServer()).getPlayerManager().broadcast(Text.literal(broadcastMessage).formatted(Formatting.YELLOW), false);

        // Play sound and spawn particles
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        ServerWorld world = (ServerWorld) player.getWorld();

        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 1.0F, 1.0F);
        world.spawnParticles(ParticleTypes.RAID_OMEN, x, y, z, 50, 0.4, 1.5, 0.4, 0.05);
        world.spawnParticles(ParticleTypes.TRIAL_SPAWNER_DETECTION_OMINOUS, x, y, z, 40, 0.8, 1.5, 0.8, 0.05);
        world.spawnParticles(ParticleTypes.WITCH, x, y, z, 40, 0.5, 1.5, 0.5, 0.05);

        return 1;
    }

    public static boolean isPlayerInIronmanTeam(ServerPlayerEntity player) {
        ServerScoreboard scoreboard = Objects.requireNonNull(player.getServer()).getScoreboard();
        Team ironmanTeam = scoreboard.getTeam("ironman");
        if (ironmanTeam == null) {
            return false;
        }
        return scoreboard.getScoreHolderTeam(player.getNameForScoreboard()) == ironmanTeam;
    }

    private static void onPlayerRespawnAfterDeath(ServerPlayerEntity newPlayer) {
        removePlayerFromIronmanTeam(newPlayer);
        removeHealthReduction(newPlayer);
        newPlayer.sendMessage(Text.literal("Ironman Mode disabled due to death.").formatted(Formatting.YELLOW), false);
    }

    private static void createIronmanTeam(ServerScoreboard scoreboard) {
        Team ironmanTeam = scoreboard.getTeam("ironman");
        if (ironmanTeam == null) {
            ironmanTeam = scoreboard.addTeam("ironman");
            ironmanTeam.setPrefix(Text.literal("☠ ").formatted(Formatting.RED, Formatting.BOLD));
            ironmanTeam.setFriendlyFireAllowed(false);
            ironmanTeam.setShowFriendlyInvisibles(true);
        }
    }

    public static void assignPlayerToIronmanTeam(ServerPlayerEntity player) {
        ServerScoreboard scoreboard = Objects.requireNonNull(player.getServer()).getScoreboard();
        createIronmanTeam(scoreboard);
        Team ironmanTeam = scoreboard.getTeam("ironman");
        if (ironmanTeam != null) {
            scoreboard.addScoreHolderToTeam(player.getNameForScoreboard(), ironmanTeam);
        }
    }

    public static void removePlayerFromIronmanTeam(ServerPlayerEntity player) {
        ServerScoreboard scoreboard = Objects.requireNonNull(player.getServer()).getScoreboard();
        Team ironmanTeam = scoreboard.getTeam("ironman");
        if (ironmanTeam != null) {
            scoreboard.removeScoreHolderFromTeam(player.getNameForScoreboard(), ironmanTeam);
        }
    }

    private static void applyHealthReduction(ServerPlayerEntity player) {
        EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
        if (healthAttribute != null) {
            healthAttribute.removeModifier(IRONMAN_HEALTH_MODIFIER_ID);
            healthAttribute.addPersistentModifier(
                    new EntityAttributeModifier(
                            IRONMAN_HEALTH_MODIFIER_ID,
                            -10.0,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
            );
        }
    }

    private static void removeHealthReduction(ServerPlayerEntity player) {
        EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
        if (healthAttribute != null) {
            healthAttribute.removeModifier(IRONMAN_HEALTH_MODIFIER_ID);
        }
    }
}