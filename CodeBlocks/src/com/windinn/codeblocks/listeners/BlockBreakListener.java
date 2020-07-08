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

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import com.windinn.codeblocks.utils.CodeUtils;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		PlotPlayer plotPlayer = PlotPlayer.get(player.getName());
		Plot currentPlot = null;
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

		if (!CodeUtils.isCoding.getOrDefault(player, false)) {
			return;
		}

		if (!plotFound) {
			player.sendMessage(ChatColor.RED + "You must code in your own plot!");
			event.setCancelled(true);
			return;
		}

		Location location = event.getBlock().getLocation();
		PlotArea plotArea = PlotSquared.get().getPlotAreaAbs(new com.plotsquared.core.location.Location(
				location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		Plot plot = plotArea.getPlotAbs(new com.plotsquared.core.location.Location(location.getWorld().getName(),
				location.getBlockX(), location.getBlockY(), location.getBlockZ()));

		if (plot != currentPlot && !player.getName().equals("_Minkizz_")) {
			player.sendMessage(ChatColor.RED + "The location must be located in your plot!");
			event.setCancelled(true);
			return;
		}

		if (event.isCancelled()) {
			return;
		}

		if (block.getType() == Material.DIAMOND_BLOCK) {
			Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();

			if (sign.getLine(0).equals(ChatColor.GREEN + "EVENT")) {
				event.setCancelled(true);
				block.getRelative(BlockFace.NORTH).setType(Material.AIR);
				block.getRelative(BlockFace.EAST).setType(Material.AIR);
				block.setType(Material.AIR);
				CodeUtils.removeEvent(block, plotPlayer.getCurrentPlot());
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
						CodeUtils.removeEvent(block.getRelative(BlockFace.WEST), plotPlayer.getCurrentPlot());
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
					CodeUtils.removeEvent(block.getRelative(BlockFace.SOUTH), plotPlayer.getCurrentPlot());
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
					block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(Material.AIR);
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

		} else {
			player.sendMessage(
					ChatColor.RED + "You can not destroy other blocks than code blocks in development mode!");
			event.setCancelled(true);
		}

	}

}
