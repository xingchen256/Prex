package com.prex.prexmod.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class SpeedUpdatePowerFlower extends BlockContainer {//力量花加速
    public int tww;
    protected SpeedUpdatePowerFlower(int matter) {
        super(Material.glass);
        this.tww=matter;
        setCreativeTab(PREX_TAB);
        setLightOpacity(16+matter);
        setHardness(10.0F);
        setHarvestLevel("pickaxe", 2);
        setBlockName("prex_speed_update_block_power_flower_mk" + matter);
        setBlockTextureName("prex:speed_update_power_flower/"+matter);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileSpeedUpdatePowerFlower();
    }
}
