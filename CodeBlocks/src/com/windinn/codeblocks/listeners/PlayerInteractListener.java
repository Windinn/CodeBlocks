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

import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.GuiUtils;

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

						inventory.addItem(GuiUtils.createItem(Material.PAPER, ChatColor.RESET + "Text Value",
								ChatColor.GRAY + "Say something in chat while holding the text variable",
								ChatColor.GRAY + "to set the name of this variable"));

						player.openInventory(inventory);
					}

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

		if (block.getType() == Material.OAK_WALL_SIGN) {
			Sign sign = (Sign) block.getState();

			if (sign.getLine(0).equals(ChatColor.GREEN + "EVENT")) {
				Inventory inventory = Bukkit.createInventory(null, 9, "Modify Event Block");

				inventory.addItem(GuiUtils.createItem(Material.PAPER, ChatColor.GREEN + "Player Join Plot",
						ChatColor.GRAY + "This event is fired when a player joins your plot."));

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

				player.openInventory(inventory);
				CodeUtils.savedSigns.put(player, block);
			}

		}

	}

}
