package nl.tacticaldev.ddgvanish.module;

import lombok.AccessLevel;
import lombok.Getter;
import nl.tacticaldev.ddgvanish.DDGVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class VanishModule {

    @Getter(AccessLevel.PUBLIC)
    private ArrayList<Player> vanishedPlayers;
    private DDGVanish ddgVanish;

    VanishPlayer vanishPlayer;

    public VanishModule(final DDGVanish ddgVanish) {
        this.ddgVanish = ddgVanish;
        vanishedPlayers = new ArrayList<>();
    }

    public void addVanish(final Player player) {
        vanishedPlayers.add(player);
        vanishPlayer = new VanishPlayer(player, ddgVanish);

        vanishPlayer.setVanished(true);
    }

    public void removeVanish(final Player player) {
        vanishedPlayers.remove(player);
        vanishPlayer = new VanishPlayer(player, ddgVanish);

        vanishPlayer.setVanished(false);
    }

    public void addVanishToJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (Iterator<Player> it = vanishedPlayers.iterator(); it.hasNext(); ) {
            Player vanishPlayer = it.next();
            player.hidePlayer(ddgVanish, vanishPlayer);
            if (player.hasPermission("ddgvanish.bypass")) {
                player.showPlayer(ddgVanish, vanishPlayer);
            }
        }
    }
}
