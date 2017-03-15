package betterwithmods.integration.minetweaker;


import betterwithmods.common.registry.bulk.BulkRecipe;
import betterwithmods.common.registry.bulk.CraftingManagerCauldron;
import com.blamejared.mtlib.helpers.InputHelper;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Purpose:
 *
 * @author Tyler Marshall
 * @version 12/31/16
 */
@ZenClass(Cauldron.clazz)
public class Cauldron {

    public static final String clazz = "mods.betterwithmods.Cauldron";

    @ZenMethod
    public static void add(IItemStack output, @Optional IItemStack secondaryOutput, @NotNull IIngredient[] inputs) {
        BulkRecipe r = new BulkRecipe("cauldron", InputHelper.toStack(output), InputHelper.toStack(secondaryOutput),InputHelper.toObjects(inputs));
        MineTweakerAPI.apply(new BulkAdd("cauldron", CraftingManagerCauldron.getInstance(),r));
    }

    @ZenMethod
    public static void remove(IItemStack output) {
        MineTweakerAPI.apply(new BulkRemove("cauldron", CraftingManagerCauldron.getInstance(),InputHelper.toStack(output)));
    }

    @ZenMethod
    public static void remove(IItemStack output, @NotNull IIngredient[] inputs) {
        MineTweakerAPI.apply(new BulkRemove("cauldron", CraftingManagerCauldron.getInstance(),InputHelper.toStack(output), InputHelper.toObjects(inputs)));
    }
}
