package nl.tacticaldev.ddgvanish.listeners;

import nl.tacticaldev.ddgvanish.DDGVanish;
import nl.tacticaldev.ddgvanish.module.VanishPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        VanishPlayer vanishPlayer = new VanishPlayer(event.getPlayer(), DDGVanish.getInstance());

        vanishPlayer.create();

        DDGVanish.getVanishModule().addVanishToJoin(event);
    }

}
