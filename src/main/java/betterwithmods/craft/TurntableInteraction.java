package betterwithmods.craft;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TurntableInteraction extends BlockMetaHandler {
    public static TurntableInteraction INSTANCE = new TurntableInteraction();

    public TurntableInteraction() {
        super("turntable");
    }

    public void addTurntableRecipe(ItemStack inputBlock, ItemStack outputBlock, ItemStack... scraps) {
        if (inputBlock != null && inputBlock.getItem() instanceof ItemBlock) {
            Block iBlock = ((ItemBlock) inputBlock.getItem()).getBlock();
            int iMeta = inputBlock.getMetadata();
            if (outputBlock == null)
                addTurntableRecipe(iBlock, iMeta, null, 0, scraps);
            else if (outputBlock.getItem() instanceof ItemBlock) {
                Block oBlock = ((ItemBlock) outputBlock.getItem()).getBlock();
                int oMeta = outputBlock.getMetadata();
                addTurntableRecipe(iBlock, iMeta, oBlock, oMeta, scraps);
            }
        }
    }

    public void addTurntableRecipe(Block block, int meta, Block result, int resultMeta, ItemStack... scraps) {
        addRecipe(new TurntableRecipe(block, meta, result, resultMeta, scraps == null ? Lists.newArrayList() : Arrays.asList(scraps)));
    }

    public static class TurntableRecipe extends BlockMetaRecipe {
        private Block result;
        private int resultMeta;

        public TurntableRecipe(Block block, int meta, Block result, int resultMeta, List<ItemStack> scraps) {
            super("turntable", block, meta, scraps);
            this.result = result;
            this.resultMeta = resultMeta;
        }

        public TurntableRecipe(ItemStack input, ItemStack output, List<ItemStack> scraps) {
            this(((ItemBlock)input.getItem()).getBlock(),input.getMetadata(),((ItemBlock)output.getItem()).getBlock(),output.getMetadata(),scraps);
        }

        public ItemStack getResult() {
            if (result == null)
                return null;
            return new ItemStack(result, 1, resultMeta);
        }
    }
}
