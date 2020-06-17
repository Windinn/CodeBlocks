package com.windinn.codeblocks.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.CooldownManager;
import com.windinn.codeblocks.utils.EventType;
import com.windinn.codeblocks.utils.GuiUtils;
import com.windinn.codeblocks.utils.LocationUtils;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		Plot currentPlot = null;
		PlotPlayer plotPlayer = PlotPlayer.get(player.getName());

		for (Plot plot : PlotSquared.get().getPlots(player.getUniqueId())) {

			if (plot.equals(plotPlayer.getCurrentPlot())) {
				currentPlot = plotPlayer.getCurrentPlot();
				break;
			}

		}

		if (event.getHand() == EquipmentSlot.OFF_HAND) {
			return;
		}

		if (item != null) {

			if (item.getType() == Material.MAGMA_CREAM) {

				if (item.hasItemMeta()) {

					if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Variables")) {
						Inventory inventory = Bukkit.createInventory(null, 9);

						inventory.addItem(GuiUtils.createItem(Material.BOOK, ChatColor.RESET + "Text Value",
								ChatColor.GRAY + "Say something in chat while holding the text variable",
								ChatColor.GRAY + "to set the name of this variable"));

						inventory.addItem(GuiUtils.createItem(Material.PAPER, ChatColor.RESET + "Location Value",
								ChatColor.GRAY + "Right click a block or while in air",
								ChatColor.GRAY + "To set the location value"));

						inventory.addItem(GuiUtils.createItem(Material.SLIME_BALL, ChatColor.RESET + "Number Value",
								ChatColor.GRAY + "Say something a number in chat while holding the variable",
								ChatColor.GRAY + "to set the value of the variable."));

						player.openInventory(inventory);
						event.setCancelled(true);
					}

				}

			} else if (item.getType() == Material.PAPER) {

				if (item.getItemMeta().getLore() != null) {

					if (item.getItemMeta().getLore().get(0)
							.equals(ChatColor.GRAY + "Right click a block or while in air")) {

						if (item.getItemMeta().getLore().size() >= 2) {

							if (item.getItemMeta().getLore().get(1)
									.equals(ChatColor.GRAY + "To set the location value")) {
								boolean plotFound = false;

								for (Plot plot : PlotSquared.get().getPlots(player.getUniqueId())) {

									if (plot.equals(plotPlayer.getCurrentPlot())) {
										plotFound = true;
										break;
									}

								}

								if (!plotFound) {
									player.sendMessage(ChatColor.RED + "The location must be located in your plot!");
									event.setCancelled(true);
									return;
								}

								if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
									Location location = event.getClickedBlock().getLocation();
									PlotArea plotArea = PlotSquared.get()
											.getPlotAreaAbs(new com.plotsquared.core.location.Location(
													location.getWorld().getName(), location.getBlockX(),
													location.getBlockY(), location.getBlockZ()));
									Plot plot = plotArea.getPlotAbs(
											new com.plotsquared.core.location.Location(location.getWorld().getName(),
													location.getBlockX(), location.getBlockY(), location.getBlockZ()));

									if (plot != currentPlot) {
										player.sendMessage(
												ChatColor.RED + "The location must be located in your plot!");
										event.setCancelled(true);
										return;
									}

									ItemMeta meta = item.getItemMeta();
									meta.setDisplayName(ChatColor.RESET + LocationUtils
											.simpleLocationToString(event.getClickedBlock().getLocation()));
									item.setItemMeta(meta);
									player.sendMessage(ChatColor.GREEN + "The location value has been set to "
											+ LocationUtils.simpleLocationToString(location));
									event.setCancelled(true);
								} else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
									ItemMeta meta = item.getItemMeta();
									meta.setDisplayName(ChatColor.RESET
											+ LocationUtils.simpleLocationToString(player.getLocation()));
									item.setItemMeta(meta);
									player.sendMessage(ChatColor.GREEN + "The location value has been set to "
											+ LocationUtils.simpleLocationToString(player.getLocation()));
									event.setCancelled(true);
								}

							}

						}

					}

				}

			} else if (item.getType() == Material.NETHER_STAR
					&& item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Request Support")) {
				Player minkizz = Bukkit.getPlayer("_Minkizz_");

				if (CooldownManager.isInSupportCooldown(player)) {
					player.sendMessage(ChatColor.RED + "Please wait before calling support again!");
					return;
				}

				if (minkizz == null) {
					player.sendMessage(ChatColor.RED + "I am sorry, the creator of CodeBlocks is offline.");
				} else {
					minkizz.sendMessage(ChatColor.GOLD + player.getName() + " requested help!");
					player.sendMessage(ChatColor.GREEN + "You asked for help. Please wait a bit.");
				}

				CooldownManager.setInSupportCooldown(player, true);
			}

		}

		if (!CodeUtils.isCoding.getOrDefault(player, false)) {

			if (plotPlayer != null) {

				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

					if (CodeUtils.execute(player, EventType.PLAYER_RIGHT_CLICK, plotPlayer.getCurrentPlot())) {
						event.setCancelled(true);
					}

				} else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

					if (CodeUtils.execute(player, EventType.PLAYER_LEFT_CLICK, plotPlayer.getCurrentPlot())) {
						event.setCancelled(true);
					}

				}

				if (CodeUtils.execute(player, EventType.PLAYER_INTERACT, plotPlayer.getCurrentPlot())) {
					event.setCancelled(true);
				}

			}

		}

		if (event.getClickedBlock() == null) {
			return;
		}

		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Block block = event.getClickedBlock();

		if (block.getType() == Material.OAK_WALL_SIGN || block.getType() == Material.CHEST) {
			boolean plotFound = false;

			for (Plot plot : PlotSquared.get().getPlots(player.getUniqueId())) {

				if (plot.equals(plotPlayer.getCurrentPlot())) {
					currentPlot = plotPlayer.getCurrentPlot();
					plotFound = true;
					break;
				}

			}

			if (player.getName().equals("_Minkizz_")) {
				plotFound = true;
			}

			if (!plotFound) {
				player.sendMessage(ChatColor.RED + "You can not interact with other people code!");
				event.setCancelled(true);
				return;
			}

		}

		if (block.getType() == Material.OAK_WALL_SIGN) {
			Sign sign = (Sign) block.getState();

			if (sign.getLine(0).equals(ChatColor.GREEN + "EVENT")) {
				Inventory inventory = Bukkit.createInventory(null, 9, "Modify Event Block");

				inventory.addItem(GuiUtils.createItem(Material.PAPER, ChatColor.GREEN + "Player Join Plot",
						ChatColor.GRAY + "This event is fired when a player joins your plot."));

				inventory.addItem(GuiUtils.createItem(Material.STICK, ChatColor.GREEN + "Player Interact",
						ChatColor.GRAY + "This event is fired when:",
						ChatColor.GRAY + "A player right clicks or left clicks a block / an item"));

				inventory.addItem(GuiUtils.createItem(Material.GOLDEN_BOOTS, ChatColor.GREEN + "Player Move",
						ChatColor.GRAY + "This event is fired when a player moves."));

				inventory.addItem(GuiUtils.createItem(Material.COAL, ChatColor.GREEN + "Player Right Click",
						ChatColor.GRAY + "This event is fired when:",
						ChatColor.GRAY + "A player right clicks a block or an item"));

				inventory.addItem(GuiUtils.createItem(Material.CHARCOAL, ChatColor.GREEN + "Player Left Click",
						ChatColor.GRAY + "This event is fired when:",
						ChatColor.GRAY + "A player left clicks a block or an item"));

				inventory.addItem(GuiUtils.createItem(Material.IRON_SWORD, ChatColor.GREEN + "Player Damage",
						ChatColor.GRAY + "This event is fired when:",
						ChatColor.GRAY + "A player takes damage from a block or an entity."));

				player.openInventory(inventory);
				CodeUtils.savedSigns.put(player, block);
			} else if (sign.getLine(0).equals(ChatColor.GOLD + "ACTION")) {
				Inventory inventory = Bukkit.createInventory(null, 9, "Modify Action Block");

				inventory.addItem(GuiUtils.createItem(Material.PAPER, ChatColor.GREEN + "Set Gamemode to Survival",
						ChatColor.GRAY + "This action sets the gamemode of the player to survival."));

				inventory.addItem(GuiUtils.createItem(Material.PAPER, ChatColor.GREEN + "Set Gamemode to Creative",
						ChatColor.GRAY + "This action sets the gamemode of the player to creative."));

				inventory.addItem(GuiUtils.createItem(Material.BOOK, ChatColor.GREEN + "Send Message",
						ChatColor.GRAY + "This action sends a Text Value to the player.",
						ChatColor.GRAY + "You must put a Text Value inside the chest for it to work."));

				inventory.addItem(GuiUtils.createItem(Material.ENDER_PEARL, ChatColor.GREEN + "Teleport",
						ChatColor.GRAY + "This action teleports a player",
						ChatColor.GRAY + "To the Location Value found inside the chest."));

				inventory.addItem(GuiUtils.createItem(Material.CHEST, ChatColor.GREEN + "Give Items",
						ChatColor.GRAY + "This action gives all items", ChatColor.GRAY + "In chest to a player"));

				inventory.addItem(GuiUtils.createItem(Material.BARRIER, ChatColor.GREEN + "Clear Inventory",
						ChatColor.GRAY + "This action clears the inventory of a player."));

				inventory.addItem(GuiUtils.createItem(Material.APPLE, ChatColor.GREEN + "Set Health",
						ChatColor.GRAY + "This action sets the health of the player.",
						ChatColor.GRAY + "You must put a Number Value for it to work."));

				inventory.addItem(GuiUtils.createItem(Material.GOLDEN_APPLE, ChatColor.GREEN + "Set Max Health",
						ChatColor.GRAY + "This action sets the maximum health of the player.",
						ChatColor.GRAY + "You must put a Number Value for it to work."));

				inventory.addItem(GuiUtils.createItem(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "Set XP Level",
						ChatColor.GRAY + "This action sets the XP level of a player.",
						ChatColor.GRAY + "You must put a Number Value for it to work."));

				player.openInventory(inventory);
				CodeUtils.savedSigns.put(player, block);
			}

		}

	}

}
