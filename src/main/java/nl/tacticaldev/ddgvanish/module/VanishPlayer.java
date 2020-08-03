package nl.tacticaldev.ddgvanish.module;

import lombok.AccessLevel;
import lombok.Getter;
import nl.tacticaldev.ddgvanish.DDGVanish;
import nl.tacticaldev.ddgvanish.VanishConfig;
import nl.tacticaldev.ddgvanish.config.VanishUserConf;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.logging.Level;

public class VanishPlayer {

    @Getter(AccessLevel.PUBLIC)
    private Player base;

    private DDGVanish ddgVanish;

    //    private VanishConfig config;
    private final VanishUserConf config;

    public VanishPlayer(Player base, DDGVanish ddgVanish) {
        this.base = base;
        this.ddgVanish = ddgVanish;
        File folder = new File(ddgVanish.getDataFolder(), "userdata");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filename;
        try {
            filename = base.getUniqueId().toString();
        } catch (Throwable ex) {
            ddgVanish.getLogger().warning("Falling back to old username system for " + base.getName());
            filename = base.getName();
        }

        config = new VanishUserConf(base.getName(), base.getUniqueId(), new File(folder, filename + ".yml"));
        config.load();
    }

    public void create() {
        if (!config.hasProperty("name")) {
            config.setProperty("name", getBase().getName());
            config.setProperty("ip", getBase().getAddress().getAddress().toString().replace("/", ""));
            config.setProperty("vanished", false);
            config.save();

            DDGVanish.getInstance().getLogger().log(Level.INFO, "Created new player from file " + config.getFile().getAbsolutePath());
        }

        config.load();
    }

    public boolean isVanished() {
        return config.getBoolean("vanished");
    }

    public void setVanished(boolean bool) {
        config.setProperty("vanished", bool);
        config.save();
    }
}
