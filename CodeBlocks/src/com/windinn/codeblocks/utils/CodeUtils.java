package com.windinn.codeblocks.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;

public final class CodeUtils {

	public static Map<Player, Block> savedSigns;
	public static Map<Player, Boolean> isCoding;
	private static JavaPlugin plugin;

	private CodeUtils() {
		throw new IllegalAccessError();
	}

	public static void setPlugin(JavaPlugin plugin) {
		CodeUtils.plugin = plugin;
		isCoding = new HashMap<>();
		savedSigns = new HashMap<>();
	}

	public static void addEvent(Block block) {
		List<String> locations = plugin.getConfig().getStringList("eventBlocks");

		if (locations == null) {
			locations = new ArrayList<>();
		}

		Plot plot = BukkitUtil.getPlot(block.getLocation());

		if (plot == null) {
			return;
		}

		String loc = LocationUtils.simpleLocationToString(
				new SimpleLocation(plot.getId().getX(), plot.getId().getY(), block.getLocation()));

		if (!locations.contains(loc)) {
			locations.add(loc);
		}

		plugin.getConfig().set("eventBlocks", locations);
		plugin.saveConfig();
	}

	public static void removeEvent(Block block) {
		List<String> events = getEvents();
		Iterator<String> iterator = events.iterator();

		while (iterator.hasNext()) {
			String locationStr = iterator.next();
			SimpleLocation location = LocationUtils.simpleStringToLocation(locationStr);

			if (location.getLocation().equals(block.getLocation())) {
				iterator.remove();
			}

		}

		plugin.getConfig().set("eventBlocks", events);
		plugin.saveConfig();
	}

	public static List<String> getEvents() {
		List<String> locations = plugin.getConfig().getStringList("eventBlocks");

		if (locations == null) {
			locations = new ArrayList<>();
		}

		return locations;
	}

