package nl.tacticaldev.ddgvanish.commands;

import nl.tacticaldev.ddgvanish.DDGVanish;
import nl.tacticaldev.ddgvanish.module.VanishPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ddgvanish.vanish")) {
            player.sendMessage(DDGVanish.getPrefix() + DDGVanish.color("&cYou don't have the right permissions for this command."));
            return false;
        }

        VanishPlayer vanishPlayer = new VanishPlayer(player, DDGVanish.getInstance());

        if (!vanishPlayer.isVanished()) {
            DDGVanish.getVanishModule().addVanish(player);
            player.sendMessage(DDGVanish.getPrefix() + DDGVanish.color("&aVanish now &6Enabled&a!"));
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                onlinePlayers.hidePlayer(DDGVanish.getInstance(), player);
                if (onlinePlayers.hasPermission("ddgvanish.bypass")) {
                    onlinePlayers.showPlayer(DDGVanish.getInstance(), player);
                }
            }
        } else if (vanishPlayer.isVanished()) {
            DDGVanish.getVanishModule().removeVanish(player);
            player.sendMessage(DDGVanish.getPrefix() + DDGVanish.color("&aVanish now &6Disabled&a!"));
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                onlinePlayers.showPlayer(DDGVanish.getInstance(), player);
            }
        }

        return false;
    }
}
