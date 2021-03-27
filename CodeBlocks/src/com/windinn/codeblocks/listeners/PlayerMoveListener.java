package com.windinn.codeblocks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.windinn.codeblocks.utils.CodeUtils;
import com.windinn.codeblocks.utils.EventType;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (!CodeUtils.isCoding.getOrDefault(player, false)) {
			CodeUtils.execute(player, EventType.PLAYER_MOVE, player.getTargetBlock(null, 5));
		}

	}

}
