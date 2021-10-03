package com.strixmc.acid.messages;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MessageUtils {

    private final static int CENTER_PX = 154;

    public static void sendMessage(CommandSender sender, String text) {
        if (text.trim().isEmpty()) return;
        if (sender instanceof Player) {
            sender.sendMessage(translate(text));
            return;
        }
        sender.sendMessage(strip(text));
    }

    public static void sendProxiedMessage(net.md_5.bungee.api.CommandSender sender, String text) {
        if (text.trim().isEmpty()) return;
        if (sender instanceof ProxiedPlayer) {
            sender.sendMessage(translateProxy(text));
            return;
        }
        sender.sendMessage(stripProxy(text));
    }

    public static String strip(String text) {
        return ChatColor.stripColor(translate(text));
    }

    public static String stripProxy(String text) {
        return net.md_5.bungee.api.ChatColor.stripColor(translateProxy(text));
    }

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String[] translateToArray(String[] text) {
        return translate(Arrays.asList(text)).toArray(new String[text.length]);
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = Arrays.asList(lines.clone());
        toReturn.replaceAll(MessageUtils::translate);
        return toReturn;
    }

    public static List<String> translate(List<String> list) {
        list.replaceAll(MessageUtils::translate);
        return list;
    }

    public static String translateProxy(String text) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> translateProxy(List<String> list) {
        list.replaceAll(MessageUtils::translateProxy);
        return list;
    }

    public static void sendCenteredMessage(Player player, String message) {
        player.sendMessage(centeredMessage(message));
    }

    public static String centeredMessage(String message) {
        if (message == null || message.equals("")) return "";
        message = MessageUtils.translate(message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb.toString() + message;
    }
}
