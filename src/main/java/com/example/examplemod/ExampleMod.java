package com.example.examplemod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "SpamDecSaico";
    public static final String VERSION = "1.1";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	Keybinds.register();
    	MinecraftForge.EVENT_BUS.register(new MyEventHandler());
    }
}
