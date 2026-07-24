package com.prex.prexmod.block;

import moze_intel.projecte.gameObjs.tiles.CollectorMK1Tile;
public class TileCollector extends CollectorMK1Tile {
    int tirea;
    public TileCollector(int tire) {
        super((int)Math.pow(2,tire)*100000, (int)Math.pow(2,tire+7)*10, 9, 10);
        this.tirea = tire;
        //最大EMC,生存速度,格子数,???
    }
    public String func_145825_b() {
        return "tile.collector_mk"+this.tirea+4+".name";
    }
}
