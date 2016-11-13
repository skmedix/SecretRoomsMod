package com.github.abrarsyed.secretroomsmod.client.waila;

import com.github.abrarsyed.secretroomsmod.blocks.*;
import com.github.abrarsyed.secretroomsmod.client.ClientBlockLocation;
import com.github.abrarsyed.secretroomsmod.common.BlockLocation;
import com.github.abrarsyed.secretroomsmod.common.OwnershipManager;
import com.github.abrarsyed.secretroomsmod.common.SecretRooms;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WailaProvider implements IWailaDataProvider {

	public static void register(IWailaRegistrar registrar) {
		WailaProvider provider = new WailaProvider();

		registrar.registerStackProvider(provider, BlockSolidAir.class);
		registrar.registerStackProvider(provider, BlockTorchLever.class);
		registrar.registerStackProvider(provider, BlockCamoTrapDoor.class);

		registrar.registerBodyProvider(provider, BlockCamoWire.class);
		registrar.registerBodyProvider(provider, BlockCamoButton.class);
		registrar.registerBodyProvider(provider, BlockCamoLever.class);
		registrar.registerBodyProvider(provider, BlockTorchLever.class);
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler configHandler) {
		BlockPos pos = accessor.getPosition();
		BlockLocation loc = new ClientBlockLocation(accessor.getWorld(), pos.getX(), pos.getY(), pos.getZ());
		boolean owner = OwnershipManager.isOwner(accessor.getPlayer().getUniqueID(), loc);

		Block block = accessor.getBlock();
		if (block == SecretRooms.solidAir) {
			return new ItemStack(owner ? SecretRooms.solidAir : Blocks.AIR);
		} else if (block == SecretRooms.torchLever) {
			return new ItemStack(owner ? SecretRooms.torchLever : Blocks.TORCH);
		} else if (block == SecretRooms.camoTrapDoor) {
			// TODO: @null: Fix me
			return block.getPickBlock(block.getBlockState().getBaseState(), null, accessor.getWorld(), pos, accessor.getPlayer());
		}

		return null;
	}


	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		BlockPos pos = accessor.getPosition();
		BlockLocation loc = new ClientBlockLocation(accessor.getWorld(), pos.getX(), pos.getY(), pos.getZ());
		boolean owner = OwnershipManager.isOwner(accessor.getPlayer().getUniqueID(), loc);
		Block block = accessor.getBlock();

		if (!owner)
			return list;

		if (block instanceof BlockTorchLever || block instanceof BlockCamoLever || block instanceof BlockCamoButton) {
			boolean off = (accessor.getMetadata() & 8) == 0;
			if (block instanceof BlockCamoLever) {
				off = accessor.getMetadata() == 0;
			}

			list.add(LangUtil.translateG("hud.msg.state") + " : " + LangUtil.translateG(off ? "hud.msg.off" : "hud.msg.on"));
		} else if (block instanceof BlockCamoWire) {
			list.add(String.format(LangUtil.translateG("hud.msg.power") + " : " + accessor.getMetadata()));
		}

		return list;
	}

	@Override
	public List<String> getWailaHead(ItemStack paramItemStack, List<String> paramList, IWailaDataAccessor paramIWailaDataAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
		return paramList;
	}

	@Override
	public List<String> getWailaTail(ItemStack paramItemStack, List<String> paramList, IWailaDataAccessor paramIWailaDataAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
		return paramList;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		return tag;
	}
}
