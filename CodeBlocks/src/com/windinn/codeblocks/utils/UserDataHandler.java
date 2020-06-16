package com.windinn.codeblocks.utils;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.windinn.codeblocks.CodeBlocks;

public class UserDataHandler {

	private UUID uuid;
	private File userFile;
	private FileConfiguration userConfig;

	public UserDataHandler(UUID uuid) {
		this.uuid = uuid;

		userFile = new File(JavaPlugin.getPlugin(CodeBlocks.class).getDataFolder() + "/players/", uuid + ".yml");

		userConfig = YamlConfiguration.loadConfiguration(userFile);
	}

	public void createUser(final Player player) {

		if (!(userFile.exists())) {

			try {

				YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(userFile);

				UserConfig.set("user.info.name", player.getName());
				UserConfig.set("user.info.uuid", player.getUniqueId().toString());
				UserConfig.set("user.info.ipAddress", player.getAddress().getAddress().getHostAddress());

				UserConfig.save(userFile);

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

	public FileConfiguration getUserFile() {
		return userConfig;
	}

	public void saveUserFile() {

		try {
			getUserFile().save(userFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
