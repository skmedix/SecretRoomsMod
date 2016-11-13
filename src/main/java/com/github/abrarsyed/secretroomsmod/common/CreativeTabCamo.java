package com.github.abrarsyed.secretroomsmod.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabCamo extends CreativeTabs
{
	public CreativeTabCamo()
	{
		super(SecretRooms.MODID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem()
	{
		return SecretRooms.camoPaste;
	}

}
