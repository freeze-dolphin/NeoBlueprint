package io.elixir_crystal.xortrax.blueprint.utils;

import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class BlueprintUtils {

    private static final String SEP = File.separator;
    private static final String DPTH = Bukkit.getPluginManager().getPlugin("Blueprint").getDataFolder().getPath();

    public static String[] listAll() throws IOException {
        File f = new File(DPTH + SEP + "storage");

        if (!f.exists()) {
            if (!f.mkdirs()) throw new IOException("Unable to create storage.");
        }

        return f.list((dir, name) -> {
            if (name.endsWith(".yml")) {
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(dir.getPath() + SEP + name + ".yml"));
                return yml.contains("target") && yml.contains("recipe");
            }
            return false;
        });
    }

    public static void createRecipe(String id, ItemStack target, ItemStack... ingredients) throws IOException, IllegalStateException {
        File f = new File(DPTH + SEP + "storage" + SEP + id + ".yml");

        if (!f.exists()) {
            if (!f.createNewFile()) throw new IOException("Unable to create recipe.");
        } else {
            throw new IllegalStateException("Already existed.");
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
        yml.set("target", target);

        for (int i = 0; i < ingredients.length; i++) {
            yml.set("recipe." + i, ingredients[i]);
        }

        yml.save(f);
    }

    public static ItemStack[] getRecipe(String id) throws IOException {
        File f = new File(DPTH + SEP + "storage" + SEP + id + ".yml");

        if (!f.exists()) throw new IOException("Blueprint not found.");

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

        if (!yml.contains("recipe")) throw new IllegalStateException("This is not a valid blueprint.");

        List<ItemStack> r = new ArrayList<>();
        for (int i = 0; i < yml.createSection("recipe").getKeys(false).size(); i++) {
            r.add(yml.getItemStack("recipe." + i));
        }
        // TODO this method returns empty things, so player cannot see any ingredients in blueprint preview
        ItemStack[] rr = new ItemStack[r.size()];
        return r.toArray(rr);
    }

    public static ItemStack getTarget(String id) throws IOException {
        File f = new File(DPTH + SEP + "storage" + SEP + id + ".yml");

        if (!f.exists()) throw new IOException("Blueprint not found.");

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

        if (!yml.contains("target")) throw new IllegalStateException("This is not a valid blueprint.");

        return yml.getItemStack("target");
    }

    @Nullable
    public static ItemStack searchRecipe(ItemStack... recipes) {
        for (File f : Objects.requireNonNull(new File(DPTH + SEP + "storage").listFiles((dir, name) -> name.endsWith(".yml")))) {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

            if (yml.contains("target") && yml.contains("recipe")) {
                int matched = 0;
                for (ItemStack ingredient : recipes) {
                    for (int i = 0; i < yml.createSection("recipe").getKeys(false).size(); i++) {
                        ItemStack iR = yml.getItemStack("recipe." + i);
                        if (SlimefunManager.isItemSimiliar(ingredient, iR, true) && ingredient.getAmount() >= iR.getAmount()) {
                            matched++;
                        }
                    }
                }

                if (matched == yml.createSection("recipe").getKeys(false).size()) {
                    return yml.getItemStack("target");
                }
            }
        }
        return null;
    }

    public static String getIdByItem(ItemStack item) {
        String[] idd = ChatColor.stripColor(item.getItemMeta().getLore().get(2)).split(": ");
        return idd[idd.length - 1];
    }
}
