package betterwithmods.integration.jei.wrapper;

import betterwithmods.common.registry.HopperFilters;
import betterwithmods.common.registry.HopperInteractions;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Purpose:
 *
 * @author Tyler Marshall
 * @version 11/20/16
 */
public class HopperRecipeWrapper extends BlankRecipeWrapper {

    private final List<ItemStack> input;
    private final List<ItemStack> filter;
    private final List<ItemStack> outputs;

    public HopperRecipeWrapper(HopperInteractions.HopperRecipe recipe) {
        this.input = Lists.newArrayList(recipe.getInput());

        this.outputs = Lists.newArrayList(recipe.getOutput());
        if (!recipe.getSecondaryOutput().isEmpty())
            this.outputs.addAll(recipe.getSecondaryOutput());
        this.filter = HopperFilters.getFilter(recipe.getFilterType());
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, Stream.concat(filter.stream(), input.stream()).collect(Collectors.toList()));
        ingredients.setOutputs(ItemStack.class, outputs);
    }

    public static class SoulUrn extends HopperRecipeWrapper {
        public SoulUrn(HopperInteractions.SoulUrnRecipe recipe) {
            super(recipe);
        }
    }
}
