package com.windinn.codeblocks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.windinn.codeblocks.utils.UserDataHandler;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UserDataHandler userData = new UserDataHandler(player.getUniqueId());
		userData.createUser(player);
	}

}
