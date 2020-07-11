package com.windinn.codeblocks.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.windinn.codeblocks.utils.CooldownManager;

public class AsyncPlayerChatListener implements Listener {

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		String message = event.getMessage();

		if (item == null) {
			return;
		}

		if (CooldownManager.isInChatCooldown(player)) {
			player.sendMessage(ChatColor.RED + "You are in chat cooldown!");
			event.setCancelled(true);
			return;
		}

		CooldownManager.setInChatCooldown(player, true);

		if (item.getType() == Material.BOOK) {

			if (item.hasItemMeta()) {

				if (item.getItemMeta().getLore() != null) {

					if (item.getItemMeta().getLore().get(0)
							.equals(ChatColor.GRAY + "Say something in chat while holding the text variable")) {

						if (item.getItemMeta().getLore().size() >= 2) {

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

		} else if (item.getType() == Material.SLIME_BALL) {

			if (item.hasItemMeta()) {

				if (item.getItemMeta().getLore() != null) {

					if (item.getItemMeta().getLore().get(0)
							.equals(ChatColor.GRAY + "Say something a number in chat while holding the variable")) {

						if (item.getItemMeta().getLore().size() >= 2) {

							if (item.getItemMeta().getLore().get(1)
									.equals(ChatColor.GRAY + "to set the value of the variable.")) {
								ItemMeta meta = item.getItemMeta();
								String name = ChatColor.translateAlternateColorCodes('&', message);

								try {
									Double.parseDouble(name);
								} catch (NumberFormatException exception) {
									player.sendMessage(ChatColor.RED + "This is not a number!");
									event.setCancelled(true);
									return;
								}

								meta.setDisplayName(ChatColor.RESET + name);
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
