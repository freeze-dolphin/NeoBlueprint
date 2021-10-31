package io.elixir_crystal.xortrax.blueprint.slimefun.abstracts;

import io.elixir_crystal.xortrax.blueprint.utils.BlueprintUtils;
import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Misc.compatibles.ProtectionUtils;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineHelper;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import redempt.redlib.itemutils.ItemBuilder;
import redempt.redlib.misc.FormatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class AssemblyMachine extends SlimefunItem {

    public static boolean reflectCanOpenMethod(Block b, Player p) {
        try {
            Class.forName("me.mrCookieSlime.Slimefun.Misc.compatibles.ProtectionUtils");
            boolean perm = (p.hasPermission("slimefun.inventory.bypass")) || (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true));
            return (perm) && (ProtectionUtils.canAccessItem(p, b));
        } catch (ClassNotFoundException cnfe) {
            return p.hasPermission("slimefun.inventory.bypass") || CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true);
        }
    }

    protected static final int indicator = 49;
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

    private static final int blueprint_slot = 47;


    private final String id;
    public static Map<Block, MachineRecipe> processing = new HashMap<>();
    public static Map<Block, Integer> progress = new HashMap<>();

    public AssemblyMachine(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, id, recipeType, recipe);

        this.id = id;

        new BlockMenuPreset(id, getInventoryTitle()) {
            public void init() {
                constructMenu(this);
            }

            public void newInstance(BlockMenu menu, Block b) {
            }

            public boolean canOpen(Block b, Player p) {
                return reflectCanOpenMethod(b, p);
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
                }
                AssemblyMachine.progress.remove(b);
                AssemblyMachine.processing.remove(b);
                return true;
            }
        });
    }

    protected void constructMenu(BlockMenuPreset preset) {
        for (int i : border) {
            preset.addItem(i,
                    new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(7).setName(" "),
                    (arg0, arg1, arg2, arg3) -> false);
        }
        for (int i : border_in) {
            preset.addItem(i,
                    new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(9).setName(" "),
                    (arg0, arg1, arg2, arg3) -> false);
        }
        for (int i : border_out) {
            preset.addItem(i,
                    new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(1).setName(" "),
                    (arg0, arg1, arg2, arg3) -> false);
        }
        preset.addItem(indicator, new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(5).setName(" "), (arg0, arg1, arg2, arg3) -> false);
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
    }

    public String getInventoryTitle() {
        return FormatUtils.color("&c组装机");
    }

    public ItemStack getProgressBar() {
        return new ItemStack(Material.DISPENSER);
    }

    public int[] getInputSlots() {
        return input_slot;
    }

    public int[] getOutputSlots() {
        return new int[]{output_slot};
    }

    public abstract int getEnergyConsumption();

    public abstract int getSpeed();

    public String getMachineIdentifier() {
        return this.id;
    }

    public MachineRecipe getProcessing(Block b) {
        return processing.get(b);
    }

    public boolean isProcessing(Block b) {
        return getProcessing(b) != null;
    }

    private Inventory inject(Block b) {
        int size = BlockStorage.getInventory(b).toInventory().getSize();
        Inventory inv = Bukkit.createInventory(null, size);
        for (int i = 0; i < size; i++) {
            inv.setItem(i, new CustomItem(Material.COMMAND, " &4ALL YOUR PLACEHOLDERS ARE BELONG TO US", 0));
        }
        for (int slot : getOutputSlots()) {
            inv.setItem(slot, BlockStorage.getInventory(b).getItemInSlot(slot));
        }
        return inv;
    }

    protected boolean fits(Block b, ItemStack[] items) {
        return !inject(b).addItem(items).isEmpty();
    }

    protected void pushItems(Block b, ItemStack[] items) {
        Inventory inv = inject(b);
        inv.addItem(items);
        for (int slot : getOutputSlots()) {
            BlockStorage.getInventory(b).replaceExistingItem(slot, inv.getItem(slot));
        }
    }

    public void register(boolean slimefun) {
        addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                AssemblyMachine.this.tick(b);
            }

            public void uniqueTick() {
            }

            public boolean isSynchronized() {
                return false;
            }
        });
        super.register(slimefun);
    }

    protected void tick(Block b) {

        if (b.getBlockPower() > 1) {
            return;
        }

        ItemMeta im;
        BlockMenu bm = BlockStorage.getInventory(b);
        if (isProcessing(b)) {
            int timeleft = progress.get(b);
            if (timeleft > 0) {
                ItemStack item = getProgressBar().clone();
                item.setDurability(MachineHelper.getDurability(item, timeleft, processing.get(b).getTicks()));
                im = item.getItemMeta();
                im.setDisplayName(" ");
                List<String> lore = new ArrayList<>();
                lore.add(MachineHelper.getProgress(timeleft, processing.get(b).getTicks()));
                lore.add("");
                lore.add(MachineHelper.getTimeLeft(timeleft / 2));
                im.setLore(lore);
                item.setItemMeta(im);

                BlockStorage.getInventory(b).replaceExistingItem(indicator, item);
                if (ChargableBlock.isChargable(b)) {
                    if (ChargableBlock.getCharge(b) < getEnergyConsumption()) {
                        return;
                    }
                    ChargableBlock.addCharge(b, -getEnergyConsumption());
                }
                progress.put(b, timeleft - 1);
            } else {
                BlockStorage.getInventory(b).replaceExistingItem(indicator,
                        new ItemBuilder(Material.STAINED_GLASS_PANE).addDamage(15).setName(" "));
                pushItems(b, processing.get(b).getOutput().clone());

                progress.remove(b);
                processing.remove(b);
            }
        } else {
            MachineRecipe r = null;
            Map<Integer, Integer> found = new HashMap<>();

            if (bm.getItemInSlot(blueprint_slot) == null) return;

            try {
                String id = BlueprintUtils.getIdByItem(bm.getItemInSlot(blueprint_slot));
                if (id != null) {
                    ItemStack[] recipe = BlueprintUtils.getRecipe(id);
                    int tAmount = 1;
                    for (ItemStack it : recipe) {
                        tAmount += it.getAmount();
                    }
                    r = new MachineRecipe(tAmount / getSpeed(), recipe, new ItemStack[]{BlueprintUtils.getTarget(id)});
                }

                if (r != null) {
                    for (ItemStack input : r.getInput()) {
                        for (int slot : getInputSlots()) {
                            if (SlimefunManager.isItemSimiliar(bm.getItemInSlot(slot), input, true)) {
                                found.put(slot, input.getAmount());
                                break;
                            }
                        }
                    }
                    if (found.size() == r.getInput().length) {
                        if (!fits(b, r.getOutput())) {
                            return;
                        }
                        for (Map.Entry<Integer, Integer> entry : found.entrySet())
                            BlockStorage.getInventory(b).replaceExistingItem(entry.getKey(), InvUtils.decreaseItem(bm.getItemInSlot(entry.getKey()), entry.getValue()));
                        processing.put(b, r);
                        progress.put(b, r.getTicks());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
