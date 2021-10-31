package io.elixir_crystal.xortrax.blueprint.commands;

import io.elixir_crystal.xortrax.blueprint.PlugGividado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import redempt.redlib.misc.FormatUtils;

import java.io.File;

@AllArgsConstructor
@Getter
public class DeleteRunnable implements Runnable {

    private final PlugGividado plug;
    private final CommandSender sender;
    private final String id;

    private static final String SEP = File.separator;

    @Override
    public void run() {
        try {
            if (new File(Bukkit.getPluginManager().getPlugin("Blueprint").getDataFolder().getPath() + SEP + "storage" + SEP + getId() + ".yml").delete()) {
                getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&aSuccessfully deleted"));
            }
        } catch (Exception e) {
            getSender().sendMessage(FormatUtils.color(getPlug().getPrefix() + "&cError occurred: " + e.getMessage()));
        }
    }

}
