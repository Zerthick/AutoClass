package io.github.zerthick.autoclass.commandgroup.requirement;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class RequirementFactory {

    public static List<CommandGroupRequirement> fromConfigurationSection(ConfigurationSection section) {

        List<CommandGroupRequirement> requirements = new ArrayList<>();

        for (String key : section.getKeys(false)) {
            switch (key) {
                case "expLevel" -> {
                    int level = section.getInt(key);
                    requirements.add(new ExpLevelRequirement(level));
                }
                case "permission" -> {
                    String permission = section.getString(key);
                    requirements.add(new PermissionRequirement(permission));
                }
            }
        }

        return requirements;
    }

}
