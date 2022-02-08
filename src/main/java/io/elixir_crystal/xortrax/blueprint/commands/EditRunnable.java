package io.elixir_crystal.xortrax.blueprint.commands;

import io.elixir_crystal.xortrax.blueprint.PlugGividado;
import io.elixir_crystal.xortrax.blueprint.utils.BlueprintUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.itemutils.ItemBuilder;
import redempt.redlib.misc.FormatUtils;

import java.io.File;
import java.io.FileNotFoundException;

@AllArgsConstructor
@Getter
public class EditRunnable implements Runnable {

    private static final String SEP = File.separator;
    private final PlugGividado plug;
    private final CommandSender sender;
    private final String id;

    @Override
    public void run() {
        try {

            String id;

            if (getId() == null) {
                Player p = (Player) getSender();
                ItemStack hand = p.getInventory().getItemInMainHand();
                id = BlueprintUtils.getIdByItem(hand);
            } else {
                id = getId();
            }

            Player p = (Player) getSender();

            File f = new File(getPlug().getDataFolder().getPath() + SEP + "storage" + SEP + id + ".yml");

            if (!f.exists()) {
                throw new FileNotFoundException("Blueprint not found.");
            }

            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

            ItemStack hand = yml.getItemStack("target");

            InventoryGUI gui = new InventoryGUI(27, "§6编辑蓝图 §8(" + id + "§8) [§f" + hand.getItemMeta().getDisplayName() + "§8]");
            gui.setReturnsItems(false);
            for (int i = 0; i < 27; i++) {
                gui.openSlot(i);
                gui.getInventory().setItem(i, yml.getItemStack("recipe." + i));
            }

            gui.setOnDestroy(() -> {
                try {
                    BlueprintUtils.createRecipeForce(getId(), hand, gui.getInventory().getContents());
                    getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&aSuccessfully edited"));

                    if (getId() != null) {
                        ((Player) getSender()).getInventory().addItem(new ItemBuilder(Material.PAPER)
                                .setName("§d蓝图§r")
                                .setLore("", "§f合成目标: §e" + BlueprintUtils.getTarget(getId()).getItemMeta().getDisplayName(), "§f蓝图编号: §e" + getId()));
                    }
                } catch (Exception e) {
                    getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&cError occurred: " + e.getMessage()));
                }

            });

            gui.open(p);

        } catch (Exception e) {
            e.printStackTrace();
            getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&cError occurred"));
        }
    }

}
