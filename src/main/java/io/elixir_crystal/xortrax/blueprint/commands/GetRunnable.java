package io.elixir_crystal.xortrax.blueprint.commands;

import io.elixir_crystal.xortrax.blueprint.PlugGividado;
import io.elixir_crystal.xortrax.blueprint.utils.BlueprintUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.itemutils.ItemBuilder;
import redempt.redlib.misc.FormatUtils;

import java.io.File;

@AllArgsConstructor
@Getter
public class GetRunnable implements Runnable {

    private final PlugGividado plug;
    private final CommandSender sender;
    private final String id;

    private static final String SEP = File.separator;

    @Override
    public void run() {
        try {
            ItemStack tg = BlueprintUtils.getTarget(getId());
            ItemStack bp = (new ItemBuilder(Material.PAPER).setName("§d蓝图§r").setLore("", "§f合成目标: §e" + tg.getItemMeta().getDisplayName(), "§f蓝图编号: §e" + getId()));

            Player plr = ((Player) getSender());
            if (plr.getInventory().firstEmpty() == -1) {
                throw new IllegalStateException("Your inventory is full.");
            }
            plr.getInventory().addItem(bp);
            getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&aSuccessfully got: &f") + tg.getItemMeta().getDisplayName());
        } catch (Exception e) {
            getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&cError occurred: " + e.getMessage()));
        }
    }

}
