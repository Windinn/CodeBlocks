package com.windinn.codeblocks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class LocationUtils {

	private LocationUtils() {
		throw new IllegalAccessError();
	}

	public static String simpleLocationToString(Location location) {
		return Math.round(location.getX() * 100f) / 100f + " " + Math.round(location.getY() * 100f) / 100f + " "
				+ Math.round(location.getZ() * 100f) / 100f + " " + location.getWorld().getName();
	}

	public static Location simpleStringToLocation(String string) {
		String[] words = string.split(" ");
		Location location = new Location(Bukkit.getWorld(words[3]), Double.parseDouble(words[0]),
				Double.parseDouble(words[1]), Double.parseDouble(words[2]));
		return location;
	}

}
