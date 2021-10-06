package com.strixmc.acid.custom_events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BaseEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public void call() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
