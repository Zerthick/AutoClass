package io.github.zerthick.autoclass.commandgroup.requirement;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ExpLevelRequirement implements CommandGroupRequirement {

    private final int expLevel;

    public ExpLevelRequirement(int expLevel) {
        this.expLevel = expLevel;
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return player.getLevel() >= expLevel;
    }

    @Override
    public Component renderComponent() {
        return Component.text("%d Experience Levels".formatted(expLevel));
    }
}
