package lykrast.defiledlands.common.block;

import java.util.Random;

import lykrast.defiledlands.common.init.ModBlocks;
import lykrast.defiledlands.common.world.biome.BiomeDefiled;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockConjuringAltar extends BlockCorrupted {
    public static final PropertyBool ACTIVE = PropertyBool.create("active");
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
    
	public BlockConjuringAltar(float hardness, float resistance) {
		super(Material.ROCK, SoundType.STONE, hardness, resistance, "pickaxe", 0);
	}
	
	public boolean isActive(IBlockAccess worldIn, BlockPos pos)
	{
		boolean flag1 = worldIn.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.up(2));
		boolean flag2 = worldIn.getBiome(pos) instanceof BiomeDefiled;
		return flag1 && flag2;
	}

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
    	return state.withProperty(ACTIVE, isActive(worldIn, pos));
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ModBlocks.stoneDefiled.getItemDropped(ModBlocks.stoneDefiled.getDefaultState(), rand, fortune);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ACTIVE});
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_)
    {
        return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

}
