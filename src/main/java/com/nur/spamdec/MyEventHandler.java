package com.nur.spamdec;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
		String msg = event.message.getUnformattedText();
		if(msg.contains(": /msg")||msg.contains(": /w")||msg.contains(": /r")||msg.contains(": /mail")||msg.contains(": /m")||msg.contains(": /t")||msg.contains(": /whisper")||msg.contains(": /emsg")||msg.contains(": /tell")||msg.contains(": /er")||msg.contains(": /reply")||msg.contains(": /ereply")||msg.contains(": /email")||msg.contains(": /action")||msg.contains(": /describe")||msg.contains(": /eme")||msg.contains(": /eaction")||msg.contains(": /edescribe")||msg.contains(": /etell")||msg.contains(": /ewhisper")||msg.contains(": /pm")) {
			return;
		}
		if(msg.contains(":")&&msg.contains("[")&&msg.contains("]")) {
			if(msg.contains("»")) {
				list.add(new Msg(msg.substring(0,msg.indexOf(":")+1)+"[item]",System.currentTimeMillis()));
			} else {
				list.add(new Msg(msg,System.currentTimeMillis()));
			}
			if(checkSpam(list)) {
				//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""+EnumChatFormatting.DARK_RED+EnumChatFormatting.BOLD+"(!) POSSIBLE SPAM BELOW (!)"));
			}
		}
    }
}
