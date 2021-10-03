package com.strixmc.acid.items;

import com.google.common.base.Strings;
import com.strixmc.acid.messages.MessageUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ItemStackBuilder extends ItemStack {

    public ItemStackBuilder() {
        this(Material.STONE);
    }

    public ItemStackBuilder(Material type) {
        super(type);
        lore(new ArrayList<>());
    }

    public ItemStackBuilder material(Material material) {
        setType(material);
        return this;
    }

    public ItemStackBuilder addAmount(int change) {
        setAmount(getAmount() + change);
        return this;
    }

    public ItemStackBuilder amount(int amount) {
        setAmount(amount);
        return this;
    }

    public ItemStackBuilder data(short data) {
        setDurability(data);
        return this;
    }

    public ItemStackBuilder data(MaterialData data) {
        setData(data);
        return this;
    }

    public ItemStackBuilder enchantments(HashMap<Enchantment, Integer> enchantments) {
        getEnchantments().keySet().forEach(this::removeEnchantment);
        addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder hideEnchantments() {
        getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStackBuilder name(String name) {
        if (name == null) return this;

        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(name.isEmpty() ? " " : MessageUtils.translate(name));
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder enchantedBook(Enchantment enchantment, int level) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) getItemMeta();
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemStackBuilder addBlankLineLore() {
        addLore(" ");
        return this;
    }

    public ItemStackBuilder addLineLore() {
        return addLineLore(20);
    }

    public ItemStackBuilder addLineLore(int length) {
        addLore("&8&m&l" + Strings.repeat("-", length));
        return this;
    }

    public ItemStackBuilder addLore(String... lore) {
        if (lore == null) return this;
        ItemMeta itemMeta = getItemMeta();
        List<String> original = itemMeta.getLore();
        if (original == null) original = new ArrayList<>();
        Collections.addAll(original, MessageUtils.translateToArray(lore));
        itemMeta.setLore(original);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addLore(List<String> lore) {
        if (lore == null) return this;
        ItemMeta itemMeta = getItemMeta();
        List<String> original = itemMeta.getLore();
        if (original == null) original = new ArrayList<>();
        original.addAll(MessageUtils.translate(lore));
        itemMeta.setLore(original);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder lore(String... lore) {
        if (lore == null) return this;
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(MessageUtils.translate(lore));
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder lore(List<String> lore) {
        if (lore == null) return this;
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(MessageUtils.translate(lore));
        setItemMeta(itemMeta);
        return this;
    }
}
