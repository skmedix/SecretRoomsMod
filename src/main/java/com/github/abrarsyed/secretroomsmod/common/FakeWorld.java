package com.github.abrarsyed.secretroomsmod.common;

import com.github.abrarsyed.secretroomsmod.api.BlockHolder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.HashMap;

public class FakeWorld implements IBlockAccess {
	private final World backend;
	private final HashMap<BlockPos, BlockHolder> overrideMap;

	public FakeWorld(World toCopy) {
		this.backend = toCopy;
		overrideMap = new HashMap<BlockPos, BlockHolder>();
	}

	// actual stuff...

	public void addOverrideBlock(int x, int y, int z, BlockHolder holder) {
		BlockPos position = new BlockPos(x, y, z);
		overrideMap.put(position, holder);
	}

	public void removeOverrideBlock(int x, int y, int z) {
		BlockPos position = new BlockPos(x, y, z);
		overrideMap.remove(position);
	}

	// overrides...


	@Nullable
	@Override
	public TileEntity getTileEntity(BlockPos pos) {
		if (overrideMap.containsKey(pos)) {
			return overrideMap.get(pos).getTileEntity(backend, pos.getX(), pos.getY(), pos.getZ());
		} else {
			return backend.getTileEntity(pos);
		}
	}

	@Override
	public IBlockState getBlockState(BlockPos pos) {
		if (overrideMap.containsKey(pos)) {
			return (IBlockState) overrideMap.get(pos).getBlock().getBlockState();
		} else {
			return backend.getBlockState(pos);
		}
	}

	@Override
	public boolean isAirBlock(BlockPos pos) {
		if (overrideMap.containsKey(pos)) {
			return overrideMap.get(pos).getBlock().isAir(getBlockState(pos), backend, pos);
		} else {
			return backend.isAirBlock(pos);
		}
	}

	// unnecessary overrides

	public int getHeight() {
		return backend.getHeight();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((backend == null) ? 0 : backend.hashCode());
		result = prime * result + ((overrideMap == null) ? 0 : overrideMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FakeWorld other = (FakeWorld) obj;
		if (backend == null) {
			if (other.backend != null)
				return false;
		} else if (!backend.equals(other.backend))
			return false;
		if (overrideMap == null) {
			if (other.overrideMap != null)
				return false;
		} else if (!overrideMap.equals(other.overrideMap))
			return false;
		return true;
	}

	@Override
	public int getCombinedLight(BlockPos pos, int lightValue) {
		return 0;
	}

	@Override
	public Biome getBiome(BlockPos pos) {
		return backend.getBiome(pos);
	}

	@Override
	public int getStrongPower(BlockPos pos, EnumFacing direction) {
		return backend.getStrongPower(pos, direction);
	}

	@Override
	public WorldType getWorldType() {
		return backend.getWorldType();
	}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
		return backend.isSideSolid(pos, side, _default);
	}
}
