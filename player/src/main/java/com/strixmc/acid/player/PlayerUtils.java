package com.strixmc.acid.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    /*
   Some useful player related stuff.
    */

    public static void clearInventory(Player player, boolean clearArmor) {
        player.getInventory().clear();
        if (clearArmor) player.getInventory().setArmorContents(null);
        player.updateInventory();
    }

    public static void clearEffects(Player player) {
        if (player.getActivePotionEffects().isEmpty()) return;
        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(activePotionEffect.getType());
        }
    }

    public static void toggleFlight(Player player) {
        player.setAllowFlight(!player.getAllowFlight());
        if (player.getAllowFlight()) player.setFlying(true);
    }


    public static void hideFromAll(Player player) {
        for (Player pl : getOnlinePlayers()) {
            pl.hidePlayer(player);
        }
    }

    public static void hideFromPlayers(Player player, List<Player> players) {
        for (Player pl : players) {
            pl.hidePlayer(player);
        }
    }

    public static void showToAll(Player player) {
        for (Player pl : getOnlinePlayers()) {
            pl.showPlayer(player);
        }
    }

    public static void showToPlayers(Player player, List<Player> players) {
        for (Player pl : players) {
            pl.showPlayer(player);
        }
    }

    public static List<Player> getOnlinePlayersWithPermission(String permission) {
        if (permission == null) return getOnlinePlayers();
        List<Player> players = new ArrayList<>();
        for (Player onlinePlayer : getOnlinePlayers()) {
            if (onlinePlayer.hasPermission(permission)) {
                players.add(onlinePlayer);
            }
        }
        return players;
    }

    public static List<Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }
}
