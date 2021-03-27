package com.windinn.codeblocks.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.EventType;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (!CodeUtils.isCoding.getOrDefault(player, false)) {

			if (CodeUtils.execute(player, EventType.PLAYER_BLOCK_BREAK, player.getTargetBlock(null, 5))) {
				event.setCancelled(true);
			}

		}

		if (!CodeUtils.isCoding.getOrDefault(player, false)) {

			if (!player.isOp()) {

				if (block.getType() == Material.PISTON) {

					if (block.getRelative(BlockFace.SOUTH).getType() == Material.AIR) {
						Block block2 = block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH);
						Block block3 = block2.getRelative(BlockFace.SOUTH);

						if (block2.getType() == Material.PISTON || ((block2.getType() == Material.STONE)
								&& (block3.getType() == Material.COBBLESTONE || block3.getType() == Material.RED_WOOL
										|| block3.getType() == Material.NETHERRACK))) {
							player.sendMessage(
									ChatColor.RED + "You can't break codeblocks while being not in coding mode!");
							event.setCancelled(true);
						}

					} else {

						if (block.getRelative(BlockFace.NORTH).getType() == Material.AIR) {

							if (block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
									.getType() == Material.PISTON) {
								player.sendMessage(
										ChatColor.RED + "You can't break codeblocks while being not in coding mode!");
								event.setCancelled(true);
							}

						}

					}

				}

			}

			return;
		}

		if (event.isCancelled()) {
			return;
		}

		if (block.getType() == Material.DIAMOND_BLOCK) {

			if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
				Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.GREEN + "EVENT")) {
					event.setCancelled(true);
					block.getRelative(BlockFace.NORTH).setType(Material.AIR);
					block.getRelative(BlockFace.EAST).setType(Material.AIR);
					block.setType(Material.AIR);
					CodeUtils.removeEvent(block);
				}

			}

		} else if (block.getType() == Material.COBBLESTONE) {

			if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
				Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.GOLD + "ACTION")) {
					event.setCancelled(true);
					block.getRelative(BlockFace.UP).setType(Material.AIR);
					block.getRelative(BlockFace.NORTH).setType(Material.AIR);
					block.getRelative(BlockFace.EAST).setType(Material.AIR);
					block.setType(Material.AIR);
				}

			}

		} else if (block.getType() == Material.NETHERRACK) {

			if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
				Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.RED + "ACTION")) {
					event.setCancelled(true);
					block.getRelative(BlockFace.UP).setType(Material.AIR);
					block.getRelative(BlockFace.NORTH).setType(Material.AIR);
					block.getRelative(BlockFace.EAST).setType(Material.AIR);
					block.setType(Material.AIR);
				}

			}

		} else if (block.getType() == Material.RED_WOOL) {

			if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
				Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.RED + "REDSTONE")) {
					event.setCancelled(true);
					block.getRelative(BlockFace.UP).setType(Material.AIR);
					block.getRelative(BlockFace.NORTH).setType(Material.AIR);
					block.getRelative(BlockFace.EAST).setType(Material.AIR);
					block.setType(Material.AIR);
				}

			}

		} else if (block.getType() == Material.OAK_WALL_SIGN) {
			Sign sign = (Sign) block.getState();

			if (sign.getLine(0).equals(ChatColor.GREEN + "EVENT")) {

				if (block.getRelative(BlockFace.WEST).getType() == Material.DIAMOND_BLOCK) {

					if (block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getType() == Material.STONE) {
						event.setCancelled(true);
						block.setType(Material.AIR);
						block.getRelative(BlockFace.WEST).setType(Material.AIR);
						block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).setType(Material.AIR);
						CodeUtils.removeEvent(block.getRelative(BlockFace.WEST));
					}

				}

			} else if (sign.getLine(0).equals(ChatColor.GOLD + "ACTION")) {

				if (block.getRelative(BlockFace.WEST).getType() == Material.COBBLESTONE) {

					if (block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getType() == Material.STONE) {
						event.setCancelled(true);
						block.setType(Material.AIR);
						block.getRelative(BlockFace.WEST).setType(Material.AIR);
						block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).setType(Material.AIR);
						block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).setType(Material.AIR);
					}

				}

			} else if (sign.getLine(0).equals(ChatColor.RED + "ACTION")) {

				if (block.getRelative(BlockFace.WEST).getType() == Material.NETHERRACK) {

					if (block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getType() == Material.STONE) {
						event.setCancelled(true);
						block.setType(Material.AIR);
						block.getRelative(BlockFace.WEST).setType(Material.AIR);
						block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).setType(Material.AIR);
						block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).setType(Material.AIR);
					}

				}

			} else if (sign.getLine(0).equals(ChatColor.RED + "REDSTONE")) {

				if (block.getRelative(BlockFace.WEST).getType() == Material.RED_WOOL) {

					if (block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getType() == Material.STONE) {
						event.setCancelled(true);
						block.setType(Material.AIR);
						block.getRelative(BlockFace.WEST).setType(Material.AIR);
						block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).setType(Material.AIR);
						block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).setType(Material.AIR);
					}

				}

			} else if (sign.getLine(0).equals(ChatColor.YELLOW + "CONDITION")) {

				if (block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
						.getType() == Material.PISTON) {
					int pistonZ = block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH)
							.getRelative(BlockFace.NORTH).getLocation().getBlockZ();
					Block current = block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH)
							.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH);
					int i = 0;
					int z = pistonZ - 1;

					while (true) {
						current = new Location(block.getLocation().getWorld(),
								block.getRelative(BlockFace.WEST).getLocation().getBlockX(),
								block.getLocation().getBlockY(), z).getBlock();

						if (current.getType() == Material.PISTON) {
							break;
						}

						if (i >= 128) {
							break;
						}

						current.getRelative(BlockFace.EAST).setType(Material.AIR);
						current.getRelative(BlockFace.UP).setType(Material.AIR);
						current.setType(Material.AIR);
						i++;
						z--;
					}

					event.setCancelled(true);
					block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).setType(Material.AIR);
					block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).setType(Material.AIR);
					block.getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
							.setType(Material.AIR);
					block.setType(Material.AIR);
					current.getRelative(BlockFace.EAST).setType(Material.AIR);
					current.getRelative(BlockFace.UP).setType(Material.AIR);
					current.setType(Material.AIR);
					block.getRelative(BlockFace.WEST).setType(Material.AIR);
				}

			}

		} else if (block.getType() == Material.STONE) {

			if (block.getRelative(BlockFace.SOUTH).getType() == Material.DIAMOND_BLOCK) {
				Sign sign = (Sign) block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.GREEN + "EVENT")) {
					event.setCancelled(true);
					block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setType(Material.AIR);
					block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
					block.setType(Material.AIR);
					CodeUtils.removeEvent(block.getRelative(BlockFace.SOUTH));
				}

			} else if (block.getRelative(BlockFace.SOUTH).getType() == Material.COBBLESTONE) {
				Sign sign = (Sign) block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.GOLD + "ACTION")) {
					event.setCancelled(true);
					block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(Material.AIR);
					block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setType(Material.AIR);
					block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
					block.setType(Material.AIR);
				}

			} else if (block.getRelative(BlockFace.SOUTH).getType() == Material.NETHERRACK) {
				Sign sign = (Sign) block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.RED + "ACTION")) {
					event.setCancelled(true);
					block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(Material.AIR);
					block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setType(Material.AIR);
					block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
					block.setType(Material.AIR);
				}

			} else if (block.getRelative(BlockFace.SOUTH).getType() == Material.RED_WOOL) {
				Sign sign = (Sign) block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.RED + "REDSTONE")) {
					event.setCancelled(true);
					block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setType(Material.AIR);
					block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
					block.setType(Material.AIR);
				}

			} else if (block.getRelative(BlockFace.SOUTH).getType() == Material.OAK_PLANKS) {

				if (block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST)
						.getType() == Material.OAK_WALL_SIGN) {
					Sign sign = (Sign) block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getState();

					if (sign.getLine(0).equals(ChatColor.YELLOW + "CONDITION")) {

						if (block.getRelative(BlockFace.NORTH).getType() == Material.PISTON) {
							int pistonZ = block.getRelative(BlockFace.NORTH).getLocation().getBlockZ();
							Block current = block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH);
							int i = 0;
							int z = pistonZ - 1;

							while (true) {
								current = new Location(block.getLocation().getWorld(), block.getLocation().getBlockX(),
										block.getLocation().getBlockY(), z).getBlock();

								if (current.getType() == Material.PISTON) {
									break;
								}

								if (i >= 128) {
									break;
								}

								current.getRelative(BlockFace.EAST).setType(Material.AIR);
								current.getRelative(BlockFace.UP).setType(Material.AIR);
								current.setType(Material.AIR);
								i++;
								z--;
							}

							event.setCancelled(true);
							block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setType(Material.AIR);
							block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(Material.AIR);
							block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
							block.getRelative(BlockFace.NORTH).setType(Material.AIR);
							current.getRelative(BlockFace.EAST).setType(Material.AIR);
							current.getRelative(BlockFace.UP).setType(Material.AIR);
							current.setType(Material.AIR);
							block.setType(Material.AIR);
						}

					}

				}

			}

		} else if (block.getType() == Material.CHEST) {

			if (block.getRelative(BlockFace.DOWN).getType() == Material.COBBLESTONE) {
				Sign sign = (Sign) block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.GOLD + "ACTION")) {

					if (block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getType() == Material.STONE) {
						event.setCancelled(true);
						block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).setType(Material.AIR);
						block.getRelative(BlockFace.DOWN).setType(Material.AIR);
						block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).setType(Material.AIR);
						block.setType(Material.AIR);
					}

				}

			} else if (block.getRelative(BlockFace.DOWN).getType() == Material.NETHERRACK) {
				Sign sign = (Sign) block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.RED + "ACTION")) {

					if (block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getType() == Material.STONE) {
						event.setCancelled(true);
						block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).setType(Material.AIR);
						block.getRelative(BlockFace.DOWN).setType(Material.AIR);
						block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).setType(Material.AIR);
						block.setType(Material.AIR);
					}

				}

			} else if (block.getRelative(BlockFace.DOWN).getType() == Material.OAK_PLANKS) {

				if (block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
					Sign sign = (Sign) block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getState();

					if (sign.getLine(0).equals(ChatColor.YELLOW + "CONDITION")) {

						if (block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
								.getType() == Material.PISTON) {
							int pistonZ = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH)
									.getRelative(BlockFace.NORTH).getLocation().getBlockZ();
							Block current = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH)
									.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH);
							int i = 0;
							int z = pistonZ - 1;

							while (true) {
								current = new Location(block.getLocation().getWorld(),
										block.getRelative(BlockFace.DOWN).getLocation().getBlockX(),
										block.getRelative(BlockFace.DOWN).getLocation().getY(), z).getBlock();

								if (current.getType() == Material.PISTON) {
									break;
								}

								if (i >= 128) {
									break;
								}

								current.getRelative(BlockFace.EAST).setType(Material.AIR);
								current.getRelative(BlockFace.UP).setType(Material.AIR);
								current.setType(Material.AIR);
								i++;
								z--;
							}

							event.setCancelled(true);
							block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).setType(Material.AIR);
							block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
									.setType(Material.AIR);
							block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).setType(Material.AIR);
							block.getRelative(BlockFace.DOWN).setType(Material.AIR);
							current.getRelative(BlockFace.EAST).setType(Material.AIR);
							current.getRelative(BlockFace.UP).setType(Material.AIR);
							current.setType(Material.AIR);
							block.setType(Material.AIR);
						}

					}

				}

			}

		} else if (block.getType() == Material.OAK_PLANKS) {

			if (block.getRelative(BlockFace.EAST).getType() == Material.OAK_WALL_SIGN) {
				Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

				if (sign.getLine(0).equals(ChatColor.YELLOW + "CONDITION")) {

					if (block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getType() == Material.PISTON) {
						int pistonZ = block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getLocation()
								.getBlockZ();
						Block current = block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
								.getRelative(BlockFace.NORTH);
						int i = 0;
						int z = pistonZ - 1;

						while (true) {
							current = new Location(block.getLocation().getWorld(), block.getLocation().getBlockX(),
									block.getLocation().getBlockY(), z).getBlock();

							if (current.getType() == Material.PISTON) {
								break;
							}

							if (i >= 128) {
								break;
							}

							current.getRelative(BlockFace.EAST).setType(Material.AIR);
							current.getRelative(BlockFace.UP).setType(Material.AIR);
							current.setType(Material.AIR);
							i++;
							z--;
						}

						event.setCancelled(true);
						block.getRelative(BlockFace.UP).setType(Material.AIR);
						block.getRelative(BlockFace.NORTH).setType(Material.AIR);
						block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setType(Material.AIR);
						block.getRelative(BlockFace.EAST).setType(Material.AIR);
						current.getRelative(BlockFace.EAST).setType(Material.AIR);
						current.getRelative(BlockFace.UP).setType(Material.AIR);
						current.setType(Material.AIR);
						block.setType(Material.AIR);
					}

				}

			}

		} else if (block.getType() == Material.PISTON) {
			player.sendMessage(ChatColor.RED + "You can't break pistons in code mode!");
			event.setCancelled(true);
		}

	}

}
