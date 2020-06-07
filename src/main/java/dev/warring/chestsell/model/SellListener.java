package dev.warring.chestsell.model;

import dev.warring.chestsell.PremiumChestSell;
import dev.warring.chestsell.lang.Language;
import dev.warring.core.library.events.EventStart;
import dev.warring.core.library.model.Model;
import dev.warring.core.library.pair.Pair;
import dev.warring.core.library.utils.Formatter;
import dev.warring.core.library.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sign;

import java.text.DecimalFormat;
import java.util.List;

public class SellListener extends Model {

    @Override
    public void register() {
        EventStart.register(SignChangeEvent.class).addFilter(e -> e.getPlayer() != null).handleEvent(e -> {
            Sign sign = (Sign) e.getBlock().getState().getData();
            Block behind = e.getBlock().getRelative(sign.getAttachedFace());
            if (e.getLine(0).contains("ChestSell")) {
                if (behind.getType() != Material.CHEST && behind.getType() != Material.TRAPPED_CHEST) {
                    e.getBlock().breakNaturally();
                    Language.NO_CHEST.display(e.getPlayer());
                    return;
                }
                List<String> strings = PremiumChestSell.getInstance().getConfig().getStringList("SignLines");
                for (int i = 0; i < 4; ++i) {
                    e.setLine(i, Utils.toColor(strings.get(i)));
                }
                Language.CREATED_CHEST.display(e.getPlayer());
            }
        }).dispatch();

        EventStart.register(PlayerInteractEvent.class).addFilter(e -> e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getPlayer() != null && e.getClickedBlock() != null && (e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.WALL_SIGN)).handleEvent(e -> {
            if (!isChestSellSign(e.getClickedBlock())) return;
            Chest chest = (Chest) e.getClickedBlock().getRelative(((Sign)e.getClickedBlock().getState().getData()).getAttachedFace()).getState();
            Pair<Double, Integer> pair = sellItems(chest.getInventory());

            if (pair.getValue() != 0) {
                PremiumChestSell.getInstance().getEcon().depositPlayer(e.getPlayer(), pair.getKey());
                DecimalFormat format = new DecimalFormat("####,####,####,####");
                Language.SOLD_ITEMS.display(e.getPlayer(), new String[]{"%amount%", "%price%"}, new String[]{format.format(pair.getValue()), format.format(pair.getKey())});
            } else {
                Language.SOLD_NOTHING.display(e.getPlayer());
            }
        }).dispatch();

        EventStart.register(BlockBreakEvent.class).addFilter(e -> e.getPlayer() != null && (e.getBlock().getType() == Material.SIGN || e.getBlock().getType() == Material.WALL_SIGN)).handleEvent(e -> {
            if (!isChestSellSign(e.getBlock())) return;

            Language.BROKEN_SIGN.display(e.getPlayer());

        }).dispatch();
    }

    public Pair<Double, Integer> sellItems(Inventory inv) {
        int items = 0;
        double value = 0.0;
        for (int i = 0; i < inv.getSize(); ++i) {
            ItemStack it = inv.getItem(i);
            if (it == null) continue;
            if (it.hasItemMeta() && (it.getItemMeta().hasDisplayName() || it.getItemMeta().hasLore())) continue;
            if (PremiumChestSell.getInstance().getPrices().containsKey(it.getType().name() + ";" + it.getData().getData())) {
                inv.setItem(i, null);
                value += PremiumChestSell.getInstance().getPrices().get(it.getType().name() + ";" + it.getData().getData()) * it.getAmount();
                items += it.getAmount();
            }
        }
        Pair<Double, Integer> pair = new Pair<>(value, items);

        return pair;
    }

    public boolean isChestSellSign(Block b) {
        return ((org.bukkit.block.Sign)b.getState()).getLine(0).equalsIgnoreCase(Utils.toColor(PremiumChestSell.getInstance().getConfig().getStringList("SignLines").get(0)));
    }
}
