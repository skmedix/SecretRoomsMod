package com.github.abrarsyed.secretroomsmod.common;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ProxyCommon
{
    private HashMap<Integer, FakeWorld>   fakes;
    private HashSet<UUID>                 awaySet;

    public ProxyCommon()
    {
        fakes = new HashMap<Integer, FakeWorld>();
        awaySet = new HashSet<UUID>();
    }

    public void loadRenderStuff()
    {
        // client only
    }

    public void loadKeyStuff()
    {
        // client only...
    }

    public void onServerStop(FMLServerStoppingEvent e)
    {
        fakes.clear();
        awaySet.clear();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onWorldLoad(Load event)
    {
        int dim = event.getWorld().provider.getDimension();
        fakes.put(dim, new FakeWorld(event.getWorld()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onWorldUnLoad(Unload event)
    {
        int dim = event.getWorld().provider.getDimension();
        fakes.remove(dim);
    }

    public FakeWorld getFakeWorld(World world)
    {
        int dim = world.provider.getDimension();
        return fakes.get(dim);
    }

    public void onKeyPress(UUID uuid)
    {
        if (awaySet.contains(uuid))
        {
            awaySet.remove(uuid);
        }
        else
        {
            awaySet.add(uuid);
        }
    }

    public boolean getFaceTowards(UUID uuid)
    {
        return !awaySet.contains(uuid);
    }
    
    public boolean isOwner(IBlockAccess world, int x, int y, int z)
    {
        // no context here....
        return false;  // no your not the owner if I don't know who you are...
    }
}
