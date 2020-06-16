package com.windinn.codeblocks.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.plotsquared.core.plot.Plot;
import com.windinn.codeblocks.CodeBlocks;

public final class CodeUtils {

	public static Map<Player, Block> savedSigns = new HashMap<>();
	public static Map<Player, Boolean> isCoding = new HashMap<>();

	private CodeUtils() {
		throw new IllegalAccessError();
	}

	public static void addEvent(Block block, Plot plot) {
		JavaPlugin plugin = JavaPlugin.getPlugin(CodeBlocks.class);
		List<String> locations = plugin.getConfig().getStringList("eventBlocks");

		if (locations == null) {
			locations = new ArrayList<>();
		}

		String loc = LocationUtils.locationToString(block.getLocation(), plot);

		if (!locations.contains(loc)) {
			locations.add(loc);
		}

		plugin.getConfig().set("eventBlocks", locations);
		plugin.saveConfig();
	}

	public static void removeEvent(Block block, Plot plot) {
		JavaPlugin plugin = JavaPlugin.getPlugin(CodeBlocks.class);
		List<String> events = getEvents();
		events.remove(LocationUtils.locationToString(block.getLocation(), plot));
		plugin.getConfig().set("eventBlocks", events);
		plugin.saveConfig();
	}

	public static List<String> getEvents() {
		JavaPlugin plugin = JavaPlugin.getPlugin(CodeBlocks.class);
		List<String> locations = plugin.getConfig().getStringList("eventBlocks");

		if (locations == null) {
			locations = new ArrayList<>();
		}

		return locations;
	}

	public static void execute(Player player, EventType event, Plot plot) {

		if (event == EventType.PLAYER_JOIN_PLOT) {

			for (String eventBlock : getEvents()) {
				CustomLocation loc = LocationUtils.stringToLocation(eventBlock);

				if (plot.getId().getX() != loc.getPlotIdX() || plot.getId().getY() != loc.getPlotIdY()) {
					return;
				}

				Location realLoc = loc.getLocation();

				for (int z = realLoc.getBlockZ(); true; z--) {
					Block block = new Location(realLoc.getWorld(), realLoc.getX(), realLoc.getY(), z).getBlock();

					if (block.getType() == Material.AIR) {
						break;
					} else if (block.getType() == Material.COBBLESTONE) {
						Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

						if (sign.getLine(0).equals(ChatColor.GOLD + "ACTION")) {
							doAction(sign, player);
						}

					}

				}

			}

		}

	}

	private static void doAction(Sign sign, Player player) {

		if (sign.getLine(1).equals(ChatColor.WHITE + "Set gamemode to:")) {

			if (sign.getLine(2).equals(ChatColor.WHITE + "Survival")) {
				player.setGameMode(GameMode.SURVIVAL);
			} else if (sign.getLine(2).equals(ChatColor.WHITE + "Creative")) {
				player.setGameMode(GameMode.CREATIVE);
			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Send Message")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				String message = "";

				for (ItemStack item : inventory.getContents()) {

					if (item == null) {
						continue;
					}

					if (item.getType() == Material.PAPER) {
						message += item.getItemMeta().getDisplayName() + " ";
					}

				}

				player.sendMessage(message);
			}

		}

	}

}
