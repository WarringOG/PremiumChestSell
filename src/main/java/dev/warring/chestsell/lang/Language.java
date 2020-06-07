package dev.warring.chestsell.lang;

import dev.warring.chestsell.PremiumChestSell;
import dev.warring.core.library.utils.ChatCenter;
import dev.warring.core.library.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum Language {

    NO_CHEST(true, false, "&cThat sign is not attached to a chest."),
    CREATED_CHEST(true, false, "&cYou have created a chest sell chest."),
    SOLD_NOTHING(true, false, "&cThere are no sellable items in the chest"),
    SOLD_ITEMS(true, false, "&cYou have sold %amount% items for %price%"),
    BROKEN_SIGN(true, false, "&cYou have broken a chest sell sign.");

    private List<String> message;
    private boolean centered;
    private boolean enabled;

    private Language(boolean enabled, boolean centered, String... message) {
        this.enabled = enabled;
        this.centered = centered;
        this.message = Arrays.asList(message);
    }

    public boolean isCentered() {
        return centered;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public void sendMessage(Player p) {
        if (!enabled) return;
        if (centered) {
            displayCentered(p);
        } else {
            display(p);
        }
    }

    public void broadcastMessage() {
        if (!enabled) return;
        if (centered) {
            broadcastCentered();
        } else {
            broadcast();
        }
    }

    public void display(Player p, String[] replace, String[] replacements) {
        if (!enabled) return;
        for (String s : message) {
            for (int i = 0; i < replace.length; ++i) {
                s = Utils.toColor(s);
                s = s.replaceAll(replace[i], replacements[i]);
            }
            p.sendMessage(s);
        }
    }

    public void display(CommandSender p, String[] replace, String[] replacements) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            for (int i = 0; i < replace.length; ++i) {
                s = s.replaceAll(replace[i], replacements[i]);
            }
            p.sendMessage(s);
        }
    }

    public void display(CommandSender p) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            p.sendMessage(s);
        }
    }

    public void display(Player p) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            p.sendMessage(s);
        }
    }

    public void display(Player p, String replace, String replacement) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            s = s.replaceAll(replace, replacement);
            p.sendMessage(s);
        }
    }

    public void display(CommandSender p, String replace, String replacement) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            s = s.replaceAll(replace, replacement);
            p.sendMessage(s);
        }
    }

    public void displayCentered(Player p) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            ChatCenter.sendCenteredMessage(p, s);
        }
    }

    public void displayCentered(CommandSender p) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            p.sendMessage(ChatCenter.getCenteredMessage(s));
        }
    }

    public void displayCentered(Player p, String replace, String replacement) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            s = s.replaceAll(replace, replacement);
            ChatCenter.sendCenteredMessage(p, s);
        }
    }

    public void displayCentered(CommandSender p, String replace, String replacement) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            s = s.replaceAll(replace, replacement);
            p.sendMessage(ChatCenter.getCenteredMessage(s));
        }
    }

    public void displayCentered(Player p, String[] replace, String[] replacements) {
        if (!enabled) return;
        for (String s : message) {
            for (int i = 0; i < replace.length; ++i) {
                s = Utils.toColor(s);
                s = s.replaceAll(replace[i], replacements[i]);
            }
            ChatCenter.sendCenteredMessage(p, s);
        }
    }

    public void displayCentered(CommandSender p, String[] replace, String[] replacements) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            for (int i = 0; i < replace.length; ++i) {
                s = s.replaceAll(replace[i], replacements[i]);
            }
            p.sendMessage(ChatCenter.getCenteredMessage(s));
        }
    }

    public void broadcast() {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            Bukkit.broadcastMessage(s);
        }
    }

    public void broadcast(String replace, String replacement) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            s = s.replaceAll(replace, replacement);
            Bukkit.broadcastMessage(s);
        }
    }

    public void broadcast(String[] replace, String[] replacements) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            for (int i = 0; i < replace.length; ++i) {
                s = s.replaceAll(replace[i], replacements[i]);
            }
            Bukkit.broadcastMessage(s);
        }
    }

    public void broadcastCentered() {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            Bukkit.broadcastMessage(ChatCenter.getCenteredMessage(s));
        }
    }

    public void broadcastCentered(String replace, String replacement) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            s = s.replaceAll(replace, replacement);
            Bukkit.broadcastMessage(ChatCenter.getCenteredMessage(s));
        }
    }

    public void broadcastCentered(String[] replace, String[] replacements) {
        if (!enabled) return;
        for (String s : message) {
            s = Utils.toColor(s);
            for (int i = 0; i < replace.length; ++i) {
                s = s.replaceAll(replace[i], replacements[i]);
            }
            Bukkit.broadcastMessage(ChatCenter.getCenteredMessage(s));
        }
    }

    public void putToConfig() {
        PremiumChestSell.getInstance().getConfig().set("MESSAGES." + name() + ".ENABLED", isEnabled());
        PremiumChestSell.getInstance().getConfig().set("MESSAGES." + name() + ".CENTERED", isCentered());
        PremiumChestSell.getInstance().getConfig().set("MESSAGES." + name() + ".LINES", getMessage());
        PremiumChestSell.getInstance().saveConfig();
        PremiumChestSell.getInstance().reloadConfig();
    }

    public static void mapToConfig() {
        for (Language lang : Language.values()) {
            lang.putToConfig();
        }
    }

    public static void pullFromConfig() {
        if (PremiumChestSell.getInstance().getConfig().getConfigurationSection("MESSAGES") == null) return;
        for (String key : PremiumChestSell.getInstance().getConfig().getConfigurationSection("MESSAGES").getValues(false).keySet()) {
            for (Language lang : Language.values()) {
                if (lang.name().equalsIgnoreCase(key)) {
                    lang.setEnabled(PremiumChestSell.getInstance().getConfig().getBoolean("MESSAGES." + key + ".ENABLED"));
                    lang.setCentered(PremiumChestSell.getInstance().getConfig().getBoolean("MESSAGES." + key + ".CENTERED"));
                    lang.setMessage(PremiumChestSell.getInstance().getConfig().getStringList("MESSAGES." + key + ".LINES"));
                    break;
                }
            }
        }
    }
}