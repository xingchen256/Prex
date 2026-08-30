package com.prex.prexmod;

import com.prex.prexmod.emc.PrEXEMC;
import com.prex.prexmod.emc.PrEmcMap;
import cpw.mods.fml.common.Optional;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import moze_intel.projecte.api.ProjectEAPI;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

//改自https://github.com/Fadenfire/ViewEMC/blob/master/src/main/java/silly511/viewemc/HwylaPlugin.java
@Optional.Interface(
        iface = "mcp.mobius.waila.api.IWailaDataProvider",
        modid = "Waila"
)
public class wailaHandler implements  IWailaDataProvider {

    public static final NumberFormat commanator = new DecimalFormat("#,###");
    @Optional.Method(
            modid = "Waila"
    )
    public static void callbackRegister(IWailaRegistrar register) {
        register.registerBodyProvider(new wailaHandler(), Block.class);
        register.addConfig("EMC", "emc.showEMC", true);
    }

    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        int blockEMC = 0;
        Item item = accessor.getBlock().getItem(accessor.getWorld(),
                accessor.getPosition().blockX,
                accessor.getPosition().blockY,
                accessor.getPosition().blockZ);
        ItemStack pitem=null;
        if (item != null) {
            Block blockx = (item instanceof ItemBlock && !accessor.getBlock().isNormalCube())
                    ? Block.getBlockFromItem(item)
                    : accessor.getBlock();
            int meta = blockx.getDamageValue(accessor.getWorld(),
                    accessor.getPosition().blockX,
                    accessor.getPosition().blockY,
                    accessor.getPosition().blockZ);
            pitem=new ItemStack(item, 1, meta);
            blockEMC = ProjectEAPI.getEMCProxy().getValue(pitem);
        }

        if (config.getConfig("emc.showEMC")) {
            if (blockEMC > 0) {
                String p=PrEXEMC.getEMCString(BigInteger.valueOf(blockEMC));
                if (PrEmcMap.contains(pitem)){
                    p=PrEXEMC.getEMCString(PrEmcMap.get(pitem));
                }
                currenttip.add(EnumChatFormatting.YELLOW + "EMC: " +
                        EnumChatFormatting.WHITE +  p+
                        EnumChatFormatting.RESET);
            }
        }
        return currenttip;
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return tag;
    }


}
