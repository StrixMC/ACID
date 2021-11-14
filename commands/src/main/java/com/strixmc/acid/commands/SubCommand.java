package com.strixmc.acid.commands;

import com.strixmc.acid.messages.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;


public interface SubCommand {
    /**
     * Returns name of this subCommand. (used in /mainCommand subCommandName)
     *
     * @return The name.
     */
    String getName();

    /**
     * @return Alternative command names.
     */
    default List<String> getAliases() {
        return new ArrayList<>();
    }

    /**
     * Returns sub-command description if it exists.
     *
     * @return The description.
     */
    default String getDescription() {
        return "";
    }

    /**
     * Returns the syntax of this subCommand. (ex. /troll swap &lt;player1&gt; &lt;player2&gt;)
     *
     * @return The syntax.
     */
    default String getSyntax() {
        if (getArgsCount() > 0) {
            StringJoiner joiner = new StringJoiner(" ", " ", "");
            for (int i = 0; i < getArgsCount(); i++) {
                joiner.add("arg" + i + 1);
            }
            return getName() + joiner;
        }
        return getName();
    }

    /**
     * @return Minimum arguments required by command.
     */
    default int getArgsCount() {
        return 0;
    }

    /**
     * Returns permission required to run this subCommand.
     *
     * @return The permission.
     */
    default String getPermission() {
        return null;
    }

    /**
     * @return No permission message.
     */
    default String getPermissionMessage() {
        return "You have no permission access to " + getName() + " command";
    }

    default boolean hasAccess(CommandSender sender) {
        // If command doesn't require admin permissions access is granted.
        if (!requireAdmin()) return true;
        // If sender is console access is granted.
        if (!(sender instanceof Player)) return true;
        // If permission it not null and player has permission access is granted.
        if (getPermission() != null && sender.hasPermission(getPermission())) return true;

        return false;
    }

    default boolean testPermission(CommandSender sender) {
        if (sender instanceof Player) {
            // If sub-command doesn't have a permission access denied.
            if (getPermission() == null) {
                sender.sendMessage("[Orion] SubCommand require admin permission but this doesn't exist!");
                return false;
            }

            if (!sender.hasPermission(getPermission())) {
                sender.sendMessage(MessageUtils.translate(getPermissionMessage()));
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the boolean if command can be used only by players.
     *
     * @return The boolean if command is player only.
     */

    default boolean requirePlayer() {
        return true;
    }

    /**
     * Returns the boolean if command sender needs to be an admin.
     *
     * @return The boolean if command is admin only.
     */

    default boolean requireAdmin() {
        return false;
    }

    /**
     * Returns the list of words to tab-complete on an index starting after the subCommand. (ex. /troll swap 0 1 | numbers representing the index of tabComplete)
     *
     * @param index The index to get tab-complete for.
     * @param args  All the args in the command, including the currently tab-completed one.
     * @return The list of words to tab-complete.
     */
    default List<String> getTabCompletion(int index, String[] args) {
        return Collections.emptyList();
    }

    /**
     * Performs the command.
     *
     * @param sender Sender, who has invoked the command. (either Player or Console)
     * @param args   The arguments after this subCommand.
     */
    void execute(CommandSender sender, String commandLabel, String[] args);
}