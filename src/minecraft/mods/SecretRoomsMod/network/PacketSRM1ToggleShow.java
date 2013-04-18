package mods.SecretRoomsMod.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mods.SecretRoomsMod.SecretRooms;
import mods.SecretRoomsMod.common.BlockHolder;
import mods.SecretRoomsMod.common.TileEntityCamoFull;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketSRM1ToggleShow extends PacketSRMBase
{
	
	public static final String COLOR = "\u00a7e";
	
	public PacketSRM1ToggleShow()
	{
	}

	public PacketSRM1ToggleShow(ObjectInputStream stream) throws IOException
	{
	}

	@Override
	public void writeToStream(ObjectOutputStream stream) throws IOException
	{
	}

	@Override
	public int getID()
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void actionClient(World world, EntityPlayerMP player)
	{
		SecretRooms.displayCamo = !SecretRooms.displayCamo;

		if (SecretRooms.displayCamo)
		{
			player.addChatMessage(COLOR+"Camo blocks made secret");
		}
		else
		{
			player.addChatMessage(COLOR+"Camo blocks made obvious");
		}

		int rad = 20; // update radius
		world.markBlockRangeForRenderUpdate((int) player.posX - rad, (int) player.posY - rad, (int) player.posZ - rad, (int) player.posX + rad, (int) player.posY + rad, (int) player.posZ + rad);
	}

	@Override
	public void actionServer(World world, EntityPlayerMP player)
	{
		// nothing.
	}

}