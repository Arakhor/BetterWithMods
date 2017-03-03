package betterwithmods.integration.mcmultipart;

import mcmultipart.api.multipart.IMultipart;
import mcmultipart.api.slot.IPartSlot;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.function.Function;

/**
 * Purpose:
 *
 * @author Tyler Marshall
 * @version 2/27/17
 */
public class MultipartProxy implements IMultipart {

    private final Block block;
    private final Function<IBlockState, IPartSlot> placementWrapper, worldWrapper;

    public MultipartProxy(Block block, Function<IBlockState, IPartSlot> placementWrapper, Function<IBlockState, IPartSlot> worldWrapper) {
        this.block = block;
        this.placementWrapper = placementWrapper;
        this.worldWrapper = worldWrapper;
    }

    public MultipartProxy(Block block, Function<IBlockState, IPartSlot> wrapper) {
        this(block,wrapper,wrapper);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public IPartSlot getSlotForPlacement(World world, BlockPos pos, IBlockState state, EnumFacing facing, float hitX, float hitY, float hitZ, EntityLivingBase placer) {
        return placementWrapper.apply(state);
    }

    @Override
    public IPartSlot getSlotFromWorld(IBlockAccess world, BlockPos pos, IBlockState state) {
        return worldWrapper.apply(state);
    }
}
