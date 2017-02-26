package betterwithmods.client.container.bulk;

import betterwithmods.BWMod;
import betterwithmods.blocks.tile.TileEntityFilteredHopper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiFilteredHopper extends GuiContainer {
    private final TileEntityFilteredHopper tile;

    public GuiFilteredHopper(EntityPlayer player, TileEntityFilteredHopper tile) {
        super(new ContainerFilteredHopper(player, tile));
        this.ySize = 193;
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = I18n.format(tile.getName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f,
                                                   int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(BWMod.MODID, "textures/gui/other.png"));

        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);

        if (this.tile.power > 0) {
            drawTexturedModalRect(xPos + 80, yPos + 18, 176, 0, 14, 14);
        }

    }
}
