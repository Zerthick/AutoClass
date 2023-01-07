package io.github.zerthick.autoclass.commandgroup;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandGroupManager {

    private final Map<String, CommandGroup> commandGroups;

    public CommandGroupManager(FileConfiguration config) {

        commandGroups = new HashMap<>();

        ConfigurationSection groups = config.getConfigurationSection("groups");
        if (groups != null) {
            for (String key : groups.getKeys(false)) {
                commandGroups.put(key.toLowerCase(), new CommandGroup(Objects.requireNonNull(groups.getConfigurationSection(key))));
            }
        }
    }

    public List<CommandGroup> getAvailableGroups(Player player) {
        return commandGroups.values().stream().filter((g) -> g.hasPermission(player)).toList();
    }

    public Optional<CommandGroup> getCommandGroup(String groupName) {
        return Optional.ofNullable(commandGroups.get(groupName.toLowerCase()));
    }

}
