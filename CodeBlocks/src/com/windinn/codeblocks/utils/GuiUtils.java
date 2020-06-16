package com.windinn.codeblocks.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public final class GuiUtils {

	private GuiUtils() {
		throw new IllegalAccessError();
	}

	public static ItemStack createItem(Material material, String displayName) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createItem(Material material, String displayName, boolean unbreakable) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemMeta.setUnbreakable(unbreakable);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createCustomSkull(int amount, String displayName, String lore, String texture) {
		texture = "http://textures.minecraft.net/texture/" + texture;

		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, amount);

		if (texture.isEmpty()) {
			return skull;
		}

		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setDisplayName(displayName);

		List<String> loreList = new ArrayList<>();
		loreList.add(lore);
		skullMeta.setLore(loreList);

		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.getEncoder()
				.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;
		try {
			profileField = skullMeta.getClass().getDeclaredField("profile");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		assert profileField != null;
		profileField.setAccessible(true);
		try {
			profileField.set(skullMeta, profile);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		skull.setItemMeta(skullMeta);
		return skull;
	}

	public static ItemStack createCustomSkull(String displayName, String lore, OfflinePlayer owner) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setDisplayName(displayName);

		List<String> loreList = new ArrayList<>();
		loreList.add(lore);
		skullMeta.setLore(loreList);

		skullMeta.setOwningPlayer(owner);

		skull.setItemMeta(skullMeta);
		return skull;
	}

	public static ItemStack createItem(Material material, String displayName, String... lore) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);

		if (lore != null) {
			List<String> loreList = new ArrayList<>();

			for (String str : lore) {
				loreList.add(str);
			}

			itemMeta.setLore(loreList);
		}

		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createItem(Material material, String displayName, int amount, String... lore) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);

		if (lore != null) {
			List<String> loreList = new ArrayList<>();

			for (String str : lore) {
				loreList.add(str);
			}

			itemMeta.setLore(loreList);
		}

		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemStack.setItemMeta(itemMeta);
		itemStack.setAmount(amount);
		return itemStack;
	}

	public static ItemStack createItem(Material material, String displayName, int amount, boolean unbreakable,
			String... lore) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);

		if (lore != null) {
			List<String> loreList = new ArrayList<>();

			for (String str : lore) {
				loreList.add(str);
			}

			itemMeta.setLore(loreList);
		}

		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		itemMeta.setUnbreakable(unbreakable);
		itemStack.setItemMeta(itemMeta);
		itemStack.setAmount(amount);
		return itemStack;
	}

}
