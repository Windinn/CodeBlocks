package com.windinn.codeblocks.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import com.plotsquared.core.player.PlotPlayer;
import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.EventType;
import com.windinn.codeblocks.utils.GuiUtils;
import com.windinn.codeblocks.utils.LocationUtils;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

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

						player.openInventory(inventory);
					}

				}

			} else if (item.getType() == Material.PAPER) {

				if (item.getItemMeta().getLore() != null) {

					if (item.getItemMeta().getLore().get(0)
							.equals(ChatColor.GRAY + "Right click a block or while in air")) {

						if (item.getItemMeta().getLore().size() >= 2) {

							if (item.getItemMeta().getLore().get(1)
									.equals(ChatColor.GRAY + "To set the location value")) {

								if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
									ItemMeta meta = item.getItemMeta();
									meta.setDisplayName(ChatColor.RESET + LocationUtils
											.simpleLocationToString(event.getClickedBlock().getLocation()));
									item.setItemMeta(meta);
									player.sendMessage(
											ChatColor.GREEN + "The location value has been set to " + LocationUtils
													.simpleLocationToString(event.getClickedBlock().getLocation()));
								} else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
									ItemMeta meta = item.getItemMeta();
									meta.setDisplayName(ChatColor.RESET
											+ LocationUtils.simpleLocationToString(player.getLocation()));
									item.setItemMeta(meta);
									player.sendMessage(ChatColor.GREEN + "The location value has been set to "
											+ LocationUtils.simpleLocationToString(player.getLocation()));
								}

							}

						}

					}

				}

			}

		}

		if (!CodeUtils.isCoding.getOrDefault(player, false)) {
			PlotPlayer plotPlayer = PlotPlayer.get(player.getName());

			if (plotPlayer != null) {
				CodeUtils.execute(player, EventType.PLAYER_INTERACT, plotPlayer.getCurrentPlot());
			}

		}

		if (event.getClickedBlock() == null) {
			return;
		}

		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Block block = event.getClickedBlock();

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

				player.openInventory(inventory);
				CodeUtils.savedSigns.put(player, block);
			}

		}

	}

}
