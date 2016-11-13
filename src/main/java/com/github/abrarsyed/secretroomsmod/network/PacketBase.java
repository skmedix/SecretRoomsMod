package com.github.abrarsyed.secretroomsmod.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author abrarsyed
 *         This is the base packet type for tall the SecretRoomsMod network stuff.
 *         Any class that extends this should have an empty constructor, otherwise the network system will fail.
 */
public abstract class PacketBase
{
    public abstract void encode(ByteArrayDataOutput output);

    public abstract void decode(ByteArrayDataInput input);

    @SideOnly(Side.CLIENT)
    public abstract void actionClient(World world, EntityPlayer player);

    public abstract void actionServer(World world, EntityPlayerMP player);
}
