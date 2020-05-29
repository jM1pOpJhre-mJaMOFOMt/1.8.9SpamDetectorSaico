package com.example.examplemod;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MyEventHandler {
	ArrayList<Msg> list = new ArrayList<Msg>();
	class Msg {
		private String mmessage;
		private long mtimestamp;
		public Msg(String message, long l) {
			mmessage=message;
			mtimestamp=l;
		}
		public String getMessage() {
			return mmessage;
		}
		public long getTimestamp() {
			return mtimestamp;
		}
	}
	int countOccurrences(ArrayList<Msg> Clist, String Cmsg) 
    { 
        int res = 0;
        for (int i=0;i<Clist.size(); i++) {
            if (Cmsg.equalsIgnoreCase((Clist.get(i).getMessage()))&&(Clist.get(i).getTimestamp()+20000)>=System.currentTimeMillis()) {
            	res++;
            } else if ((Clist.get(i).getTimestamp()+20000)<System.currentTimeMillis()) {
            	Clist.remove(firstOccurrenceOf(list,Clist.get(i).getMessage()));
            }
        }
        return res; 
    }
	int firstOccurrenceOf(ArrayList<Msg> Clist, String Cmsg) {
		int first=-1;
		for (int counter = 0; counter < Clist.size(); counter++) {
			if(Clist.get(counter).getMessage().equalsIgnoreCase(Cmsg)) {
				first=counter;
				break;
			}
		}
		return first;
	}
	boolean checkSpam(ArrayList<Msg> Clist) {
		boolean spam = false;
		for (int counter = 0; counter < Clist.size(); counter++) {
			Msg msg = Clist.get(counter);
			if(countOccurrences(Clist,msg.getMessage())>=3) {
				list.remove(firstOccurrenceOf(list,msg.getMessage()));
				spam=true;
			};	
	    }
		if(spam) {
			Minecraft.getMinecraft().thePlayer.playSound("random.anvil_land", 0.5F, 1.0F);
		}
		return spam;
	}
	@SubscribeEvent
    public void onOtherChat(ClientChatReceivedEvent event)
    {
		if(event.message.getUnformattedText().contains(":")&&event.message.getUnformattedText().contains("[")&&event.message.getUnformattedText().contains("]")) {
			if(event.message.getUnformattedText().contains("»")) {
				list.add(new Msg(event.message.getUnformattedText().substring(0,event.message.getUnformattedText().indexOf(":")+1)+"[item]",System.currentTimeMillis()));
			} else {
				list.add(new Msg(event.message.getUnformattedText(),System.currentTimeMillis()));
			}
			if(checkSpam(list)) {
				//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""+EnumChatFormatting.DARK_RED+EnumChatFormatting.BOLD+"(!) POSSIBLE SPAM BELOW (!)"));
			};
		}
    }
}
