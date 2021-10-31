package io.elixir_crystal.xortrax.blueprint.slimefun;

import io.elixir_crystal.xortrax.blueprint.slimefun.abstracts.AssemblyMachine;
import io.elixir_crystal.xortrax.blueprint.utils.BlueprintUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.DamagableChargableItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.itemutils.ItemBuilder;
import redempt.redlib.misc.FormatUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Getter
public class SlimeProvider {

    private static final RecipeType EMPTY_RECIPE_TYPE = new RecipeType(new ItemBuilder(Material.BARRIER).setName(FormatUtils.color("&c无合成表&r")));
    private static final ItemStack[] EMPTY_RECIPE = new ItemStack[]{null, null, null, null, null, null, null, null, null};

    public void setup() {

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_1,
                        "ASM_MACHINE_1",
                        EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 2;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }).registerChargeableBlock(64);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_2,
                        "ASM_MACHINE_2",
                        EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 8;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }).registerChargeableBlock(128);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_3,
                        "ASM_MACHINE_3",
                        EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 16;
            }

            @Override
            public int getSpeed() {
                return 2;
            }
        }).registerChargeableBlock(256);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_4,
                        "ASM_MACHINE_4",
                        EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 32;
            }

            @Override
            public int getSpeed() {
                return 3;
            }
        }).registerChargeableBlock(512);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_5,
                        "ASM_MACHINE_5",
                        EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 96;
            }

            @Override
            public int getSpeed() {
                return 4;
            }
        }).registerChargeableBlock(1024);

        (new AssemblyMachine
                (BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_6,
                        "ASM_MACHINE_6",
                        EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 288;
            }

            @Override
            public int getSpeed() {
                return 8;
            }
        }).registerChargeableBlock(2048);

        (new DamagableChargableItem
                (BlueprintCategories.BLUEPRINT, BlueprintItems.BLUEPRINT,
                        "BLUEPRINT",
                        EMPTY_RECIPE_TYPE, EMPTY_RECIPE, "Blueprint"))
                .register(false, new ItemInteractionHandler() {

                    @Override
                    public boolean onRightClick(ItemUseEvent evt, Player plr, ItemStack item) {
                        if (SlimefunManager.isItemSimiliar(item, BlueprintItems.BLUEPRINT, false))
                            return true;

                        try {
                            final String id = BlueprintUtils.getIdByItem(item);

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
                        } catch (NullPointerException | IOException | IllegalStateException ignored) {
                        }
                        return false;
                    }
                });


    }

}
