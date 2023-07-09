package io.sn.blueprint.slimefun.objects.abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import de.tr7zw.nbtapi.NBT;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.sn.blueprint.utils.BlueprintUtils;
import io.sn.dumortierite.DumoCore;
import io.sn.dumortierite.utils.*;
import io.sn.slimefun4.ChestMenuTexture;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("deprecation")
@Getter
public abstract class AssemblyMachine extends AContainer {

    private final ProgramLoader programLoader = new ProgramLoader(ProcessorType.GENERATOR, new SpecificProgramType[]{SpecificProgramType.ASSEMBLY}, this);

    private static final int[] BORDER = new int[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 40, 44, 45, 53};

    private static final int[] BORDER_IN = new int[]{37, 38, 39, 46, 48};

    private static final int[] BORDER_OUT = new int[]{41, 42, 43, 50, 52};

    private static final int OUTPUT_SLOT = 51;
    private static final int[] INPUT_SLOT = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};

    private static final int SLOT_INDICATOR = 49;
    private static final int SLOT_BLUEPRINT = 47;
    private static final int SLOT_CIRCUIT = 1;


    private final MachineProcessor<CraftingOperation> processor = new MachineProcessor<>(this);

    @Override
    @NotNull
    public MachineProcessor<CraftingOperation> getMachineProcessor() {
        return processor;
    }


    public AssemblyMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        new BlockMenuPreset(item.getItemId(), getInventoryTitle(), new ChestMenuTexture("dumortierite", "blank")) {
            @Override
            public void init() {
                constructMenu(this);
            }

            @Override
            public void newInstance(@NotNull BlockMenu menu, @NotNull Block b) {
                updateBlockInventory(menu, b);
            }

            @Override
            public boolean canOpen(@NotNull Block b, @NotNull Player p) {
                return p.hasPermission("slimefun.inventory.bypass") || Slimefun.getProtectionManager().hasPermission(p, b.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.INSERT) {
                    return getInputSlots();
                } else {
                    return getOutputSlots();
                }
            }
        };

        addItemHandler(onBlockBreak());
    }

    private void updateBlockInventory(BlockMenu menu, Block b) {
        var blockData = StorageCacheUtils.getBlock(b.getLocation());
        String val;
        if (blockData == null || (val = blockData.getData("in-process")) == null || val.equals(String.valueOf(false))) {
            menu.addMenuClickHandler(SLOT_BLUEPRINT, (p, slot, item, action) -> false);
        } else {
            menu.addMenuClickHandler(SLOT_BLUEPRINT, (p, slot, item, action) -> true);
        }
    }

    @NotNull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            @Override
            public void onBlockBreak(@NotNull Block b) {
                BlockMenu inv = StorageCacheUtils.getMenu(b.getLocation());

                if (inv != null) {
                    for (int slot : getInputSlots()) {
                        if (inv.getItemInSlot(slot) != null && !SlimefunUtils.isItemSimilar(inv.getItemInSlot(slot), new CustomItemStack(Material.STRUCTURE_VOID, " "), true)) {
                            b.getWorld().dropItemNaturally(b.getLocation(), inv.getItemInSlot(slot));
                            inv.replaceExistingItem(slot, null);
                        }
                    }
                    inv.dropItems(b.getLocation(), getOutputSlots());
                    inv.dropItems(b.getLocation(), getMiscItemSlots());
                }

                processor.endOperation(b);
            }
        };
    }

    protected void constructMenu(BlockMenuPreset preset) {
        for (int i : BORDER) {
            preset.addItem(i, new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : BORDER_IN) {
            preset.addItem(i, new CustomItemStack(Material.CYAN_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : BORDER_OUT) {
            preset.addItem(i, new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(SLOT_INDICATOR, new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());

        for (int i : getOutputSlots()) {
            preset.addMenuClickHandler(i, new ChestMenu.AdvancedMenuClickHandler() {
                @Override
                public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                    return false;
                }

                @Override
                public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                    return cursor == null || cursor.getType() == Material.AIR;
                }
            });
        }

        int c = 0;
        for (int i : reverse(getInputSlots())) {
            if (c < 21 - getSpace()) {
                c++;
                preset.addItem(i, new CustomItemStack(Material.STRUCTURE_VOID, " "), ChestMenuUtils.getEmptyClickHandler());
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
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{OUTPUT_SLOT};
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

    protected boolean takeCharge(@Nonnull Location l, int progressive) {
        if (isChargeable()) {
            int charge = getCharge(l);

            if (charge < getEnergyConsumption() * progressive) {
                return false;
            }

            setCharge(l, charge - getEnergyConsumption() * progressive);
        }
        return true;
    }

    protected void tick(Block b) {
        Location l = b.getLocation();
        BlockMenu inv = StorageCacheUtils.getMenu(l);
        CraftingOperation currentOperation = processor.getOperation(b);
        SlimefunBlockData data = StorageCacheUtils.getBlock(l);

        if (inv == null || data == null) return;

        if (CircuitUtils.isCircuit(inv.getItemInSlot(SLOT_CIRCUIT))) {
            var program = NBT.get(inv.getItemInSlot(SLOT_CIRCUIT), nbt -> nbt.getString("program-id"));
            try {
                if (!programLoader.preLoad(Objects.requireNonNull(DumoCore.Companion.getProgramRegistry().getProgramById(program)), l, data)) {
                    return;
                }
            } catch (IncompatibleProgramTypeException ex) {
                return;
            }
        } else {
            return;
        }

        var progressive = 0;

        var progressiveNullable = data.getData("progressive");
        if (progressiveNullable != null) {
            progressive = Integer.parseInt(progressiveNullable);
        }

        var bp = inv.getItemInSlot(SLOT_BLUEPRINT);
        if (bp == null) {
            return;
        }

        String bpid = BlueprintUtils.getIdByItem(bp);

        if (currentOperation != null) {
            if (takeCharge(l, progressive)) {
                if (!currentOperation.isFinished()) {
                    processor.updateProgressBar(inv, SLOT_INDICATOR, currentOperation);
                    currentOperation.addProgress(progressive);
                } else {
                    inv.replaceExistingItem(SLOT_INDICATOR, UIUtils.UI_BACKGROUND);

                    for (ItemStack output : currentOperation.getResults()) {
                        inv.pushItem(output.clone(), getOutputSlots());
                    }

                    processor.endOperation(b);
                    StorageCacheUtils.setData(l, "in-process", "false");
                }
            }
        } else {
            MachineRecipe next;
            try {
                next = findNextRecipe(inv, bpid);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (next != null) {
                currentOperation = new CraftingOperation(next);
                processor.startOperation(b, currentOperation);

                // Fixes #3534 - Update indicator immediately
                processor.updateProgressBar(inv, SLOT_INDICATOR, currentOperation);

                StorageCacheUtils.setData(l, "in-process", "true");
            }
        }
    }

    protected MachineRecipe findNextRecipe(BlockMenu inv, String bpid) throws IOException {
        ItemStack[] recipe = BlueprintUtils.getRecipe(bpid);
        Map<Integer, ItemStack> inventory = new HashMap<>();

        for (int slot : getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);

            if (item != null) {
                inventory.put(slot, ItemStackWrapper.wrap(item));
            }
        }

        Map<Integer, Integer> found = new HashMap<>();

        for (ItemStack input : recipe) {
            for (int slot : getInputSlots()) {
                if (SlimefunUtils.isItemSimilar(inventory.get(slot), input, true)) {
                    found.put(slot, input.getAmount());
                    break;
                }
            }
        }

        if (found.size() == recipe.length) {
            int tAmount = 0;
            for (ItemStack im : recipe) {
                tAmount += im.getAmount();
            }

            if (!InvUtils.fitAll(inv.toInventory(), new ItemStack[]{BlueprintUtils.getTarget(bpid)}, getOutputSlots())) {
                return null;
            }

            for (Map.Entry<Integer, Integer> entry : found.entrySet()) {
                inv.consumeItem(entry.getKey(), entry.getValue());
            }

            return new MachineRecipe(tAmount / getSpeed(), recipe, new ItemStack[]{BlueprintUtils.getTarget(bpid)});
        } else {
            found.clear();
        }

        return null;
    }

    public int[] getMiscItemSlots() {
        return new int[]{SLOT_CIRCUIT};
    }

}
