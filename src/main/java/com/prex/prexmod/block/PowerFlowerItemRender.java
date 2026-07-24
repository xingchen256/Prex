package com.prex.prexmod.block;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class PowerFlowerItemRender implements IItemRenderer {
    private IModelCustom model1;
    private IModelCustom model2;

    public PowerFlowerItemRender(){

        model1 = AdvancedModelLoader.loadModel(
                new ResourceLocation(
                        "prex:models/power_flower/power_flower_a.obj")
        );
        model2 = AdvancedModelLoader.loadModel(
                new ResourceLocation(
                        "prex:models/power_flower/power_flower_b.obj")
        );

    }


    @Override
    public boolean handleRenderType(
            ItemStack item,
            ItemRenderType type) {
        return true;
    }


    @Override
    public boolean shouldUseRenderHelper(
            ItemRenderType type,
            ItemStack item,
            ItemRendererHelper helper) {return true;}
    private void setRender(ItemStack item,String w) {
        for(PowerFlower a:PrExBlocks.power_flower){
            if(Item.getItemFromBlock(a).equals(item.getItem())){
                String tww=PrExBlocks.Color[a.matter-1];
                if(a.matter==16)tww="qwq";
                Minecraft.getMinecraft()
                        .getTextureManager()
                        .bindTexture(
                                new ResourceLocation(
                                        "prex:textures/blocks/"+w+"/" + tww+ ".png"));}
        }
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        if(type == ItemRenderType.INVENTORY){
//            GL11.glRotatef(180F, 0F, 0F, 180F);
//            GL11.glRotatef(0, 0, 0, 0);
//            GL11.glScalef(1F, -1F, 1F);
            GL11.glTranslatef(0, -0.5F, 0);
        }

        setRender(item,"relay");
        model1.renderAll();
        setRender(item,"collector");
        model2.renderAll();
        GL11.glPopMatrix();

    }
}
