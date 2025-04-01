package com.tardis_han.RP_public.bot.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
// import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.List;

// import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.bots.AbsSender;


@Service
public class BotService {
    private final Map<Long, String> userMessages = new ConcurrentHashMap<>();
    private final AbsSender bot;

    public BotService(@Lazy AbsSender bot) {
        this.bot = bot;
    }

    public void executeMessage(SendMessage message) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void saveUserMessage(Long chatId, String username, String phone, String question) {
        System.out.println("📝 Сохраняем сообщение от " + chatId + ": " + question + "name" + username + "phone" + phone);
        // Здесь нужно сохранить username, phone и question в базу данных
        // Например, можно использовать какой-то DAO или репозиторий для сохранения данных
        // userMessages.put(chatId, question); // Это можно удалить, если не нужно хранить сообщения в памяти
    }

    public String getUserMessage(Long chatId) {
        System.out.println("📤 Ищем сообщение для chatId: " + chatId);
        System.out.println("🗂 Все сохраненные сообщения: " + userMessages);
        String message = userMessages.getOrDefault(chatId, "");
        System.out.println("📝 Получено сообщение: " + message);
        return message;
    }

    public ReplyKeyboardMarkup getPhoneKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        
        KeyboardButton phoneButton = new KeyboardButton("📞 Отправить номер");
        phoneButton.setRequestContact(true);
    
        KeyboardRow row = new KeyboardRow();
        row.add(phoneButton);
    
        keyboardMarkup.setKeyboard(List.of(row));
        return keyboardMarkup;
    }

    public void askForPhoneNumber(Long chatId) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText("Пожалуйста, отправьте ваш номер телефона, нажав кнопку ниже:");
    message.setReplyMarkup(getPhoneKeyboard());
    executeMessage(message);
    }
    
}
