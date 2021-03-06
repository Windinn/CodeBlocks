package com.windinn.codeblocks.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.GuiUtils;

public class CodeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			boolean coding = CodeUtils.isCoding.getOrDefault(player, false);

			if (coding) {
				CodeUtils.isCoding.put(player, false);

				player.getInventory().clear();

				player.sendMessage(ChatColor.RED + "You are not longer in coding mode.");
			} else {

				for (Sound sound : Sound.values()) {
					player.stopSound(sound);
				}

				CodeUtils.isCoding.put(player, true);
				player.getInventory().clear();
				player.getInventory()
						.addItem(GuiUtils.createItem(Material.DIAMOND_BLOCK, ChatColor.GREEN + "Event Block",
								ChatColor.GRAY + "Use this block to detect player events.",
								ChatColor.GRAY + "Exemple: when a player jumps."));

				player.getInventory()
						.addItem(GuiUtils.createItem(Material.COBBLESTONE, ChatColor.GOLD + "Action Block",
								ChatColor.GRAY + "Use this do an action on the player.",
								ChatColor.GRAY + "Exemple: set the gamemode of the player to survival."));

				player.getInventory()
						.addItem(GuiUtils.createItem(Material.NETHERRACK, ChatColor.RED + "Server Action Block",
								ChatColor.GRAY + "Use this do a server-sided action.",
								ChatColor.GRAY + "Exemple: cancel event."));

				player.getInventory().addItem(GuiUtils.createItem(Material.RED_WOOL, ChatColor.RED + "Redstone Block",
						ChatColor.GRAY + "Use this to connect redstone to event blocks."));

				player.getInventory()
						.addItem(GuiUtils.createItem(Material.OAK_PLANKS, ChatColor.YELLOW + "Condition Block",
								ChatColor.GRAY + "Use this to put conditions in your code.",
								ChatColor.GRAY + "Exemple: detect right clicking a block"));

				player.getInventory()
						.addItem(GuiUtils.createItem(Material.ARROW, ChatColor.RED + "NOT Arrow",
								ChatColor.GRAY + "Use this to inverse conditions.",
								ChatColor.GRAY + "Exemple: if player is NOT sneaking"));

				player.getInventory().setItem(7,
						GuiUtils.createItem(Material.NETHER_STAR, ChatColor.GREEN + "Request Support",
								ChatColor.GRAY + "Do you need help? Request support!",
								ChatColor.GRAY + "The CREATOR of CodeBlocks will come help you!"));

				player.getInventory().setItem(8,
						GuiUtils.createItem(Material.MAGMA_CREAM, ChatColor.GOLD + "Variables",
								ChatColor.GRAY + "Variables are used to answer to a question (WHAT, WHY, WHO).",
								ChatColor.GRAY + "Exemple: when you use a send message action.",
								ChatColor.GRAY + "WHAT message should I send?"));

				player.setGameMode(GameMode.CREATIVE);

				player.sendMessage(ChatColor.GREEN + "You are now in coding mode.");
			}

		}

		return false;
	}

}
