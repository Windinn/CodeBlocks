package com.windinn.codeblocks.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.plotsquared.core.player.PlotPlayer;
import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.EventType;

public class PlayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			PlotPlayer plotPlayer = PlotPlayer.get(player.getName());
			CodeUtils.isCoding.put(player, false);
			player.getInventory().clear();

			player.setGameMode(GameMode.CREATIVE);

			CodeUtils.execute(Bukkit.getPlayer(player.getUniqueId()), EventType.PLAYER_JOIN_PLOT,
					plotPlayer.getCurrentPlot(), player.getTargetBlock(null, 5));
		}

		return false;
	}

}
