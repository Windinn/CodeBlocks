package com.windinn.codeblocks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.windinn.codeblocks.commands.CodeCommand;
import com.windinn.codeblocks.commands.PlayCommand;
import com.windinn.codeblocks.listeners.AsyncPlayerChatListener;
import com.windinn.codeblocks.listeners.BlockBreakListener;
import com.windinn.codeblocks.listeners.BlockPlaceListener;
import com.windinn.codeblocks.listeners.EntityDamageListener;
import com.windinn.codeblocks.listeners.InventoryClickListener;
import com.windinn.codeblocks.listeners.PlayerInteractListener;
import com.windinn.codeblocks.listeners.PlayerJoinListener;
import com.windinn.codeblocks.listeners.PlayerMoveListener;
import com.windinn.codeblocks.utils.CodeUtils;

public class CodeBlocks extends JavaPlugin {

	@Override
	public void onEnable() {
		super.onEnable();

		saveDefaultConfig();

		getCommand("code").setExecutor(new CodeCommand());
		getCommand("play").setExecutor(new PlayCommand());

		getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
		getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
		getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);

		for (Player player : Bukkit.getOnlinePlayers()) {

			for (Player player2 : Bukkit.getOnlinePlayers()) {
				player.showPlayer(player2);
				player2.showPlayer(player);
			}

		}

		CodeUtils.setPlugin(this);
	}

	@Override
	public void onDisable() {
		super.onDisable();
		Bukkit.getScheduler().cancelTasks(this);
	}

}
