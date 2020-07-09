package com.windinn.codeblocks.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.plotsquared.core.player.PlotPlayer;
import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.EventType;

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof Player) {
			Player player = (Player) entity;
			PlotPlayer plotPlayer = PlotPlayer.get(player.getName());

			if (plotPlayer == null) {
				return;
			}

			if (!CodeUtils.isCoding.getOrDefault(player, false)) {

				if (CodeUtils.execute(player, EventType.PLAYER_DAMAGE, plotPlayer.getCurrentPlot(),
						player.getTargetBlock(null, 5))) {
					event.setCancelled(true);
				}

			}

		}

	}

}
