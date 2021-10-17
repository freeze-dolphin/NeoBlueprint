package io.elixir_crystal.xortrax.blueprint.slimefun;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
@Getter
public class SlimeProvider {

    public void setup() {

        (new SlimefunItem
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_1.getItem(),
                        "ASM_MACHINE_1",
                        RecipeType.NULL, new ItemStack[]{}))
                .registerChargeableBlock(false, 64);

        (new SlimefunItem
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_2.getItem(),
                        "ASM_MACHINE_2",
                        RecipeType.NULL, new ItemStack[]{}))
                .registerChargeableBlock(false, 108);

        (new SlimefunItem
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_3.getItem(),
                        "ASM_MACHINE_3",
                        RecipeType.NULL, new ItemStack[]{}))
                .registerChargeableBlock(false, 128);

        (new SlimefunItem
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_4.getItem(),
                        "ASM_MACHINE_4",
                        RecipeType.NULL, new ItemStack[]{}))
                .registerChargeableBlock(false, 256);

        (new SlimefunItem
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.ASM_MACHINE_5.getItem(),
                        "ASM_MACHINE_5",
                        RecipeType.NULL, new ItemStack[]{}))
                .registerChargeableBlock(false, 512);

        (new SlimefunItem
                (BlueprintCategories.BLUEPRINT.getCategory(), BlueprintItems.BLUEPRINT.getItem(),
                        "BLUEPRINT",
                        RecipeType.NULL, new ItemStack[]{}))
                .registerChargeableBlock(false, 1024);


    }

}
