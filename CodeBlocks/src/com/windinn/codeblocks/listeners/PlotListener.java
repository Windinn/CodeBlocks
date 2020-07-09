package com.windinn.codeblocks.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.google.common.eventbus.Subscribe;
import com.plotsquared.core.events.PlayerEnterPlotEvent;
import com.plotsquared.core.events.PlayerLeavePlotEvent;
import com.plotsquared.core.player.PlotPlayer;
import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.EventType;

public class PlotListener {

	@Subscribe
	public void onPlayerEnterPlot(PlayerEnterPlotEvent event) {
		PlotPlayer player = event.getPlotPlayer();

		if (player == null) {
			return;
		}

		Player bukkitPlayer = Bukkit.getPlayer(player.getUUID());
		bukkitPlayer.setGameMode(GameMode.CREATIVE);

		if (!CodeUtils.isCoding.getOrDefault(Bukkit.getPlayer(player.getUUID()), false)) {
			CodeUtils.execute(Bukkit.getPlayer(player.getUUID()), EventType.PLAYER_JOIN_PLOT, player.getCurrentPlot(),
					bukkitPlayer.getTargetBlock(null, 5));
		}

	}

	@Subscribe
	public void onPlayerLeavePlot(PlayerLeavePlotEvent event) {
		Player player = Bukkit.getPlayer(event.getPlotPlayer().getUUID());

		if (player == null) {
			return;
		}

		if (player.getGameMode() == GameMode.SURVIVAL) {
			player.setGameMode(GameMode.CREATIVE);
			player.getInventory().clear();
		}

		player.setMaxHealth(20d);

		if (CodeUtils.isCoding.getOrDefault(player, false)) {
			CodeUtils.isCoding.put(player, false);
			player.sendMessage(ChatColor.RED + "You are not longer in coding mode.");
		} else {

			for (Sound sound : Sound.values()) {
				player.stopSound(sound);
			}

		}

	}

}