package betterwithmods.items;

import betterwithmods.client.BWCreativeTabs;
import betterwithmods.config.BWConfig;

/**
 * Purpose:
 *
 * @author Tyler Marshall
 * @version 11/15/16
 */
public class ItemBreedingHarness extends ItemAltName {
    public ItemBreedingHarness() {
        setCreativeTab(BWCreativeTabs.BWTAB);
        setMaxStackSize(1);
    }

    @Override
    public String[] getLocations() {
        if (BWConfig.kidFriendly)
            return new String[]{"breeding_harness_kf"};
        else
            return new String[]{"breeding_harness"};
    }
}
