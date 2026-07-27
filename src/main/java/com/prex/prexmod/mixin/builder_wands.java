package com.prex.prexmod.mixin;

import com.prex.prexmod.block.PrExBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import portablejim.bbw.basics.Point3d;
import portablejim.bbw.core.WandWorker;
import portablejim.bbw.core.conversion.CustomMapping;
import portablejim.bbw.shims.BasicWorldShim;
import portablejim.bbw.shims.IPlayerShim;
import portablejim.bbw.shims.IWorldShim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

@Mixin(WandWorker.class)
public class builder_wands {
    @Shadow
    private IPlayerShim player;
    @Shadow
    private IWorldShim world;
    @Inject(
            method = "placeBlocks",
            at = @At(
                    value="INVOKE",
                    target = "Lportablejim/bbw/shims/IWorldShim;playPlaceAtBlock(Lportablejim/bbw/basics/Point3d;Lnet/minecraft/block/Block;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            remap=false
    )
    private void prex$playerPlace(
            ItemStack wandItem,
            LinkedList<Point3d> blockPosList,
            Point3d originalBlock,
            ItemStack sourceItems,
            int side,
            float hitX,
            float hitY,
            float hitZ,
            CallbackInfoReturnable<ArrayList<Point3d>> cir,

            ArrayList placedBlocks,
            Iterator iterator,
            Point3d blockPos,
            CustomMapping mapping,
            boolean blockPlaceSuccess,
            Item itemFromBlock
    ){
        if(blockPos == null) return;
        boolean qw=true;
        Block c=Block.getBlockFromItem(sourceItems.getItem());
        for(Block b:PrExBlocks.power_flower){
            if(c==b){
                qw=false;
                break;
            }
        }
        if(qw) return;
        EntityPlayer player = this.player.getPlayer();
        if(player==null) return;
        World world = ((BasicWorldShim)this.world).getWorld();
        c.onBlockPlacedBy(
                world,
                blockPos.x,
                blockPos.y,
                blockPos.z,
                player,
                sourceItems
        );
    }
}
