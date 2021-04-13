package com.windinn.codeblocks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.plot.Plot;

public final class LocationUtils {

	private LocationUtils() {
		throw new IllegalAccessError();
	}

	public static String simpleLocationToString(SimpleLocation location) {
		Location bukkitLocation = location.getLocation();
		return location.getPlotX() + " " + location.getPlotY() + " " + Math.round(bukkitLocation.getX() * 100f) / 100f
				+ " " + Math.round(bukkitLocation.getY() * 100f) / 100f + " "
				+ Math.round(bukkitLocation.getZ() * 100f) / 100f + " " + bukkitLocation.getWorld().getName();
	}

	public static SimpleLocation simpleStringToLocation(String string) {
		String[] words = string.split(" ");
		Location bukkitLoc = new Location(Bukkit.getWorld(words[5]), Double.parseDouble(words[2]),
				Double.parseDouble(words[3]), Double.parseDouble(words[4]));
		Plot plot = BukkitUtil.getPlot(bukkitLoc);
		return new SimpleLocation(plot.getId().getX(), plot.getId().getY(), bukkitLoc);
	}

	public static String locationToString(Location location) {
		return Math.round(location.getX() * 100f) / 100f + " " + Math.round(location.getY() * 100f) / 100f + " "
				+ Math.round(location.getZ() * 100f) / 100f + " " + location.getWorld().getName();
	}

	public static Location stringToLocation(String string) {
		String[] words = string.split(" ");
		Location bukkitLoc = new Location(Bukkit.getWorld(words[3]), Double.parseDouble(words[0]),
				Double.parseDouble(words[1]), Double.parseDouble(words[2]));
		return bukkitLoc;
	}

}
