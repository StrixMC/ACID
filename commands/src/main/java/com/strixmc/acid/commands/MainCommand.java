package com.strixmc.acid.commands;

import com.strixmc.acid.commands.argumentmatcher.StartingWithStringArgumentMatcher;
import com.strixmc.acid.messages.Placeholder;
import com.strixmc.acid.messages.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MainCommand implements TabExecutor {

    protected final Set<SubCommand> subCommands = new HashSet<>();
    protected final ArgumentMatcher argumentMatcher;

    public MainCommand() {
        this(new StartingWithStringArgumentMatcher());
    }

    public MainCommand(ArgumentMatcher argumentMatcher) {
        this.argumentMatcher = argumentMatcher;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /* Send sender a help if he doesn't use any subCommand and has permission for the help subCommand */
        if (args.length == 0) {
            SubCommand helpSC = getHelpSubCommand();

            /* If help sub-command exits is tested for access. */
            if (helpSC != null) {
                if (!helpSC.hasAccess(sender)) {
                    /* Check if sender doesn't have permissions tu run the sub-command.*/
                    if (!helpSC.testPermission(sender)) return true;
                }

                /* Run help sub-command. */
                helpSC.execute(sender, label, args);
                return true;
            }
        }

        /* Gets the subcommand by the name in first argument. Or help, if the subCommand doesn't exist. */
        SubCommand subCommand = getSubCommandByAliasOrName(args[0]);

        /* If sub-command doesn't exist code doesn't continue. */
        if (subCommand == null) return true;

        /* If sender doesn't have access to sub-command code doesn't continue. */
        if (!subCommand.hasAccess(sender)) {
            if (!subCommand.testPermission(sender)) return true;
        }

        /* If sub-command require a player */
        if (subCommand.requirePlayer() && !(sender instanceof Player)) {
            sender.sendMessage("This command cannot be used in console.");
            return true;
        }

        /* If current arguments are less than needed this is checked. */
        if (args.length < subCommand.getArgsCount()) {
            sender.sendMessage(StringUtils.replace(subCommand.getSyntax(), new Placeholder("$command", label)));
            return true;
        }

        /* Execute subcommand. */
        subCommand.execute(sender, label, Arrays.copyOfRange(args, 1, args.length));
        return false;
    }

    private SubCommand getSubCommandByAliasOrName(String arg) {
        return subCommands.stream().filter(sc ->
                        sc.getName().equalsIgnoreCase(arg) ||
                                (!sc.getAliases().isEmpty() && sc.getAliases().stream().
                                        anyMatch(alias -> alias.equalsIgnoreCase(arg)))).
                findAny().
                orElse(getHelpSubCommand());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        /* Return if there is nothing to tab complete. */
        if (args.length == 0) return null;

        /* If it's the first argument, that means that a subCommand need to be completed. */
        if (args.length == 1) {
            /* Filtered list with sub-command suggestions that player has access. */
            List<String> subCommandsTC = subCommands.stream().filter(sc -> sc.hasAccess(sender)).map(SubCommand::getName).collect(Collectors.toList());
            //List<String> subCommandsTC = subCommands.stream().map(SubCommand::getName).collect(Collectors.toList());
            //List<String> subCommandsTC = subCommands.stream().filter(sc -> (sc.requireAdmin() && (sc.getPermission() != null && sender.hasPermission(sc.getPermission())))).map(SubCommand::getName).collect(Collectors.toList());
            return getMatchingStrings(subCommandsTC, args[args.length - 1], argumentMatcher);
        }

        /* Gets the subcommand by the name in first argument. */
        SubCommand subCommand = subCommands.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findAny().orElse(null);
        if (subCommand == null) return null;

        /* Gets the tabCompletion from the subCommand. */
        List<String> subCommandTB = subCommand.getTabCompletion(args.length - 2, args);
        return getMatchingStrings(subCommandTB, args[args.length - 1], argumentMatcher);
    }

    /**
     * Returns a new list of tabCompletions based on unfinished argument filtered by selected ArgumentMatcher.
     *
     * @param tabCompletions  The source tabCompletions.
     * @param arg             The argument string.
     * @param argumentMatcher The ArgumentMather.
     * @return A list of new tabCompletions.
     */
    private static List<String> getMatchingStrings(List<String> tabCompletions, String arg, ArgumentMatcher argumentMatcher) {
        if (tabCompletions == null || arg == null) return null;

        List<String> result = argumentMatcher.filter(tabCompletions, arg);

        Collections.sort(result);

        return result;
    }


    /**
     * Registers the bukkit command.
     *
     * @param main    Main class of your plugin.
     * @param cmdName Name of the command to register. !!! Has to be the same as one in the plugin.yml !!!
     */
    public void registerMainCommand(JavaPlugin main, String cmdName) {
        PluginCommand cmd = main.getCommand(cmdName);

        cmd.setExecutor(this);
        cmd.setTabCompleter(this);
    }

    /**
     * Adds sub-command to sub-commands set collection.
     *
     * @param subCommand SubCommand to add.
     */
    private void addSubCommand(SubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    /**
     * Adds all specified sub-commands to the subCommands set collection.
     *
     * @param subCommands Specified sub-commands.
     */
    public void registerSubCommands(SubCommand... subCommands) {
        Arrays.stream(subCommands).forEach(this::addSubCommand);
    }

    /**
     * Returns the help sub-command from subCommands set. By default, returns subCommand named "help".
     *
     * @return the help sub-command.
     */
    protected SubCommand getHelpSubCommand() {
        return subCommands.stream().filter(sc -> sc.getName().equalsIgnoreCase("help")).findAny().orElse(null);
    }

    /**
     * Returns a copy of set of subCommands used in this MainCommand.
     *
     * @return The set of subCommands of this MainCommand.
     */
    public Set<SubCommand> getSubCommands() {
        return new HashSet<>(subCommands);
    }
}