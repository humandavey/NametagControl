package me.humandavey.nametagcontrol;

import me.humandavey.nametagcontrol.command.commands.NametagCommand;
import me.humandavey.nametagcontrol.nametag.NametagManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NametagControl extends JavaPlugin {

	private static NametagControl instance;

	@Override
	public void onEnable() {
		instance = this;

		setupConfigs();
		setupManagers();
		registerListeners();
		registerCommands();
	}

	@Override
	public void onDisable() {

	}

	private void setupConfigs() {
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	private void setupManagers() {

	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new NametagManager(), this);
	}

	private void registerCommands() {
		new NametagCommand();
	}

	public static NametagControl getInstance() {
		return instance;
	}
}
