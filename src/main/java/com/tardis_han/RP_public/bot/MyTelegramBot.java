package com.tardis_han.RP_public.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(MyTelegramBot.class);
    private final String botUsername;
    private final String botToken;
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    public MyTelegramBot(@Value("${telegram.bot.token}") String botToken, 
                     @Value("${telegram.bot.username}") String botUsername,
                     MessageHandler messageHandler, CallbackHandler callbackHandler) {
        super(botToken);
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    @PostConstruct
    public void init() {
        callbackHandler.setMyTelegramBot(this);
    }    

    @PostConstruct
    public void registerBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            logger.info("✅ Бот успешно зарегистрирован!");
        } catch (TelegramApiException e) {
            logger.error("Ошибка при регистрации бота: {}", e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageHandler.processMessage(update);
        } else if (update.hasCallbackQuery()) {
            logger.info("🔥 Получен callback-запрос!");
            callbackHandler.processCallback(update);
        }
    }

    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Ошибка при отправке сообщения: {}", e.getMessage());
        }
    }
}
