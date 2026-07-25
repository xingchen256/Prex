package com.prex.prexmod;

//import com.prex.prexmod.emc.PrEMC;

import com.prex.prexmod.emc.PrEXEMC;
import cpw.mods.fml.common.FMLLog;
import moze_intel.projecte.playerData.Transmutation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Test extends Block {
    protected Test(Material p_i45394_1_) {
        super(p_i45394_1_);
    }

    @Override
    public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {
        FMLLog.info("Remc="+PrEXEMC.get(p_149699_5_).toString());
        FMLLog.info("Pemc = %s",(Transmutation.getEmc(p_149699_5_)));
        p_149699_5_.addChatMessage(
                new ChatComponentText(
                       "Remc="+PrEXEMC.get(p_149699_5_).toString()
                )
        );
        FMLLog.info(" "+QWQ.getRemcs(p_149699_5_));
        super.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
    }
}
