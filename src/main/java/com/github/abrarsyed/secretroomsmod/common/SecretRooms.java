package com.github.abrarsyed.secretroomsmod.common;

import com.github.abrarsyed.secretroomsmod.blocks.*;
import com.github.abrarsyed.secretroomsmod.items.ItemCamoDoor;
import com.github.abrarsyed.secretroomsmod.network.PacketManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;


/**
 * @author AbrarSyed
 */
@Mod(modid = SecretRooms.MODID, version = "@VERSION@", useMetadata = true,
		acceptableRemoteVersions = "@CHANGE_VERSION@", acceptedMinecraftVersions = "@MC_VERSION@", acceptableSaveVersions = "@CHANGE_VERSION@",
		dependencies = "after:malisisdoors")
public class SecretRooms {

	@SidedProxy(clientSide = "com.github.abrarsyed.secretroomsmod.client.ProxyClient", serverSide = "com.github.abrarsyed.secretroomsmod.common.ProxyCommon")
	public static ProxyCommon proxy;

	public static final String MODID = "secretroomsmod";

	@Mod.Instance(value = MODID)
	public static SecretRooms instance;

	// textures
	public static final String TEXTURE_ITEM_PASTE = MODID + ":CamoPaste";
	public static final String TEXTURE_ITEM_DOOR_WOOD = MODID + ":CamoDoorWood";
	public static final String TEXTURE_ITEM_DOOR_STEEL = MODID + ":CamoDoorSteel";

	public static final String TEXTURE_BLOCK_BASE = MODID + ":CamoBase";
	public static final String TEXTURE_BLOCK_STAIR = MODID + ":CamoStair";
	public static final String TEXTURE_BLOCK_CHEST = MODID + ":CamoChest";
	public static final String TEXTURE_BLOCK_DETECTOR = MODID + ":CamoDetector";
	public static final String TEXTURE_BLOCK_GATE = MODID + ":CamoGate";
	public static final String TEXTURE_BLOCK_LEVER = MODID + ":CamoLever";
	public static final String TEXTURE_BLOCK_REDSTONE = MODID + ":CamoRedstone";
	public static final String TEXTURE_BLOCK_BUTTON = MODID + ":CamoButton";

	public static final String TEXTURE_BLOCK_PLATE_PLAYER = MODID + ":CamoPlatePlayer";
	public static final String TEXTURE_BLOCK_PLATE_WOOD = MODID + ":CamoPlateWood";
	public static final String TEXTURE_BLOCK_PLATE_IRON = MODID + ":CamoPlateIron";
	public static final String TEXTURE_BLOCK_PLATE_GOLD = MODID + ":CamoPlateGold";

	public static final String TEXTURE_BLOCK_TORCH = MODID + ":TorchLever";

	public static final String TEXTURE_BLOCK_SOLID_AIR = MODID + ":SolidAir";
	public static final String TEXTURE_BLOCK_CLEAR = MODID + ":clear";

	// ore dict strings
	public static final String CAMO_PASTE = "camoPaste";

	// render IDs
	public static boolean displayCamo = true;
	public static int render3DId;
	public static int renderFlatId;

	// misc
	public static Block torchLever, oneWay;

	// doors and Trap-Doors
	public static Block camoTrapDoor;
	public static Block camoDoorWood, camoDoorIron;
	public static Item camoDoorWoodItem, camoDoorIronItem;

	// Camo Paste
	public static Item camoPaste;

	// FullCamo Stuff
	public static Block camoGhost;
	public static Block camoLever;
	public static Block camoCurrent;
	public static Block camoButton;
	public static Block camoGate, camoGateExt;
	public static Block camoPlateAll, camoPlatePlayer;
	public static Block camoPlateLight, camoPlateHeavy;
	public static Block camoStairs;
	public static Block camoChest;
	public static Block camoTrappedChest;
	public static Block camoLightDetector;

	public static Block solidAir;

	// creative tab
	public static CreativeTabs tab;

	// config stuff
	private boolean malisisCompat, wailaCompat;

