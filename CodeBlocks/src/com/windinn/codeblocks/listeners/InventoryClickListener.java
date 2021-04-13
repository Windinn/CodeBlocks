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
			Sign sign = (Sign) signBlock.getState();

			if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Gamemode to Survival")) {
				sign.setLine(1, ChatColor.WHITE + "Set gamemode to:");
				sign.setLine(2, ChatColor.WHITE + "Survival");

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Gamemode to Creative")) {
				sign.setLine(1, ChatColor.WHITE + "Set gamemode to:");
				sign.setLine(2, ChatColor.WHITE + "Creative");

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Gamemode to Adventure")) {
				sign.setLine(1, ChatColor.WHITE + "Set gamemode to:");
				sign.setLine(2, ChatColor.WHITE + "Adventure");

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Gamemode to Spectator")) {
				sign.setLine(1, ChatColor.WHITE + "Set gamemode to:");
				sign.setLine(2, ChatColor.WHITE + "Spectator");

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Send Message")) {
				sign.setLine(1, ChatColor.WHITE + "Send Message");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Teleport")) {
				sign.setLine(1, ChatColor.WHITE + "Teleport");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Give Items")) {
				sign.setLine(1, ChatColor.WHITE + "Give Items");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Clear Inventory")) {
				sign.setLine(1, ChatColor.WHITE + "Clear Inventory");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Health")) {
				sign.setLine(1, ChatColor.WHITE + "Set Health");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Max Health")) {
				sign.setLine(1, ChatColor.WHITE + "Set Max Health");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set XP Level")) {
				sign.setLine(1, ChatColor.WHITE + "Set XP Level");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Add XP Level")) {
				sign.setLine(1, ChatColor.WHITE + "Add XP Level");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Play Music Disk")) {
				sign.setLine(1, ChatColor.WHITE + "Play Music Disk");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Set Texture Pack")) {
				sign.setLine(1, ChatColor.WHITE + "Set Texture Pack");
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

			if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Player Join World")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Player Join World");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Player Interact")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Player Interact");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Player Move")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Player Move");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Player Right Click")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Player Right Click");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Player Left Click")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Player Left Click");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Player Damage")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Player Damage");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Player Break Block")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Break Block");
				sign.setLine(2, null);

				sign.update();
			}

			player.closeInventory();
			event.setCancelled(true);
		} else if (view.getTitle().equals("Modify Condition Block")) {

			if (!CodeUtils.savedSigns.containsKey(player)) {
				player.sendMessage(ChatColor.RED + "An error occured, please reclick the sign again.");
				return;
			}

			Block signBlock = CodeUtils.savedSigns.get(player);

			if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Is Looking At Block")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Is Looking At");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Is Sneaking")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Is Sneaking");
				sign.setLine(2, null);

				sign.update();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Is Sprinting")) {
				Sign sign = (Sign) signBlock.getState();

				sign.setLine(1, ChatColor.WHITE + "Is Sprinting");
				sign.setLine(2, null);

				sign.update();
			}

			player.closeInventory();
			event.setCancelled(true);
		}

	}

}
