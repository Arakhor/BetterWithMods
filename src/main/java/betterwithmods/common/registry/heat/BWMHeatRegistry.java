package betterwithmods.common.registry.heat;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.util.Hashtable;

public class BWMHeatRegistry {
    private static final Hashtable<String, BWMHeatSource> heatSources = new Hashtable<>();

    public static void setBlockHeatRegistry(Block block, int meta, int value) {
        BWMHeatSource entry = new BWMHeatSource(block, meta, value);
        heatSources.put(block + ":" + meta, entry);
    }

    public static void setBlockHeatRegistry(Block block, int value) {
        for (int i = 0; i < 16; i++) {
            setBlockHeatRegistry(block, i, value);
        }
    }

    public static boolean contains(IBlockState state) {
        return contains(state.getBlock(), state.getBlock().getMetaFromState(state));
    }

    public static boolean contains(Block block, int meta) {
        return heatSources.containsKey(block + ":" + meta);
    }

    public static BWMHeatSource get(IBlockState state) {
        return get(state.getBlock(), state.getBlock().getMetaFromState(state));
    }

    public static BWMHeatSource get(Block block, int meta) {
        return heatSources.get(block + ":" + meta);
    }

    public static Hashtable<String, BWMHeatSource> getHeatRegistry() {
        return heatSources;
    }
}
