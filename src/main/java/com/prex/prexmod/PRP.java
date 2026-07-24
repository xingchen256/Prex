package com.prex.prexmod;


import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//全称ProjectReEx Plugin
@LateMixin
public class PRP implements ILateMixinLoader {
//神秘的mixin启动器,如果你在开发端可以把这个删了
    @Override
    public String getMixinConfig() {
        return "mixins.prex.json";
    }

    @Nonnull
    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        List<String> mixins = Stream.of(
                "Conderser",
                "FuelMap",
                "ItemConsume",
                "ItemEMCH",
                "ItemEMCM",
                "ItemEMCT",
                "ItemEMCTC",
                "ItemInput",
                "ItemLock",
                "ItemTips",
                "playerget",
                "ProjectEEMCMixin",
                "ProjectGUI",
                "ProjectMaxEMC",
                "RecipeItemEmc",
                "tbb",
                "Te").collect(Collectors.toList());
        return mixins;
    }
}
