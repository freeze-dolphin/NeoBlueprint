package io.elixir_crystal.xortrax.blueprint.utils;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.Slimefun.Misc.compatibles.ProtectionUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CommonUtils {

    public static boolean reflectCanOpenMethod(Block b, Player p) {
        try {
            Class.forName("me.mrCookieSlime.Slimefun.Misc.compatibles.ProtectionUtils");
            boolean perm = (p.hasPermission("slimefun.inventory.bypass")) || (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true));
            return (perm) && (ProtectionUtils.canAccessItem(p, b));
        } catch (ClassNotFoundException cnfe) {
            return p.hasPermission("slimefun.inventory.bypass") || CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true);
        }
    }

}
