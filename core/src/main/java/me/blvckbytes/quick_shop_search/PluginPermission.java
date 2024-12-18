package me.blvckbytes.quick_shop_search;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public enum PluginPermission {
  MAIN_COMMAND("command.qss"),
  LANGUAGE_COMMAND("command.qssl"),
  RELOAD_COMMAND("command.qssrl"),
  EMPTY_PREDICATE("empty-predicate"),
  FEATURE_SORT("feature.sort"),
  FEATURE_FILTER("feature.filter"),
  FEATURE_TELEPORT("feature.teleport"),
  FEATURE_TELEPORT_OTHER_WORLD("feature.teleport.other-world"),
  FEATURE_INTERACT("feature.interact"),
  FEATURE_INTERACT_OTHER_WORLD("feature.interact.other-world"),
  OTHER_WORLD("other-world"),
  ;

  private static final String PREFIX = "quickshopsearch";
  private final String node;

  PluginPermission(String node) {
    this.node = PREFIX + "." + node;
  }

  public boolean has(CommandSender sender) {
    if (sender instanceof ConsoleCommandSender)
      return true;

    if (sender instanceof Player player)
      return player.hasPermission(node);

    return false;
  }
}
