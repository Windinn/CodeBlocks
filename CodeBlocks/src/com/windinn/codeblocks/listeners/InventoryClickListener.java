package com.windinn.codeblocks.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.windinn.codeblocks.utils.CodeUtils;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getWhoClicked();
		InventoryView view = event.getView();
		ItemStack item = event.getCurrentItem();

		if (item == null) {
			return;
		}

		if (view.getTitle().equals("Modify Action Block")) {

			if (!CodeUtils.savedSigns.containsKey(player)) {
				player.sendMessage(ChatColor.RED + "An error occured, please reclick the sign again.");
				return;
			}

			Block signBlock = CodeUtils.savedSigns.get(player);

			if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Gamemode to Survival")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Set gamemode to:");
				sign.setLine(2, ChatColor.WHITE + "Survival");

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Gamemode to Creative")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Set gamemode to:");
				sign.setLine(2, ChatColor.WHITE + "Creative");

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Send Message")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Send Message");
				sign.setLine(2, null);

				sign.update();
			}

			player.closeInventory();
			event.setCancelled(true);
		} else if (view.getTitle().equals("Modify Event Block")) {

			if (!CodeUtils.savedSigns.containsKey(player)) {
				player.sendMessage(ChatColor.RED + "An error occured, please reclick the sign again.");
				return;
			}

			Block signBlock = CodeUtils.savedSigns.get(player);

			if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Player Join Plot")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Player Join Plot");
				sign.setLine(2, null);

				sign.update();
			}

			player.closeInventory();
			event.setCancelled(true);
		}

	}

}
