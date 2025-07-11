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
		IronmanMode.init(); // Initialize Ironman Mode

		CommandRegistrationCallback.EVENT.register(
				(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) -> {
					// Register /localdifficulty command
					dispatcher.register(
							CommandManager.literal("localdifficulty")
									.requires(source -> source.hasPermissionLevel(0))
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

										source.sendFeedback(() -> Text.literal("Local Difficulty Info:").formatted(Formatting.BOLD, Formatting.YELLOW), false);
										source.sendFeedback(() -> Text.literal(" - Global Difficulty: ").append(Text.literal(String.valueOf(global)).formatted(Formatting.AQUA)), false);
										source.sendFeedback(() -> Text.literal(" - Raw Local Difficulty: ").append(Text.literal(String.valueOf(raw)).formatted(Formatting.RED)), false);
										source.sendFeedback(() -> Text.literal(" - Clamped Local Difficulty: ").append(Text.literal(String.valueOf(clamped)).formatted(Formatting.GREEN)), false);
										source.sendFeedback(() -> Text.literal(" - Time of Day: ").append(Text.literal(String.valueOf(timeOfDay)).formatted(Formatting.GRAY)), false);
										source.sendFeedback(() -> Text.literal(" - Inhabited Chunk Time: ").append(Text.literal(String.valueOf(inhabitedTime)).formatted(Formatting.GRAY)), false);

										return 1;
									})
					);

					// Register /ironman enable command
					dispatcher.register(
							CommandManager.literal("ironman")
									.requires(source -> source.hasPermissionLevel(0))
									.then(CommandManager.literal("enable")
											.executes(IronmanMode::enableIronman))
					);
				}
		);
	}
}