package com.nur.spamdec;


import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybinds {
	public static KeyBinding hello;
	 
    public static void register()
    {
    	System.out.println("KAVEH");
        hello = new KeyBinding("key.hello", Keyboard.KEY_V, "key.categories.tutorial");
 
        ClientRegistry.registerKeyBinding(hello);
    }
}
