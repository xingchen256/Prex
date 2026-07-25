package com.prex.prexmod.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class SpeedUpdateBlock extends BlockContainer {
    public int tww=1;
    protected SpeedUpdateBlock(int tww) {
        super(Material.iron);
        this.tww = tww;
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 2);
        this.setBlockName("prex_speed_update_block_mk" + tww);
        this.setCreativeTab(PREX_TAB);
        this.setBlockTextureName("prex:speed/"+tww);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileSpeedUpdateBlock();
    }
}
