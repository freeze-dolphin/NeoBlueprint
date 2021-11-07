package io.elixir_crystal.xortrax.blueprint.slimefun;

import io.elixir_crystal.xortrax.blueprint.PlugGividado;
import io.elixir_crystal.xortrax.blueprint.slimefun.abstracts.AssemblyMachine;
import io.elixir_crystal.xortrax.blueprint.utils.BlueprintUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.DamagableChargableItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.energy.EnergyTicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.itemutils.ItemBuilder;
import redempt.redlib.misc.FormatUtils;

@RequiredArgsConstructor
@Getter
public class SlimeProvider {

    private static final RecipeType EMPTY_RECIPE_TYPE = new RecipeType(new ItemBuilder(Material.BARRIER).setName(FormatUtils.color("&c无合成表&r")));
    private static final ItemStack[] EMPTY_RECIPE = new ItemStack[]{null, null, null, null, null, null, null, null, null};

    private final PlugGividado plug;

    public void setup() throws Exception {

        (new SlimefunItem(BlueprintCategories.BLUEPRINT, new CustomItem(CustomSkull.getItem(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y5ZjM1NmY1ZmU3ZDFiYzkyY2RkZmFlYmEzZWU3NzNhYzlkZjFjYzRkMWMyZjhmZTVmNDcwMTMwMzJjNTUxZCJ9fX0="),
                "&7恶臭发电机&r", "", "&r当接收到红石信号时工作", "", "&8？？发电机", "&8⇨ &e⚡ &7发电效率由接收到的红石信号强度决定 (114514)"), "ENDLESS_GENERATOR_114514",
                EMPTY_RECIPE_TYPE, EMPTY_RECIPE)).register(false, new EnergyTicker() {

            @Override
            public boolean explode(Location paramLocation) {
                return false;
            }

            @Override
            public double generateEnergy(Location l, SlimefunItem paramSlimefunItem, Config paramConfig) {
                return l.getBlock().getBlockPower() * 114514;
            }
        });

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
        }).register(64);

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
        }).register(128);

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
        }).register(256);

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
        }).register(512);

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
        }).register(1024);

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
        }).register(2048);

        (new DamagableChargableItem
                (BlueprintCategories.BLUEPRINT, BlueprintItems.BLUEPRINT,
                        "BLUEPRINT",
                        EMPTY_RECIPE_TYPE, EMPTY_RECIPE, "Blueprint"))
                .register(false, new ItemInteractionHandler() {

                    @Override
                    public boolean onRightClick(ItemUseEvent evt, Player plr, ItemStack item) {
                        if (!SlimefunManager.isItemSimiliar(item, BlueprintItems.BLUEPRINT, false)) return true;

                        try {
                            final String id = BlueprintUtils.getIdByItem(item);
                            if (getPlug().isDebug()) getPlug().getLogger().warning(id);

                            InventoryGUI gui = new InventoryGUI(45, "§9蓝图预览 §8[§f" + BlueprintUtils.getTarget(id).getItemMeta().getDisplayName() + "§8]");
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
                            for (ItemStack ingredient : BlueprintUtils.getRecipe(id)) {
                                gui.getInventory().addItem(ingredient);
                            }
                            gui.open(plr);
                        } catch (Exception e) {
                            if (getPlug().isDebug()) {
                                plr.sendMessage(getPlug().getPrefix() + FormatUtils.color("&c") + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                });
    }

}
