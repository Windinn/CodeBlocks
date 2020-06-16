package com.windinn.codeblocks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.plotsquared.core.plot.Plot;

public final class LocationUtils {

	private LocationUtils() {
		throw new IllegalAccessError();
	}

	public static String locationToString(Location location, Plot plot) {
		return location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ() + " "
				+ location.getWorld().getName() + " " + plot.getId().getX() + " " + plot.getId().getY();
	}

	public static CustomLocation stringToLocation(String string) {
		String[] words = string.split(" ");
		Location location = new Location(Bukkit.getWorld(words[3]), Double.parseDouble(words[0]),
				Double.parseDouble(words[1]), Double.parseDouble(words[2]));
		return new CustomLocation(location, Integer.parseInt(words[4]), Integer.parseInt(words[5]));
	}

}
