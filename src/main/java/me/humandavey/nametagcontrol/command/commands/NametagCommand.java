package me.humandavey.nametagcontrol.command.commands;

import me.humandavey.nametagcontrol.NametagControl;
import me.humandavey.nametagcontrol.command.Command;
import me.humandavey.nametagcontrol.menu.Menu;
import me.humandavey.nametagcontrol.menu.menus.PagedMenu;
import me.humandavey.nametagcontrol.nametag.NametagManager;
import me.humandavey.nametagcontrol.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class NametagCommand extends Command {

	private Menu menu;

	public NametagCommand() {
		super("nametag", new String[]{"nametags"}, "Set, remove, and change a player's nametag!");

		ArrayList<ItemStack> heads = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) stack.getItemMeta();
			meta.setOwner(player.getName());
			meta.setLore(List.of("", Util.colorize("&eClick to edit!")));
			meta.setDisplayName(Util.colorize("&a" + player.getName()));
			stack.setItemMeta(meta);
			heads.add(stack);
		}
		menu = new PagedMenu("Edit player", 6, heads, 0);

		// TODO: Make menu work
	}

	@Override
	public void execute(Player player, String[] args) {
		if (player.hasPermission(NametagControl.getInstance().getConfig().getString("permission"))) {
			if (args.length == 0) {
				menu.open(player);
				return;
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
				player.sendMessage(Util.colorize("&cInvalid Usage: /nametag <prefix|color|suffix> <player> [text]"));
				return;
			}

			switch (args[0].toLowerCase()) {
				case "prefix" -> {
					Player target = Bukkit.getPlayer(args[1]);
					if (args.length >= 3) {
						String prefix = "";
						for (int i = 2; i < args.length; i++) prefix += args[i];
						NametagManager.setPrefix(target, Util.colorize(prefix));
						player.sendMessage(Util.colorize("&aSet &2" + target.getName() + "'s &aprefix to &r" + prefix));
					} else {
						NametagManager.setPrefix(target, "");
						player.sendMessage(Util.colorize("&aReset &2" + target.getName() + "'s &aprefix!"));
					}
				}
				case "color" -> {
					Player target = Bukkit.getPlayer(args[1]);
					if (args.length >= 3) {
						ChatColor color = ChatColor.valueOf(args[2]);
						NametagManager.setColor(target, color);
						player.sendMessage(Util.colorize("&aSet &2" + target.getName() + "'s &acolor to " + color + color.name()));
					} else {
						NametagManager.setColor(target, ChatColor.RESET);
						player.sendMessage(Util.colorize("&aReset &2" + target.getName() + "'s &acolor!"));
					}
				}
				case "suffix" -> {
					Player target = Bukkit.getPlayer(args[1]);
					if (args.length >= 3) {
						String suffix = "";
						for (int i = 2; i < args.length; i++) suffix += args[i];
						NametagManager.setSuffix(target, Util.colorize(suffix));
						player.sendMessage(Util.colorize("&aSet &2" + target.getName() + "'s &asuffix to &r" + suffix));
					} else {
						NametagManager.setSuffix(target, "");
						player.sendMessage(Util.colorize("&aReset &2" + target.getName() + "'s &asuffix!"));
					}
				}
				default -> {
					player.sendMessage(Util.colorize("&cInvalid Usage: /nametag <menu|prefix|color|suffix> <player> [text]"));
				}
			}
		} else {
			player.sendMessage(Util.colorize("&cYou don't have permission to use this command!"));
		}
	}
}
