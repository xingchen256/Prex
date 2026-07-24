package com.prex.prexmod.block;

import moze_intel.projecte.gameObjs.tiles.RelayMK1Tile;

public class TitleRelay extends RelayMK1Tile {
    private int tier;
    public TitleRelay(int tier) {
        //tier=0
        super(6,(int)Math.pow(tier+1,2)*1000_0000, 64*(int)Math.pow(tier+4,2));
        this.tier = tier;
        //格子，最大EMC,输出
    }

    public String func_145825_b() {
        return "tile.prex_relay_mk"+(this.tier+4)+".name";
    }
}
