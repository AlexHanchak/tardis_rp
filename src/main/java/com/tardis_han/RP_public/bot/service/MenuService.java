package com.tardis_han.RP_public.bot.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.tardis_han.RP_public.bot.MyTelegramBot;

import java.util.Arrays;

@Service
public class MenuService {

    private final MyTelegramBot myTelegramBot;
    
    public MenuService(@Lazy MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }
    
    
    public void sendStartMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Добро пожаловать! Выберите опцию:");
        message.setReplyMarkup(new InlineKeyboardMarkup(Arrays.asList(
                Arrays.asList(createUrlButton("На сайт", "https://hanchakweb.web.app")),
                Arrays.asList(createButton("Обо мне", "about_me")),
                // Arrays.asList(createButton("Задать вопрос", "ask_question")),
                Arrays.asList(createButton("Вебразработка и кибербезопасность", "web_and_security"))
        )));
        myTelegramBot.sendMessage(message);
    }

    public InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.setCallbackData(callbackData);
        return button;
    }

    public InlineKeyboardButton createUrlButton(String text, String url) {
        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.setUrl(url);
        return button;
    }

    // Other menu-related methods can be added here
}
