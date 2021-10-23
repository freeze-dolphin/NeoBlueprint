package io.elixir_crystal.xortrax.blueprint.slimefun;

import io.elixir_crystal.xortrax.blueprint.slimefun.abstracts.AssemblyMachine;
import io.elixir_crystal.xortrax.blueprint.utils.BlueprintUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.DamagableChargableItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.itemutils.ItemBuilder;

import java.io.IOException;

@RequiredArgsConstructor
@Getter
public class SlimeProvider {

    public void setup() throws IOException {

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_1.getItem(),
                        "ASM_MACHINE_1",
                        RecipeType.NULL, new ItemStack[]{}) {

            @Override
            public int getEnergyConsumption() {
                return 2;
            }

            @Override
            public int getSpeed() {
                return 1;
            }

            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_1";
            }
        }).register(64);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_2.getItem(),
                        "ASM_MACHINE_2",
                        RecipeType.NULL, new ItemStack[]{}) {

            @Override
            public int getEnergyConsumption() {
                return 8;
            }

            @Override
            public int getSpeed() {
                return 1;
            }

            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_2";
            }
        }).register(128);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_3.getItem(),
                        "ASM_MACHINE_3",
                        RecipeType.NULL, new ItemStack[]{}) {

            @Override
            public int getEnergyConsumption() {
                return 16;
            }

            @Override
            public int getSpeed() {
                return 2;
            }

            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_3";
            }
        }).register(256);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_4.getItem(),
                        "ASM_MACHINE_4",
                        RecipeType.NULL, new ItemStack[]{}) {

            @Override
            public int getEnergyConsumption() {
                return 32;
            }

            @Override
            public int getSpeed() {
                return 3;
            }

            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_4";
            }
        }).register(512);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_5.getItem(),
                        "ASM_MACHINE_5",
                        RecipeType.NULL, new ItemStack[]{}) {

            @Override
            public int getEnergyConsumption() {
                return 96;
            }

            @Override
            public int getSpeed() {
                return 4;
            }

            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_5";
            }
        }).register(1024);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_5.getItem(),
                        "ASM_MACHINE_6",
                        RecipeType.NULL, new ItemStack[]{}) {

            @Override
            public int getEnergyConsumption() {
                return 288;
            }

            @Override
            public int getSpeed() {
                return 8;
            }

            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_6";
            }
        }).register(2048);

        (new DamagableChargableItem
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.BLUEPRINT.getItem(),
                        "BLUEPRINT",
                        RecipeType.NULL, new ItemStack[]{}, "Blueprint"))
                .register(false, new ItemInteractionHandler() {
                    @SneakyThrows
                    @Override
                    public boolean onRightClick(ItemUseEvent evt, Player plr, ItemStack item) {
                        if (SlimefunManager.isItemSimiliar(item, BlueprintItems.BLUEPRINT.getItem(), false))
                            return true;

                        final String id = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(": ")[1];

                        InventoryGUI gui = new InventoryGUI(45, "§9蓝图 §8[§f" + BlueprintUtils.getTarget(id).getItemMeta().getDisplayName() + "§f]");
                        for (int i = 0; i < 9; i++) {
                            gui.getInventory().setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(7));
                        }
                        for (int i = 0; i < 3; i++) {
                            gui.getInventory().setItem(i * 9 + 9, new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(7));
                            gui.getInventory().setItem(i * 9 + 17, new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(7));
                        }
                        for (int i = 0; i < 9; i++) {
                            if (i != 4)
                                gui.getInventory().setItem(i + 36, new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(7));
                        }
                        gui.getInventory().setItem(40, item);

                        return false;
                    }
                });


    }

}
