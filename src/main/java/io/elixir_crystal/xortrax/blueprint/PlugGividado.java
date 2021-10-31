package io.elixir_crystal.xortrax.blueprint;

import io.elixir_crystal.xortrax.blueprint.commands.CommandBus;
import io.elixir_crystal.xortrax.blueprint.slimefun.SlimeProvider;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.commandmanager.CommandParser;
import redempt.redlib.configmanager.ConfigManager;

@Getter
public class PlugGividado extends JavaPlugin {

    private Plugin plug;
    private ConfigManager cman;

    @SneakyThrows
    @Override
    public void onEnable() {

        plug = this;
        cman = new ConfigManager(this).register(new ConfigBus()).saveDefaults().load();
        new CommandParser(getPlug().getResource("command.rdcml")).parse().register(getPlug().getDescription().getName(),
                new CommandBus(this));

        new SlimeProvider(this).setup();
    }

    public String getPrefix() {
        return cman.getConfig().getString("prefix");
    }

    public boolean isDebug() {
        return cman.getConfig().getBoolean("debug");
    }

}
