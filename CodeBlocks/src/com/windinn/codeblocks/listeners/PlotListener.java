package com.windinn.codeblocks.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
		Player bukkitPlayer = Bukkit.getPlayer(player.getUUID());
		bukkitPlayer.setGameMode(GameMode.CREATIVE);

		if (!CodeUtils.isCoding.getOrDefault(Bukkit.getPlayer(player.getUUID()), false)) {
			CodeUtils.execute(Bukkit.getPlayer(player.getUUID()), EventType.PLAYER_JOIN_PLOT, player.getCurrentPlot());
		}

	}

	@Subscribe
	public void onPlayerLeavePlot(PlayerLeavePlotEvent event) {
		Player player = Bukkit.getPlayer(event.getPlotPlayer().getUUID());
		player.setGameMode(GameMode.CREATIVE);

		if (CodeUtils.isCoding.getOrDefault(player, false)) {
			CodeUtils.isCoding.put(player, false);

			player.getInventory().clear();
			player.setMaxHealth(20d);

			player.sendMessage(ChatColor.RED + "You are not longer in coding mode.");
		}

	}

}