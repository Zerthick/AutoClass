package io.github.zerthick.autoclass.commands;

import io.github.zerthick.autoclass.AutoClass;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    protected final AutoClass instance;
    private final String name;
    private final String permission;

    public SubCommand(String name, String permission, AutoClass instance) {
        this.name = name;
        this.permission = permission;
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public abstract boolean perform(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(CommandSender sender, String[] args);
}
