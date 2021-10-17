package io.elixir_crystal.xortrax.blueprint.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import io.elixir_crystal.xortrax.blueprint.PlugGividado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import redempt.redlib.commandmanager.CommandHook;

@AllArgsConstructor
@Getter
public class CommandBus {

    private final PlugGividado plug;

    @CommandHook("reload")
    public void reload(CommandSender sender) {
        Bukkit.getScheduler().runTask(getPlug(), new ReloadRunnable(getPlug(), sender));
    }

    @CommandHook("create")
    public void create(CommandSender sender) {
        Bukkit.getScheduler().runTask(getPlug(), new CreateRunnable(getPlug(), sender));
    }

    @CommandHook("del")
    public void del(CommandSender sender) {
        Bukkit.getScheduler().runTask(getPlug(), new DeleteRunnable(getPlug(), sender));
    }

}
