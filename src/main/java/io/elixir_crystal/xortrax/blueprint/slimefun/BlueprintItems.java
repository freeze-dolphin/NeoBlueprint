package io.elixir_crystal.xortrax.blueprint.slimefun;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.itemutils.ItemBuilder;

@RequiredArgsConstructor
@Getter
public enum BlueprintItems {

    ASM_MACHINE_1(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE)).addDamage(14)
            .setName("§c组装机§r")
            .setLore("", "§e基础机器", "§8⇨ §7速度: 1x", "§8⇨ §7容量: 3", "§8⇨ §e⚡ §78 J/s")),

    ASM_MACHINE_2(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE)).addDamage(14)
            .setName("§c组装机 §7- §eII§r")
            .setLore("", "§e基础机器", "§8⇨ §7速度: 2x", "§8⇨ §7容量: 7", "§8⇨ §e⚡ §716 J/s")),

    ASM_MACHINE_3(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE)).addDamage(14)
            .setName("§c组装机 §7- §eIII§r")
            .setLore("", "§a中级机器", "§8⇨ §7速度: 2x", "§8⇨ §7容量: 11", "§8⇨ §e⚡ §724 J/s")),

    ASM_MACHINE_4(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE)).addDamage(14)
            .setName("§c组装机 §7- §eIV§r")
            .setLore("", "§6高级机器", "§8⇨ §7速度: 3x", "§8⇨ §7容量: 14", "§8⇨ §e⚡ §786 J/s")),

    ASM_MACHINE_5(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE)).addDamage(14)
            .setName("§c组装机 §7- §eV§r")
            .setLore("", "§4终极机器", "§8⇨ §7速度: 5x", "§8⇨ §7容量: 21", "§8⇨ §e⚡ §7256 J/s")),

    BLUEPRINT(new ItemBuilder(Material.PAPER)
            .setName("§d蓝图§r")
            .setLore("", "§f合成目标: §7NONE"));

    private final ItemStack item;

}
