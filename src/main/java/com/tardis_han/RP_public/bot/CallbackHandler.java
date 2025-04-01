package com.tardis_han.RP_public.bot;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.tardis_han.RP_public.bot.service.MenuService;
import com.tardis_han.RP_public.bot.service.MessageService;
import com.tardis_han.RP_public.bot.service.BotService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CallbackHandler {

    private static final Logger logger = LoggerFactory.getLogger(CallbackHandler.class);
    private final MessageService messageService;
    private final MenuService menuService;
    private MyTelegramBot myTelegramBot;
    private final BotService botService;

    public CallbackHandler(MessageService messageService, MenuService menuService, BotService botService) {
        this.botService = botService;
        this.menuService = menuService;
        this.messageService = messageService;
    }

    public void setMyTelegramBot(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    // public void handleTextMessage(Update update) {
    //     System.out.println("📝 HandleTextMessage вызван!");
    //     if (update.hasMessage() && update.getMessage().hasText()) {
    //         System.out.println("📝 зашли в иф с сообщением");
    //         Long chatId = update.getMessage().getChatId();
    //         String text = update.getMessage().getText();
            
    //         // Проверяем, если сообщение отправлено с блока send_question
    //         if (text.startsWith("send_question")) {
    //             String username = update.getMessage().getFrom().getUserName();
    //             String phone = getUserPhone(chatId);
    //             String question = text.substring(12); // Вырезаем "send_question" из сообщения
                
    //             // Отправляем сообщение в базу данных

    //             System.out.println("📝 Сохраняем сообщение от " + chatId + ": " + question + "name" + username + "phone" + phone);
    //             logQuestionToFile(chatId, username, phone, question);
    //             // botService.saveUserMessage(chatId, username, phone, question);
    //         }
            
    //         sendMessage(chatId, text);
    //         System.out.println("📥 Получено сообщение от " + chatId + ": " + text);
    //     }
    // }

    // private final Map<Long, String> userPhones = new ConcurrentHashMap<>();

    // public void handleContactMessage(Update update) {
    //     Long chatId = update.getMessage().getChatId();
    //     if (update.getMessage().hasContact()) {
    //         String phoneNumber = update.getMessage().getContact().getPhoneNumber();
    //         userPhones.put(chatId, phoneNumber);
    //         sendMessage(chatId, "Спасибо! Ваш номер сохранён.");
    //     } else {
    //         sendMessage(chatId, "Отправьте номер телефона через кнопку.");
    //     }
    // }

    // public String getUserPhone(Long chatId) {
    //     return userPhones.getOrDefault(chatId, "Неизвестно");
    // }
    

    public void processCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
    
        logger.info("📌 processCallback'{}'", callbackData);
    
        switch (callbackData) {
            case "about_me" -> sendMessageWithBack(chatId, 
                "I am a developer with 5 years of experience, specializing in Java, Python, JavaScript. In my spare time I do web development and cybersecurity.", 
                "main_menu");
            
            case "web_and_security" -> sendWebAndSecurityMenu(chatId);
            case "web" -> sendSubMenu(chatId, "Select a language:", "java", "python", "javascript");
            case "security" -> sendSubMenu(chatId, "Choose a topic:", "wifi", "wireshark", "aircrack-ng");
            case "main_menu" -> sendStartMenu(chatId);

            // case "ask_question" -> {
            //     chatId = update.getCallbackQuery().getMessage().getChatId(); // Убедись, что chatId правильно получен
            //     String username = update.getCallbackQuery().getFrom().getUserName();
            //     sendQuestionMessage(chatId, username);
            // }

            // case "send_question" -> {
            //     System.out.println("send_question вызван");
            
            //     chatId = update.getCallbackQuery().getMessage().getChatId();
            //     String username = update.getCallbackQuery().getFrom().getUserName();
            //     String question = botService.getUserMessage(chatId);
            //     String phone = getUserPhone(chatId);
            
            //     System.out.println("Получен вопрос: " + question);
            
            //     if (question == null || question.trim().isEmpty()) {
            //         SendMessage message = new SendMessage();
            //         message.setChatId(String.valueOf(chatId));
            //         message.setText("Вы не написали вопрос! Пожалуйста, введите его перед отправкой.");
            //         return;
            //     }
            
            //     // Логируем вопрос в файл
            //     logQuestionToFile(chatId, username, phone, question);
            
            //     // Сохраняем вопрос в БД (если нужно)
            //     botService.saveUserMessage(chatId, username, phone, question);
            
            //     SendMessage message = new SendMessage();
            //     message.setChatId(String.valueOf(chatId));
            //     message.setText("Ваш вопрос отправлен! Я свяжусь с вами в ближайшее время.");
            //     botService.executeMessage(message);
            
            //     sendInlineKeyboard(chatId, List.of(
            //         menuService.createButton("Вернуться в меню", "main_menu")
            //     ));
            // }
            
            case "java" -> sendMessageWithBack(chatId, "Java is a powerful object-oriented programming language widely used for web development and enterprise systems.", "web");
            case "python" -> sendMessageWithBack(chatId, "Python is a simple and powerful programming language used for web development, data science, and automation.", "web");
            case "javascript" -> sendMessageWithBack(chatId, "JavaScript is a frontend and backend programming language that allows you to create dynamic web applications.", "web");

            case "wifi" -> sendMessageWithBack(chatId, "Wi-Fi security includes WEP, WPA, and WPA2. Use complex passwords and encryption to protect your network.", "security");
            case "wireshark" -> sendMessageWithBack(chatId, "Wireshark is a powerful network traffic analyzer used for security diagnostics and testing.", "security");
            case "aircrack-ng" -> sendMessageWithBack(chatId, "Aircrack-ng is a suite of tools for Wi-Fi security testing, including WEP/WPA monitoring and cracking.", "security");

        
            default -> messageService.sendMessage(chatId, "Unknown command.");
        }
    }
    

    private void sendMessage(long chatId, String text) {
        messageService.sendMessage(chatId, text);
    }

    private void sendInlineKeyboard(long chatId, List<InlineKeyboardButton> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Collections.singletonList(buttons));
        
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(markup);
        
        myTelegramBot.sendMessage(message);
    }

    private void sendStartMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Welcome! Select an option:");

        message.setReplyMarkup(new InlineKeyboardMarkup(Arrays.asList(
                Arrays.asList(menuService.createUrlButton("To the website", "https://hanchakweb.web.app")),
                Arrays.asList(menuService.createButton("About me", "about_me")),
                // Arrays.asList(menuService.createButton("Задать вопрос", "ask_question")),
                Arrays.asList(menuService.createButton("Web development and cybersecurity", "web_and_security"))
        )));

        myTelegramBot.sendMessage(message);
    }

    // public void sendQuestionMessage(Long chatId, String username) {
    //     String text = "Привет, " + username + "! Напишите ваш вопрос и контактные данные (телефон или email).";
    
    //     SendMessage message = new SendMessage();
    //     message.setChatId(String.valueOf(chatId));
    //     message.setText(text);
    
    //     InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    //     markup.setKeyboard(List.of(
    //         List.of(menuService.createButton("Назад", "main_menu"))
    //     ));
    //     message.setReplyMarkup(markup);
    
    //     myTelegramBot.sendMessage(message);
    // }
    

    // Метод для логирования в файл
    // private void logQuestionToFile(Long chatId, String username, String phone, String question) {
    //     String filePath = "logs/questions.log"; // Путь к файлу логов
    //     String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    
    //     String logEntry = String.format("%s | chat_id: %d | user: %s | phone: %s | question: %s%n",
    //                                 timestamp, chatId, username, phone, question);

    //     try (FileWriter writer = new FileWriter(filePath, true)) {
    //         writer.write(logEntry);
    //     } catch (IOException e) {
    //         System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
    //     }
    // }

    
    

    private void sendWebAndSecurityMenu(long chatId) {
        SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(chatId));
    message.setText("Choose a section:");

    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    markup.setKeyboard(Arrays.asList(
        Arrays.asList(menuService.createButton("Web development", "web")),
        Arrays.asList(menuService.createButton("Cybersecurity", "security")),
        Arrays.asList(menuService.createButton("🔙 Back", "main_menu"))
    ));

    message.setReplyMarkup(markup);
    myTelegramBot.sendMessage(message);
    }

    private void sendSubMenu(long chatId, String text, String... options) {
        if (myTelegramBot == null) {
            logger.error("❌ Error: myTelegramBot in CallbackHandler == null!");
            return;
        }

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        // Создаём изменяемый список кнопок
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
    
        for (String option : options) {
            buttons.add(List.of(menuService.createButton(option, option)));
        }

    // Добавляем кнопку "Назад"
        buttons.add(List.of(menuService.createButton("🔙 Back", "web_and_security")));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(buttons);

        message.setReplyMarkup(markup);
        myTelegramBot.sendMessage(message);
    }

    private void sendMessageWithBack(long chatId, String text, String backCallback) {
        if (myTelegramBot == null) {
            logger.error("❌ Error: myTelegramBot in CallbackHandler == null!");
            return;
        }
    
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setReplyMarkup(new InlineKeyboardMarkup(List.of(
                List.of(menuService.createButton("🔙 Back", backCallback)) // 🔥 тут исправлено
        ))); 
    
        myTelegramBot.sendMessage(message);
    }
    
}