	@Mod.EventHandler
	public void preLoad(FMLPreInitializationEvent e) {
		// config
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();
		malisisCompat = config.get("compat", "MalisisDoors", true).getBoolean();
		wailaCompat = config.get("compat", "WAILA", true).getBoolean();
		if (config.hasChanged()) {
			config.save();
		}

		MinecraftForge.EVENT_BUS.register(proxy);

		// make creative tab.
		tab = new CreativeTabCamo();

		torchLever = new BlockTorchLever(80).setRegistryName("TorchLever");

		// Camo oneWay
		oneWay = new BlockOneWay().setRegistryName("OneWayGlass");

		// gates
		camoGate = new BlockCamoGate().setRegistryName("CamoGate");
		camoGateExt = new BlockCamoDummy().setRegistryName("CamoDummy");

		//if (canUseMalsisDoors()) {
		//	MalisisDoorsCompat.preInit();
		//} else {
		// TrapDoor
		camoTrapDoor = new BlockCamoTrapDoor().setRegistryName("SecretTrapDoor");

		// doors, Iron AND Wood
		camoDoorWoodItem = new ItemCamoDoor(Material.WOOD).setUnlocalizedName("SecretWoodenDoorItem");
		camoDoorWood = new BlockCamoDoor(Material.WOOD).setRegistryName("SecretWoodenDoorBlock");
		camoDoorIronItem = new ItemCamoDoor(Material.IRON).setUnlocalizedName("SecretIronDoorItem");
		camoDoorIron = new BlockCamoDoor(Material.IRON).setRegistryName("SecretIronDoorBlock");
		//}

		// Camo Paste
		camoPaste = new Item().setUnlocalizedName("CamoflaugePaste").setCreativeTab(SecretRooms.tab);//.setTextureName(TEXTURE_ITEM_PASTE);

		// FullCamoBlocks
		camoGhost = new BlockCamoGhost().setRegistryName("GhostBlock");
		camoLever = new BlockCamoLever().setRegistryName("SecretLever");
		camoCurrent = new BlockCamoWire().setRegistryName("SecretRedstone");
		camoButton = new BlockCamoButton().setRegistryName("SecretButton");

		camoPlateAll = new BlockCamoPlate(false).setRegistryName("SecretPressurePlate");
		camoPlatePlayer = new BlockCamoPlate(true).setRegistryName("SecretPlayerPlate");
		camoPlateLight = new BlockCamoPlateWeighted(64).setRegistryName("SecretLightPlate");
		camoPlateHeavy = new BlockCamoPlateWeighted(640).setRegistryName("SecretHeavyPlate");

		camoStairs = new BlockCamoStair().setRegistryName("SecretStair");

		camoChest = new BlockCamoChest(false).setRegistryName("SecretChest");
		camoTrappedChest = new BlockCamoChest(true).setRegistryName("SecretTrappedChest");

		camoLightDetector = new BlockCamoLightDetector().setRegistryName("SecretLightDetector");

		solidAir = new BlockSolidAir(new MaterialFakeAir()).setRegistryName("SolidAir");

		// registers
		GameRegistry.registerBlock(torchLever, "TorchLever");
		GameRegistry.registerBlock(oneWay, "OneWayGlass");
		GameRegistry.registerBlock(camoGate, "CamoGate");
		GameRegistry.registerBlock(camoGateExt, "CamoDummy");

		GameRegistry.registerBlock(camoTrapDoor, "SecretTrapDoor");

		GameRegistry.registerBlock(camoDoorWood, "SecretWoodenDoorBlock");
		GameRegistry.registerItem(camoDoorWoodItem, "SecretWoodenDoorItem");
		GameRegistry.registerBlock(camoDoorIron, "SecretIronDoorBlock");
		GameRegistry.registerItem(camoDoorIronItem, "SecretWoodenIronItem");

		GameRegistry.registerItem(camoPaste, "CamoflaugePaste");
		OreDictionary.registerOre(CAMO_PASTE, camoPaste);

		GameRegistry.registerBlock(camoGhost, "GhostBlock");
		GameRegistry.registerBlock(camoLever, "SecretCamoLever");
		GameRegistry.registerBlock(camoCurrent, "SecretCamoRedstone");

		//GameRegistry.registerBlock(camoButton, ItemBlockCamoButton.class, "SecretCamoButton");

		GameRegistry.registerBlock(camoPlateAll, "SecretPressurePlate");
		GameRegistry.registerBlock(camoPlatePlayer, "SecretPlayerPlate");
		GameRegistry.registerBlock(camoPlateLight, "SecretLightPlate");
		GameRegistry.registerBlock(camoPlateHeavy, "SecretHeavyPlate");

		GameRegistry.registerBlock(camoStairs, "SecretStair");

		GameRegistry.registerBlock(camoChest, "SecretChest");
		GameRegistry.registerBlock(camoTrappedChest, "SecretTrappedChest");

		GameRegistry.registerBlock(camoLightDetector, "SecretLightDetector");

		GameRegistry.registerBlock(solidAir, "SolidAir");

		// Tile Entities
		GameRegistry.registerTileEntity(TileEntityCamo.class, "TE_CamoFull");
		GameRegistry.registerTileEntity(TileEntityCamoChest.class, "TE_CamoChest");
		GameRegistry.registerTileEntity(TileEntityCamoDetector.class, "TE_CamoDetector");
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent e) {
		PacketManager.init();

		// key Events
		proxy.loadKeyStuff();

		// ore dictionary
		OreDictionary.registerOre(CAMO_PASTE, camoPaste);

		// Renders
		proxy.loadRenderStuff();

		addrecipes();

		// ownership stuff
		OwnershipManager.init();

		// waila compat.
		if (wailaCompat) {
			FMLInterModComms.sendMessage("Waila", "register", "com.github.abrarsyed.secretroomsmod.client.waila.WailaProvider.register");
		}
	}

