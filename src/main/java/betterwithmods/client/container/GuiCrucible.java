package betterwithmods.client.container;

import betterwithmods.common.blocks.tile.TileEntityCrucible;
import net.minecraft.entity.player.EntityPlayer;

public class GuiCrucible extends GuiCookingPot {
    public GuiCrucible(EntityPlayer player, TileEntityCrucible crucible) {
        super(player, crucible);
    }
}