	public static boolean execute(Player player, EventType event, Block targetBlock) {
		boolean cancel = false;
		PlotPlayer<Player> plotPlayer = BukkitUtil.getPlayer(player);

		for (String eventBlock : getEvents()) {
			SimpleLocation loc = LocationUtils.simpleStringToLocation(eventBlock);

			if (!loc.getLocation().getWorld().equals(player.getWorld())) {
				continue;
			}

			if (loc.getPlotX() != plotPlayer.getCurrentPlot().getId().getX()
					|| loc.getPlotY() != plotPlayer.getCurrentPlot().getId().getY()) {
				continue;
			}

			if (loc.getLocation().getBlock().getType() != Material.DIAMOND_BLOCK) {
				removeEvent(loc.getLocation().getBlock());
				continue;
			}

			Block eventSignBlock = loc.getLocation().getBlock().getRelative(BlockFace.EAST);

			if (eventSignBlock.getType() != Material.OAK_WALL_SIGN) {
				removeEvent(loc.getLocation().getBlock());
				continue;
			}

			Sign eventSign = (Sign) eventSignBlock.getState();
			boolean pass = false;

			if (event == EventType.PLAYER_JOIN_PLOT
					&& eventSign.getLine(1).equals(ChatColor.WHITE + "Player Join Plot")) {
				pass = true;
			} else if (event == EventType.PLAYER_INTERACT
					&& eventSign.getLine(1).equals(ChatColor.WHITE + "Player Interact")) {
				pass = true;
			} else if (event == EventType.PLAYER_MOVE && eventSign.getLine(1).equals(ChatColor.WHITE + "Player Move")) {
				pass = true;
			} else if (event == EventType.PLAYER_LEFT_CLICK
					&& eventSign.getLine(1).equals(ChatColor.WHITE + "Player Left Click")) {
				pass = true;
			} else if (event == EventType.PLAYER_RIGHT_CLICK
					&& eventSign.getLine(1).equals(ChatColor.WHITE + "Player Right Click")) {
				pass = true;
			} else if (event == EventType.PLAYER_DAMAGE
					&& eventSign.getLine(1).equals(ChatColor.WHITE + "Player Damage")) {
				pass = true;
			} else if (event == EventType.PLAYER_BLOCK_BREAK
					&& eventSign.getLine(1).equals(ChatColor.WHITE + "Break Block")) {
				pass = true;
			}

			if (!pass) {
				continue;
			}

			int conditionX = 0;
			boolean canExecute = true;
			int x = 0;

			for (int z = loc.getLocation().getBlockZ(); true; z--) {
				Block block = new Location(loc.getLocation().getWorld(), loc.getLocation().getX(),
						loc.getLocation().getY(), z).getBlock();

				if (block.getType() == Material.PISTON) {
					conditionX--;
				}

				if (conditionX <= 0) {
					conditionX = 0;
					canExecute = true;
				}

				x++;

				if (canExecute) {

					if (block.getType() == Material.AIR
							&& block.getRelative(BlockFace.NORTH).getType() != Material.PISTON) {
						break;
					} else if (block.getType() == Material.COBBLESTONE) {

						if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
							Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

							if (sign.getLine(0).equals(ChatColor.GOLD + "ACTION")) {
								doAction(sign, player);
							}

						}

					} else if (block.getType() == Material.NETHERRACK) {

						if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
							Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

							if (sign.getLine(0).equals(ChatColor.RED + "ACTION")) {

								if (sign.getLine(1).equals(ChatColor.WHITE + "Cancel Event")) {
									cancel = true;
								}

							}

						}

					} else if (block.getType() == Material.RED_WOOL) {

						if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
							Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

							if (sign.getLine(0).equals(ChatColor.RED + "REDSTONE")) {

								if (sign.getLine(1).equals(ChatColor.WHITE + "1 tick")) {

									if (block.getRelative(BlockFace.NORTH).getType() == Material.STONE) {
										block.getRelative(BlockFace.NORTH).setType(Material.REDSTONE_BLOCK);

										Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

											@Override
											public void run() {
												block.getRelative(BlockFace.NORTH).setType(Material.STONE);
											}

										}, 2L);

									}

								}

							}

						}

					} else if (block.getType() == Material.OAK_PLANKS) {

						if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
							Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

							if (sign.getLine(0).equals(ChatColor.YELLOW + "CONDITION")) {
								boolean isMeetingCondition = isMeetingCondition(sign, player, targetBlock);
								conditionX += 2;
								canExecute = isMeetingCondition;
							}

						}

					}

				}

				if (x >= 200) {
					break;
				}

			}

		}

		return cancel;
	}

	private static boolean isMeetingCondition(Sign sign, Player player, Block targetBlock) {
		boolean inversed = sign.getLine(3).equals(ChatColor.RED + "NOT");

		if (sign.getLine(1).equals(ChatColor.WHITE + "Is Looking At")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				String locationString = "";

				for (ItemStack item : inventory.getContents()) {

					if (item == null) {
						continue;
					}

					if (item.getType() == Material.PAPER) {
						locationString = ChatColor.stripColor(item.getItemMeta().getDisplayName());
						break;
					}

				}

				if (locationString.equals("")) {

					if (inversed) {
						return true;
					} else {
						return false;
					}

				}

				Location location = LocationUtils.stringToLocation(locationString);

				if (location == null) {

					if (inversed) {
						return true;
					} else {
						return false;
					}

				}

				if (targetBlock == null) {

					if (inversed) {
						return true;
					} else {
						return false;
					}

				}

				if (LocationUtils.locationToString(targetBlock.getLocation()).equals(locationString)) {

					if (inversed) {
						return false;
					} else {
						return true;
					}

				} else {

					if (inversed) {
						return true;
					} else {
						return false;
					}

				}

			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Is Sneaking")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {

				if (player.isSneaking()) {

					if (inversed) {
						return false;
					} else {
						return true;
					}

				} else {

					if (inversed) {
						return true;
					} else {
						return false;
					}

				}

			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Is Sprinting")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {

				if (player.isSprinting()) {

					if (inversed) {
						return false;
					} else {
						return true;
					}

				} else {

					if (inversed) {
						return true;
					} else {
						return false;
					}

				}

			}

		}

		return true;
	}

	private static void doAction(Sign sign, Player player) {

		if (sign.getLine(1).equals(ChatColor.WHITE + "Set gamemode to:")) {

			if (sign.getLine(2).equals(ChatColor.WHITE + "Survival")) {
				player.setGameMode(GameMode.SURVIVAL);
			} else if (sign.getLine(2).equals(ChatColor.WHITE + "Creative")) {
				player.setGameMode(GameMode.CREATIVE);
			} else if (sign.getLine(2).equals(ChatColor.WHITE + "Adventure")) {
				player.setGameMode(GameMode.ADVENTURE);
			} else if (sign.getLine(2).equals(ChatColor.WHITE + "Spectator")) {
				player.setGameMode(GameMode.SPECTATOR);
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

					if (item.getType() == Material.BOOK) {
						message += item.getItemMeta().getDisplayName();
					}

				}

				player.sendMessage(message);
			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Teleport")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				Location location;
				ItemStack item = null;

				for (ItemStack content : inventory.getContents()) {

					if (content == null) {
						continue;
					}

					item = content;
					break;
				}

				if (item == null) {
					return;
				}

				location = LocationUtils.stringToLocation(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

				if (location == null) {
					return;
				}

				player.teleport(location);
			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Give Items")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();

				for (ItemStack item : inventory.getContents()) {

					if (item == null) {
						continue;
					}

					player.getInventory().addItem(item);
				}

			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Clear Inventory")) {
			player.getInventory().clear();
		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Set Health")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				ItemStack item = null;
				double health = 0d;

				for (ItemStack content : inventory.getContents()) {

					if (content == null) {
						continue;
					}

					if (content.getType() != Material.SLIME_BALL) {
						continue;
					}

					item = content;
					break;
				}

				if (item == null) {
					return;
				}

				try {
					health = Double.parseDouble(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
				} catch (NumberFormatException exception) {
					return;
				}

				if (health >= 0) {
					player.setHealth(health);
				}

			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Set Max Health")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				ItemStack item = null;
				double health = 0d;

				for (ItemStack content : inventory.getContents()) {

					if (content == null) {
						continue;
					}

					if (content.getType() != Material.SLIME_BALL) {
						continue;
					}

					item = content;
					break;
				}

				if (item == null) {
					return;
				}

				try {
					health = Double.parseDouble(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
				} catch (NumberFormatException exception) {
					return;
				}

				if (health >= 1) {
					player.setMaxHealth(health);
				}

			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Set XP Level")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				ItemStack item = null;
				double level = 0d;

				for (ItemStack content : inventory.getContents()) {

					if (content == null) {
						continue;
					}

					if (content.getType() != Material.SLIME_BALL) {
						continue;
					}

					item = content;
					break;
				}

				if (item == null) {
					return;
				}

				try {
					level = Double.parseDouble(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
				} catch (NumberFormatException exception) {
					return;
				}

				if ((int) Math.round(level) >= 0) {
					player.setLevel((int) Math.round(level));
				}

			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Add XP Level")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				ItemStack item = null;
				double level = 0d;

				for (ItemStack content : inventory.getContents()) {

					if (content == null) {
						continue;
					}

					if (content.getType() != Material.SLIME_BALL) {
						continue;
					}

					item = content;
					break;
				}

				if (item == null) {
					return;
				}

				try {
					level = Double.parseDouble(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
				} catch (NumberFormatException exception) {
					return;
				}

				int x = player.getLevel() + ((int) Math.round(level));

				if (x >= 0) {
					player.setLevel(x);
				}

			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Play Music Disk")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				MusicType type = MusicType.NONE;

				for (ItemStack content : inventory.getContents()) {

					if (content == null) {
						continue;
					}

					if (content.getType() == Material.MUSIC_DISC_11) {
						type = MusicType._11;
					} else if (content.getType() == Material.MUSIC_DISC_13) {
						type = MusicType._13;
					} else if (content.getType() == Material.MUSIC_DISC_BLOCKS) {
						type = MusicType.BLOCKS;
					} else if (content.getType() == Material.MUSIC_DISC_CAT) {
						type = MusicType.CAT;
					} else if (content.getType() == Material.MUSIC_DISC_CHIRP) {
						type = MusicType.CHIRP;
					} else if (content.getType() == Material.MUSIC_DISC_FAR) {
						type = MusicType.FAR;
					} else if (content.getType() == Material.MUSIC_DISC_MALL) {
						type = MusicType.MALL;
					} else if (content.getType() == Material.MUSIC_DISC_MELLOHI) {
						type = MusicType.MELLOHI;
					} else if (content.getType() == Material.MUSIC_DISC_STAL) {
						type = MusicType.STAL;
					} else if (content.getType() == Material.MUSIC_DISC_STRAD) {
						type = MusicType.STRAD;
					} else if (content.getType() == Material.MUSIC_DISC_WAIT) {
						type = MusicType.WAIT;
					} else if (content.getType() == Material.MUSIC_DISC_WARD) {
						type = MusicType.WARD;
					}

					break;
				}

				if (type == MusicType._11) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_11, 10000f, 1f);
				} else if (type == MusicType._13) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_13, 10000f, 1f);
				} else if (type == MusicType.BLOCKS) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_BLOCKS, 10000f, 1f);
				} else if (type == MusicType.CAT) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_CAT, 10000f, 1f);
				} else if (type == MusicType.CHIRP) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_CHIRP, 10000f, 1f);
				} else if (type == MusicType.FAR) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_FAR, 10000f, 1f);
				} else if (type == MusicType.MALL) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_MALL, 10000f, 1f);
				} else if (type == MusicType.MELLOHI) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_MELLOHI, 10000f, 1f);
				} else if (type == MusicType.STAL) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_STAL, 10000f, 1f);
				} else if (type == MusicType.STRAD) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_STRAD, 10000f, 1f);
				} else if (type == MusicType.WAIT) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_WAIT, 10000f, 1f);
				} else if (type == MusicType.WARD) {
					player.playSound(player.getLocation(), Sound.MUSIC_DISC_WARD, 10000f, 1f);
				}

			}

		} else if (sign.getLine(1).equals(ChatColor.WHITE + "Set Texture Pack")) {
			Block chestBlock = sign.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);

			if (chestBlock.getType() == Material.CHEST) {
				Chest chest = (Chest) chestBlock.getState();
				Inventory inventory = chest.getInventory();
				ItemStack item = null;
				String texturePackUrl;

				for (ItemStack content : inventory.getContents()) {

					if (content == null) {
						continue;
					}

					if (content.getType() != Material.BOOK) {
						continue;
					}

					item = content;
					break;
				}

				if (item == null) {
					return;
				}

				texturePackUrl = ChatColor.stripColor(item.getItemMeta().getDisplayName());

				if (texturePackUrl != null) {
					player.setTexturePack(texturePackUrl);
				}

			}

		}

	}

}
