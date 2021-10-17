package io.elixir_crystal.xortrax.blueprint;

import redempt.redlib.configmanager.annotations.ConfigValue;
import redempt.redlib.misc.FormatUtils;

public class ConfigBus {

    @ConfigValue("prefix")
    public final String prefix = FormatUtils.color("&f[&d蓝图&f] ");

}
