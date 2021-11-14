package io.elixir_crystal.xortrax.blueprint.slimefun.objects.abstracts;

import io.elixir_crystal.xortrax.blueprint.slimefun.UniversalMaterial;
import io.elixir_crystal.xortrax.blueprint.utils.BlueprintUtils;
import io.elixir_crystal.xortrax.blueprint.utils.CommonUtils;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineHelper;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import redempt.redlib.misc.FormatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class AssemblyMachine extends AContainer {

    private final String id;

    private static final int[] border = new int[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 40, 44,
            45, 53
    };

    private static final int[] border_in = new int[]{
            37, 38, 39,
            46, 48
    };

    private static final int[] border_out = new int[]{
            41, 42, 43,
            50, 52
    };

    private static final int output_slot = 51;
    private static final int[] input_slot = new int[]{
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
    };

    private static final int process = 49;
    private static final int blueprint_slot = 47;

    public AssemblyMachine(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, id, recipeType, recipe);

        this.id = id;

        new BlockMenuPreset(id, getInventoryTitle()) {
            public void init() {
                constructLimitedMenu(this);
            }

            public void newInstance(BlockMenu menu, Block b) {
            }

            public boolean canOpen(Block b, Player p) {
                return CommonUtils.reflectCanOpenMethod(b, p);
            }

            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow.equals(ItemTransportFlow.INSERT)) {
                    return getInputSlots();
                }
                return getOutputSlots();
            }
        };
        registerBlockHandler(id, new SlimefunBlockHandler() {
            public void onPlace(Player p, Block b, SlimefunItem item) {
            }

            public boolean onBreak(Player p, Block b, SlimefunItem item, UnregisterReason reason) {
                BlockMenu inv = BlockStorage.getInventory(b);
                if (inv != null) {
                    for (int slot : getInputSlots()) {
                        if (inv.getItemInSlot(slot) != null) {
                            b.getWorld().dropItemNaturally(b.getLocation(), inv.getItemInSlot(slot));
                            inv.replaceExistingItem(slot, null);
                        }
                    }
                    for (int slot : getOutputSlots()) {
                        if (inv.getItemInSlot(slot) != null) {
                            b.getWorld().dropItemNaturally(b.getLocation(), inv.getItemInSlot(slot));
                            inv.replaceExistingItem(slot, null);
                        }
                    }
                    if (inv.getItemInSlot(blueprint_slot) != null) {
                        b.getWorld().dropItemNaturally(b.getLocation(), inv.getItemInSlot(blueprint_slot));
                        inv.replaceExistingItem(blueprint_slot, null);
                    }
                }
                AssemblyMachine.progress.remove(b);
                AssemblyMachine.processing.remove(b);
                return true;
            }
        });
    }

    protected void constructLimitedMenu(BlockMenuPreset preset) {
        for (int i : border) {
            preset.addItem(i,
                    new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 7), " "),
                    (arg0, arg1, arg2, arg3) -> false);
        }
        for (int i : border_in) {
            preset.addItem(i,
                    new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 9), " "),
                    (arg0, arg1, arg2, arg3) -> false);
        }
        for (int i : border_out) {
            preset.addItem(i,
                    new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 1), " "),
                    (arg0, arg1, arg2, arg3) -> false);
        }
        preset.addItem(process, new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 15), " "), (arg0, arg1, arg2, arg3) -> false);
        for (int i : getOutputSlots()) {
            preset.addMenuClickHandler(i, new ChestMenu.AdvancedMenuClickHandler() {
                public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                    return false;
                }

                public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor,
                                       ClickAction action) {
                    return (cursor == null) || (cursor.getType() == null) || (cursor.getType() == Material.AIR);
                }
            });
        }

        int c = 0;
        for (int i : reverse(getInputSlots())) {
            if (c < 21 - getSpace()) {
                c++;
                preset.addItem(i,
                        new CustomItem(new UniversalMaterial(Material.STRUCTURE_VOID), " "),
                        (arg0, arg1, arg2, arg3) -> false);
            }
        }
    }

    private static int[] reverse(int[] arr) {
        for (int s = 0, e = arr.length - 1; s < e; s++, e--) {
            int tmp = arr[s];
            arr[s] = arr[e];
            arr[e] = tmp;
        }
        return arr;
    }

    @Override
    public int[] getInputSlots() {
        return input_slot;
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{output_slot};
    }

    @Override
    public String getInventoryTitle() {
        return FormatUtils.color("&c组装机");
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.DISPENSER);
    }

    @Override
    public void registerDefaultRecipes() {
    }

    public abstract int getSpeed();

    public abstract int getSpace();

    public String getMachineIdentifier() {
        return getId();
    }

    @Override
    protected void constructMenu(BlockMenuPreset preset) {
        for (int i : border) {
            preset.addItem(i, new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 7), " "), (arg0, arg1, arg2, arg3) -> false);
        }
        for (int i : border_in) {
            preset.addItem(i, new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 9), " "), (arg0, arg1, arg2, arg3) -> false);
        }
        for (int i : border_out) {
            preset.addItem(i, new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 1), " "), (arg0, arg1, arg2, arg3) -> false);
        }
        preset.addItem(process, new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 15), " "), (arg0, arg1, arg2, arg3) -> false);
        for (int i : getOutputSlots()) {
            preset.addMenuClickHandler(i, new ChestMenu.AdvancedMenuClickHandler() {
                public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                    return false;
                }

                public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                    return (cursor == null || cursor.getType() == null || cursor.getType() == Material.AIR);
                }
            });
        }
    }

    @Override
    public void tick(Block b) {
        if (b.getBlockPower() > 1) {
            return;
        }

        BlockMenu bm = BlockStorage.getInventory(b);
        if (isProcessing(b)) {
            int timeleft = progress.get(b);
            if (timeleft > 0) {
                ItemStack item = getProgressBar().clone();
                item.setDurability(MachineHelper.getDurability(item, timeleft, processing.get(b).getTicks()));
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(" ");
                List<String> lore = new ArrayList<>();
                lore.add(MachineHelper.getProgress(timeleft, processing.get(b).getTicks()));
                lore.add("");
                lore.add(MachineHelper.getTimeLeft(timeleft / 2));
                im.setLore(lore);
                item.setItemMeta(im);
                bm.replaceExistingItem(process, item);
                if (ChargableBlock.isChargable(b)) {
                    if (ChargableBlock.getCharge(b) < getEnergyConsumption())
                        return;
                    ChargableBlock.addCharge(b, -getEnergyConsumption());
                }
                progress.put(b, timeleft - 1);
            } else {
                bm.replaceExistingItem(process, new CustomItem(new UniversalMaterial(Material.STAINED_GLASS_PANE, 15), " "));
                pushItems(b, processing.get(b).getOutput().clone());
                progress.remove(b);
                processing.remove(b);
            }
        } else {
            MachineRecipe r = null;
            Map<Integer, Integer> found = new HashMap<>();

            if (bm.getItemInSlot(blueprint_slot) == null || bm.getItemInSlot(blueprint_slot).getType().equals(Material.AIR)) {
                return;
            }

            try {
                String bpid = BlueprintUtils.getIdByItem(bm.getItemInSlot(blueprint_slot));
                ItemStack[] recipe = BlueprintUtils.getRecipe(bpid);

                for (ItemStack input : recipe) {
                    for (int slot : getInputSlots()) {
                        if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), input, false)) {
                            found.put(slot, input.getAmount());
                            break;
                        }
                    }
                }
                if (found.size() == recipe.length) {
                    int tAmount = 1;
                    for (ItemStack im : recipe) {
                        tAmount += im.getAmount();
                    }
                    r = new MachineRecipe(tAmount / getSpeed(), recipe, new ItemStack[]{BlueprintUtils.getTarget(bpid)});
                } else {
                    found.clear();
                }

                if (r != null) {
                    if (!fits(b, r.getOutput())) {
                        return;
                    }
                    for (Map.Entry<Integer, Integer> entry : found.entrySet()) {
                        BlockStorage.getInventory(b).replaceExistingItem(entry.getKey(),
                                InvUtils.decreaseItem(
                                        BlockStorage.getInventory(b).getItemInSlot(entry.getKey()),
                                        entry.getValue()));
                    }
                    processing.put(b, r);
                    progress.put(b, r.getTicks());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
