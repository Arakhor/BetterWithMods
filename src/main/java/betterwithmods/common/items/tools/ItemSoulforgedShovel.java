package betterwithmods.common.items.tools;

import betterwithmods.common.BWMItems;
import betterwithmods.client.BWCreativeTabs;
import net.minecraft.item.ItemSpade;

public class ItemSoulforgedShovel extends ItemSpade {
    public ItemSoulforgedShovel() {
        super(BWMItems.SOULFORGED_STEEL);
        setCreativeTab(BWCreativeTabs.BWTAB);
    }
}
