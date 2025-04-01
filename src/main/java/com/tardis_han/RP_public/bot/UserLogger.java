package com.tardis_han.RP_public.bot;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserLogger {
    public static void logUserMessage(String username, String phone, String message) {
        String filename = username + (phone.isEmpty() ? "" : "_" + phone) + ".log";

        try {
            Files.createDirectories(Paths.get("logs"));
            FileWriter writer = new FileWriter("logs/" + filename, true);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
