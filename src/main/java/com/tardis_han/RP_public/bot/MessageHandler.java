package com.tardis_han.RP_public.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.tardis_han.RP_public.bot.service.MenuService;
import com.tardis_han.RP_public.bot.service.MessageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyTelegramBot.class);
    private final MessageService messageService;
    private final MenuService menuService;
    private MyTelegramBot myTelegramBot;

    public MessageHandler(MessageService messageService, @Lazy MenuService menuService) {
        this.menuService = menuService;
        this.messageService = messageService;
    }

    public void setMyTelegramBot(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void processMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        if ("/start".equals(messageText)) {
            menuService.sendStartMenu(chatId);
        } else {
            logger.info("Received unknown message: {}", messageText, chatId);
        }
    }

    
    
    // Other methods related to message handling can be added here
}
