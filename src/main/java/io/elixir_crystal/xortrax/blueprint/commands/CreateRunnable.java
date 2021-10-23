package io.elixir_crystal.xortrax.blueprint.commands;

import io.elixir_crystal.xortrax.blueprint.PlugGividado;
import io.elixir_crystal.xortrax.blueprint.utils.BlueprintUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.itemutils.ItemBuilder;
import redempt.redlib.misc.FormatUtils;

import java.io.IOException;

@AllArgsConstructor
@Getter
public class CreateRunnable implements Runnable {

    private final PlugGividado plug;
    private final CommandSender sender;
    private final String id;

    @Override
    public void run() {
        try {
            Player p = (Player) sender;
            ItemStack hand = p.getInventory().getItemInMainHand();

            if (hand == null || hand.getType().equals(Material.AIR)) {
                p.sendMessage(FormatUtils.color(plug.getPrefix() + "&fYou must hold an item on your hand to create a blueprint"));
                return;
            }

            InventoryGUI gui = new InventoryGUI(21, "§9绘制蓝图 §8(" + getId() + "§8) [§f" + hand.getItemMeta().getDisplayName() + "§8]");
            for (int i = 0; i < 27; i++) {
                gui.openSlot(i);
            }

            gui.setOnDestroy(() -> {
                try {
                    BlueprintUtils.createRecipe(getId(), hand, gui.getInventory().getContents());
                    getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&aSuccessfully created"));
                    ((Player) getSender()).getInventory().addItem(new ItemBuilder(Material.PAPER)
                            .setName("§d蓝图§r")
                            .setLore("", "§f合成目标: §e" + BlueprintUtils.getTarget(getId()).getItemMeta().getDisplayName(), "§f蓝图编号: §e" + getId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&cError occurred"));
        }
    }

}
