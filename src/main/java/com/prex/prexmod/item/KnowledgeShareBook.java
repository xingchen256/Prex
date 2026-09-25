package com.prex.prexmod.item;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.playerData.Transmutation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class KnowledgeShareBook extends Item {

    public KnowledgeShareBook() {
        this.setMaxStackSize(1);
        this.setTextureName("prex:knowledge_sharing_book");
        this.setUnlocalizedName("knowledge_share_book");
        this.setCreativeTab(PREX_TAB);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        //判断是否写入过
        if (player.isSneaking()) {//是否shift
            if (!world.isRemote) {
                if (stack.stackTagCompound == null) {
                    stack.stackTagCompound = new NBTTagCompound();//新建nbt
                }

                stack.stackTagCompound.setString("id", player.getUniqueID().toString());//玩家uuid
                stack.stackTagCompound.setString("name", player.getCommandSenderName());//玩家名字
            }

            return stack;
        }
//      ???
        if (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey("id")) {
            return stack;
        }
        //获取uuid
        UUID id = UUID.fromString(stack.stackTagCompound.getString("id"));
        String uname= stack.stackTagCompound.getString("name");
        //与当前玩家比对,是否是A
        if (id.equals(player.getUniqueID())) {
            return stack;
        }

        if (!world.isRemote) {
            //当前玩家知识(不是分享的A)是B
            List<ItemStack> playerKnowledge = Lists.newArrayList(Transmutation.getKnowledge(player));
            //获取玩家实体
            EntityPlayer pa=world.func_152378_a(id);
            if(pa==null)pa=world.getPlayerEntityByName(uname);
            List<ItemStack> otherKnowledge = Lists.newArrayList(Transmutation.getKnowledge(pa));
            if (otherKnowledge == null) return stack;
            for (ItemStack stack1 : otherKnowledge) {
                Transmutation.addKnowledge(stack1,player);//给予玩家知识
            }
            Transmutation.sync(player);//数据包

            world.playSoundEffect(
                    player.posX,
                    player.posY,
                    player.posZ,
                    "random.break",
                    0.8F,
                    0.8F + world.rand.nextFloat() * 0.4F
            );

        } else {
            //莫名的粒子效果
            for (int i = 0; i < 5; i++) {

                Vec3 motion = Vec3.createVectorHelper(
                        (world.rand.nextFloat() - 0.5D) * 0.1D,
                        Math.random() * 0.1D + 0.1D,
                        0.0D);

                motion.rotateAroundX(-player.rotationPitch * 0.017453292F);
                motion.rotateAroundY(-player.rotationYaw * 0.017453292F);

                double d0 = (-world.rand.nextFloat()) * 0.6D - 0.3D;

                Vec3 pos = Vec3.createVectorHelper(
                        (world.rand.nextFloat() - 0.5D) * 0.3D,
                        d0,
                        0.6D);

                pos.rotateAroundX(-player.rotationPitch * 0.017453292F);
                pos.rotateAroundY(-player.rotationYaw * 0.017453292F);

                pos.xCoord += player.posX;
                pos.yCoord += player.posY + player.getEyeHeight();
                pos.zCoord += player.posZ;

                Minecraft.getMinecraft().effectRenderer.addEffect(
                        new EntityBreakingFX(
                                world,
                                pos.xCoord,
                                pos.yCoord,
                                pos.zCoord,
                                motion.xCoord,
                                motion.yCoord + 0.05D,
                                motion.zCoord,
                                this,
                                0
                        )
                );
            }
        }

        stack.stackSize--;

        return stack;
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("id");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack,
                               EntityPlayer player,
                               List list,
                               boolean advanced) {
        list.add(StatCollector.translateToLocalFormatted("prex.item.knowledge_share_book.tip1"));
        list.add(StatCollector.translateToLocalFormatted("prex.item.knowledge_share_book.tip2"));
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("name")) {
            list.add((EnumChatFormatting.GOLD+StatCollector.translateToLocalFormatted("prex.item.knowledge_share_book.tip3")+stack.getTagCompound().getString("name")));
        }
    }
}

