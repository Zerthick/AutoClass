package io.github.zerthick.autoclass.commands;

import io.github.zerthick.autoclass.AutoClass;
import io.github.zerthick.autoclass.commandgroup.CommandGroup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CommandManger implements TabExecutor {

    private final Map<String, SubCommand> subCommands;
    private final AutoClass instance;

    public CommandManger(AutoClass instance) {
        this.instance = instance;
        subCommands = new HashMap<>();

        registerSubCommand(new ChooseCommand("choose", "autoclass.commands.choose", instance));
        registerSubCommand(new InfoCommand("info", "autoclass.commands.info", instance));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {

            if (args.length > 0) {
                Optional<SubCommand> subCommandOptional = getSubCommand(args[0]);
                if (subCommandOptional.isPresent()) {
                    return subCommandOptional.get().perform(player, Arrays.copyOfRange(args, 1, args.length));
                }
            } else {
                List<CommandGroup> availableGroups = instance.getCommandGroupManager().getAvailableGroups(player);

                List<CommandGroup> metGroups = availableGroups.stream().filter(g -> g.hasRequirements(player)).toList();
                List<CommandGroup> unmetGroups = availableGroups.stream().filter(g -> !g.hasRequirements(player)).toList();

                Component component = Component.text("---Available Groups---", NamedTextColor.BLUE)
                        .appendNewline();
                for (TextComponent c : buildGroupComponents(metGroups, NamedTextColor.GREEN)) {
                    component = component.append(c).append(Component.space());
                }

                component = component.appendNewline().append(Component.text("---Unavailable Groups---", NamedTextColor.BLUE))
                        .appendNewline();
                for (TextComponent c : buildGroupComponents(unmetGroups, NamedTextColor.RED)) {
                    component = component.append(c).append(Component.space());
                }

                player.sendMessage(component);
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return subCommands.values().stream().map(SubCommand::getName).toList();
        } else if (args.length > 1) {
            Optional<SubCommand> subCommandOptional = getSubCommand(args[0]);

            if (subCommandOptional.isPresent()) {
                return subCommandOptional.get().tabComplete(sender, Arrays.copyOfRange(args, 2, args.length));
            }
        }

        return new ArrayList<>();
    }

    private Optional<SubCommand> getSubCommand(String name) {
        return Optional.ofNullable(subCommands.get(name.toLowerCase()));
    }

    public void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName().toLowerCase(), subCommand);
    }

    private List<TextComponent> buildGroupComponents(List<CommandGroup> groups, NamedTextColor color) {

        List<TextComponent> out = new ArrayList<>();
        for (CommandGroup group : groups) {
            out.add(Component.text(group.getName())
                    .color(color)
                    .decorate(TextDecoration.UNDERLINED)
                    .clickEvent(ClickEvent.runCommand("/autoclass choose " + group.getName()))
                    .hoverEvent(HoverEvent.showText(Component.text(group.getDescription())))
            );
        }
        return out;
    }
}
