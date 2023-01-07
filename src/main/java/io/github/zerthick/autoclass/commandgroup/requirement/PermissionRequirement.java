package io.github.zerthick.autoclass.commandgroup.requirement;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class PermissionRequirement implements CommandGroupRequirement {

    private final String permission;

    public PermissionRequirement(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return player.hasPermission(permission);
    }

    @Override
    public Component renderComponent() {
        return Component.text("%s Permission".formatted(permission));
    }
}
