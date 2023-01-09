# AutoClass

A simple paper plugin to allow players to execute a set of commands given requirements.

## Plugin configuration

A sample plugin configuration is given below

```yaml
# For any commands %p will be replaced with the player's name and %w with the player's world
groups:
  First:
    description: "The first group"
    permission: "autoclass.first"
    commands:
      - "say hello %p in %w!"
  Second:
    description: "The second group"
    permission: "autoclass.second"
    requirements:
      expLevel: 30
      permission: "some.other.permission"
    commands:
      - "say hello %p in %w, you have 30 exp!"
```

## Plugin permissions

* `autoclass.commands.autoclass` - Allows a player to use the `/autoclass` command to access basic functionality
* `autoclass.commands.choose` - Allows a player to use the `/autoclass choose <group_name>` command to select a group
* `autoclass.commands.info` - Allows a player to user the `/autoclass info <group_name` command to view the info of a
  specific group

## Support Me

I will **never** charge money for the use of my plugins, however they do require a significant amount of work to
maintain and update. If you'd like to show your support and buy me a cup of tea sometime (I don't drink that horrid
coffee stuff :P) you can do so [here](https://www.paypal.me/zerthick)