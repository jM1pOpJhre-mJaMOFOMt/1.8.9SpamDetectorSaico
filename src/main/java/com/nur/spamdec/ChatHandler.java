package com.nur.spamdec;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatHandler {
    public static final ArrayList<Message> list = new ArrayList<Message>();
    public int delay = 10;

    public int countOccurrences(ArrayList<Message> Clist, String Cmsg) {
        int res = 0;
        for (int i = 0; i < Clist.size(); i++) {
            if (Cmsg.equalsIgnoreCase((Clist.get(i).getMessage())) && Clist.get(i).getTimestamp() + delay * 1000 >= System.currentTimeMillis()) {
                res++;
            } else if (Clist.get(i).getTimestamp() + delay * 1000 < System.currentTimeMillis()) {
                Clist.remove(firstOccurrenceOf(list, Clist.get(i).getMessage()));
            }
        }
        return res;
    }

    public int firstOccurrenceOf(ArrayList<Message> Clist, String Cmsg) {
        int first = -1;
        for (int counter = 0; counter < Clist.size(); counter++) {
            if (Clist.get(counter).getMessage().equalsIgnoreCase(Cmsg)) {
                first = counter;
                break;
            }
        }
        return first;
    }

    public boolean checkSpam(ArrayList<Message> Clist) {
        boolean spam = false;
        for (int counter = 0; counter < Clist.size(); counter++) {
            Message msg = Clist.get(counter);
            if (countOccurrences(Clist, msg.getMessage()) >= 3) {
                list.remove(firstOccurrenceOf(list, msg.getMessage()));
                spam = true;
            }
        }
        if (spam) {
            Minecraft.getMinecraft().thePlayer.playSound("random.anvil_land", 0.5F, 1.0F);
        }
        return spam;
    }

    @SubscribeEvent
    public void onOtherChat(ClientChatReceivedEvent event) {
        String msg = event.message.getUnformattedText();
        if (msg.contains("<SC>") || msg.contains("VL") || msg.toLowerCase().contains(": /msg") || msg.toLowerCase().contains(": /w") || msg.toLowerCase().contains(": /r") || msg.toLowerCase().contains(": /mail") || msg.toLowerCase().contains(": /m") || msg.toLowerCase().contains(": /t") || msg.toLowerCase().contains(": /whisper") || msg.toLowerCase().contains(": /emsg") || msg.toLowerCase().contains(": /tell") || msg.toLowerCase().contains(": /er") || msg.toLowerCase().contains(": /reply") || msg.toLowerCase().contains(": /ereply") || msg.toLowerCase().contains(": /email") || msg.toLowerCase().contains(": /action") || msg.toLowerCase().contains(": /describe") || msg.toLowerCase().contains(": /eme") || msg.toLowerCase().contains(": /eaction") || msg.toLowerCase().contains(": /edescribe") || msg.toLowerCase().contains(": /etell") || msg.toLowerCase().contains(": /ewhisper") || msg.toLowerCase().contains(": /pm")) {
            return;
        }
        if (msg.contains(":") && msg.contains("[") && msg.contains("]")) {
            if (msg.contains("�")) {
                list.add(new Message(msg.substring(0, msg.indexOf(":") + 1) + "[item]", System.currentTimeMillis()));
            } else {
                list.add(new Message(msg, System.currentTimeMillis()));
            }
            checkSpam(list);//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""+EnumChatFormatting.DARK_RED+EnumChatFormatting.BOLD+"(!) POSSIBLE SPAM BELOW (!)"));
        }
    }
}
