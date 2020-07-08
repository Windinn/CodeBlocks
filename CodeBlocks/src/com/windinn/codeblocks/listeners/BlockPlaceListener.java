package com.windinn.codeblocks.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.PistonBaseMaterial;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import com.windinn.codeblocks.utils.CodeUtils;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack item = event.getItemInHand();

		if (item == null) {
			return;
		}

		if (!item.hasItemMeta()) {
			return;
		}

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

		if (block.getType() == Material.DIAMOND_BLOCK
				&& item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Event Block")) {

			if (block.getRelative(BlockFace.NORTH).getType() != Material.AIR
					|| block.getRelative(BlockFace.EAST).getType() != Material.AIR) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "There is not enough place to place it here!");
				return;
			}

			block.getRelative(BlockFace.NORTH).setType(Material.STONE);
			block.getRelative(BlockFace.EAST).setType(Material.OAK_WALL_SIGN);

			BlockState state = block.getRelative(BlockFace.EAST).getState();
			Sign sign = (Sign) state;

			sign.setLine(0, ChatColor.GREEN + "EVENT");
			sign.setLine(1, ChatColor.WHITE + "Player Join Plot");

			WallSign wallSign = (WallSign) sign.getBlockData();
			wallSign.setFacing(BlockFace.EAST);

			sign.setBlockData(wallSign);
			sign.update();
			CodeUtils.addEvent(block, plotPlayer.getCurrentPlot());
		} else if (block.getType() == Material.COBBLESTONE
				&& item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Action Block")) {

			if (block.getRelative(BlockFace.NORTH).getType() != Material.AIR
					|| block.getRelative(BlockFace.EAST).getType() != Material.AIR
					|| block.getRelative(BlockFace.UP).getType() != Material.AIR) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "There is not enough place to place it here!");
				return;
			}

			block.getRelative(BlockFace.UP).setType(Material.CHEST);
			block.getRelative(BlockFace.NORTH).setType(Material.STONE);
			block.getRelative(BlockFace.EAST).setType(Material.OAK_WALL_SIGN);

			BlockState state = block.getRelative(BlockFace.EAST).getState();
			Sign sign = (Sign) state;

			sign.setLine(0, ChatColor.GOLD + "ACTION");
			sign.setLine(1, ChatColor.WHITE + "Set gamemode to:");
			sign.setLine(2, ChatColor.WHITE + "Survival");

			WallSign wallSign = (WallSign) sign.getBlockData();
			wallSign.setFacing(BlockFace.EAST);

			sign.setBlockData(wallSign);
			sign.update();
		} else if (block.getType() == Material.NETHERRACK
				&& item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Server Action Block")) {

			if (block.getRelative(BlockFace.NORTH).getType() != Material.AIR
					|| block.getRelative(BlockFace.EAST).getType() != Material.AIR
					|| block.getRelative(BlockFace.UP).getType() != Material.AIR) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "There is not enough place to place it here!");
				return;
			}

			block.getRelative(BlockFace.UP).setType(Material.CHEST);
			block.getRelative(BlockFace.NORTH).setType(Material.STONE);
			block.getRelative(BlockFace.EAST).setType(Material.OAK_WALL_SIGN);

			BlockState state = block.getRelative(BlockFace.EAST).getState();
			Sign sign = (Sign) state;

			sign.setLine(0, ChatColor.RED + "ACTION");
			sign.setLine(1, ChatColor.WHITE + "Cancel Event");

			WallSign wallSign = (WallSign) sign.getBlockData();
			wallSign.setFacing(BlockFace.EAST);

			sign.setBlockData(wallSign);
			sign.update();
		} else if (block.getType() == Material.REDSTONE_BLOCK
				&& item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Redstone Block")) {

			if (block.getRelative(BlockFace.NORTH).getType() != Material.AIR
					|| block.getRelative(BlockFace.EAST).getType() != Material.AIR
					|| block.getRelative(BlockFace.UP).getType() != Material.AIR) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "There is not enough place to place it here!");
				return;
			}

			block.getRelative(BlockFace.NORTH).setType(Material.STONE);
			block.getRelative(BlockFace.EAST).setType(Material.OAK_WALL_SIGN);

			BlockState state = block.getRelative(BlockFace.EAST).getState();
			Sign sign = (Sign) state;

			sign.setLine(0, ChatColor.RED + "REDSTONE");
			sign.setLine(1, ChatColor.WHITE + "1 tick");

			WallSign wallSign = (WallSign) sign.getBlockData();
			wallSign.setFacing(BlockFace.EAST);

			sign.setBlockData(wallSign);
			sign.update();
		} else if (block.getType() == Material.IRON_BLOCK
				&& item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Condition Block")) {

			if (block.getRelative(BlockFace.NORTH).getType() != Material.AIR
					&& block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getType() != Material.AIR
					&& block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
							.getType() != Material.AIR
					&& block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
							.getRelative(BlockFace.NORTH).getType() != Material.AIR
					|| block.getRelative(BlockFace.EAST).getType() != Material.AIR
					|| block.getRelative(BlockFace.UP).getType() != Material.AIR) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "There is not enough place to place it here!");
				return;
			}

			block.getRelative(BlockFace.UP).setType(Material.CHEST);
			block.getRelative(BlockFace.NORTH).setType(Material.STONE);
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setType(Material.PISTON);
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
					.getRelative(BlockFace.NORTH).setType(Material.PISTON);

			BlockState pistonState = block.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH)
					.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getState();
			PistonBaseMaterial piston = (PistonBaseMaterial) pistonState.getData();

			piston.setFacingDirection(BlockFace.SOUTH);

			pistonState.setData(piston);
			pistonState.update();

			block.getRelative(BlockFace.EAST).setType(Material.OAK_WALL_SIGN);

			BlockState state = block.getRelative(BlockFace.EAST).getState();
			Sign sign = (Sign) state;

			sign.setLine(0, ChatColor.YELLOW + "CONDITION");
			sign.setLine(1, ChatColor.WHITE + "Target Block");
			sign.setLine(2, ChatColor.WHITE + "Equals Location");

			WallSign wallSign = (WallSign) sign.getBlockData();
			wallSign.setFacing(BlockFace.EAST);

			sign.setBlockData(wallSign);
			sign.update();
		}

	}

}
