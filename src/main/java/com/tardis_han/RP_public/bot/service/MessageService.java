package com.tardis_han.RP_public.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    
    public MessageService() {
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        // Assuming you have a way to access the bot instance, you can send the message here.
        // For example, you might want to use a static reference or another method to send the message.
    }

    
}
