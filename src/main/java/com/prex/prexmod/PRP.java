package com.prex.prexmod;


import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import cpw.mods.fml.common.Loader;

import javax.annotation.Nonnull;
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
        if (Loader.isModLoaded("betterbuilderswands")) mixins.add("builder_wands");//兼容性修改
        if (Loader.isModLoaded("equivalentenergistics")) {
            mixins.add("EmcCondenserBase");
            mixins.add("EMCCraftingPatternMixin");
            mixins.add("ProjectEMixin");
        }//兼容应用能源
        return mixins;
    }
}
