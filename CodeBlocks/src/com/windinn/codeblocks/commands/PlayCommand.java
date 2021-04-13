package com.windinn.codeblocks.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.EventType;

public class PlayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			CodeUtils.isCoding.put(player, false);
			player.getInventory().clear();

			player.setGameMode(GameMode.CREATIVE);
			player.sendMessage(ChatColor.GREEN + "You entered play mode.");

			CodeUtils.execute(player, EventType.PLAYER_JOIN_PLOT, player.getTargetBlock(null, 5));
		}

		return false;
	}

}
