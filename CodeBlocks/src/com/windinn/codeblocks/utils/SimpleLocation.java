package com.windinn.codeblocks.utils;

import org.bukkit.Location;

public class SimpleLocation {

	private int plotX, plotY;
	private Location location;

	public SimpleLocation(int plotX, int plotY, Location location) {
		this.plotX = plotX;
		this.plotY = plotY;
		this.location = location;
	}

	public int getPlotX() {
		return plotX;
	}

	public int getPlotY() {
		return plotY;
	}

	public Location getLocation() {
		return location;
	}

}
