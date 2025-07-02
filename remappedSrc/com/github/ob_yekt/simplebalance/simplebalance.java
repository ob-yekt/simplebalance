package com.github.ob_yekt.simplebalance;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.chunk.Chunk;

public class simplebalance implements ModInitializer {
	public static final String MOD_ID = "simplebalance";

	@Override
	public void onInitialize() {
		System.out.println("SimpleBalance mod initialized!");
		VillagerTrades.registerCustomTrades();
		// Any other init tasks go here
		CommandRegistrationCallback.EVENT.register(
				(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) -> {
					dispatcher.register(
							CommandManager.literal("localdifficulty")
									.requires(source -> source.hasPermissionLevel(0))  // Now source is ServerCommandSource
									.executes(context -> {
										ServerCommandSource source = context.getSource();
										ServerWorld world = source.getWorld();
										BlockPos pos = BlockPos.ofFloored(source.getPosition());
										Chunk chunk = world.getChunk(pos);

										LocalDifficulty localDifficulty = world.getLocalDifficulty(pos);

										float raw = localDifficulty.getLocalDifficulty();
										float clamped = localDifficulty.getClampedLocalDifficulty();
										Difficulty global = localDifficulty.getGlobalDifficulty();

										long timeOfDay = world.getTime();
										long inhabitedTime = chunk.getInhabitedTime();

										source.sendFeedback(() -> Text.literal("§eLocal Difficulty Info:").formatted(Formatting.BOLD), false);
										source.sendFeedback(() -> Text.literal(" - Global Difficulty: §b" + global), false);
										source.sendFeedback(() -> Text.literal(" - Raw Local Difficulty: §c" + raw), false);
										source.sendFeedback(() -> Text.literal(" - Clamped Local Difficulty: §a" + clamped), false);
										source.sendFeedback(() -> Text.literal(" - Time of Day: §7" + timeOfDay), false);
										source.sendFeedback(() -> Text.literal(" - Inhabited Chunk Time: §7" + inhabitedTime), false);

										return 1;
									})
					);
				}
		);
	}
}
