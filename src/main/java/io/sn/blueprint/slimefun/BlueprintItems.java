package io.sn.blueprint.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import io.sn.dumortierite.utils.DHeadTexture;
import org.bukkit.Material;

public class BlueprintItems {

    public static final SlimefunItemStack ASM_MACHINE_1 = new SlimefunItemStack("ASM_MACHINE_1", new CustomItemStack(DHeadTexture.ASSEMBLY.getItem(), "&c组装机&r", "",
            LoreBuilder.machine(MachineTier.BASIC, MachineType.MACHINE),
            LoreBuilder.speed(1),
            "&8⇨ &7容量: 3", LoreBuilder.powerPerSecond(4), LoreBuilder.powerBuffer(64)));
    public static final SlimefunItemStack ASM_MACHINE_2 = new SlimefunItemStack("ASM_MACHINE_2", new CustomItemStack(DHeadTexture.ASSEMBLY.getItem(), "&c组装机 &7- &eII&r", "",
            LoreBuilder.machine(MachineTier.AVERAGE, MachineType.MACHINE),
            LoreBuilder.speed(1)
            , "&8⇨ &7容量: 7", LoreBuilder.powerPerSecond(16), LoreBuilder.powerBuffer(128)));
    public static final SlimefunItemStack ASM_MACHINE_3 = new SlimefunItemStack("ASM_MACHINE_3", new CustomItemStack(DHeadTexture.ASSEMBLY.getItem(), "&c组装机 &7- &eIII&r", "",
            LoreBuilder.machine(MachineTier.MEDIUM, MachineType.MACHINE),
            LoreBuilder.speed(2),
            "&8⇨ &7容量: 11", LoreBuilder.powerPerSecond(32), LoreBuilder.powerBuffer(256)));
    public static final SlimefunItemStack ASM_MACHINE_4 = new SlimefunItemStack("ASM_MACHINE_4", new CustomItemStack(DHeadTexture.ASSEMBLY.getItem(), "&c组装机 &7- &eIV&r", "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            LoreBuilder.speed(3),
            "&8⇨ &7容量: 14", LoreBuilder.powerPerSecond(64), LoreBuilder.powerBuffer(512)));
    public static final SlimefunItemStack ASM_MACHINE_5 = new SlimefunItemStack("ASM_MACHINE_5", new CustomItemStack(DHeadTexture.ASSEMBLY.getItem(), "&c组装机 &7- &eV&r", "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            LoreBuilder.speed(4),
            "&8⇨ &7容量: 19", LoreBuilder.powerPerSecond(128), LoreBuilder.powerBuffer(1024)));
    public static final SlimefunItemStack ASM_MACHINE_6 = new SlimefunItemStack("ASM_MACHINE_6", new CustomItemStack(DHeadTexture.ASSEMBLY.getItem(), "&c组装机 &7- &eVI&r", "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(6),
            "&8⇨ &7容量: 21", LoreBuilder.powerPerSecond(256), LoreBuilder.powerBuffer(2048)));

    public static final SlimefunItemStack BLUEPRINT = new SlimefunItemStack("BLUEPRINT", new CustomItemStack(Material.PAPER, "&d蓝图&r", "", "&f合成目标: &eNONE", "&f蓝图编号: &eNONE"));

}
