package io.sn.blueprint.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class BlueprintCategories {


    public static final ItemGroup BLUEPRINT = new ItemGroup(
            new NamespacedKey("neoblueprint", "blueprint_itemgroup"),
            new CustomItemStack(Material.MAP, "&9蓝图 : Blueprint Remake&f"), 4
    );

    //new ItemBuilder(Material.EMPTY_MAP).setName("§7蓝图").setLore("", "§a> 点击打开"), 4);

}
