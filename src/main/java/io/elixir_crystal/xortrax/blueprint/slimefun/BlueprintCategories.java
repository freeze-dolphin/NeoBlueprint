package io.elixir_crystal.xortrax.blueprint.slimefun;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.Material;
import redempt.redlib.itemutils.ItemBuilder;

@RequiredArgsConstructor
@Getter
public enum BlueprintCategories {

    BLUEPRINT(new Category(new ItemBuilder(Material.EMPTY_MAP).setName("§7蓝图").setLore("", "§a> 点击打开"), 4));

    private final Category category;

}
