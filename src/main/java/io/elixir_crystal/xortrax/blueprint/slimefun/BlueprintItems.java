package io.elixir_crystal.xortrax.blueprint.slimefun;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.itemutils.ItemBuilder;

public class BlueprintItems {

    public static final ItemStack ASM_MACHINE_1 = (new CustomItem(new UniversalMaterial(Material.STAINED_GLASS, 14), "§c组装机§r", "", "§e基础机器", "§8⇨ §7速度: 1x", "§8⇨ §7容量: 3", "§8⇨ §e⚡ §74 J/s"));
    public static final ItemStack ASM_MACHINE_2 = (new CustomItem(new UniversalMaterial(Material.STAINED_GLASS, 14), "§c组装机 §7- §eII§r", "", "§e基础机器", "§8⇨ §7速度: 1x", "§8⇨ §7容量: 7", "§8⇨ §e⚡ §716 J/s"));
    public static final ItemStack ASM_MACHINE_3 = (new CustomItem(new UniversalMaterial(Material.STAINED_GLASS, 14), "§c组装机 §7- §eIII§r", "", "§a中级机器", "§8⇨ §7速度: 2x", "§8⇨ §7容量: 11", "§8⇨ §e⚡ §732 J/s"));
    public static final ItemStack ASM_MACHINE_4 = (new CustomItem(new UniversalMaterial(Material.STAINED_GLASS, 14), "§c组装机 §7- §eIV§r", "", "§6高级机器", "§8⇨ §7速度: 3x", "§8⇨ §7容量: 14", "§8⇨ §e⚡ §764 J/s"));
    public static final ItemStack ASM_MACHINE_5 = (new CustomItem(new UniversalMaterial(Material.STAINED_GLASS, 14), "§c组装机 §7- §eV§r", "", "§6高级机器", "§8⇨ §7速度: 4x", "§8⇨ §7容量: 21", "§8⇨ §e⚡ §7192 J/s"));
    public static final ItemStack ASM_MACHINE_6 = (new CustomItem(new UniversalMaterial(Material.STAINED_GLASS, 14), "§c组装机 §7- §eVI§r", "", "§4终极机器", "§8⇨ §7速度: 8x", "§8⇨ §7容量: 21", "§8⇨ §e⚡ §7576 J/s"));

    public static final ItemStack BLUEPRINT = (new ItemBuilder(Material.PAPER).setName("§d蓝图§r").setLore("", "§f合成目标: §eNONE", "§f蓝图编号: §eNONE"));

}
