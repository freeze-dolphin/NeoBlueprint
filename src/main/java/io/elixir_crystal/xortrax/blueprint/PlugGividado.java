package io.elixir_crystal.xortrax.blueprint;

import io.elixir_crystal.xortrax.blueprint.commands.CommandBus;
import io.elixir_crystal.xortrax.blueprint.slimefun.SlimeProvider;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import redempt.redlib.commandmanager.CommandParser;
import redempt.redlib.configmanager.ConfigManager;

import java.io.File;

@Getter
public class PlugGividado extends JavaPlugin {

    public PlugGividado() {
        super();
    }

    protected PlugGividado(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    private Plugin plug;
    private ConfigManager cman;

    @SneakyThrows
    @Override
    public void onEnable() {

        plug = this;

        cman = new ConfigManager(this).register(new ConfigBus()).saveDefaults().load();
        new CommandParser(getPlug().getResource("command.rdcml")).parse().register(getPlug().getDescription().getName(),
                new CommandBus(this));

        new SlimeProvider().setup();
    }

    public String getPrefix() {
        return cman.getConfig().getString("prefix");
    }

}
