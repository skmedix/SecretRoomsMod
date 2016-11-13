package com.github.abrarsyed.secretroomsmod.network;

import com.github.abrarsyed.secretroomsmod.common.SecretRooms;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketShowToggle extends PacketBase {
	private static final Style yellowStyle = new Style().setColor(TextFormatting.YELLOW);
	private static final ITextComponent SECRET = new TextComponentTranslation("message.secretroomsmod.commandShow.hide").setStyle(yellowStyle);
	private static final ITextComponent OBVIOUS = new TextComponentTranslation("message.secretroomsmod.commandShow.show").setStyle(yellowStyle);

	@Override
	public void encode(ByteArrayDataOutput output) {
	}

	@Override
	public void decode(ByteArrayDataInput input) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void actionClient(World world, EntityPlayer player) {
		SecretRooms.displayCamo = !SecretRooms.displayCamo;

		if (SecretRooms.displayCamo)
			player.addChatMessage(SECRET);
		else
			player.addChatMessage(OBVIOUS);

		int rad = 20; // update radius
		world.markBlockRangeForRenderUpdate((int) player.posX - rad, (int) player.posY - rad, (int) player.posZ - rad, (int) player.posX + rad, (int) player.posY + rad, (int) player.posZ + rad);
	}

	@Override
	public void actionServer(World world, EntityPlayerMP player) {
	}
}
