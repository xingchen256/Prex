package com.prex.prexmod.block;
import com.prex.prexmod.item.PrExItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class PowerFlowerRender extends TileEntitySpecialRenderer {

    private IModelCustom model1;
    private IModelCustom model2;


    public PowerFlowerRender(){

        model1 = AdvancedModelLoader.loadModel(
                new ResourceLocation("prex:models/power_flower/power_flower_a.obj"));
        model2 = AdvancedModelLoader.loadModel(
                new ResourceLocation("prex:models/power_flower/power_flower_b.obj"));

    }
    private void setRender(int matter,String tww){
        ResourceLocation relayTexture;
        String q=PrExBlocks.Color[matter-1];
        if(matter==16)q="qwq";
        relayTexture = new ResourceLocation("prex:textures/blocks/"+tww+"/" + q + ".png");
        Minecraft.getMinecraft()
                .getTextureManager()
                .bindTexture(relayTexture);
    }

    @Override
    public void renderTileEntityAt(
            TileEntity tile,
            double x, double y, double z,
            float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GL11.glScalef(1F, 1F, 1F);
        int matter=((TilePowerFlower)tile).getmatter();
        setRender(matter,"collector");
        model2.renderAll();
        setRender(matter,"relay");
        model1.renderAll();
//        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

    }
}