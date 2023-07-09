package io.sn.blueprint.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.sn.blueprint.NeoBlueprintCore;
import io.sn.blueprint.slimefun.objects.abstracts.AssemblyMachine;
import io.sn.blueprint.utils.BlueprintUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Getter
public class SlimeProvider {

    private final NeoBlueprintCore plug;

    private static final RecipeType EMPTY_RECIPE_TYPE = RecipeType.NULL;
    private static final ItemStack[] EMPTY_RECIPE = new ItemStack[]{null, null, null, null, null, null, null, null, null};

    @SuppressWarnings("deprecation")
    private void openBlueprintMenu(Player p, String bpid) throws IOException {
        //noinspection DataFlowIssue
        ChestMenu inv = new ChestMenu("&9蓝图预览 &8[&f" + ((TextComponent) BlueprintUtils.getTarget(bpid).getItemMeta().displayName()).content() + "&8]", ChestMenuUtils.getBlankTexture());

        for (int i = 0; i < 9; i++) {
            inv.addItem(i, new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, ""), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i = 0; i < 3; i++) {
            inv.addItem(i * 9 + 9, new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, ""), ChestMenuUtils.getEmptyClickHandler());
            inv.addItem(i * 9 + 17, new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, ""), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i = 0; i < 9; i++) {
            if (i != 4)
                inv.addItem(i + 36, new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, ""), ChestMenuUtils.getEmptyClickHandler());
        }

        inv.addItem(40, BlueprintUtils.getTarget(bpid), ChestMenuUtils.getEmptyClickHandler());

        for (ItemStack ingredient : BlueprintUtils.getRecipe(bpid)) {
            inv.addItem(inv.toInventory().firstEmpty(), ingredient, ChestMenuUtils.getEmptyClickHandler());
        }

        inv.open(p);
    }

    public void setup() {

        var sfbp = (new SlimefunItem(BlueprintCategories.BLUEPRINT, BlueprintItems.BLUEPRINT, EMPTY_RECIPE_TYPE, EMPTY_RECIPE));
        sfbp.addItemHandler((ItemUseHandler) e -> {
            e.cancel();
            ItemStack item = e.getItem();
            Player plr = e.getPlayer();
            final String id = BlueprintUtils.getIdByItem(item);

            try {
                openBlueprintMenu(plr, id);
            } catch (Exception ex) {
                if (plr.isOp()) {
                    plr.sendMessage("Error occurred: " + ex.getMessage());
                }
            }
        });
        sfbp.register(plug);

        (new AssemblyMachine(BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_1, EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 2;
            }

            @Override
            public int getSpeed() {
                return 1;
            }

            @NotNull
            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_1";
            }

            @Override
            public int getSpace() {
                return 3;
            }
        })
                .setProcessingSpeed(1)
                .setCapacity(64)
                .setEnergyConsumption(2)
                .register(plug);

        (new AssemblyMachine(BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_2, EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 8;
            }

            @Override
            public int getSpeed() {
                return 1;
            }

            @NotNull
            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_2";
            }

            @Override
            public int getSpace() {
                return 7;
            }
        })
                .setProcessingSpeed(1)
                .setCapacity(128)
                .setEnergyConsumption(8)
                .register(plug);

        (new AssemblyMachine(BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_3, EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 16;
            }

            @Override
            public int getSpeed() {
                return 2;
            }

            @NotNull
            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_3";
            }

            @Override
            public int getSpace() {
                return 11;
            }
        })
                .setProcessingSpeed(2)
                .setCapacity(256)
                .setEnergyConsumption(16)
                .register(plug);

        (new AssemblyMachine(BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_4, EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 32;
            }

            @Override
            public int getSpeed() {
                return 3;
            }

            @NotNull
            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_4";
            }

            @Override
            public int getSpace() {
                return 14;
            }
        })
                .setProcessingSpeed(3)
                .setCapacity(512)
                .setEnergyConsumption(32)
                .register(plug);

        (new AssemblyMachine(BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_5, EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 96;
            }

            @Override
            public int getSpeed() {
                return 4;
            }

            @NotNull
            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_5";
            }

            @Override
            public int getSpace() {
                return 19;
            }
        })
                .setProcessingSpeed(4)
                .setCapacity(1024)
                .setEnergyConsumption(64)
                .register(plug);

        (new AssemblyMachine(BlueprintCategories.BLUEPRINT, BlueprintItems.ASM_MACHINE_6, EMPTY_RECIPE_TYPE, EMPTY_RECIPE) {

            @Override
            public int getEnergyConsumption() {
                return 288;
            }

            @Override
            public int getSpeed() {
                return 8;
            }

            @NotNull
            @Override
            public String getMachineIdentifier() {
                return "ASM_MACHINE_6";
            }

            @Override
            public int getSpace() {
                return 21;
            }
        })
                .setProcessingSpeed(6)
                .setCapacity(2048)
                .setEnergyConsumption(128)
                .register(plug);

    }

}
