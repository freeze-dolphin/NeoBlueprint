package io.sn.blueprint;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.sn.blueprint.slimefun.SlimeProvider;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@Getter
public class NeoBlueprintCore extends JavaPlugin implements SlimefunAddon {

    static {
        Thread.currentThread().setContextClassLoader(NeoBlueprintCore.class.getClassLoader());
    }

    private Plugin plug;

    @SuppressWarnings("CommentedOutCode")
    @SneakyThrows
    @Override
    public void onEnable() {
        plug = this;

        File stg = new File(this.getDataFolder().getPath() + File.separator + "storage");
        if (!stg.exists()) {
            if (!stg.mkdirs()) {
                this.setEnabled(false);
            }
        }

        File etg = new File(this.getDataFolder().getParent() + File.separator + "config.edn");
        if (!etg.exists()) {
            this.saveResource("config.edn", false);
        }

        // cman = new ConfigManager(this).register(new ConfigBus()).saveDefaults().load();
        // new CommandParser(getPlug().getResource("command.rdcml")).parse().register(getPlug().getDescription().getName(),
        //        new CommandBus(this));

        new SlimeProvider(this).setup();
    }


    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return "https://github.com/freeze-dolphin/NeoBlueprint";
    }
}
