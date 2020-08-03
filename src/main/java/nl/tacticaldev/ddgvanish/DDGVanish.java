package nl.tacticaldev.ddgvanish;

import lombok.AccessLevel;
import lombok.Getter;
import nl.tacticaldev.ddgvanish.commands.VanishCommand;
import nl.tacticaldev.ddgvanish.listeners.PlayerJoinListener;
import nl.tacticaldev.ddgvanish.module.VanishModule;
import nl.tacticaldev.ddgvanish.module.VanishPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DDGVanish extends JavaPlugin {

    private static Logger logger = Logger.getLogger("DDGVanish");

    @Getter(AccessLevel.PUBLIC)
    private static DDGVanish instance;

    @Getter(AccessLevel.PUBLIC)
    private static transient VanishModule vanishModule;

    @Getter(AccessLevel.PUBLIC)
    private static String prefix = color("&7[&c&lD&f&lD&c&lG&7] ");

    long start, end;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.start = System.currentTimeMillis();

        // Module
        vanishModule = new VanishModule(this);

        // Commands
        getCommand("vanish").setExecutor(new VanishCommand());

        // Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        this.end = System.currentTimeMillis();
        logger.log(Level.INFO, "DDGVanish load {0}", start - end);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;

        for (Iterator<Player> it = vanishModule.getVanishedPlayers().iterator(); it.hasNext(); ) {
            Player player = it.next();

            vanishModule.removeVanish(player);
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                onlinePlayers.showPlayer(this, player);
            }
            player.sendMessage(getPrefix() + color("&aYou have been unvanished! Because the plugin has been disabled!"));
            return;
        }
    }

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
