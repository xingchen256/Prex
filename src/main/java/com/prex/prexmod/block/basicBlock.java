package com.prex.prexmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class basicBlock extends Block{
    private final int tier;


    public basicBlock(Material material, int tier)
    {
        super(material);

        this.tier = tier;

        setHardness(2.0F);
        setResistance(10F);
        setStepSound(soundTypeMetal);
    }


    public int getTier()
    {
        return tier;
    }
}
