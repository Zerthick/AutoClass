package io.github.zerthick.autoclass.commandgroup;

import io.github.zerthick.autoclass.commandgroup.requirement.CommandGroupRequirement;
import io.github.zerthick.autoclass.commandgroup.requirement.RequirementFactory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandGroup {

    private static Server server = Bukkit.getServer();
    private final String name;
    private final String description;
    private final String permission;
    private final List<CommandGroupRequirement> requirements;
    private final List<String> commands;

    public CommandGroup(ConfigurationSection section) {
        name = section.getName();
        description = section.getString("description");
        permission = section.getString("permission");
        if (section.getConfigurationSection("requirements") != null) {
            requirements = RequirementFactory.fromConfigurationSection(Objects.requireNonNull(section.getConfigurationSection("requirements")));
        } else {
            requirements = new ArrayList<>();
        }
        commands = section.getStringList("commands");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<CommandGroupRequirement> getRequirements() {
        return requirements;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(permission);
    }

    public boolean hasRequirements(Player player) {
        for (CommandGroupRequirement requirement : requirements) {
            if (!requirement.meetsRequirement(player)) {
                return false;
            }
        }
        return true;
    }

    public void executeCommands(Player player) {
        commands.forEach(c -> executeCommand(c, player));
    }

    private void executeCommand(String command, Player player) {
        String replacedCommand = command.replaceAll("%p", player.getName()).replaceAll("%w", player.getWorld().getName());
        server.dispatchCommand(server.getConsoleSender(), replacedCommand);
    }

    public Component renderInfo(Player player) {
        Component component = Component.text("---%s---".formatted(name), NamedTextColor.BLUE)
                .appendNewline()
                .append(Component.text(description, NamedTextColor.WHITE))
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .append(Component.text("Requirements:", NamedTextColor.BLUE))
                .appendNewline();

        for (CommandGroupRequirement requirement : requirements) {

            NamedTextColor color;
            if (requirement.meetsRequirement(player)) {
                color = NamedTextColor.GREEN;
            } else {
                color = NamedTextColor.RED;
            }
            component = component.append(requirement.renderComponent().color(color)).appendNewline();
        }

        return component;
    }
}
