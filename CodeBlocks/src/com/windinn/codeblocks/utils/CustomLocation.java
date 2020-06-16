package com.windinn.codeblocks.utils;

import org.bukkit.Location;

public class CustomLocation {

	private Location location;
	private int plotIdX, plotIdY;

	public CustomLocation(Location location, int plotIdX, int plotIdY) {
		this.location = location;
		this.plotIdX = plotIdX;
		this.plotIdY = plotIdY;
	}

	public Location getLocation() {
		return location;
	}

	public int getPlotIdX() {
		return plotIdX;
	}

	public int getPlotIdY() {
		return plotIdY;
	}

}
