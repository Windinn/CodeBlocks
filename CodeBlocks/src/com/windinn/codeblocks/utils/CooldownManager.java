package com.windinn.codeblocks.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.windinn.codeblocks.CodeBlocks;

public final class CooldownManager {

	private CooldownManager() {
		throw new IllegalAccessError();
	}

	private static Map<Player, Boolean> inSupportCooldown = new HashMap<>();

	public static void setInSupportCooldown(Player player, boolean cooldown) {
		inSupportCooldown.put(player, cooldown);

		Bukkit.getScheduler().runTaskLaterAsynchronously(JavaPlugin.getPlugin(CodeBlocks.class), new Runnable() {

			@Override
			public void run() {
				inSupportCooldown.put(player, false);
			}

		}, 20L * 5);

	}

	public static boolean isInSupportCooldown(Player player) {
		return inSupportCooldown.getOrDefault(player, false);
	}

}
