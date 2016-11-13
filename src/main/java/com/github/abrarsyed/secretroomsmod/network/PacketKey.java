package com.github.abrarsyed.secretroomsmod.network;

import com.github.abrarsyed.secretroomsmod.common.SecretRooms;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketKey extends PacketBase {

	@Override
	public void encode(ByteArrayDataOutput output) {
	}

	@Override
	public void decode(ByteArrayDataInput input) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void actionClient(World world, EntityPlayer player) {
	}

	@Override
	public void actionServer(World world, EntityPlayerMP player) {
		if (FMLCommonHandler.instance().getSide().isServer())
			SecretRooms.proxy.onKeyPress(player.getUniqueID());
	}

}
