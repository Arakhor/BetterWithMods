package betterwithmods.common.blocks.mini;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMoulding extends BlockMini {
    public static final PropertyInteger ORIENTATION = createOrientation();

    public BlockMoulding(Material mat) {
        super(mat);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0).withProperty(ORIENTATION, 0));
    }


    public static PropertyInteger createOrientation() {
        return PropertyInteger.create("orientation", 0, 11);
    }

    @Override
    public int getMaxOrientation() {
        return 11;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return rotate(worldIn, pos, state, playerIn, ORIENTATION);
    }

    private final AxisAlignedBB[] bounds = new AxisAlignedBB[]{
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D),
            new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D),
            new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D),
            new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D),
            new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D),
            new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D),
    };

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int ori = state.getValue(ORIENTATION);
        if (ori > 11 || ori < 1)
            return bounds[0];
        return bounds[ori];
    }



    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ORIENTATION, meta % getMaxOrientation());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ORIENTATION);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE, ORIENTATION);
    }

    @Override
    public IBlockState getStateForAdvancedRotationPlacement(IBlockState defaultState, EnumFacing facing, float hitX, float hitY, float hitZ) {
        PropertyInteger facingProperty = ORIENTATION;
        IBlockState state = defaultState;
        float hitXFromCenter = hitX - 0.5F;
        float hitYFromCenter = hitY - 0.5F;
        float hitZFromCenter = hitZ - 0.5F;
        switch (facing.getAxis()) {
            case Y:
                int corner = getCorner(hitXFromCenter, hitZFromCenter);
                if (corner != -1) {
                    int[] corners = new int[]{11, 9, 8, 10};
                    state = state.withProperty(facingProperty, corners[corner]);
                } else if (hitYFromCenter > 0) {
                    if (isMax(hitXFromCenter, hitZFromCenter)) {
                        state = state.withProperty(facingProperty, ((hitXFromCenter > 0) ? 3 : 0));
                    } else {
                        state = state.withProperty(facingProperty, ((hitZFromCenter > 0) ? 2 : 1));
                    }
                } else {
                    if (isMax(hitXFromCenter, hitZFromCenter)) {
                        state = state.withProperty(facingProperty, ((hitXFromCenter > 0) ? 7 : 4));
                    } else {
                        state = state.withProperty(facingProperty, ((hitZFromCenter > 0) ? 6 : 5));
                    }
                }
                break;
            case X:
                corner = getCorner(hitYFromCenter, hitZFromCenter);
                if (corner != -1) {
                    int[] corners = new int[]{6, 5, 1, 2};
                    state = state.withProperty(facingProperty, corners[corner]);
                } else if (hitXFromCenter > 0) {
                    if (isMax(hitYFromCenter, hitZFromCenter)) {
                        state = state.withProperty(facingProperty, ((hitYFromCenter > 0) ? 4 : 0));
                    } else {
                        state = state.withProperty(facingProperty, ((hitZFromCenter > 0) ? 10 : 8));
                    }
                } else {
                    if (isMax(hitYFromCenter, hitZFromCenter)) {
                        state = state.withProperty(facingProperty, ((hitYFromCenter > 0) ? 7 : 3));
                    } else {
                        state = state.withProperty(facingProperty, ((hitZFromCenter > 0) ? 11 : 9));
                    }
                }
                break;
            case Z:
                corner = getCorner(hitYFromCenter, hitXFromCenter);
                if (corner != -1) {
                    int[] corners = new int[]{7, 4, 0,3};
                    state = state.withProperty(facingProperty, corners[corner]);
                } else if (hitZFromCenter > 0) {
                    if (isMax(hitXFromCenter, hitYFromCenter)) {
                        state = state.withProperty(facingProperty, ((hitXFromCenter > 0) ? 9 : 8));
                    } else {
                        state = state.withProperty(facingProperty, ((hitYFromCenter > 0) ? 5 : 1));
                    }
                } else {
                    if (isMax(hitXFromCenter, hitYFromCenter)) {
                        state = state.withProperty(facingProperty, ((hitXFromCenter > 0) ? 11 : 10));
                    } else {
                        state = state.withProperty(facingProperty, ((hitYFromCenter > 0) ? 6 : 2));
                    }
                }
                break;
            default:
                state = state.withProperty(facingProperty, facing.getOpposite().getIndex());
                break;
        }

        return state;
    }

    private int getCorner(float hitXFromCenter, float hitZFromCenter) {
        boolean positiveX = hitXFromCenter > 0, positiveZ = hitZFromCenter > 0;
        double x = Math.abs(hitXFromCenter), z = Math.abs(hitZFromCenter);
        if (x > 0.25 && z > 0.25) {
            if (positiveX && positiveZ)
                return 0;
            if (positiveX && !positiveZ)
                return 1;
            if (!positiveX && !positiveZ)
                return 2;
            if (!positiveX && positiveZ)
                return 3;
        }
        return -1;
    }
}
