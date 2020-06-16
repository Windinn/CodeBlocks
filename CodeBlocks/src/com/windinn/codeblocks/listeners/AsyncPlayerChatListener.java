package com.windinn.codeblocks.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AsyncPlayerChatListener implements Listener {

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		String message = event.getMessage();

		if (item == null) {
			return;
		}

		if (item.getType() == Material.PAPER) {

			if (item.hasItemMeta()) {

				if (item.getItemMeta().getLore() != null) {

					if (item.getItemMeta().getLore().get(0)
							.equals(ChatColor.GRAY + "Say something in chat while holding the text variable")) {

						if (item.getItemMeta().getLore().get(1) != null) {

							if (item.getItemMeta().getLore().get(1)
									.equals(ChatColor.GRAY + "to set the name of this variable")) {
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(
										ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', message));
								item.setItemMeta(meta);
								event.setCancelled(true);
							}

						}

					}

				}

			}

		}

	}

}
