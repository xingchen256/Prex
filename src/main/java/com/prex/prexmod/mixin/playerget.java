package com.prex.prexmod.mixin;

import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TransmutationInventory.class)
public interface playerget {
        @Accessor(value = "player")
        EntityPlayer getPlayer();
}
