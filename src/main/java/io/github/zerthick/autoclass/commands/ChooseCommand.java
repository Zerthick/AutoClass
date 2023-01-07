package io.github.zerthick.autoclass.commands;

import io.github.zerthick.autoclass.AutoClass;
import io.github.zerthick.autoclass.commandgroup.CommandGroup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChooseCommand extends SubCommand {
    public ChooseCommand(String name, String permission, AutoClass instance) {
        super(name, permission, instance);
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {

        if (sender instanceof Player player) {

            if (player.hasPermission(getPermission())) {
                if (args.length > 0) {
                    String groupName = args[0];
                    Optional<CommandGroup> commandGroupOptional = instance.getCommandGroupManager().getCommandGroup(groupName);
                    if (commandGroupOptional.isPresent()) {
                        CommandGroup commandGroup = commandGroupOptional.get();

                        if (!commandGroup.hasPermission(player)) {
                            player.sendMessage(Component.text("You don't have permission to select this group!", NamedTextColor.RED));
                            return true;
                        }

                        if (!commandGroup.hasRequirements(player)) {
                            player.sendMessage(Component.text("You don't meet all the requirements to select this group!", NamedTextColor.RED));
                            return true;
                        }

                        commandGroup.executeCommands(player);
                        player.sendMessage(Component.text("Command group: " + args[0] + " selected!", NamedTextColor.YELLOW));
                        return true;

                    } else {
                        player.sendMessage(Component.text("Unknown command group: " + args[0], NamedTextColor.RED));
                        return true;
                    }
                }
            } else {
                player.sendMessage(Component.text("You don't have permission to perform this command!", NamedTextColor.RED));
                return true;
            }

        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if (args.length == 0 && sender instanceof Player player) {
            return instance.getCommandGroupManager().getAvailableGroups(player).stream().map(CommandGroup::getName).toList();
        }

        return new ArrayList<>();
    }
}