	@Mod.EventHandler
	public void registerCommand(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandShow());
	}

	@Mod.EventHandler
	public void registerCommand(FMLServerStoppingEvent e) {
		proxy.onServerStop(e);
	}

	// TODO: Malisis Support
	/* private boolean canUseMalsisDoors() {

	    if (!Loader.isModLoaded("malisisdoors") || !malisisCompat)
        {
            return false;
        }

        // get malsis doors version
        String version = Loader.instance().getIndexedModList().get("malisisdoors").getVersion();

        // check compatability
        if (version.startsWith("1.7.10-1.3.") || version.startsWith("1.7.10-1.4."))
        {
            return true;
        }

		return false;
	}*/

	public static void addrecipes() {
		ArrayList<IRecipe> recipes = new ArrayList<IRecipe>();

		// Camo gate
		recipes.add(new ShapedOreRecipe(camoGate, "#0#",
				"0A0",
				"#@#",
				'#', Blocks.PLANKS,
				'0', CAMO_PASTE,
				'@', Items.REDSTONE,
				'A', Items.ENDER_PEARL));

		// TorchLever
		recipes.add(new ShapedOreRecipe(torchLever, "#",
				"X",
				'#', Blocks.TORCH,
				'X', Items.REDSTONE));

		// CamoDoors
		recipes.add(new ShapelessOreRecipe(camoDoorWoodItem,
				CAMO_PASTE,
				Items.OAK_DOOR));
		recipes.add(new ShapelessOreRecipe(camoDoorIronItem,
				CAMO_PASTE,
				Items.IRON_DOOR));
		recipes.add(new ShapelessOreRecipe(camoTrapDoor,
				CAMO_PASTE,
				Blocks.TRAPDOOR));

		// CAMO_PASTE
		recipes.add(new ShapedOreRecipe(new ItemStack(camoPaste, 9), "XXX",
				"X0X",
				"XXX",
				'X', new ItemStack(Items.DYE, 1, OreDictionary.WILDCARD_VALUE),
				'0', Blocks.DIRT));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoPaste, 9), "XXX",
				"X0X",
				"XXX",
				'X', new ItemStack(Items.DYE, 1, OreDictionary.WILDCARD_VALUE),
				'0', Blocks.SAND));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoPaste, 9), "XXX",
				"X0X",
				"XXX",
				'X', new ItemStack(Items.DYE, 1, OreDictionary.WILDCARD_VALUE),
				'0', Items.CLAY_BALL));

		// Camo OneWay
		recipes.add(new ShapedOreRecipe(new ItemStack(oneWay, 9), "X00",
				"X00",
				"X00",
				'X', CAMO_PASTE,
				'0', Blocks.GLASS));
		recipes.add(new ShapedOreRecipe(new ItemStack(oneWay, 9), "00X",
				"00X",
				"00X",
				'X', CAMO_PASTE,
				'0', Blocks.GLASS));
		recipes.add(new ShapedOreRecipe(new ItemStack(oneWay, 9), "XXX",
				"000",
				"000",
				'X', CAMO_PASTE,
				'0', Blocks.GLASS));
		recipes.add(new ShapedOreRecipe(new ItemStack(oneWay, 9), "000",
				"000",
				"XXX",
				'X', CAMO_PASTE,
				'0', Blocks.GLASS));
		recipes.add(new ShapelessOreRecipe(oneWay, CAMO_PASTE, Blocks.GLASS));

		// CamoGhost
		recipes.add(new ShapedOreRecipe(new ItemStack(camoGhost, 4), "X0X",
				"0 0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoGhost, 4), "X0X",
				"0 0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)));

		// Camo-Redstone
		recipes.add(new ShapedOreRecipe(new ItemStack(camoCurrent, 4), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Items.REDSTONE));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoCurrent, 4), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Items.REDSTONE));

		// Camo-Lever
		recipes.add(new ShapedOreRecipe(new ItemStack(camoLever, 4), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.LEVER));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoLever, 4), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.LEVER));

		// Camo-Button Stone stuff
		recipes.add(new ShapedOreRecipe(new ItemStack(camoButton, 4, 0), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.STONE_BUTTON));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoButton, 4, 0), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.STONE_BUTTON));

		// Camo-Button Wood stuff
		recipes.add(new ShapedOreRecipe(new ItemStack(camoButton, 4, 1), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.WOODEN_BUTTON));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoButton, 4, 1), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.WOODEN_BUTTON));

		// pressure plates
		recipes.add(new ShapedOreRecipe(camoPlateAll, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.WOODEN_PRESSURE_PLATE));
		recipes.add(new ShapedOreRecipe(camoPlateAll, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.WOODEN_PRESSURE_PLATE));

		recipes.add(new ShapedOreRecipe(camoPlatePlayer, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.STONE_PRESSURE_PLATE));
		recipes.add(new ShapedOreRecipe(camoPlatePlayer, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.STONE_PRESSURE_PLATE));

		// weighted pressure plates
		recipes.add(new ShapedOreRecipe(camoPlateLight, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE));
		recipes.add(new ShapedOreRecipe(camoPlateLight, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				// new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)
				'@', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE));

		recipes.add(new ShapedOreRecipe(camoPlateHeavy, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE));
		recipes.add(new ShapedOreRecipe(camoPlateHeavy, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE));

		// CamoStairs
		recipes.add(new ShapedOreRecipe(new ItemStack(camoStairs, 4), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', "stairWood"));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoStairs, 4), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', "stairWood"));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoStairs, 4), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.STONE_STAIRS));
		recipes.add(new ShapedOreRecipe(new ItemStack(camoStairs, 4), "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.STONE_STAIRS));

		// CamoChests
		recipes.add(new ShapedOreRecipe(camoChest, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.CHEST));
		recipes.add(new ShapedOreRecipe(camoChest, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.CHEST));

		// Trapped Chests
		recipes.add(new ShapedOreRecipe(camoTrappedChest, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.TRAPPED_CHEST));
		recipes.add(new ShapedOreRecipe(camoTrappedChest, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.TRAPPED_CHEST));

		// Trapped Chests
		recipes.add(new ShapedOreRecipe(camoLightDetector, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', Items.ROTTEN_FLESH,
				'@', Blocks.DAYLIGHT_DETECTOR));
		recipes.add(new ShapedOreRecipe(camoLightDetector, "X0X",
				"0@0",
				"X0X",
				'X', CAMO_PASTE,
				'0', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
				'@', Blocks.DAYLIGHT_DETECTOR));

		// Solid Air
		recipes.add(new ShapelessOreRecipe(solidAir,
				CAMO_PASTE,
				Items.BLAZE_POWDER,
				Items.WATER_BUCKET));
		recipes.add(new ShapelessOreRecipe(solidAir,
				CAMO_PASTE,
				Items.BLAZE_ROD,
				Items.WATER_BUCKET));
		recipes.add(new ShapelessOreRecipe(solidAir,
				CAMO_PASTE,
				Items.MAGMA_CREAM,
				Items.WATER_BUCKET));
		recipes.add(new ShapelessOreRecipe(solidAir,
				CAMO_PASTE,
				Items.BLAZE_POWDER,
				Items.POTIONITEM));
		recipes.add(new ShapelessOreRecipe(solidAir,
				CAMO_PASTE,
				Items.BLAZE_ROD,
				Items.POTIONITEM));
		recipes.add(new ShapelessOreRecipe(solidAir,
				CAMO_PASTE,
				Items.MAGMA_CREAM,
				Items.POTIONITEM));

		// actually add the recipe
		for (IRecipe r : recipes) {
			GameRegistry.addRecipe(r);
		}
	}
}
