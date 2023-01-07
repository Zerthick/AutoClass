package io.github.zerthick.autoclass.commandgroup.requirement;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface CommandGroupRequirement {

    boolean meetsRequirement(Player player);

    Component renderComponent();
}
