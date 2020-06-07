package dev.warring.chestsell;

import dev.warring.chestsell.model.SellListener;
import dev.warring.core.lang.Language;
import dev.warring.core.library.WarringPlugin;
import dev.warring.core.library.storage.MapStorage;
import dev.warring.core.library.utils.ItemBuilder;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PremiumChestSell extends WarringPlugin {

    private static PremiumChestSell instance;
    private Economy econ;

    private MapStorage<String, Double> prices;

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().getPlugin("PremiumCore").isEnabled()) {
            this.getLogger().info("=====================================");
            this.getLogger().info("PREMIUMCHESTSELL DEPENDS ON PREMIUMCORE");
            this.getLogger().info("CONTACT WARRING FOR IT!");
            this.getLogger().info("=====================================");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (!setupEconomy()) {
            this.getLogger().info("=====================================");
            this.getLogger().info("PREMIUMCHESTSELL DEPENDS ON VAULT");
            this.getLogger().info("=====================================");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;

        registerModels(new SellListener());
        loadPrices();

        if (getConfig().getConfigurationSection("MESSAGES") == null) {
            Language.mapToConfig();
        } else {
            Language.pullFromConfig();
        }
    }

    @Override
    public void disable() {

    }

    public static PremiumChestSell getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public void loadPrices() {
        prices = new MapStorage<>();
        for (String key : getConfig().getStringList("Prices")) {
            String[] args = key.split(";");
            prices.set(args[0] + ";" + args[1], Double.valueOf(args[2]));
        }
    }

    public Economy getEcon() {
        return econ;
    }

    public MapStorage<String, Double> getPrices() {
        return prices;
    }
}
