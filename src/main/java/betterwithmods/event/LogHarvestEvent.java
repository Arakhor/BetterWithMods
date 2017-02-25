package betterwithmods.event;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWMItems;
import betterwithmods.api.block.IDebarkable;
import betterwithmods.config.BWConfig;
import betterwithmods.common.registry.SawInteraction;
import betterwithmods.common.items.tools.ItemKnife;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import static betterwithmods.common.BWMItems.BARK;

public class LogHarvestEvent {
    public static IRecipe findMatchingRecipe(InventoryCrafting inv, World world) {
        for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
            IRecipe recipe = CraftingManager.getInstance().getRecipeList().get(i);

            if (recipe.matches(inv, world)) {
                return recipe;
            }
        }
        return null;
    }

    @SubscribeEvent
    public void debarkLog(PlayerInteractEvent.RightClickBlock evt) {
        World world = evt.getWorld();
        if (!world.isRemote) {
            BlockPos pos = evt.getPos();
            EntityPlayer player = evt.getEntityPlayer();
            Block block = world.getBlockState(pos).getBlock();
            ItemStack playerStack = player.getHeldItem(evt.getHand());
            if (playerStack == ItemStack.EMPTY)
                return;
            BlockPos playerPos = pos.offset(evt.getFace());
            if (playerStack != ItemStack.EMPTY && (playerStack.getItem().getHarvestLevel(playerStack, "axe", player, world.getBlockState(pos)) >= 0) || playerStack.getItem().getToolClasses(playerStack).contains("axe")) {
                if (block == Blocks.LOG) {
                    IBlockState state = world.getBlockState(pos);
                    IBlockState dbl = BWMBlocks.DEBARKED_OLD.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)).withProperty(BlockOldLog.VARIANT, state.getValue(BlockOldLog.VARIANT));
                    InvUtils.ejectStackWithOffset(world, playerPos, ((IDebarkable) dbl.getBlock()).getBark(dbl));
                    world.setBlockState(pos, dbl);
                    world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, SoundCategory.BLOCKS, 0.5F, 2.5F);
                    playerStack.damageItem(1, player);
                } else if (block == Blocks.LOG2) {
                    IBlockState state = world.getBlockState(pos);
                    IBlockState dbl = BWMBlocks.DEBARKED_NEW.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)).withProperty(BlockNewLog.VARIANT, state.getValue(BlockNewLog.VARIANT));
                    InvUtils.ejectStackWithOffset(world, playerPos, ((IDebarkable) dbl.getBlock()).getBark(dbl));
                    world.setBlockState(pos, dbl);
                    world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, SoundCategory.BLOCKS, 0.5F, 2.5F);
                    playerStack.damageItem(1, player);
                } else {
                    IBlockState state = world.getBlockState(pos);
                    if (SawInteraction.INSTANCE.contains(block, block.getMetaFromState(state)) && InvUtils.listContains(new ItemStack(block, 1, block.damageDropped(state)), OreDictionary.getOres("logWood"))) {
                        InvUtils.ejectStackWithOffset(world, playerPos, new ItemStack(BARK, 1, 0));
                        IBlockState dbl = BWMBlocks.DEBARKED_OLD.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y).withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
                        world.setBlockState(pos, dbl);
                        world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, SoundCategory.BLOCKS, 0.5F, 2.5F);
                        playerStack.damageItem(1, player);
                    }

                }
            }
        }
    }

    @SubscribeEvent
    public void harvestLog(BlockEvent.HarvestDropsEvent evt) {
        EntityPlayer player = evt.getHarvester();
        if (!BWConfig.hardcoreLumber || player == null)
            return;
        if (player.isEntityInvulnerable(DamageSource.CACTUS)) //Checking invulnerability because checking for instances of FakePlayer doesn't work.
            return;
        IBlockState state = evt.getState();
        Block block = state.getBlock();
        int harvestMeta = state.getBlock().damageDropped(state);
        World world = evt.getWorld();
        if (!world.isRemote && !evt.isSilkTouching()) {
            boolean harvest = false;
            if (player != null && player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) != ItemStack.EMPTY) {
                Item item = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem();

                if (item != null) {
                    if ((item.getHarvestLevel(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), "axe", player, state) >= 0 || item.getToolClasses(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND)).contains("axe")) && !(item instanceof ItemKnife)) {
                        harvest = true;
                    }
                }
            }

            if (!harvest) {
                int fortune = player != null && player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) != ItemStack.EMPTY && player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() instanceof ItemKnife ? evt.getFortuneLevel() : 0;
                boolean fort = fortune > 0;
                List<ItemStack> logs = OreDictionary.getOres("logWood");
                boolean isLog = logs.stream().filter(stack -> stack.isItemEqual(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE))).findAny().isPresent();
                if (SawInteraction.INSTANCE.contains(block, harvestMeta) && isLog && !evt.isSilkTouching()) {
                    for (ItemStack logStack : evt.getDrops()) {
                        if (logStack.getItem() instanceof ItemBlock) {
                            ItemBlock iBlock = (ItemBlock) logStack.getItem();
                            if (iBlock.getBlock() == block) {
                                List<ItemStack> outputs = SawInteraction.INSTANCE.getProducts(block, harvestMeta);
                                List<ItemStack> newOutputs = Lists.newArrayList();
                                if (outputs.size() == 3) {
                                    ItemStack planks = outputs.get(0).copy();
                                    planks.setCount((planks.getCount() / 2) + (fort ? world.rand.nextInt(2) : 0));
                                    int barkStack = fort ? outputs.get(1).getCount() + world.rand.nextInt(fortune) : outputs.get(1).getCount();

                                    ItemStack bark = new ItemStack(outputs.get(1).getItem(), barkStack, outputs.get(1).getItemDamage());
                                    int sawdustStack = fort ? 1 + world.rand.nextInt(fortune) : 1;
                                    ItemStack sawdust = new ItemStack(BWMItems.MATERIAL, sawdustStack, 22);
                                    newOutputs.add(planks);
                                    newOutputs.add(bark);
                                    newOutputs.add(sawdust);
                                }
                                evt.getDrops().remove(logStack);
                                evt.getDrops().addAll(newOutputs);
                                break;
                            }
                        }
                    }
                }
            }
        }

    }
}
