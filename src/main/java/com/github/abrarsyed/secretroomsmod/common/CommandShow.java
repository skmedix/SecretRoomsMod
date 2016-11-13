package com.github.abrarsyed.secretroomsmod.common;

import com.github.abrarsyed.secretroomsmod.network.PacketManager;
import com.github.abrarsyed.secretroomsmod.network.PacketShowToggle;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AbrarSyed
 */
public class CommandShow extends CommandBase
{

	@Override
	public String getCommandName()
	{
		return "srm-show";
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	@SuppressWarnings("rawtypes")
    @Override
	public List getCommandAliases()
	{
		ArrayList<String> list = new ArrayList<String>();
		list.add("show");
		return list;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		PacketManager.sendToPlayer(new PacketShowToggle(), CommandBase.getCommandSenderAsPlayer(sender));
	}

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "";
    }
}