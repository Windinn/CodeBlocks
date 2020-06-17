package com.windinn.codeblocks.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.GuiUtils;

public class CodeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			PlotPlayer plotPlayer = PlotPlayer.get(player.getName());

			for (Plot plot : PlotSquared.get().getPlots(player.getUniqueId())) {

				if (plot.equals(plotPlayer.getCurrentPlot())) {
					boolean coding = CodeUtils.isCoding.getOrDefault(player, false);

					if (coding) {
						CodeUtils.isCoding.put(player, false);

						player.getInventory().clear();

						player.sendMessage(ChatColor.RED + "You are not longer in coding mode.");
					} else {
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

				} else {
					player.sendMessage(ChatColor.RED + "You must be in your plot to do this!");
				}

			}

		}

		return false;
	}

}
