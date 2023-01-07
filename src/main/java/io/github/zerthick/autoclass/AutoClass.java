package io.github.zerthick.autoclass;

import io.github.zerthick.autoclass.commandgroup.CommandGroupManager;
import io.github.zerthick.autoclass.commands.CommandManger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class AutoClass extends JavaPlugin {

    private Logger logger;
    private CommandGroupManager commandGroupManager;

    @Override
    public void onEnable() {
        logger = getLogger();

        saveDefaultConfig();
        commandGroupManager = new CommandGroupManager(this.getConfig());

        Objects.requireNonNull(getCommand("autoclass")).setExecutor(new CommandManger(this));

        logger.info(String.format("%s version %s by %s enabled!", getDescription().getName(), getDescription().getVersion(), getDescription().getAuthors()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public CommandGroupManager getCommandGroupManager() {
        return commandGroupManager;
    }
}
